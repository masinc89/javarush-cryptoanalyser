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

    private static final char[] alphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e','f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E','F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '.', ',', '«', '»', '"',
            '\'', ':', '!', '?', ' '};

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

                dstfileWriter.write(dstBuffer);
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

        for (int i = 0; i < alphabet.length; i++) {
            if (srcLiteral == alphabet[i]) {
                int encriptLiteralIndex = (i + key) % 124;
                encriptLiteral =  alphabet[encriptLiteralIndex];
            }
        }
        return encriptLiteral;
    }
}
