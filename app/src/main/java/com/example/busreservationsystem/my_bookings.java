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
    import android.text.Editable;
    import android.text.TextWatcher;
    public class my_bookings extends AppCompatActivity
    {
        SQLiteDatabase bookingDB = null;
        String source, destination, company, time, price,date;
        TextView company3, source3, des3, date3, time3, price3, stat;
        ImageButton home,list,profile;
        EditText seats;
        Button b;
        int no_seats;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_bookings);
            home = (ImageButton) findViewById(R.id.home);
            list = (ImageButton) findViewById(R.id.list);
            profile = (ImageButton) findViewById(R.id.profile);

            company3 = (TextView)findViewById(R.id.company);
            source3 = (TextView)findViewById(R.id.from);
            des3 = (TextView)findViewById(R.id.des);
            date3 = (TextView)findViewById(R.id.date1);
            time3 = (TextView)findViewById(R.id.time);
            price3 = (TextView) findViewById(R.id.price);
            stat = (TextView) findViewById(R.id.status);
            seats = (EditText) findViewById(R.id.seats4);

            try
            {
                bookingDB = openOrCreateDatabase("Booking",MODE_PRIVATE,null);
                bookingDB.execSQL("CREATE TABLE IF NOT EXISTS book (destination varchar, source varchar, company varchar, time varchar, price varhcar, seats varchar, date varchar);");
                File database1 = getApplicationContext().getDatabasePath("Booking");
                if (database1.exists())
                {
                    Toast.makeText(getApplicationContext(),"Database created Successfully",Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {
                Log.e("Booking ERROR ","DATABASE CREATION ERROR");
            }

            b = (Button) findViewById(R.id.pay_btn);

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
            source = pref.getString("source",null);
            destination = pref.getString("destination",null);
            company = pref.getString("company",null);
            time = pref.getString("time",null);
            price = pref.getString("price",null);
            date = pref.getString("date",null);

            company3.setText(company);
            source3.setText(source);
            des3.setText(destination);
            date3.setText(date);
            time3.setText(time);
            price3.setText(price);

            seats.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                    try
                    {
                        String s1 = seats.getText().toString();
                        no_seats = Integer.parseInt(s1);
                        int p = Integer.parseInt(price.substring(1)); //To only get the integer value of the price
                        int sum = no_seats * p;
                        price3.setText("₹" + (String.valueOf(sum)));
                    }
                    catch(Exception e)
                    {
                        Log.e("Seats ERROR ","Error with conversion");
                    }
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            b.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    stat.setText("Status: Booking Confimed.");

                    bookingDB.execSQL( "Insert into book (destination, source, company, time, price, seats, date) values ('" + destination + "','" + source + "','" + company + "','" + time + "','" + price + "','" + (String.valueOf(no_seats)) + "','" + date + "');" );
                    Toast.makeText(my_bookings.this,"Data Added!", Toast.LENGTH_SHORT).show();

                    SharedPreferences preff = getApplicationContext().getSharedPreferences("Preff",MODE_PRIVATE);
                    SharedPreferences.Editor editorf = preff.edit();
                    editorf.remove("active");
                    editorf.putString("active", "no");
                    editorf.commit();
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