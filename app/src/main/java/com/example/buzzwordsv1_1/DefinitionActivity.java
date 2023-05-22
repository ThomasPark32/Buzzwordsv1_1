package com.example.buzzwordsv1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DefinitionActivity extends AppCompatActivity {
    private Buzzword theBuzzword;
    private String word;
    private ArrayList<String> definitions;
    private String allDefinitions;
    private ArrayList<String> headlines;
    private ArrayList<String> urls;
    private ArrayList<String> formattedHeadlines;


    /**
     * This method runs only once, getting the Buzzword given from the sending activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_definition);

        Intent tent = getIntent();
        word = tent.getStringExtra("Buzzword");
    }

    /**
     * This method runs after creation, handling definitions and headlines and displaying them.
     */
    @Override
    protected void onStart() {
        super.onStart();

        TextView title = findViewById(R.id.TheBuzzword);
        title.setText(word);
        handleDefinitions();
        handleHeadlines();

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

    /**
     * Pulls the headlines of the Buzzword from the Controller class and displays them in the respective box.
     */
    private void handleHeadlines(){
        formattedHeadlines = new ArrayList<String>();
        if (theBuzzword.isTrending()) {
            headlines = theBuzzword.getHeadlines();
            urls = theBuzzword.getURLs();
            for (int index = 0; index < headlines.size(); index++) {
                formattedHeadlines.add("<a href='" + urls.get(index) + "'>" + headlines.get(index) + "</a>");
            }
        } else {
            formattedHeadlines.add("No headlines available!");
            for (int k = 0; k < 4; k++) {
                formattedHeadlines.add("");
            }
        }
        // Send headlines to UI
        for (int number = 0; number < formattedHeadlines.size(); number++) {
            switch (number) {
                case 0:
                    TextView headlineBox1 = findViewById(R.id.BuzzwordHead1);
                    headlineBox1.setClickable(true);
                    headlineBox1.setMovementMethod(LinkMovementMethod.getInstance());
                    headlineBox1.setText(Html.fromHtml(formattedHeadlines.get(number),Html.FROM_HTML_MODE_COMPACT));
                    break;
                case 1:
                    TextView headlineBox2 = findViewById(R.id.BuzzwordHead2);
                    headlineBox2.setClickable(true);
                    headlineBox2.setMovementMethod(LinkMovementMethod.getInstance());
                    headlineBox2.setText(Html.fromHtml(formattedHeadlines.get(number),Html.FROM_HTML_MODE_COMPACT));
                    break;
                case 2:
                    TextView headlineBox3 = findViewById(R.id.BuzzwordHead3);
                    headlineBox3.setClickable(true);
                    headlineBox3.setMovementMethod(LinkMovementMethod.getInstance());
                    headlineBox3.setText(Html.fromHtml(formattedHeadlines.get(number),Html.FROM_HTML_MODE_COMPACT));
                    break;
                case 3:
                    TextView headlineBox4 = findViewById(R.id.BuzzwordHead4);
                    headlineBox4.setClickable(true);
                    headlineBox4.setMovementMethod(LinkMovementMethod.getInstance());
                    headlineBox4.setText(Html.fromHtml(formattedHeadlines.get(number),Html.FROM_HTML_MODE_COMPACT));
                    break;
                case 4:
                    TextView headlineBox5 = findViewById(R.id.BuzzwordHead5);
                    headlineBox5.setClickable(true);
                    headlineBox5.setMovementMethod(LinkMovementMethod.getInstance());
                    headlineBox5.setText(Html.fromHtml(formattedHeadlines.get(number),Html.FROM_HTML_MODE_COMPACT));
                    break;
                default:
                    // Display error message; can not find headline box.
                    Context context = getApplicationContext();
                    String text = "Error: Error in sending headlines.";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
            }
        }
    }
}