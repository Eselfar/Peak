package boulier.remi.peak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Remi BOULIER on 15/02/2016.
 * email: boulier.r+dev@gmail.com
 */
public class DictionaryGenerator {


    public DictionaryGenerator() {
    }

    public ArrayList<String> generateDictionary(ArrayList<String> letters, int gridRows, int gridCols) {
        Gdx.app.log("DictionaryGenerator", "Start generate");
        final ArrayList<String> dictionary = new ArrayList<String>();

        ArrayList<Prefix> prefixes = generatePrefixes();

        Gdx.app.log("DictionaryGenerator", "Done");
        return dictionary;
    }

    private ArrayList<Prefix> generatePrefixes() {
        FileHandle file = Gdx.files.internal("mydictionary.txt");
        BufferedReader br = new BufferedReader(file.reader());
        ArrayList<Prefix> prefixes = new ArrayList<Prefix>();

        try {
            String currentWord = br.readLine();
            while (currentWord != null) {
                getWordPrefixes(currentWord, prefixes);
                currentWord = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Maybe do something
        }
        return prefixes;
    }


    private void getWordPrefixes(String currentWord, ArrayList<Prefix> prefixes) {
        int wordLength = currentWord.length();
        Prefix prefix = null;

        for (int i = 0; i < wordLength; i++) {
            char c = currentWord.charAt(i);
            if (i == 0) {
                // Test if this prefix is not already in the list of prefixes.
                for (Prefix p : prefixes) {
                    if(p.c == c) {
                        prefix = p;
                        break;
                    }
                }
                if (prefix == null) {
                    prefix = new Prefix(c, i + 1 == wordLength);
                    prefixes.add(prefix);
                }
            } else {
                prefix = prefix.addChild(c, (i + 1 == wordLength));
            }
        }
    }

    private class Prefix {
        final char c;
        boolean isWord;
        final ArrayList<Prefix> children;

        public Prefix(char c, boolean isWord) {
            this.c = c;
            this.isWord = isWord;
            this.children = new ArrayList<Prefix>();
        }

        public Prefix addChild(char c, boolean isWord) {
            Prefix child = hasForChild(c);
            if (child != null) {
                if (isWord && !child.isWord)
                    child.isWord = isWord;
            } else {
                child = new Prefix(c, isWord);
                children.add(child);
            }

            return child;
        }

        public Prefix hasForChild(char c) {
            for (Prefix child : children) {
                if (child.c == c) return child;
            }
            return null;
        }
    }
}
