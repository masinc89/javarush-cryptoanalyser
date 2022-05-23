package ru.javarush.amutovin.module1;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static String command;
    static Path srcPath;
    static Path dstPath;
    static int key;

    public static void main(String[] args) {

        System.out.println("Введите команду:");

        Scanner console = new Scanner(System.in);
        String insertCommandLine = console.nextLine();

        while (!insertCommandLine.equalsIgnoreCase("exit")) {
            String[] commandLineArray = insertCommandLine.split(" ");
            isValidcommandLine(commandLineArray);

            if (command.equals("encript")){
                Encript encript = new Encript(srcPath, dstPath, key);
                encript.startEncript();
            }
            System.out.println("Введите следующую команду:");
            insertCommandLine = console.nextLine();
        }
    }

    public static void isValidcommandLine(String[] commandLineArray) {

        if (commandLineArray.length != 4) {
            System.err.println("Проверьте количество переданных аргументов. Их должно быть 4.");
            System.exit(1);
        }

        command = commandLineArray[0];
        String srcPathString = commandLineArray[1];
        String dstPathString = commandLineArray[2];
        key = 0;

        try {
             key = Integer.parseInt(commandLineArray[3]);
        } catch (NumberFormatException e){
            System.err.println("Введенный четвертый аргумент не целое число");
            System.exit(2);
        }

        if (!command.equalsIgnoreCase("encript") && !command.equalsIgnoreCase("decript")) {
            System.err.println("Первый аргумент введен некорректно, принимаемые параметры \"enсript\" или \"decript\"");
            System.exit(3);
        }

        try {
            srcPath = Path.of(srcPathString);
        } catch (InvalidPathException e) {
            System.err.println("Переданный путь " + srcPath + " не является путем к файлу!");

            System.exit(4);
        }

        if (!Files.isRegularFile(srcPath)) {
            System.err.println("Переданный путь srcPath не является файлом");
            System.exit(7);
        }

        if (!Files.exists(srcPath)) {
            System.err.println("src файл не существует");
            System.exit(8);
        }

        try {
            dstPath = Path.of(dstPathString);
            if (Files.notExists(dstPath)) {
                Files.createFile(dstPath);
            }
        } catch (InvalidPathException e) {
            System.err.println("Переданный путь "+ dstPath + " не является путем к файлу!");
            System.exit(5);
        } catch (IOException e) {
            System.err.println("произошла ошибка при создании файла" + dstPath);
            System.exit(6);
        }

        if (!Files.isRegularFile(dstPath)) {
            System.err.println("Переданный путь dstPath не является файлом");
            System.exit(9);
        }

    }


}
