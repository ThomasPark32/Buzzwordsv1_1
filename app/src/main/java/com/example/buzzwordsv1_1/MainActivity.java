package com.example.buzzwordsv1_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
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
    // Currently trending trendingBuzzwords that are accessible from the main menu
    Buzzword trending1;
    Buzzword trending2;
    Buzzword trending3;
    Buzzword trending4;
    Buzzword trending5;
    // Word of the day
    Buzzword wotd;
    // Current image used for Buzzy's expression
    int choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        readAllBuzzwordDataFB();
    }

    /**
     * This method will run when the activity is resumed. It resets Buzzy's rotation.
     */
    @Override
    protected void onResume(){
        super.onResume();
        // Reset Buzzy's rotation
        ImageView buzzyView = findViewById(R.id.buzzy);
        buzzyView.setRotation(0);
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
        switch (id) {
            case R.id.SeeMoreTrending1:
                box = findViewById(R.id.TrendingWord1Txt);
                break;
            case R.id.SeeMoreTrending2:
                box = findViewById(R.id.TrendingWord2Txt);
                break;
            case R.id.SeeMoreTrending3:
                box = findViewById(R.id.TrendingWord3Txt);
                break;
            case R.id.SeeMoreTrending4:
                box = findViewById(R.id.TrendingWord4Txt);
                break;
            case R.id.SeeMoreTrending5:
                box = findViewById(R.id.TrendingWord5Txt);
                break;
            case R.id.SeeMoreWotd:
                box = findViewById(R.id.wotdTxt);
                break;
            default:
                // Display error message; can not find button that was pressed.
                Context context = getApplicationContext();
                String text = "Error: Can not determine button pressed.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;

        }
        name = box.getText().toString();
        // Start definition activity, pass in Buzzword
        Intent tent = new Intent(this, DefinitionActivity.class);
        tent.putExtra("Buzzword",name);
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
     * Reads from Firebase Realtime Storage to obtain Buzzwords and populate Controller class' ArrayList of Buzzwords.
     */
    private void readAllBuzzwordDataFB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("All");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            // Global controller class object
            final Controller aController = (Controller) getApplicationContext();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                // Loop over all children in the "All" tab
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    // Get buzzword Firebase word
                    String str = snap.getKey();
                    ArrayList<String> definitions = (ArrayList<String>) snap.child("/").getValue();
                    // Make new buzzword object with word
                    Buzzword word = new Buzzword(str.toLowerCase());
                    // Add definitions to buzzword
                    if (definitions != null) {
                        for (int k = 0; k < definitions.size(); k++) {
                            word.addDefinition(definitions.get(k));
                        }
                    }
                    // Add buzzword object to global Controller object
                    aController.addBuzzwords(word);
                }
                setTrendingWords();
                // Choose Word of the Day, if it is a new day
                if (newDay == true) {
                    chooseWOTD(aController.getAllBuzzwords().size());
                }
                // Set the newDay boolean to false
                newDay = false;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("Reading Error", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * Gets the trending Buzzwords from the Firebase Database, sets the words already present in the ArrayList of Buzzwords to trending, then puts them in the ArrayList of Trending Buzzwords, all located in the Controller class.
     */
    private void setTrendingWords(){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Trending");

        ArrayList<String> trendingWords = new ArrayList<String>();

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
                    trendingWords.add(str);
                }
                // Clear the trending words ArrayList in the Controller object
                aController.clearTrending();
                // For each string in the trending words, check if it is in the ArrayList of all Buzzwords already, and if so, set the boolean in the Buzzword object to true and put in the Controller class' trending Buzzword ArrayList.
                for (int index1 = 0; index1 < trendingWords.size(); index1++) {
                    for (int index2 = 0; index2 < aController.getAllBuzzwords().size(); index2++) {
                        if (trendingWords.get(index1).toLowerCase().equals(aController.getAllBuzzwords().get(index2).getBuzzword().toLowerCase())){
                            aController.getAllBuzzwords().get(index2).setTrending(true);
                            aController.addToTrending(aController.getAllBuzzwords().get(index2));
                        }
                    }
                }
                // Display buzzwords to main activity screen
                sendBuzzwords();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("Reading Error", "Failed to read value.", error.toException());
            }
        });
    }
    /**
     * Sends top 3 trending trendingBuzzwords to UI to be displayed.
     */
    public void sendBuzzwords(){
        // Global controller class object
        final Controller aController = (Controller) getApplicationContext();
        // Get first 3 trending buzzword objects from global Controller object
        trending1 = aController.getATrendingBuzzword(0);
        trending2 = aController.getATrendingBuzzword(1);
        trending3 = aController.getATrendingBuzzword(2);
        trending4 = aController.getATrendingBuzzword(3);
        trending5 = aController.getATrendingBuzzword(4);
        // Set titles on main activity with first 5 trending words
        // Buzzword 1
        TextView title1 = findViewById(R.id.TrendingWord1Txt);
        title1.setText(capitalized(trending1.getBuzzword()));
        // Buzzword 2
        TextView title2 = findViewById(R.id.TrendingWord2Txt);
        title2.setText(capitalized(trending2.getBuzzword()));
        // Buzzword 3
        TextView title3 = findViewById(R.id.TrendingWord3Txt);
        title3.setText(capitalized(trending3.getBuzzword()));
        // Buzzword 4
        TextView title4 = findViewById(R.id.TrendingWord4Txt);
        title4.setText(capitalized(trending4.getBuzzword()));
        // Buzzword 5
        TextView title5 = findViewById(R.id.TrendingWord5Txt);
        title5.setText(capitalized(trending5.getBuzzword()));
        // Set definitions on main activity with first 5 trending definitions
        // Buzzword 1
        TextView box1 = findViewById(R.id.TrendingWord1Def);
        box1.setText(checkMultipleDefinitions(trending1));
        // Buzzword 2
        TextView box2 = findViewById(R.id.TrendingWord2Def);
        box2.setText(checkMultipleDefinitions(trending2));
        // Buzzword 3
        TextView box3 = findViewById(R.id.TrendingWord3Def);
        box3.setText(checkMultipleDefinitions(trending3));
        // Buzzword 4
        TextView box4 = findViewById(R.id.TrendingWord4Def);
        box4.setText(checkMultipleDefinitions(trending4));
        // Buzzword 5
        TextView box5 = findViewById(R.id.TrendingWord5Def);
        box5.setText(checkMultipleDefinitions(trending5));
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
     * Randomly chooses a word of the day from a given amount of Buzzwords. The word of the day can not be one of the trending Buzzwords.
     * @param amount the amount of Buzzwords present in the global Controller object's ArrayList of Buzzwords
     */
    public void chooseWOTD(int amount) {
        boolean done = false;
        while (!done) {
            Random randall = new Random();
            int choice = randall.nextInt(amount);
            // Global controller class object
            final Controller aController = (Controller) getApplicationContext();
            wotd = aController.getABuzzword(choice);
            if (!wotd.isTrending()) {
                aController.setWOTD(wotd);
                done = true;
            }
        }
    }

    /**
     * Switches the face of Buzzy if clicked.
     * @param v the view
     */
    public void switchFace(View v){
        Log.d("Buzz","I was clicked");
        ImageView buzzyView = findViewById(R.id.buzzy);
        int original = choice;
        int number = 0;
        while (choice == original) {
            Random randall = new Random();
            choice = randall.nextInt(6);
            switch (choice) {
                case 0:
                    number = R.drawable.buzzy;
                    break;
                case 1:
                    number = R.drawable.buzzyconfused;
                    break;
                case 2:
                    number = R.drawable.buzzyfrown;
                    break;
                case 3:
                    number = R.drawable.buzzyopen;
                    break;
                case 4:
                    number = R.drawable.buzzysmile;
                    break;
                case 5:
                    number = R.drawable.buzzyunamused;
                    break;
            }
        }
        buzzyView.setImageResource(number);
        Random rng = new Random();
        buzzyView.setRotation(rng.nextInt(360));
    }
}