package com.example.buzzwordsv1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    public void goToDefinition(View v) {
        Intent tent = new Intent(this, DefinitionActivity.class);
        startActivity(tent);
    }

    public void goToSearch(View v) {
        EditText searchTextBox = findViewById(R.id.searchTextBox);
        String query = searchTextBox.getText().toString();

        if (query.isEmpty()) {
            searchTextBox.setHint("Please type a word.");
        } else {
            Intent tent = new Intent(this, SearchResultsActivity.class);
            tent.putExtra("query",query);
            startActivity(tent);
        }
    }
}