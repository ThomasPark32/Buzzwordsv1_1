package com.example.buzzwordsv1_1;

import android.app.Application;

import java.util.ArrayList;

public class Controller extends Application {
    ArrayList<Buzzword> buzzwords = new ArrayList<Buzzword>();

    public ArrayList<Buzzword> getBuzzwords(){
        return buzzwords;
    }

    public void addBuzzwords(Buzzword word) {
        buzzwords.add(word);
    }

    public Buzzword getBuzzword(int index){
        return buzzwords.get(index);
    }

    public void removeBuzzword(Buzzword word) {
        buzzwords.remove(word);
    }

    public void removeBuzzword(int index) {
        buzzwords.remove(index);
    }
}
