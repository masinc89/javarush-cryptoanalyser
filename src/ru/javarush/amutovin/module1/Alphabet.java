package ru.javarush.amutovin.module1;

public class Alphabet {
    private static final char[] alphabetArray = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '.', ',', '«', '»', '"',
            '\'', ':', '!', '?', ' '};

    public static Alphabet alphabet = new Alphabet();

    private Alphabet() {

    }

    public char getCharLiteralFromAlphabet(int index) {

        try {
            return alphabetArray[index];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Символ не найден по индексу" + index);
        }
    }

    public int getCountLiteralinAlphabet(){
        return alphabetArray.length;
    }

    public int getIndexLiteralFromAlphabet(char literal) {
        int index = -1;

        for (int i = 0; i < alphabetArray.length; i++) {
            if (literal == alphabetArray[i]) {
                index = i;
                break;
            }
        }

        return index;
    }

}
