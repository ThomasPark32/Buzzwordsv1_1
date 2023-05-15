package com.example.buzzwordsv1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    // ArrayList of Strings of closest words to the search word.
    private ArrayList<String> closestWords;
    // Buzzwords to be displayed
    private Buzzword result1;
    private Buzzword result2;
    private Buzzword result3;
    private Buzzword result4;
    private Buzzword result5;
    /**
     * Runs this method once at the creation of this activity, setting the titles of the activity to the given search query.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_results);

        // Get the Intent that started this activity and extract the string to display

        Intent tent = getIntent();
        String query = tent.getStringExtra("query");

        TextView SearchedWord = findViewById(R.id.SearchedWord);
        SearchedWord.setText(capitalized(query));

        TextView SearchResult1Title = findViewById(R.id.SearchResult1Title);
        SearchResult1Title.setText("Similar word to " + query);

        TextView SearchResult2Title = findViewById(R.id.SearchResult2Title);
        SearchResult2Title.setText("Another similar word to " + query);

        closestWords = new ArrayList<String>();
    }

    /**
     * On start of this activity, this method will run once.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Get the Intent that started this activity and extract the string to display
        Intent tent = getIntent();
        String query = tent.getStringExtra("query");

        // Global controller class object
        final Controller aController = (Controller) getApplicationContext();

        // Run search method to find similar words to given String

        closestWords = levenshteinSearch(query.toLowerCase(), aController.getAllBuzzwords());
        // Send search results to respective text boxes in UI.
        sendSearchResults();
    }

    /**
     * Quits the current activity and goes back to the previous screen (the main activity).
     * @param v the view
     */
    public void returnToMain(View v){
        finish();
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
            case R.id.SeeMoreResult1:
                box = findViewById(R.id.SearchResult1Title);
                break;
            case R.id.SeeMoreResult2:
                box = findViewById(R.id.SearchResult2Title);
                break;
            case R.id.SeeMoreResult3:
                box = findViewById(R.id.SearchResult3Title);
                break;
            case R.id.SeeMoreResult4:
                box = findViewById(R.id.SearchResult4Title);
                break;
            case R.id.SeeMoreResult5:
                box = findViewById(R.id.SearchResult5Title);
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
     * Compares searched word with every single Buzzword string, placing it in a String ArrayList sorted by using the Levenshtein Distance formula.
     * @param searched the searched word
     * @param list the ArrayList of Buzzwords
     * @return an ArrayList containing the syntactically closest words at the front
     */
    private ArrayList<String> levenshteinSearch(String searched, ArrayList<Buzzword> list) {
        // Adapted from https://www.analyticsvidhya.com/blog/2021/02/a-simple-guide-to-metrics-for-calculating-string-similarity/

        // Given searched word size
        int size1 = searched.length() + 1;

        // List to store distances
        ArrayList<Integer> intList = new ArrayList<Integer>();
        // List to store strings associated with those distances
        ArrayList<String> stringList = new ArrayList<String>();

        // Main loop, checks searched word to next word in the given list and sorts the ArrayLists by the distance given.
        for (int index = 0; index < list.size(); index++) {
            // Get the length of the given string
            String word = list.get(index).getBuzzword();
            int size2 = word.length() + 1;

            // Create a matrix with the two word sizes, + 1 on each.
            int[][] matrix = new int[size1][size2];

            // Fill first column with index of characters from comparison word.
            for (int i = 0; i < size1; i++) {
                matrix[i][0] = i;
            }

            // Fill first row with index of characters from given word.
            for (int k = 0; k < size2; k++) {
                matrix[0][k] = k;
            }
            // Fill the values of the matrix depending if characters of the strings are same or different
            for (int j = 1; j < size1; j++) {
                for (int l = 1; l < size2; l++) {
                    // Check if characters are the same
                    if (searched.charAt(j-1) == word.charAt(l - 1)) {
                        matrix[j][l] = Math.min(matrix[j-1][l-1], Math.min(matrix[j-1][l] + 1, matrix[j][l-1] + 1));
                    } else { // else if the characters are not the same
                        matrix[j][l] = Math.min(matrix[j-1][l]+1, Math.min(matrix[j-1][l-1] + 1, matrix[j][l-1] + 1));
                    }
                }
            }

            // Gets the distance found in the last row and last column
            int distance = matrix[size1 - 1][size2 - 1];

            // Sort list using intList and new distance to be added
            int h = intList.size() -1;
            boolean done = false;

            // Checks if list is empty, needs to add initial value
            if (h != -1) {
                while (!done) {
                    // If the distance is less than the value at the index, subtract the index pointer j
                    if (distance < intList.get(h)) {
                        h--;
                        // If the index pointer j is -1, you've hit the lower bound of the ArrayList and must stop
                        if (h == -1) {
                            intList.add(0, distance);
                            stringList.add(0, word);
                            done = true;
                        }
                    } else if (distance >= intList.get(h)) {
                        // If the distance is greater or equal to the value found, add it in the index after this value.
                        intList.add(h + 1, distance);
                        stringList.add(h + 1, word);
                        done = true;
                    }
                }
            } else {
                intList.add(distance);
                stringList.add(word);
            }
        }
        return stringList;
    }

    /**
     * Displays the top 5 search results to their respective text boxes.
     */
    public void sendSearchResults(){
        // Global controller class object
        final Controller aController = (Controller) getApplicationContext();

        // Get the top five closest Buzzwords to the query
        result1 = aController.getABuzzword(aController.findBuzzword(closestWords.get(0)));
        result2 = aController.getABuzzword(aController.findBuzzword(closestWords.get(1)));
        result3 = aController.getABuzzword(aController.findBuzzword(closestWords.get(2)));
        result4 = aController.getABuzzword(aController.findBuzzword(closestWords.get(3)));
        result5 = aController.getABuzzword(aController.findBuzzword(closestWords.get(4)));

        // Result #1
        TextView title1 = findViewById(R.id.SearchResult1Title);
        title1.setText(capitalized(result1.getBuzzword()));
        TextView def1 = findViewById(R.id.SearchResult1Def);
        def1.setText(checkMultipleDefinitions(result1));

        // Result #2
        TextView title2 = findViewById(R.id.SearchResult2Title);
        title2.setText(capitalized(result2.getBuzzword()));
        TextView def2 = findViewById(R.id.SearchResult2Def);
        def2.setText(checkMultipleDefinitions(result2));

        // Result #3
        TextView title3 = findViewById(R.id.SearchResult3Title);
        title3.setText(capitalized(result3.getBuzzword()));
        TextView def3 = findViewById(R.id.SearchResult3Def);
        def3.setText(checkMultipleDefinitions(result3));

        // Result #4
        TextView title4 = findViewById(R.id.SearchResult4Title);
        title4.setText(capitalized(result4.getBuzzword()));
        TextView def4 = findViewById(R.id.SearchResult4Def);
        def4.setText(checkMultipleDefinitions(result4));

        // Result #5
        TextView title5 = findViewById(R.id.SearchResult5Title);
        title5.setText(capitalized(result5.getBuzzword()));
        TextView def5 = findViewById(R.id.SearchResult5Def);
        def5.setText(checkMultipleDefinitions(result5));
    }

    /**
     * Returns a string with the first character capitalized.
     * @param str the string to be capitalized
     * @return the capitalized string
     */
    private String capitalized(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
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
}