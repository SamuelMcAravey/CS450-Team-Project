package com.studlymen;

import org.javatuples.Pair;

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
    public static HashMap<Integer, HashMap<String, Pair<Integer,List<String>>>> Parse(String filePath, int nGramSize) throws IOException
    {
        HashMap<Integer, HashMap<String, Pair<Integer,List<String>>>> wordMaps = new HashMap<>();
        HashMap<Integer, List<String>> wordHistory = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            for (int j = 0; j < words.length; j++)
            {
                if (!words[j].isEmpty())
                    ParseWord(words[j], nGramSize, wordMaps, wordHistory);
            }
        }
        return wordMaps;
    }


    private static void ParseWord(
            String word,
            int nGramSize,
            HashMap<Integer, HashMap<String, Pair<Integer, List<String>>>> wordMaps,
            HashMap<Integer, List<String>> wordHistory)
    {
        if (!wordMaps.containsKey(nGramSize))
            wordMaps.put(nGramSize, new HashMap<>());
        if (!wordHistory.containsKey(nGramSize))
            wordHistory.put(nGramSize, new ArrayList<>());

        HashMap<String, Pair<Integer,List<String>>> currentWordMap = wordMaps.get(nGramSize);
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
            {
                Pair<Integer,List<String>> currentNGram = currentWordMap.get(historyString);
                Pair<Integer,List<String>> updatedNGram = new Pair<>(currentNGram.getValue0() + 1, currentNGram.getValue1());
                currentWordMap.put(historyString, updatedNGram);
            }
            else
            {
                Pair<Integer,List<String>> newNGram = new Pair<>(1, new ArrayList<>(history));
                currentWordMap.put(historyString, newNGram);
            }
        }

        if (nGramSize > 1)
            ParseWord(word, nGramSize - 1, wordMaps, wordHistory);
    }
}
