package com.example.buzzwordsv1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    ArrayList<String> closestWords;
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

        closestWords = levenshteinSearch(query.toLowerCase(), aController.getBuzzwords());

        Log.d("YAHOO", "Closest buzzwords to " + query + ": " + closestWords);
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

        Log.d("YAHOO", "stringList before addition:  " + stringList);
        Log.d("YAHOO", "intList before addition:  " + intList);
        Log.d("YAHOO", "regular list size before addition:  " + list.size());

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

            Log.d("YAHOO", "intList being added to:  " + intList);
            Log.d("YAHOO", "stringList being added to:  " + stringList);

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
        Log.d("YAHOO", "Final int list :" + intList);
        Log.d("YAHOO", "Final String list :" + stringList);
        return stringList;
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
}