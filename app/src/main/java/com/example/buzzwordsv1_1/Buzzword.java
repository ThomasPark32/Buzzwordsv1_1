package com.example.buzzwordsv1_1;

import java.util.ArrayList;

public class Buzzword {
    private String buzzword;
    private ArrayList<String> definitions;

    /**
     * Default constructor- Creates a new Buzzword object.
     */
    public Buzzword() {
        buzzword = "";
        definitions = new ArrayList<String>();
    }

    /**
     * Parameter constructor- Creates a new Buzzword object with a given word and a given definition.
     * @param word the Buzzword
     */
    public Buzzword(String word) {
        buzzword = word;
        definitions = new ArrayList<String>();
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
}
