package com.example.buzzwordsv1_1;

import android.app.Application;

import java.util.ArrayList;

/**
 * This class is a controller object which stores the Buzzwords in the app for usage in multiple activities.
 */
public class Controller extends Application {
    ArrayList<Buzzword> buzzwords = new ArrayList<Buzzword>();
    Buzzword wotd;

    /**
     * Returns the ArrayList of Buzzwords obtained from Firebase.
     * @return the ArrayList of Buzzword objects
     */
    public ArrayList<Buzzword> getBuzzwords(){
        return buzzwords;
    }

    /**
     * Adds a Buzzword object to the ArrayList of Buzzword objects.
     * @param word the Buzzword to be added
     */
    public void addBuzzwords(Buzzword word) {
        buzzwords.add(word);
    }

    /**
     * Returns the Buzzword stored at the provided index in the ArrayList of Buzzword objects.
     * @param index the index where the Buzzword is located
     * @return the Buzzword stored in that index in the ArrayList of Buzzword objects
     */
    public Buzzword getBuzzword(int index){
        return buzzwords.get(index);
    }

    /**
     * Removes a Buzzword object from the ArrayList of Buzzword objects.
     * @param word the Buzzword object to be removed.
     */
    public void removeBuzzword(Buzzword word) {
        buzzwords.remove(word);
    }

    /**
     * Removes a Buzzword object from the ArrayList of Buzzword objects, using an index.
     * @param index the index where the Buzzword object is stored in the ArrayList of Buzzword objects
     */
    public void removeBuzzword(int index) {
        buzzwords.remove(index);
    }

    /**
     * Sets the word of the day to a given Buzzword object.
     * @param word a Buzzword
     */
    public void setWOTD(Buzzword word){
        wotd = word;
    }

    /**
     * Returns the Buzzword object that is the word of the day.
     * @return the word of the day, expressed as a Buzzword
     */
    public Buzzword getWOTD(){
        return wotd;
    }
    public int findBuzzword(String name){
        int location = 0;
        for (int index = 0; index < buzzwords.size(); index++) {
            Buzzword b = buzzwords.get(index);
            if (b.getBuzzword().equals(name.toLowerCase())){
                location = index;
                break;
            }
        }
        return location;
    }
}
