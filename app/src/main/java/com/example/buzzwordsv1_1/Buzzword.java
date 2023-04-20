package com.example.buzzwordsv1_1;

public class Buzzword {
    private String buzzword;
    private String definition;

    /**
     * Default constructor- Creates a new Buzzword object.
     */
    public Buzzword() {
        buzzword = "";
        definition = "";
    }

    /**
     * Parameter constructor- Creates a new Buzzword object with a given word and a given definition.
     * @param word the Buzzword
     * @param def the definition to the Buzzword
     */
    public Buzzword(String word, String def) {
        buzzword = word;
        definition = def;
    }

    /**
     * Sets the Buzzword to a given word.
     * @param word the Buzzword
     */
    public void setBuzzword(String word) {
        buzzword = word;
    }

    /**
     * Sets the definition of the buzzword to a given definition.
     * @param def the definition of the Buzzword
     */
    public void setDefinition(String def) {
        definition = def;
    }

    /**
     * Gets the word from the Buzzword object.
     * @return the Buzzword
     */
    public String getBuzzword(){
        return buzzword;
    }

    /**
     * Gets the definition from the Buzzword object.
     * @return the definition of the Buzzword
     */
    public String getDefinition(){
        return definition;
    }
}
