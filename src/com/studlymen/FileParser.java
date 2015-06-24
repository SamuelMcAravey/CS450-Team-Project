package com.studlymen;

import weka.core.PropertyPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mcara on 6/23/2015.
 */
public class FileParser
{
    public static void Parse(String filePath, int nGramSize) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            String[] words = line.split("\\s+");
            for (int j = 0; j < words.length; j++)
            {
                if (!words[j].isEmpty())
                    ParseWord(words[j], nGramSize);
            }
        }
    }

    static HashMap<Integer, HashMap<String, Integer>> wordMaps = new HashMap<Integer, HashMap<String, Integer>>();
    static HashMap<Integer, List<String>> wordHistory = new HashMap<Integer, List<String>>();

    private static void ParseWord(String word, int nGramSize)
    {
        if (!wordMaps.containsKey(nGramSize))
            wordMaps.put(nGramSize, new HashMap<String, Integer>());
        if (!wordHistory.containsKey(nGramSize))
            wordHistory.put(nGramSize, new ArrayList<String>());

        HashMap<String, Integer> currentWordMap = wordMaps.get(nGramSize);
        List<String> history = wordHistory.get(nGramSize);

        history.add(word);
        if (history.size() > nGramSize)
            history.remove(0);

        String historyString = "";
        for (int i = 0; i < history.size(); i++)
            historyString = historyString.concat(history.get(i));


        if (history.size() == nGramSize)
        {
            if (currentWordMap.containsKey(historyString))
                currentWordMap.put(historyString, currentWordMap.get(historyString) + 1);
            else
                currentWordMap.put(historyString, 1);
        }

        if (nGramSize > 1)
            ParseWord(word, nGramSize - 1);
    }
}
