package ru.javarush.amutovin.module1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Decript {
    private Path srcPath;
    private Path dstPath;
    private int key;

    public Decript(Path srcPath, Path dstPath, int key) {
        this.srcPath = srcPath;
        this.dstPath = dstPath;
        this.key = key;
    }

    public void startDecript() {
        try (FileReader srcFileReader = new FileReader(srcPath.toFile());
             FileWriter dstFileWriter = new FileWriter(dstPath.toFile())) {

            char[] srcBuffer = new char[300];
            char[] dstBuffer = new char[300];

            while (srcFileReader.ready()) {
                int count = srcFileReader.read(srcBuffer);

                for (int i = 0; i < count; i++) {
                    dstBuffer[i] = getDecriptLiteral(srcBuffer[i]);
                }
                dstFileWriter.write(dstBuffer, 0, count);
                dstFileWriter.flush();
                System.out.println("Расшифровка успешно выполнена");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Файл не существует" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private char getDecriptLiteral(char encriptLiteral) {
        Alphabet alphabet = Alphabet.alphabet;
        char decriptLiteral = encriptLiteral;
        int indexEncriptLiteral = alphabet.getIndexLiteralFromAlphabet(encriptLiteral);
        if (indexEncriptLiteral != -1) {
            int decriptLiteralIndex = (124 + indexEncriptLiteral - key) % 124;
            decriptLiteral = alphabet.getCharLiteralFromAlphabet(decriptLiteralIndex);
        }
        return decriptLiteral;
    }
}
