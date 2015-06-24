package com.studlymen;

import weka.core.converters.*;

import java.io.IOException;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        TextDirectoryLoader loader = new TextDirectoryLoader();

        System.out.println("Hello Studly Men! \n\nSam... you're such a class act. :3");
        
        //Testing out the new database object
        NGramDatabase db = new NGramDatabase();
        //NGramDatabase.GenerateTables(db, 3); // Generate the ngram tables

        String fileNames[] = {"foo.txt"};
        int nGramLengths[] = {2,3};

        FileParser.Parse("C:\\Develop\\GitHub\\CS450-Team-Project\\books\\10080.txt", 3);
    }
}
