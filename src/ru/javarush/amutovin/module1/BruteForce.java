package ru.javarush.amutovin.module1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;

public class BruteForce {
    private static final int INDEX_NOT_FOUND_IN_ALPHABET = -1;
    private static final String SEPARATOR = ",";
    private static final int MAX_WORD_LENGTH = 6;
    private static final int GET_MAX_WORD_FROM_INDEX_LENGTH = 5;
    private Path srcPath;
    private Path dstPath;
    private Alphabet alphabet;
    private static final String exampleWords = "ExampleText.txt";

    public BruteForce(Path srcPath, Path dstPath) {
        this.srcPath = srcPath;
        this.dstPath = dstPath;
        this.alphabet = Alphabet.getAlphabet();
    }

    public String startDecript() {
        String decriptStatus = "";
        int key = searchKey();

        if (key == 0) {
            decriptStatus = "Could not find the key, no matches were found with popular words";
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath.toFile(), StandardCharsets.UTF_8));
                 BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dstPath.toFile(), StandardCharsets.UTF_8))) {

                while (bufferedReader.ready()) {
                    String readLine = bufferedReader.readLine();
                    readLine = getDecriptLine(readLine, key);
                    bufferedWriter.write(readLine);
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                decriptStatus = "Brute Force decryption completed successfully";

            } catch (FileNotFoundException e) {
                throw new FileProcessingException("File not found " + srcPath, e);
            } catch (IOException e) {
                throw new FileProcessingException("I/O error", e);
            }
        }
        return decriptStatus;

    }


    private int searchKey() {
        HashMap<Integer, Integer> countEntriesByKey = new HashMap<>();

        for (int key = 0; key < alphabet.getCountLiteralinAlphabet(); key++) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath.toFile(), StandardCharsets.UTF_8))) {
                while (bufferedReader.ready()) {
                    String readEncriptLine = bufferedReader.readLine();
                    String decriptLine = getDecriptLine(readEncriptLine, key);
                    decriptLine = decriptLine.replaceAll(SEPARATOR, "");
                    String[] decriptLineArray = decriptLine.split(" ");

                    for (int i = 0; i < decriptLineArray.length; i++) {
                        if (isContaintWord(decriptLineArray[i])) {
                            if (countEntriesByKey.containsKey(key)) {
                                countEntriesByKey.put(key, countEntriesByKey.get(key) + 1);
                            } else {
                                countEntriesByKey.put(key, 1);
                            }
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("Can't find encrypted file " + srcPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return getKey(countEntriesByKey);
    }

    private String getDecriptLine(String encriptLine, int key) {
        char[] decriptArray = encriptLine.toCharArray();
        int alphabetCount = alphabet.getCountLiteralinAlphabet();

        for (int i = 0; i < decriptArray.length; i++) {
            char decriptLiteral = decriptArray[i];
            int indexEncriptLiteral = alphabet.getIndexLiteralFromAlphabet(decriptArray[i]);

            if (indexEncriptLiteral != INDEX_NOT_FOUND_IN_ALPHABET) {
                int decriptLiteralIndex = (alphabetCount + indexEncriptLiteral - key) % alphabetCount;
                decriptLiteral = alphabet.getCharLiteralFromAlphabet(decriptLiteralIndex);
            }
            decriptArray[i] = decriptLiteral;

        }
        return new String(decriptArray);
    }

    private boolean isContaintWord(String word) {
        InputStream exampleWordStream = getClass().getResourceAsStream(exampleWords);
        boolean isContaint = false;

        if (word.length() > MAX_WORD_LENGTH) {
            word = word.substring(0, GET_MAX_WORD_FROM_INDEX_LENGTH);
        }

        try (BufferedReader readerExampleWordFile = new BufferedReader(new InputStreamReader(exampleWordStream, StandardCharsets.UTF_8))) {
            while (readerExampleWordFile.ready()) {
                if (isContaint) {
                    break;
                }
                String readNextLine = readerExampleWordFile.readLine();
                String[] arrayWord = readNextLine.split(",");
                for (int i = 0; i < arrayWord.length; i++) {
                    if (arrayWord[i].contains(word)) {
                        isContaint = true;
                        break;
                    }
                }
            }
            return isContaint;

        } catch (IOException e) {
            throw new FileProcessingException("I/O error in the file with the most popular files", e);
        }

    }

    private int getKey(HashMap<Integer, Integer> countEntriesByKey) {
        Integer maxValues = 0;
        Integer key = 0;
        for (Map.Entry<Integer, Integer> pair : countEntriesByKey.entrySet()) {
            Integer values = pair.getValue();
            if (values > maxValues) {
                maxValues = values;
                key = pair.getKey();
            }
        }
        return key;
    }

}
