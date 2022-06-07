package ru.javarush.amutovin.module1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Encript {
    private static final int INDEX_NOT_FOUND_IN_ALPHABET = -1;
    private static final int LENGTH_BUFFER_ARRAY = 500;
    private Path srcPath;
    private Path dstPath;
    private int key;
    private Alphabet alphabet;

    public Encript(Path srcPath, Path dstPath, int key) {
        this.srcPath = srcPath;
        this.dstPath = dstPath;
        this.key = key;
        this.alphabet = Alphabet.getAlphabet();
    }

    public String startEncript() {

        try (FileReader srcFileReader = new FileReader(srcPath.toFile(), StandardCharsets.UTF_8);
             FileWriter dstfileWriter = new FileWriter(dstPath.toFile(), StandardCharsets.UTF_8)) {

            char[] srcBuffer = new char[LENGTH_BUFFER_ARRAY];
            char[] dstBuffer = new char[LENGTH_BUFFER_ARRAY];

            while (srcFileReader.ready()) {
                int count = srcFileReader.read(srcBuffer);

                for (int i = 0; i < count; i++) {
                    dstBuffer[i] = getEncriptLiteral(srcBuffer[i]);
                }

                dstfileWriter.write(dstBuffer, 0, count);
            }
            dstfileWriter.flush();

            return "Encryption completed successfully";

        } catch (FileNotFoundException e) {
            throw new FileProcessingException("File not found", e);
        } catch (IOException e) {
            throw new FileProcessingException("I/O ERROR", e);
        }

    }

    public char getEncriptLiteral(char srcLiteral) {
        char encriptLiteral = srcLiteral;
        int alphabetCount = alphabet.getCountLiteralinAlphabet();
        int srcIndex = alphabet.getIndexLiteralFromAlphabet(srcLiteral);
        if (srcIndex != INDEX_NOT_FOUND_IN_ALPHABET) {
            int encriptLiteralIndex = (srcIndex + key) % alphabetCount;
            encriptLiteral = alphabet.getCharLiteralFromAlphabet(encriptLiteralIndex);
        }
        return encriptLiteral;
    }
}
