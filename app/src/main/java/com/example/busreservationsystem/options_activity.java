package com.example.busreservationsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import java.util.*;

import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;
import java.io.*;
import android.content.SharedPreferences;

public class options_activity extends AppCompatActivity
{
    SQLiteDatabase userDB;
    ImageButton home,list,profile;
    TextView journey,date,timing1,company1,seats1,price1,timing2,company2,seats2,price2;
    RadioButton rb1,rb2;
    String company3,timing3,price3;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        journey = (TextView) findViewById(R.id.journey);
        date = (TextView) findViewById(R.id.date);
        timing1 = (TextView) findViewById(R.id.timing1);
        timing2 = (TextView) findViewById(R.id.timing2);
        company1 = (TextView) findViewById(R.id.company1);
        company2 = (TextView) findViewById(R.id.company2);
        seats1 = (TextView) findViewById(R.id.seats1);
        seats2 = (TextView) findViewById(R.id.seats2);
        price1 = (TextView) findViewById(R.id.price1);
        price2 = (TextView) findViewById(R.id.price2);

        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        b = (Button) findViewById(R.id.confirm_btn);

        home = (ImageButton) findViewById(R.id.home);
        list = (ImageButton) findViewById(R.id.list);
        profile = (ImageButton) findViewById(R.id.profile);

        //Using Intent object to get the values
        Intent i = getIntent();
        String source = i.getStringExtra("source");
        String des = i.getStringExtra("des");
        String d = i.getStringExtra("date");

        //Journey and Date selected in previous activity
        String j = source + " to " + des;
        journey.setText(j);
        date.setText(d);

        //Different Values
        if(source.equals("Vellore") && des.equals("Chennai"))
        {
            timing1.setText("12:15 - 14:30");
            timing2.setText("19:45 - 22:10");
            seats1.setText("45 Seats");
            seats2.setText("26 Seats");
            price1.setText("₹865");
            price2.setText("₹765");
        }
        else if(source.equals("Vellore") && des.equals("Bangalore"))
        {
            timing1.setText("12:15 - 14:30");
            timing2.setText("19:45 - 22:10");
            seats1.setText("35 Seats");
            seats2.setText("15 Seats");
            price1.setText("₹1065");
            price2.setText("₹1205");
        }
        else if(source.equals("Chennai") && des.equals("Vellore"))
        {
            timing1.setText("15:00 - 17:30");
            timing2.setText("22:30 - 1:00");
            seats1.setText("31 Seats");
            seats2.setText("8 Seats");
            price1.setText("₹865");
            price2.setText("₹765");
        }
        else if(source.equals("Chennai") && des.equals("Bangalore"))
        {
            timing1.setText("12:15 - 15:30");
            timing2.setText("19:45 - 23:10");
            seats1.setText("44 Seats");
            seats2.setText("16 Seats");
            price1.setText("₹1245");
            price2.setText("₹1209");
        }
        else if(source.equals("Bangalore") && des.equals("Chennai"))
        {
            timing1.setText("12:15 - 15:30");
            timing2.setText("19:45 - 23:10");
            seats1.setText("44 Seats");
            seats2.setText("16 Seats");
            price1.setText("₹1245");
            price2.setText("₹1209");
        }
        else if(source.equals("Bangalore") && des.equals("Vellore"))
        {
            timing2.setText("4:15 - 7:30");
            timing1.setText("8:45 - 13:10");
            seats1.setText("40 Seats");
            seats2.setText("46 Seats");
            price1.setText("₹1065");
            price2.setText("₹1205");
        }

        //Validation of Radio Button
        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                    rb2.setChecked(false);
                    company3 = company1.getText().toString();
                    price3 = price1.getText().toString();
                    timing3 = timing1.getText().toString();
            }
        });

        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                    rb1.setChecked(false);
                    company3 = company2.getText().toString();
                    price3 = price2.getText().toString();
                    timing3 = timing2.getText().toString();
            }
        });

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Validation of the Selected option after clicking the button
                if(rb1.isChecked() == false && rb2.isChecked() == false)
                {
                    Toast.makeText(getApplicationContext(),"Select an option!",Toast.LENGTH_SHORT).show();
                }
                else if(rb1.isChecked() == true || rb2.isChecked() == true)
                {
                    AlertDialog.Builder b=new AlertDialog.Builder(options_activity.this);
                    b.setMessage("Confirm about your selection?");
                    b.setCancelable(false);

                    b.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("source", source);
                                    editor.putString("destination", des);
                                    editor.putString("company", company3);
                                    editor.putString("time", timing3);
                                    editor.putString("price", price3);
                                    editor.putString("date",d);
                                    editor.commit();

//                                    userDB = openOrCreateDatabase("User",MODE_PRIVATE,null);
//                                    Cursor cursor = userDB.rawQuery("select * from user",null);
//                                    cursor.moveToFirst();

                                    SharedPreferences preff = getApplicationContext().getSharedPreferences("Preff",MODE_PRIVATE);
                                    String res = preff.getString("active", "no");

                                    // So that once a user has verified himself and created his account, he wont be diverted to Sign In page
                                    if (res == "yes")
                                    {
                                        open_list();
                                    }
                                    else
                                    {
                                        open_login();
                                    }
                                    dialog.cancel();
                                }
                            });

                    b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"Action Cancelled",Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

                    AlertDialog ad=b.create();
                    ad.show();
                }
                //VALIDATE ALL THE INPUTS
                //SEND THE SELECTED ITEMS TO THE FINAL SCREEN OR MY BOOKINGS
                //ADD A DIALOG ALERT BOX TO ASK IF THE SELECTED PREFERENCE IS CONFIRMED
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
    public void open_login()
    {
        Intent i4 = new Intent(this, login_activity.class);
        startActivity(i4);
    }
}