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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    /**
     * On start of this activity, this method will run once.
     */
    @Override
    protected void onStart() {
        super.onStart();
        readBuzzwordDataFB();
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

    /**
     * Reads from Firebase Realtime Storage to obtain Buzzwords and populate Controller class.
     */
    private void readBuzzwordDataFB(){
        // Global controller class object
        final Controller aController = (Controller) getApplicationContext();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Trending");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    String bruh = dataSnapshot.child("/" + i + "/word").getValue().toString();
                    Log.d("KeepItUpBaby", "Buzzword: " + bruh);
                    ArrayList<String> ding = (ArrayList<String>) dataSnapshot.child("/"+ i +"/definitions").getValue();
                    Buzzword buddy = new Buzzword(bruh);
                    if (ding != null) {
                        for (int k = 0; k < ding.size(); k++) {
                            Log.d("KeepItUpBaby", "Definition: " + ding.get(k));
                            buddy.addDefinition(ding.get(k));
                        }
                    }
                    aController.addBuzzwords(buddy);
                }
                for (Buzzword buzzy : aController.getBuzzwords()) {
                    Log.d("KeepItUpBaby", "Buzzword ToString: " + buzzy.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("KeepItUpBaby", "Failed to read value.", error.toException());
            }
        });
    }
}