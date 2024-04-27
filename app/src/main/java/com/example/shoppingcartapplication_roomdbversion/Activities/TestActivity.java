package com.example.shoppingcartapplication_roomdbversion.Activities;
//---------------------------------------------------------------------------------------//

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingcartapplication_roomdbversion.R;

//---------------------------------------------------------------------------------------//
public class TestActivity extends AppCompatActivity {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "TestActivity";

    //---------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate(Bundle savedInstanceState) " + savedInstanceState);

        setContentView(R.layout.activity_test);
    }
    //---------------------------------------------------------------------------------------//
}