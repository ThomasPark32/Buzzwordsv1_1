package com.example.buzzwordsv1_1;

import java.util.ArrayList;

public class Buzzword {
    private String buzzword;
    private ArrayList<String> definitions;
    private boolean isTrending;
    private ArrayList<String> headlines;
    private ArrayList<String> urls;

    /**
     * Default constructor- Creates a new Buzzword object.
     */
    public Buzzword() {
        buzzword = "";
        definitions = new ArrayList<String>();
        isTrending = false;
        headlines = new ArrayList<String>();
        urls = new ArrayList<String>();
    }

    /**
     * Parameter constructor- Creates a new Buzzword object with a given word and a given definition.
     * @param word the Buzzword
     */
    public Buzzword(String word) {
        buzzword = word;
        definitions = new ArrayList<String>();
        headlines = new ArrayList<String>();
        urls = new ArrayList<String>();
    }

    /**
     * Sets the Buzzword to a given word.
     * @param word the Buzzword
     */
    public void setBuzzword(String word) {
        buzzword = word;
    }

    /**
     * Sets one of the definitions of the buzzword to a given definition.
     * @param def a definition of the Buzzword
     */
    public void addDefinition(String def) {
        definitions.add(def);
    }

    /**
     * Gets the word from the Buzzword object.
     * @return the Buzzword
     */
    public String getBuzzword(){
        return buzzword;
    }

    /**
     * Gets the definitions from the Buzzword object.
     * @return the definitions of the Buzzword
     */
    public ArrayList<String> getDefinitions(){
        return definitions;
    }
    @Override
    public String toString(){
        String stringy = buzzword + ": ";
        if (definitions.isEmpty()) {
            stringy += "No definition, please contact us!";
        } else {
            for (String str : definitions) {
                stringy += str + ", ";
            }
        }
        return stringy;
    }

    /**
     * Returns if the Buzzword is trending or not.
     * @return a boolean based on if the Buzzword is trending
     */
    public boolean isTrending(){
        return isTrending;
    }

    /**
     * Sets the Buzzword to be trending or not.
     * @param newValue a boolean if the Buzzword is trending
     */
    public void setTrending(boolean newValue){
        isTrending = newValue;
    }

    /**
     * Adds a headline to the Buzzword's headline list.
     * @param headline the headline to add
     */
    public void addHeadlines (String headline) {
        headlines.add(headline);
    }

    /**
     * Removes a headline from the Buzzword's headline list.
     * @param headline the headline to remove
     */
    public void removeHeadlines (String headline) {
        headlines.remove(headline);
    }
    /**
     * Returns the ArrayList of Strings which represent headlines stored in this Buzzword object.
     * @return an ArrayList of Strings representing headlines
     */
    public ArrayList<String> getHeadlines() {
        return headlines;
    }
    public void addURLs(String url) {
        urls.add(url);
    }
    public void removeURLs(String url){
        urls.remove(url);
    }
    public ArrayList<String> getURLs(){
        return urls;
    }
}
