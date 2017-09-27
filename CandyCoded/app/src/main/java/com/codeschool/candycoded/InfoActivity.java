package com.codeschool.candycoded;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageView candyStoreImageView = (ImageView)findViewById(R.id.image_view_candy_store);
        Picasso.with(this).
                load("https://thecavenderdiary.files.wordpress.com/2013/07/hammonds-vintage-sign-over-the-candy.jpg").
                into(candyStoreImageView);


    }

    // ***
    // Task 2 - Launch the Google Maps Activity
    // ***
    // (1) create a method called ...
    public void createMapIntent(View view) {
        // (2) Create a Uri from the address
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801");
        // (3) Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        // PROBLEM - mapIntent is null
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        //Intent emptyIntent = new Intent();
        // (4) Make the Intent explicit by setting the Google Maps package
        // PROBLEM - get null pointer exception here
        mapIntent.setPackage("com.google.android.apps.maps");
        // (5) We will attempt to start an activity that can handle the Intent.  To do this create an if
        // statement, inside call mapIntent.resolveActivity(getPackageManager()) and make sure the result
        // is not equal to null
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // (6) Inside the if statement we can call startActivity() with our mapIntent
            startActivity(mapIntent);
        }
        // (7) Now we need to connect this method to the button click....
        // Go into activity_info.xml and add the following properties to the TextView with the id
        // text_view_address - android:clickable="true" and android:onClick="createMapIntent"
    }

    // ***
    // Task 3 - Launch the Phone Activity
    // ***
    // (1) create a method called ...
    public void createPhoneIntent(View view) {
        // (2) Create an Intent with ACTION_DIAL
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // (3) Use the Intent setData() method and pass in a URI of the telephone number 0123456789
        intent.setData(Uri.parse("tel:0123456789"));
        // (4) start the Activity with the Intent
        startActivity(intent);
        // (5) Now we need to connect this method to the button click....
        // Go into activity_info.xml and add the following properties to the TextView with the id
        // text_view_phone - android:clickable="true" and android:onClick="createPhoneIntent"
    }

}
