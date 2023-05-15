package com.example.buzzwordsv1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class ContactUsActivity extends AppCompatActivity {
    /**
     * Runs this method once at the creation of this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_us);
    }

    /**
     * Quits the current activity and goes back to the previous screen.
     * @param v the view
     */
    public void returnToMain(View v){
        finish();
    }
}