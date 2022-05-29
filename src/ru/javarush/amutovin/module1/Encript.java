package ru.javarush.amutovin.module1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Encript {
    private static final int indexNotFound = -1;
    private Path srcPath;
    private Path dstPath;
    private int key;

    public Encript(Path srcPath, Path dstPath, int key) {
        this.srcPath = srcPath;
        this.dstPath = dstPath;
        this.key = key;
    }

    public String startEncript() {

        try (FileReader srcFileReader = new FileReader(srcPath.toFile(), StandardCharsets.UTF_8);
             FileWriter dstfileWriter = new FileWriter(dstPath.toFile(), StandardCharsets.UTF_8)) {

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

            return "Шифрование успешно выполнено";

        } catch (FileNotFoundException e) {
            throw new FileProcessingException("Файл не существует", e);
        } catch (IOException e) {
            throw new FileProcessingException("Ошибка ввода вывода при работе с src файлом", e);
        }

    }

    public char getEncriptLiteral(char srcLiteral) {
        char encriptLiteral = srcLiteral;
        Alphabet alphabet = Alphabet.alphabet;
        int alphabetCount = alphabet.getCountLiteralinAlphabet();
        int srcIndex = alphabet.getIndexLiteralFromAlphabet(srcLiteral);
        if (srcIndex != indexNotFound) {
            int encriptLiteralIndex = (srcIndex + key) % alphabetCount;
            encriptLiteral = alphabet.getCharLiteralFromAlphabet(encriptLiteralIndex);
        }
        return encriptLiteral;
    }
}
