package com.studlymen;

import org.javatuples.Pair;
import weka.core.converters.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        TextDirectoryLoader loader = new TextDirectoryLoader();

        System.out.println("Hello Studly Men! \n\nSam... you're such a class act. :3");

        //Testing out the new database object
        NGramDatabase db = new NGramDatabase();
        NGramDatabase.GenerateTables(db, 3); // Generate the ngram tables

        String fileNames[] = {"foo.txt"};
        int nGramLengths[] = {2,3};

        HashMap<Integer, HashMap<String, Pair<Integer,List<String>>>> wordMaps = null;//FileParser.Parse("C:\\Develop\\GitHub\\CS450-Team-Project\\books\\10080.txt", 3);

        for (int i = 1; i <= 3; i++)
        {
            Collection<Pair<Integer,List<String>>> nGramPairs = wordMaps.get(i).values();
            for (Pair<Integer,List<String>> pair : nGramPairs)
            {
                List<String> words = pair.getValue1();
                String statement = "INSERT INTO ngram" + i + "(";
                String values = "";
                for (int k = 0; k < words.size(); k++)
                {
                    if (k > 0)
                    {
                        statement += ", ";
                        values += ", ";
                    }
                    statement += "word" + k;
                    values += "'" + words.get(k) + "'";
                }

                statement += ") VALUES (" + values + ");";

                db.execute(statement);
            }
        }
    }
}

