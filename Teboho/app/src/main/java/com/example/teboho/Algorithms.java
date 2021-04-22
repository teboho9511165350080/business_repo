package com.example.teboho;

public class Algorithms {

    String sentence;
    final int full_length = 40;

    Algorithms(){

    }

    Algorithms(String sentence){
        this.sentence = sentence;
    }

    String getSpaces()
    {
        StringBuilder spaces = new StringBuilder();

        for (int x = 0; x < (full_length-sentence.length()); x++)
            spaces.append(" ");

        return spaces.toString();
    }
}
