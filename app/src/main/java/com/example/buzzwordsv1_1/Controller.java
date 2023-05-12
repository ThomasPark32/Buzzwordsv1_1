package com.example.buzzwordsv1_1;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

/**
 * This class is a controller object which stores the Buzzwords in the app for usage in multiple activities.
 */
public class Controller extends Application {
    ArrayList<Buzzword> allBuzzwords = new ArrayList<Buzzword>();
    ArrayList<Buzzword> trendingWords = new ArrayList<Buzzword>();
    Buzzword wotd;

    /**
     * Returns the ArrayList of Buzzwords obtained from Firebase.
     * @return the ArrayList of Buzzword objects
     */
    public ArrayList<Buzzword> getAllBuzzwords(){
        return allBuzzwords;
    }
    /**
     * Returns the Trending ArrayList of Buzzwords.
     * @return the ArrayList of trending Buzzword objects
     */
    public ArrayList<Buzzword> getTrendingBuzzwords(){
        return trendingWords;
    }
    /**
     * Adds a Buzzword object to the ArrayList of Buzzword objects.
     * @param word the Buzzword to be added
     */
    public void addBuzzwords(Buzzword word) {
        allBuzzwords.add(word);
    }
    /**
     * Adds a Buzzword object to the trending ArrayList of Buzzword objects.
     * @param word the Buzzword to be added to the trending list
     */
    public void addToTrending(Buzzword word) {
        trendingWords.add(word);
    }

    /**
     * Returns the Buzzword stored at the provided index in the ArrayList of Buzzword objects.
     * @param index the index where the Buzzword is located
     * @return the Buzzword stored in that index in the ArrayList of Buzzword objects
     */
    public Buzzword getABuzzword(int index){
        return allBuzzwords.get(index);
    }

    /**
     * Returns the Buzzword stored at the provided index in the ArrayList of trending Buzzword objects.
     * @param index the index where the Buzzword is located
     * @return the Buzzword stored in that index in the trending ArrayList of Buzzword objects
     */
    public Buzzword getATrendingBuzzword(int index) {
        return trendingWords.get(index);
    }

    /**
     * Removes a Buzzword object from the ArrayList of Buzzword objects.
     * @param word the Buzzword object to be removed.
     */
    public void removeABuzzword(Buzzword word) {
        allBuzzwords.remove(word);
    }
    /**
     * Removes a Buzzword object from the ArrayList of Buzzword objects, using an index.
     * @param index the index where the Buzzword object is stored in the ArrayList of Buzzword objects
     */
    public void removeABuzzword(int index) {
        allBuzzwords.remove(index);
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
        for (int index = 0; index < allBuzzwords.size(); index++) {
            Buzzword b = getAllBuzzwords().get(index);
            if (b.getBuzzword().equals(name.toLowerCase())){
                location = index;
                break;
            }
        }
        return location;
    }

    /**
     * Clears the trending Buzzword ArrayList.
     */
    public void clearTrending(){
        trendingWords.clear();
    }
}
