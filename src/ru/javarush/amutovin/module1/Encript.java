package ru.javarush.amutovin.module1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Encript {
    private Path srcPath;
    private Path dstPath;
    private int key;

    public Encript(Path srcPath, Path dstPath, int key) {
        this.srcPath = srcPath;
        this.dstPath = dstPath;
        this.key = key;
    }

    public void startEncript() {
        try (FileReader srcFileReader = new FileReader(srcPath.toFile());
             FileWriter dstfileWriter = new FileWriter(dstPath.toFile())) {

            char[] srcBuffer = new char[300];
            char[] dstBuffer = new char[300];

            while (srcFileReader.ready()) {
                int count = srcFileReader.read(srcBuffer);

                for (int i = 0; i < count; i++) {
                    dstBuffer[i] = getEncriptLiteral(srcBuffer[i]);
                }

                dstfileWriter.write(dstBuffer, 0, count);
            }
            dstfileWriter.flush();
            System.out.println("Зашифровано");

        } catch (FileNotFoundException e) {
            System.err.println("Файл не существует" + e.getMessage());
        } catch (IOException e) {
            System.err.println("ошибка ввода вывода при работает с src файлом" + e.getMessage());
        }
    }

    public char getEncriptLiteral(char srcLiteral) {
        char encriptLiteral = srcLiteral;
        Alphabet alphabet = Alphabet.alphabet;
        int srcIndex = alphabet.getIndexLiteralFromAlphabet(srcLiteral);
        if (srcIndex != -1) {
            int encriptLiteralIndex = (srcIndex + key) % 124;
            encriptLiteral = alphabet.getCharLiteralFromAlphabet(encriptLiteralIndex);
        }
        return encriptLiteral;
    }
}
