package ru.javarush.amutovin.module1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Decript {
    private static final int LENGTH_BUFFER_ARRAY = 500;
    private static final int INDEX_NOT_FOUND_IN_ALPHABET = -1;
    private Path srcPath;
    private Path dstPath;
    private int key;
    private Alphabet alphabet;

    public Decript(Path srcPath, Path dstPath, int key) {
        this.srcPath = srcPath;
        this.dstPath = dstPath;
        this.key = key;
        this.alphabet = Alphabet.getAlphabet();
    }

    public String startDecript() {
        try (FileReader srcFileReader = new FileReader(srcPath.toFile(), StandardCharsets.UTF_8);
             FileWriter dstFileWriter = new FileWriter(dstPath.toFile(), StandardCharsets.UTF_8)) {

            char[] srcBuffer = new char[LENGTH_BUFFER_ARRAY];
            char[] dstBuffer = new char[LENGTH_BUFFER_ARRAY];

            while (srcFileReader.ready()) {
                int count = srcFileReader.read(srcBuffer);

                for (int i = 0; i < count; i++) {
                    dstBuffer[i] = getDecriptLiteral(srcBuffer[i]);
                }
                dstFileWriter.write(dstBuffer, 0, count);
                dstFileWriter.flush();
            }
            return "Decryption completed successfully";

        } catch (FileNotFoundException e) {
            throw new FileProcessingException("File not found", e);
        } catch (IOException e) {
            throw new FileProcessingException("I/O ERROR", e);
        }

    }

    private char getDecriptLiteral(char encriptLiteral) {
        int alphabetCount = alphabet.getCountLiteralinAlphabet();
        char decriptLiteral = encriptLiteral;
        int indexEncriptLiteral = alphabet.getIndexLiteralFromAlphabet(encriptLiteral);
        if (indexEncriptLiteral != INDEX_NOT_FOUND_IN_ALPHABET) {
            int decriptLiteralIndex = (alphabetCount + indexEncriptLiteral - key) % alphabetCount;
            decriptLiteral = alphabet.getCharLiteralFromAlphabet(decriptLiteralIndex);
        }
        return decriptLiteral;
    }
}
