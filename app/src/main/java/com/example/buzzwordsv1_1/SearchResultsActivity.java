package com.example.buzzwordsv1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_results);

        // Get the Intent that started this activity and extract the string to display

        Intent tent = getIntent();
        String query = tent.getStringExtra("query");

        TextView SearchedWord = findViewById(R.id.SearchedWord);
        SearchedWord.setText(query);

        TextView SearchResult1Title = findViewById(R.id.SearchResult1Title);
        SearchResult1Title.setText("Similar word to " + query);

        TextView SearchResult2Title = findViewById(R.id.SearchResult2Title);
        SearchResult2Title.setText("Another similar word to " + query);
    }
    /**
     * Quits the current activity and goes back to the previous screen (the main activity).
     * @param v the view
     */
    public void returnToMain(View v){
        finish();
    }
    /**
     * Creates a new intent and starts the definition activity.
     * @param v the view
     */
    public void goToDefinition(View v) {
        Intent tents = new Intent(this, DefinitionActivity.class);
        startActivity(tents);
    }
}