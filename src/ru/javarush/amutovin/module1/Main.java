package ru.javarush.amutovin.module1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Main {

    static String command;
    static Path srcPath;
    static Path dstPath;
    static int key;

    public static void main(String[] args) {
        String status = "";
        parseArgs(args);

        if ("encript".equals(command)) {
            Encript encript = new Encript(srcPath, dstPath, key);
            status = encript.startEncript();
        } else if ("decript".equals(command)) {
            Decript decript = new Decript(srcPath, dstPath, key);
            status = decript.startDecript();
        } else if ("bruteforce".equals(command)) {
            BruteForce bruteForce = new BruteForce(srcPath, dstPath);
            status = bruteForce.startDecript();

        }
        System.out.println(status);

    }

    public static void parseArgs(String[] commandLineArray) {
        Alphabet alphabet = Alphabet.alphabet;
        int alphabetCount = alphabet.getCountLiteralinAlphabet();

        if (commandLineArray.length >= 3 && commandLineArray.length <= 4) {
            command = commandLineArray[0];

            if (!"encript".equals(command) && !"decript".equals(command) && !"bruteforce".equals(command)) {
                throw new IllegalArgumentException("Первый аргумент не распознан. Ознакомьтесь с документацией в файле README.md");
            }

            srcPath = readFilePath(commandLineArray[1], false);
            dstPath = readFilePath(commandLineArray[2], true);

            if (commandLineArray.length == 4) {
                try {
                    key = Integer.parseInt(commandLineArray[3]);
                    key = key % alphabetCount;
                    if (key < 1) {
                        throw new IllegalArgumentException("Ключ должен быть положительным числом от 1. Введен " + key);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Переданный четвертый аргумент (ключ) не может быть преобразован в целое число int");
                }
            }

        } else {
            throw new IllegalArgumentException("Не удалось распарсить аргументы. Количество аргументов должно быть 3 или 4. " +
                    "обнаружен(о) " + commandLineArray.length + " аргумент(ов)");
        }

    }

    private static Path readFilePath(String file, boolean createFileIfNotExists) {
        Path filePath = null;

        if (!file.endsWith(".txt")) {
            throw new FileProcessingException("Переданный файл не имеет расширение txt");
        }

        try {
            filePath = Path.of(file);
        } catch (InvalidPathException e) {
            throw new FileProcessingException("Переданный путь " + file + " не может быть преобразован в тип Path", e);
        }

        if (Files.notExists(filePath) && !createFileIfNotExists) {
            throw new FileProcessingException("Файл "  +filePath.toAbsolutePath() + " не существует!");
        } else if (Files.notExists(filePath) && createFileIfNotExists) {

            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new FileProcessingException("Не удалось создать файл.", e);
            }

        }

        if (!Files.isRegularFile(filePath)) {
            throw new FileProcessingException("Переданный путь " + filePath.toAbsolutePath() + " не является файлом!");
        }

        return filePath;
    }

}
