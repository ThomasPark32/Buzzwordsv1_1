package com.example.buzzwordsv1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
    }
    /**
     * Quits the current activity and goes back to the previous screen (the main activity).
     * @param v the view
     */
    public void returnToMain(View v){
        finish();
    }
}