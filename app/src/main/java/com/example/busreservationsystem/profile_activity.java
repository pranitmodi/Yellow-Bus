package com.example.busreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import android.database.Cursor;
import android.database.sqlite.*;

public class profile_activity extends AppCompatActivity
{
    SQLiteDatabase userDB;
    TextView name1, dob, phone;
    ImageButton home,list,profile;
    EditText e1;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        home = (ImageButton) findViewById(R.id.home);
        list = (ImageButton) findViewById(R.id.list);
        profile = (ImageButton) findViewById(R.id.profile);
        name1 = (TextView) findViewById(R.id.name1);
        dob = (TextView) findViewById(R.id.dob1);
        phone = (TextView) findViewById(R.id.phone2);
        e1 = (EditText) findViewById(R.id.feedback);
        b = (Button) findViewById(R.id.feedback_btn);

        userDB = openOrCreateDatabase("User",MODE_PRIVATE,null);
        Cursor cursor = userDB.rawQuery("select * from user",null);
        int name_column = cursor.getColumnIndex("user_name");
        int dob_column = cursor.getColumnIndex ("dob");
        int phone_column = cursor.getColumnIndex("phone");

        cursor.moveToFirst();
        String userlist="";

        String name_value,dob_value,phone_value;

        if ((cursor!=null) && (cursor.getCount()>0))
        {
            do
            {
                name_value = cursor.getString(name_column);
                dob_value = cursor.getString(dob_column);
                phone_value = cursor.getString(phone_column);

            }while(cursor.moveToNext());

            name1.setText(name_value);
            dob.setText(dob_value);
            phone.setText(phone_value);
        }
        else
        {
            Toast.makeText(this,"No data Record", Toast.LENGTH_LONG);
        }

//        SharedPreferences pref=getApplicationContext().getSharedPreferences("MyPref",0);
//        SharedPreferences.Editor e=pref.edit();
//        String name=pref.getString("name",null);
//        String d=pref.getString("dob",null);
//        int ph= pref.getInt("phone",-1);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                e1.setText("Feedback Submitted");
            }
        });

        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open_home();
            }
        });

        list.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open_list();
            }
        });

        profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open_profile();
            }
        });
    }
    public void open_list()
    {
        Intent i1 = new Intent(this, my_bookings.class);
        startActivity(i1);
    }
    public void open_profile()
    {
        Intent i2 = new Intent(this, profile_activity.class);
        startActivity(i2);
    }
    public void open_home()
    {
        Intent i3 = new Intent(this, MainActivity.class);
        startActivity(i3);
    }
}