package com.example.buzzwordsv1_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    /**
     * Creates a new intent and starts the definition activity.
     * @param v the view
     */
    public void goToDefinition(View v) {
        Intent tent = new Intent(this, DefinitionActivity.class);
        startActivity(tent);
    }
    /**
     * Creates a new intent and starts the settings activity.
     * @param v the view
     */
    public void goToSettings(View v) {
        Intent tent = new Intent(this, SettingsActivity.class);
        startActivity(tent);
    }
    /**
     * Creates a new intent and starts the about activity.
     * @param v the view
     */
    public void goToAbout(View v) {
        Intent tent = new Intent(this, AboutActivity.class);
        startActivity(tent);
    }
    /**
     * Creates a new intent and starts the contact us activity.
     * @param v the view
     */
    public void goToContactUs(View v) {
        Intent tent = new Intent(this, ContactUsActivity.class);
        startActivity(tent);
    }
    /**
     * Creates a new intent, pulls the search query in the text box, then gives this data to the search activity. If the box is empty, it will display a new hint message.
     * @param v the view
     */
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