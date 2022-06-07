package ru.javarush.amutovin.module1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Main {
    private static final String ENCRIPT = "encript";
    private static final String DECRIPT = "decript";
    private static final String BRUTEFORCE = "bruteforce";
    private static final String FILE_FORMAT = ".txt";
    private static final String STATUS = "successfully";
    private static final int MAX_PARAMETER_COUNT = 4;
    private static final int MIN_PARAMETER_COUNT = 3;
    static String command;
    static Path srcPath;
    static Path dstPath;
    static int key;

    public static void main(String[] args) {
        parseArgs(args);

        if (ENCRIPT.equals(command)) {
            Encript encript = new Encript(srcPath, dstPath, key);
            encript.startEncript();
        } else if (DECRIPT.equals(command)) {
            Decript decript = new Decript(srcPath, dstPath, key);
            decript.startDecript();
        } else if (BRUTEFORCE.equals(command)) {
            BruteForce bruteForce = new BruteForce(srcPath, dstPath);
            bruteForce.startDecript();

        }
        System.out.println(STATUS);

    }

    public static void parseArgs(String[] commandLineArray) {
        Alphabet alphabet = Alphabet.getAlphabet();
        int alphabetCount = alphabet.getCountLiteralinAlphabet();

        if (commandLineArray.length >= MIN_PARAMETER_COUNT && commandLineArray.length <= MAX_PARAMETER_COUNT) {
            command = commandLineArray[0];

            if (!ENCRIPT.equals(command) && !DECRIPT.equals(command) && !BRUTEFORCE.equals(command)) {
                throw new IllegalArgumentException("The first argument is not recognized. Read the documentation in the README.md file");
            }

            srcPath = readFilePath(commandLineArray[1], false);
            dstPath = readFilePath(commandLineArray[2], true);

            if (commandLineArray.length == 4) {
                try {
                    key = Integer.parseInt(commandLineArray[3]);
                    key = key % alphabetCount;
                    if (key < 1) {
                        throw new IllegalArgumentException("The key must be a positive number from 1. Entered " + key);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("The passed fourth argument (key) cannot be converted to an int");
                }
            }

        } else {
            throw new IllegalArgumentException("Failed to parse arguments. The number of arguments must be 3 or 4. " +
                    "discovered " + commandLineArray.length + " argument(s)");
        }

    }

    private static Path readFilePath(String file, boolean createFileIfNotExists) {
        Path filePath = null;

        if (!file.endsWith(FILE_FORMAT)) {
            throw new FileProcessingException(String.format("The submitted file does not have a %s extension.", FILE_FORMAT));
        }

        try {
            filePath = Path.of(file);
        } catch (InvalidPathException e) {
            throw new FileProcessingException("The path passed " + file + " cannot be converted to type Path", e);
        }


        if (Files.notExists(filePath) && !createFileIfNotExists) {
            throw new FileProcessingException("File "  +filePath.toAbsolutePath() + " does not exists!");
        } else if (Files.notExists(filePath) && createFileIfNotExists) {

            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new FileProcessingException("Failed to create file.", e);
            }

        }

        if (!Files.isRegularFile(filePath)) {
            throw new FileProcessingException("The path passed " + filePath.toAbsolutePath() + " is not a file!");
        }
        return filePath;
    }

}
