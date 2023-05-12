package com.example.buzzwordsv1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

public class DefinitionActivity extends AppCompatActivity {
    public Buzzword theBuzzword;
    public String word;
    public ArrayList<String> definitions;
    public String allDefinitions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_definition);

        Intent tent = getIntent();
        word = tent.getStringExtra("Buzzword");
        Log.d("KeepItUpBaby", "Word is " + word + ".");
    }
    @Override
    protected void onStart() {
        super.onStart();

        TextView title = findViewById(R.id.TheBuzzword);
        title.setText(word);
        handleDefinitions();
    }
    /**
     * Quits the current activity and goes back to the previous screen.
     * @param v the view
     */
    public void returnToMain(View v){
        finish();
    }

    /**
     * Pulls the definitions of the Buzzword from the Controller class and displays them in the respective box.
     */
    private void handleDefinitions() {
        // Global controller class object
        final Controller aController = (Controller) getApplicationContext();
        int index = aController.findBuzzword(word);
        theBuzzword = aController.getAllBuzzwords().get(index);
        definitions = theBuzzword.getDefinitions();

        allDefinitions = "";
        for (String str : definitions) {
            allDefinitions += "\u2022 " + str + "\n" + "\n";
        }
        if (allDefinitions.isEmpty()) {
            allDefinitions = "No definitions available, please contact us!";
        }
        TextView definitionBox = findViewById(R.id.BuzzwordDef);
        definitionBox.setText(allDefinitions);
    }
}