package com.example.busreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import java.util.*;

import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;
import java.io.*;

public class MainActivity extends AppCompatActivity
{
    SQLiteDatabase userDB;
    Spinner s1,s2;
    Button b1;
    EditText e1;
    ImageButton home,list,profile;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_CODE_EXECUTED = "code_executed";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean codeExecuted = prefs.getBoolean(KEY_CODE_EXECUTED, false);

        if (!codeExecuted) {
            try
            {
                userDB = openOrCreateDatabase("User",MODE_PRIVATE,null);
                userDB.execSQL("CREATE TABLE IF NOT EXISTS user (user_name varchar, dob varchar, phone varchar);");
                File database1 = getApplicationContext().getDatabasePath("User");
                if (database1.exists())
                {
                    userDB.execSQL( "delete from user"+";" );
                    Log.e("Data Good","Table deletion Done in Main Activity");
                }
            }
            catch(Exception e)
            {
                Log.e("User ERROR ","DATABASE CREATION ERROR");
            }

            SharedPreferences preff = getApplicationContext().getSharedPreferences("Preff",MODE_PRIVATE);
            SharedPreferences.Editor editorf = preff.edit();
            editorf.putString("active", "no");
            editorf.commit();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_CODE_EXECUTED, true);
            editor.apply();
        }

        s1 = (Spinner) findViewById(R.id.source_spinner);
        s2 = (Spinner) findViewById(R.id.des_spinner);
        b1 = (Button) findViewById(R.id.search_btn);
        e1 = (EditText) findViewById(R.id.date_text);
        home = (ImageButton) findViewById(R.id.home);
        list = (ImageButton) findViewById(R.id.list);
        profile = (ImageButton) findViewById(R.id.profile);

        List <String> src = new ArrayList<String>();
        src.add("Vellore");
        src.add("Bangalore");
        src.add("Chennai");
        ArrayAdapter<String> da = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, src);
        da.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(da);

        List <String> des = new ArrayList<String>();
        des.add("Bangalore");
        des.add("Vellore");
        des.add("Chennai");
        ArrayAdapter<String> df = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, des);
        df.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(df);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String source = s1.getSelectedItem().toString();
                String des = s2.getSelectedItem().toString();
                String date = e1.getText().toString();

                //DATE FIELD VALIDATION

                String s1 = date.substring(0,2);
                String s2 = date.substring(3,5);
                String s3 = date.substring(6,10);

                int d = Integer.parseInt(s1);
                int m = Integer.parseInt(s2);
                int y = Integer.parseInt(s3);

                if(source == des) //CHECKING FOR SOURCE AND DESTINANTION
                {
                    Toast.makeText(getApplicationContext(),"Both Source and Destinantion cannot be same",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(), options_activity.class);
                    i.putExtra("source", source);
                    i.putExtra("des", des);

                    if((d>=01 && d<=31) && (m>=01 && m<=12) && (y>=2023)) //DATE FIELD VALIDATION
                    {
                        i.putExtra("date", date);
                        startActivity(i);
                    }
                    else //CHECKING FOR CORRECT DATE
                    {
                        Toast.makeText(MainActivity.this,"Enter Correct Date!",Toast.LENGTH_SHORT).show();
                    }
                }
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