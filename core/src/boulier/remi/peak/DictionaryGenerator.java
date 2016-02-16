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

    private final ArrayList<String> dictionary = new ArrayList<String>();

    public DictionaryGenerator() {
    }

    public ArrayList<String> generateDictionary(char[] letters, int gridRows, int gridCols) {
        Gdx.app.log("DictionaryGenerator", "Start generate");
        dictionary.clear();

        ArrayList<Prefix> prefixes = generatePrefixes();
        Gdx.app.log("DictionaryGenerator", "Prefixes generated");

        processTheGrid(prefixes, letters, gridRows, gridCols);

        Gdx.app.log("DictionaryGenerator", "Done");
        return dictionary;
    }

    private void processTheGrid(ArrayList<Prefix> prefixes, char[] letters, int gridRows, int gridCols) {
        char[][] grid = initGrid(letters, gridRows, gridCols);
        final int[][] map = new int[gridRows][gridCols];

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                map[r][c] = 1;
                for (Prefix p : prefixes) {
                    if (p.c == grid[r][c]) {
                        StringBuilder builder = new StringBuilder();
                        builder.append(p.c);
                        findNextLetter(r, c, gridRows, gridCols, grid, map, p.children, builder);
                        break;
                    }
                }
                resetMap(map);
            }
        }
    }

    private void findNextLetter(int curRow, int curCol, int gridRows, int gridCols, char[][] grid, int[][] map, ArrayList<Prefix> currPrefixes, StringBuilder builder) {
        for (int x = curRow - 1; x <= curRow + 1; x++) {
            for (int y = curCol - 1; y <= curCol + 1; y++) {
                if (x >= 0 && x < gridRows && y >= 0 && y < gridCols && map[x][y] != 1) {
                    for (Prefix p : currPrefixes) {
                        if (p.c == grid[x][y]) {
                            builder.append(p.c);
                            if (p.isWord) {
                                dictionary.add(builder.toString());
                            }
                            map[x][y] = 1;
                            findNextLetter(x, y, gridRows, gridCols, grid, map, p.children, builder);
                            builder.setLength(builder.length() - 1);
                            map[x][y] = 0;
                            break;
                        }
                    }
                }
            }
        }
    }

    private char[][] initGrid(char[] letters, int rows, int cols) {
        char[][] grid = new char[rows][cols];
        int index = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = letters[index++];
            }
        }

        return grid;
    }

    private void resetMap(int[][] map) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                map[r][c] = 0;
            }
        }
    }

    private ArrayList<Prefix> generatePrefixes() {
        FileHandle file = Gdx.files.internal("words.txt");
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
                    if (p.c == c) {
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
                    child.isWord = true;
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
