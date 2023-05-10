package com.example.buzzwordsv1_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // Determines if the Buzzword list has been updated (i.e. if it is a new day)
    boolean newDay = true;
    // Currently trending buzzwords that are accessible from the main menu
    Buzzword trending1;
    Buzzword trending2;
    Buzzword trending3;
    // Word of the day
    Buzzword wotd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        readBuzzwordDataFB();
        Log.d("YAHOO", "I'M FINNA READ THE FIREBASE!!!!!");
    }

    /**
     * Creates a new intent, finds which button was pressed, gets the word associated with the button, and starts the definition activity.
     * @param v the view
     */
    public void goToDefinition(View v) {
        String name = "";
        TextView box = null;
        int id = v.getId();
        // Check which button is being pressed, then pass in buzzword.
        if (id == R.id.SeeMoreTrending1) {
            box = findViewById(R.id.TrendingWord1Txt);
        } else if (id == R.id.SeeMoreTrending2) {
            box = findViewById(R.id.TrendingWord2Txt);
        } else if (id == R.id.SeeMoreTrending3) {
            box = findViewById(R.id.TrendingWord3Txt);
        } else if (id == R.id.SeeMoreWotd) {
            box = findViewById(R.id.wotdTxt);
        } else {
            // Display error message; can not find button that was pressed.
            Context context = getApplicationContext();
            String text = "Error: Can not determine button pressed.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        name = box.getText().toString();
        // Start definition activity, pass in Buzzword
        Intent tent = new Intent(this, DefinitionActivity.class);
        tent.putExtra("Buzzword",name);
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
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Trending");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            // Global controller class object
            final Controller aController = (Controller) getApplicationContext();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                // Loop over all children in the trending tab
                for (int index = 0; index < dataSnapshot.getChildrenCount(); index++) {
                    // Get buzzword Firebase word
                    String str = dataSnapshot.child("/" + index + "/word").getValue().toString();
                    Log.d("KeepItUpBaby", "Buzzword: " + str);
                    // Get ArrayList from Firebase definitions
                    ArrayList<String> definitions = (ArrayList<String>) dataSnapshot.child("/"+ index +"/definitions").getValue();
                    // Make new buzzword object with word
                    Buzzword word = new Buzzword(str);
                    // Add definitions to buzzword
                    if (definitions != null) {
                        for (int k = 0; k < definitions.size(); k++) {
                            word.addDefinition(definitions.get(k));
                        }
                    }
                    // Add buzzword object to global Controller object
                    aController.addBuzzwords(word);
                }
                // Choose Word of the Day, if it is a new day
                if (newDay == true) {
                    chooseWOTD(aController.getBuzzwords().size());
                }
                // Display buzzwords to main activity screen
                sendBuzzwords();
                // Set the newDay boolean to false
                newDay = false;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("KeepItUpBaby", "Failed to read value.", error.toException());
            }
        });
    }
    /**
     * Sends top 3 trending buzzwords to UI to be displayed
     */
    public void sendBuzzwords(){
        // Global controller class object
        final Controller aController = (Controller) getApplicationContext();
        // Get first 3 trending buzzword objects from global Controller object
        trending1 = aController.getBuzzword(0);
        trending2 = aController.getBuzzword(1);
        trending3 = aController.getBuzzword(2);
        // Set titles on main activity with first 3 trending words
        TextView title1 = findViewById(R.id.TrendingWord1Txt);
        title1.setText(capitalized(trending1.getBuzzword()));
        TextView title2 = findViewById(R.id.TrendingWord2Txt);
        title2.setText(capitalized(trending2.getBuzzword()));
        TextView title3 = findViewById(R.id.TrendingWord3Txt);
        title3.setText(capitalized(trending3.getBuzzword()));
        // Set definitions on main activity with first 3 trending definitions
        TextView box1 = findViewById(R.id.TrendingWord1Def);
        box1.setText(checkMultipleDefinitions(trending1));
        TextView box2 = findViewById(R.id.TrendingWord2Def);
        box2.setText(checkMultipleDefinitions(trending2));
        TextView box3 = findViewById(R.id.TrendingWord3Def);
        box3.setText(checkMultipleDefinitions(trending3));
        // Set Word of the Day text boxes
        Buzzword wotd = aController.getWOTD();
        TextView wotdTitle = findViewById(R.id.wotdTxt);
        wotdTitle.setText(capitalized(wotd.getBuzzword()));
        TextView wotdDef = findViewById(R.id.wotdDef);
        wotdDef.setText(checkMultipleDefinitions(wotd));
    }
    /**
     * Returns a string with the first character capitalized.
     * @param str the string to be capitalized
     * @return the capitalized string
     */
    private String capitalized(String str){
        String capital = str.substring(0,1).toUpperCase() + str.substring(1);
        return capital;
    }
    /**
     * Checks if a Buzzword object has more than one object. If it does, then it will display that there are multiple definitions to the Buzzword.
     * @param buzz the Buzzword object to be checked
     * @return the single definition of the Buzzword or the string "Multiple definitions available." if there is more than one definition for the Buzzword
     */
    private String checkMultipleDefinitions(Buzzword buzz){
        String stringy = "";
        if (buzz.getDefinitions().size() == 0) {
            stringy = "No definitions available, please contact us!";
        } else if (buzz.getDefinitions().size() < 2) {
            stringy = buzz.getDefinitions().get(0) +".";
        } else {
            stringy = "Multiple definitions available.";
        }
        return stringy;
    }

    /**
     * Randomly chooses a word of the day from a given amount of Buzzwords. Ignores the top three trending Buzzwords to avoid redundancy.
     * @param amount the amount of Buzzwords present in the global Controller object's ArrayList of Buzzwords
     */
    public void chooseWOTD(int amount) {
        // Need to choose between these words in the ArrayList
        int checkThrough = amount - 3;
        Random randall = new Random();
        int choice = randall.nextInt(checkThrough) + 3;
        // Global controller class object
        final Controller aController = (Controller) getApplicationContext();
        wotd = aController.getBuzzword(choice);
        aController.setWOTD(wotd);
    }
}