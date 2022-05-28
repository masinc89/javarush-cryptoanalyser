package ru.javarush.amutovin.module1;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BruteForce {
    private Path srcPath;
    private Path dstPath;

    private static final String exampleWords = "src/ru/javarush/amutovin/module1/ExampleText.txt";

    public BruteForce(Path srcPath, Path dstPath) {
        this.srcPath = srcPath;
        this.dstPath = dstPath;
    }

    public String startDecript() {
        String decriptStatus = "";
        int key = searchKey();

        if (key == 0) {
            decriptStatus = "Не удалось обнаружить ключ, не найдено ни одного совпадения с популярными словами";
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath.toFile()));
                 BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dstPath.toFile()))) {

                while (bufferedReader.ready()) {
                    String readLine = bufferedReader.readLine();
                    readLine = getDecriptLine(readLine, key);
                    bufferedWriter.write(readLine);
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                decriptStatus = "Расшифровка методом Brute Force успешно выполнена";

            } catch (FileNotFoundException e) {
                throw new FileProcessingException("Не найден файл " + srcPath, e);
            } catch (IOException e) {
                throw new FileProcessingException("Ошибка I/O", e);
            }
        }
        return decriptStatus;

    }


    private int searchKey() {
        Alphabet alphabet = Alphabet.alphabet;
        HashMap<Integer, Integer> countEntriesByKey = new HashMap<>();

        for (int key = 0; key < alphabet.getCountLiteralinAlphabet(); key++) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(srcPath.toFile()))) {
                while (bufferedReader.ready()) {
                    String readEncriptLine = bufferedReader.readLine();
                    String decriptLine = getDecriptLine(readEncriptLine, key);
                    decriptLine = decriptLine.replaceAll(",", "");
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
                throw new IllegalArgumentException("Не найдет шифрованный файл " + srcPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return getKey(countEntriesByKey);
    }

    private String getDecriptLine(String encriptLine, int key) {
        char[] decriptArray = encriptLine.toCharArray();
        Alphabet alphabet = Alphabet.alphabet;

        for (int i = 0; i < decriptArray.length; i++) {
            char decriptLiteral = decriptArray[i];
            int indexEncriptLiteral = alphabet.getIndexLiteralFromAlphabet(decriptArray[i]);

            if (indexEncriptLiteral != -1) {
                int decriptLiteralIndex = (124 + indexEncriptLiteral - key) % 124;
                decriptLiteral = alphabet.getCharLiteralFromAlphabet(decriptLiteralIndex);
            }
            decriptArray[i] = decriptLiteral;

        }
        return new String(decriptArray);
    }

    private boolean isContaintWord(String word) {
        boolean isContaint = false;
        if (word.length() > 6) {
            word = word.substring(0, 5);
        }

        try (BufferedReader readerExampleWordFile = new BufferedReader(new FileReader(exampleWords))) {
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

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Файл с массивом популярных слов не найден");
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка IO в файле самых популярных файлов");
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
        if (key == 0) {
            System.out.println("элемент не найден");
            System.exit(0);
        }
        return key;
    }

}
