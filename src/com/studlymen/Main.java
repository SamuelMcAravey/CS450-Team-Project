package com.studlymen;

import weka.core.converters.*;

public class Main
{

    public static void main(String[] args)
    {
        TextDirectoryLoader loader = new TextDirectoryLoader();

        System.out.println("Hello Studly Men! \n\nSam... you're such a class act. :3");
        
        //Testing out the new database object
        NGramDatabase db = new NGramDatabase();
        NGramDatabase.GenerateTables(db, 3); // Generate the ngram tables
    }
}
