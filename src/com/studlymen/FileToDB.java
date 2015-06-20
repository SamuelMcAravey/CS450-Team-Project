package com.studlymen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileToDB
{
	private String mFileNames[];
	private String mDbFileName;
	private int mNGramLengths[];
	private ArrayList<HashMap<List<String>, Integer>> mNGramTables;
	private int mLargestNGram;
	private NGramBuffer mBuffer;
	
	public FileToDB(String fileNames[], String dbFileName, int nGramLengths[]) 
	{
		mFileNames = fileNames;
		mDbFileName = dbFileName;
		mNGramLengths = nGramLengths;
		mNGramTables = new ArrayList<HashMap<List<String>, Integer>>(mNGramLengths.length);
		for(int i = 0; i < mNGramLengths.length; i++)
			mNGramTables.add(new HashMap<List<String>, Integer>());
		
		Arrays.sort(mNGramLengths);
		mLargestNGram = mNGramLengths[mNGramLengths.length - 1];
		mBuffer = new NGramBuffer(mNGramLengths.length);
	}
		
	public void parse() 
	{
		for(String file : mFileNames)
		{
			//"home/you/Desktop"
			if(file.endsWith("/*"))
			{
				file = file.substring(0,file.length() - 2);
				try {
					Files.walk(Paths.get(file)).forEach(filePath -> {
					    if (Files.isRegularFile(filePath)) {
					    	parseFile(filePath.toString());
					    }
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
				parseFile(file);
		}

		//HashMap<List<String>, Integer> map = mNGramTables.get(0);
	    for(HashMap<List<String>, Integer> map : mNGramTables) {
	    	HashMap<List<String>, Integer> map1 = (HashMap<List<String>, Integer>) sortByValue(map);
	    	map1.forEach(this::printEntry);
	    }
	}
	
	private String getNext(Scanner s)
	{
    	String original = s.next();
    	// Removing all digits from beginning and end.
    	// However, it might be good to replace a digit with a symbol.
    	
    	String modified = original.replaceAll("^[^a-zA-Z]+|[^a-zA-Z]+$","");
    			//.replaceAll("[^a-zA-Z]+|[a-zA-Z]$","");
    			//.replaceAll("^\\W+|\\W+$","");
    			//.replaceAll("[\\W]|_", "");
    	
    	//Those that are left that have non alpha characters
    	//if (!modified.isEmpty() && !original.equals(modified) && !original.matches("[a-zA-Z]+"))
    	if(!modified.isEmpty())
    		return modified;
    	else
    		return null;
    	    	
    	/*if (!original.equals(modified))
    		System.out.println((count++) + " " + original + " " + modified);*/		
	}
	
	private void parseFile(String file)
	{
	    Scanner sc2 = null;
	    try 
	    {
	        sc2 = new Scanner(new File(file));
	        sc2.useDelimiter("\\s+|--"); // "July,[Footnote" is a problem with this delimiter
	    } catch (FileNotFoundException e)
	    {
	        System.out.println("ERROR: Cannot find " + file);
	        return;
	        //System.exit(1);
	    }

	    int count = 1;
	    
	   // String buffer[] = new String[mLargestNGram];
	    String next = null;
/*	    while(sc2.hasNext() && count < mLargestNGram)
	    {
	    	next = getNext(sc2);
	    	if(next != null)
	    	{
	    		count++;
	    		for(int i = 0; i < mLargestNGram; i++)
	    		{
	    			if(count <= )
	    		}
	    	}
	    }
*/	    
	    while(sc2.hasNext())
//	    for(; count < mLargestNGram && sc2.hasNext(); count++)
	    {
	    	next = getNext(sc2);
	    	if(next != null)
	    	{
	    		count++;
	    		mBuffer.append(next);
	    		String buffer[] = mBuffer.getBuffer();
	    		int buffLen = buffer.length;
	    		
	    		// cycle through the nGrams
	    		for(int i = 0; i < mNGramLengths.length; i++)
	    		{
	    			// if there are enough words in the buffer for the nGram table
	    			// place the last n words from the buffer into the table
	    			int start = buffLen - mNGramLengths[i];
	    			
	    			if(start == 0)
	    			{//TODO won't get the last indexed mNGramLengths
	    				mNGramTables.get(i).merge(Arrays.asList(buffer), 1, Math::addExact);	    				
	    			}
	    			else if (start > 0)
	    			{
	    				mNGramTables.get(i).merge(
	    						/*Arrays.copyOfRange(bufferbuffer[0], start, buffLenmNGramLengths[i])*/
	    						Arrays.asList(buffer).subList(start, buffLen /*mNGramLengths[i]*/), 1, Math::addExact);
	    			}
	    			else
	    			{
	    				System.err.println("Skipped");
	    				//skip over this case
	    			}
/*	    			if(start >= 0buffLen >= mNGramLengths[i])
	    			{
	    				
	    				
	    				// buffer[bufferSize - nWords] = 
	    				String buffer[] = mBuffer.getBuffer();
	    				mNGramTables.get(i).;
	    			}*/
	    		}
	    	}
	    }
	    
/*	    while(sc2.hasNext()) 
	    {
	    	String next = getNext(sc2);
	    	if(next != null)
	    	{
	    		++count;
	    		System.out.println((count) + " " + next);
	    	}	    	
	    }*/
	    sc2.close();
	    


	    
	    /*while (sc2.hasNextLine()) {
	            Scanner s2 = new Scanner(sc2.nextLine());
	        while (s2.hasNext()) {
	            String s = s2.next();
	            System.out.println(s);
	        }
	    }*/
	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
		Map<K,V> result = new LinkedHashMap<>();
	     Stream <Entry<K,V>> st = map.entrySet().stream();
	
	     st.sorted(Comparator.comparing(e -> e.getValue()))
	          .forEach(e ->result.put(e.getKey(),e.getValue()));
	
	     return result;
    }
	
	public void printEntry(List<String> key, Integer value) 
	{
		for(String word : key)
			System.out.print(word + " ");
	
		System.out.println(value);
	}
	
	public static void main(String args[])
	{
		final int OPTION_LENGTH = 2;
		String databaseFileName = null;
		String files[] = null;
		int nGramLengths[] = null;
		FileToDB fileToDB;
		
		String usage = "FileToDB: options file[s]\n"
				+ "Add no spaces after -$ arguments.\n"
				+ "(1) Pick One: \n"
				+ "\t-N: add to or create 1..N ngrams\n"
				+ "\t-n: add to or create an ngram table\n"
				+ "(2) Required: \n"
				+ "\t-d: the database name and location to write to\n"
				+ "(3) file[s] to read from";
		
		if(args.length >= 3) 
		{
			files = new String[args.length - OPTION_LENGTH];
			for(int i = OPTION_LENGTH; i < args.length; i++)
			{
				if(args[i].startsWith("-"))
				{
					System.out.println(usage);
					System.exit(1);					
				}
				files[i - OPTION_LENGTH] = args[i];
			}
			
			for(int i = 0; i < OPTION_LENGTH; i++)
			{
				String arg = args[i].substring(2);
				switch(args[i].substring(0,2))
				{
					case "-N":
						// Check to see if -n/-N was already seen
						if(nGramLengths != null)
						{
							System.out.println(usage);
							System.exit(1);
						}
						int nNGrams = Integer.parseInt(arg);
						nGramLengths = new int[nNGrams];
						for(int n = 1; n <= nNGrams; n++)
						{
							nGramLengths[n - 1] = n;
						}
						break;
					case "-n":
						// Check to see if -N/-n was already seen
						if(nGramLengths != null)
						{
							System.out.println(usage);
							System.exit(1);
						}
						int nGram = Integer.parseInt(arg);
						nGramLengths = new int[1];
						nGramLengths[0] = nGram;
						break;
					case "-d":
						// Check to see if -d was already seen
						if(databaseFileName != null)
						{
							System.out.println(usage);
							System.exit(1);						
						}
						databaseFileName = arg;
						break;
				}
			}
			
			if(nGramLengths == null || databaseFileName == null)
			{
				System.out.println(usage);
				System.exit(1);
			}
			
			fileToDB = new FileToDB(files, databaseFileName, nGramLengths);
			fileToDB.parse();
		} else
		{
			System.out.println(usage);
			System.exit(1);			
		}
	}
}
