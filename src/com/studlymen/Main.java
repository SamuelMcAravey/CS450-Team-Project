package com.studlymen;

import org.javatuples.Pair;

import weka.core.converters.*;

import java.io.IOException;
import java.util.ArrayList;
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

        
        
/**
 *  Hey JOSH!!!!!!!!!      
 *  Implement the following between here....
 */
        String fileNames[] = {"books/*"};
        int nGramLengths[] = {2,3};
        String dbFileName = "ngram.db";

        FileToDB ftDB = new FileToDB(fileNames, dbFileName, nGramLengths);
        //ftDB.run();
        
        //In FileToDB
        // write a function
        // i.e.
        /*
        run() {
        	parse(); // this initializes mNGramTables.
        	NGramDatabase.insert(mNGramTables); // this will insert mNGramTables into the databse as below.
    	}
    	
    	*/
        
        //In NGramDatabse.insert()
        /*
          insert() {
          		for(int i = 0; i < nGramLengths.length; i++) {
        			HashMap<List<String>, Integer> table = nGramTables.get(i);
        			//..for each loop put table into the database with the List<String> as the word columns and the Integer as the count.
        			//using the SQL batch insert statement.
        		}
        }
          }
         */
        
/**
 * ...and here.
 */
       

        
//      HashMap<Integer, HashMap<String, Pair<Integer,List<String>>>> wordMaps = FileParser.Parse("C:\\Develop\\GitHub\\CS450-Team-Project\\books\\10080.txt", 3);

        
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
