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

            if (command.equalsIgnoreCase("encript")){
                Encript encript = new Encript(srcPath, dstPath, key);
                encript.startEncript();
            } else if (command.equalsIgnoreCase("decript")) {
                Decript decript = new Decript(srcPath, dstPath, key);
                decript.startDecript();
            }
            System.out.println("Введите следующую команду[exit - выход]:");
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
             if (key < 1 || key > 123) {
                 System.err.println("Ключ в четвертом параметре должен быть от 1 до 123 включительно");
                 System.exit(2);
             }
        } catch (NumberFormatException e){
            System.err.println("Введенный четвертый аргумент не целое число");
            System.exit(3);
        }

        if (!command.equalsIgnoreCase("encript") && !command.equalsIgnoreCase("decript")) {
            System.err.println("Первый аргумент введен некорректно, принимаемые параметры \"enсript\" или \"decript\"");
            System.exit(4);
        }

        try {
            srcPath = Path.of(srcPathString);
        } catch (InvalidPathException e) {
            System.err.println("Переданный путь " + srcPath + " не является путем к файлу!");

            System.exit(5);
        }

        if (!Files.isRegularFile(srcPath)) {
            System.err.println("Переданный путь srcPath не является файлом");
            System.exit(6);
        }

        if (!Files.exists(srcPath)) {
            System.err.println("src файл не существует");
            System.exit(7);
        }

        try {
            dstPath = Path.of(dstPathString);
            if (Files.notExists(dstPath)) {
                Files.createFile(dstPath);
            }
        } catch (InvalidPathException e) {
            System.err.println("Переданный путь "+ dstPath + " не является путем к файлу!");
            System.exit(8);
        } catch (IOException e) {
            System.err.println("произошла ошибка при создании файла" + dstPath);
            System.exit(9);
        }

        if (!Files.isRegularFile(dstPath)) {
            System.err.println("Переданный путь dstPath не является файлом");
            System.exit(10);
        }

    }


}
