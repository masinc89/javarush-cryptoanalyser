package ru.javarush.amutovin.module1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CheckWord {
    private static final String exampleWords = "src/ru/javarush/amutovin/module1/ExampleText.txt";

    public boolean isContaintWord(String word) {

        if (word.length() > 6) {
            word = word.substring(0, 5);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(exampleWords))) {
            boolean isContaint = false;
            while (reader.ready()) {
                if (isContaint) {
                    break;
                }
                String readNextLine = reader.readLine();
                String[]arrayWord = readNextLine.split(",");
                for (int i = 0; i < arrayWord.length; i++) {
                    if(arrayWord[i].contains(word)){
                        isContaint = true;
                        break;
                    }
                }
            }
            return isContaint;
        } catch (FileNotFoundException e){
        throw new IllegalArgumentException("Файл с массивом популярных слов не найден");
    } catch (IOException e) {
        throw new IllegalArgumentException("Ошибка IO в файле самых популярных файлов");
    }

    }

}
