package com.example.busreservationsystem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.*;
import java.util.concurrent.TimeUnit;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;
import java.io.*;

public class login_activity extends AppCompatActivity
{
    SQLiteDatabase userDB = null;
    EditText fullname, dob, mPhoneNumberEditText, mOTPEditText;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    String name,dob1,phone;
    Button mSendOTPButton,mVerifyOTPButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        fullname = (EditText) findViewById(R.id.fullname);
        dob = (EditText) findViewById(R.id.dob);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phone1);
        mSendOTPButton = (Button) findViewById(R.id.next_btn);
        mOTPEditText = (EditText) findViewById(R.id.OTP);
        mVerifyOTPButton = (Button) findViewById(R.id.final_btn);

        try
        {
            userDB = openOrCreateDatabase("User",MODE_PRIVATE,null);
            userDB.execSQL("CREATE TABLE IF NOT EXISTS user (user_name varchar, dob varchar, phone varchar);");
            File database1 = getApplicationContext().getDatabasePath("User");
            if (database1.exists())
            {
                Toast.makeText(getApplicationContext(),"Database created Successfully",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Log.e("User ERROR ","DATABASE CREATION ERROR");
        }

        mSendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = fullname.getText().toString();
                dob1 = dob.getText().toString();
                phone = mPhoneNumberEditText.getText().toString();

                String s1 = dob1.substring(0,2);
                String s2 = dob1.substring(3,5);
                String s3 = dob1.substring(6,10);

                int d = Integer.parseInt(s1);
                int m = Integer.parseInt(s2);
                int y = Integer.parseInt(s3);

                if((d>=01 && d<=31) && (m>=01 && m<=12) && (y>=1950 && y<=2023)) //DATE FIELD VALIDATION
                {
                    String phoneNumber = mPhoneNumberEditText.getText().toString();
                    if (TextUtils.isEmpty(phoneNumber))
                    {
                        Toast.makeText(login_activity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    sendVerificationCode(phoneNumber);
                }
                else
                {
                    Toast.makeText(login_activity.this,"Enter Correct DOB!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mVerifyOTPButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String code = mOTPEditText.getText().toString();
                if (TextUtils.isEmpty(code))
                {
                    Toast.makeText(login_activity.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                userDB.execSQL( "Insert into user (user_name, dob, phone) values ('" + name + "','" + dob1 + "','" + phone + "');" );

                SharedPreferences preff = getApplicationContext().getSharedPreferences("Preff",MODE_PRIVATE);
                SharedPreferences.Editor editorf = preff.edit();
                editorf.putString("active", "yes");
                editorf.commit();

                Toast.makeText(login_activity.this,"Data Added!", Toast.LENGTH_SHORT).show();
                verifyCode(code);

            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(login_activity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        mVerificationId = verificationId;
                        mResendToken = forceResendingToken;
                        Toast.makeText(login_activity.this, "Verification code sent", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(login_activity.this, "Verification successful", Toast.LENGTH_SHORT).show();
                            open_profile();
                            // Do something here after the user has been verified
                        }
                        else
                        {
                            Toast.makeText(login_activity.this, "Verification failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void open_profile()
    {
        Intent i2 = new Intent(this, profile_activity.class);
        startActivity(i2);
    }
}