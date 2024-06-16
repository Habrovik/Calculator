package Calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Map<Character, Integer> MyMap = new HashMap<Character, Integer>();
    static {
        MyMap.put('I', 1);
        MyMap.put('V', 5);
        MyMap.put('X', 10);
        MyMap.put('L', 50);
        MyMap.put('C', 100);
    }
//Создаем карту и вносим в него символы

    private static int rimToArab(String str) {
        int result = 0, Value = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            int thisValue = MyMap.get(str.charAt(i));
            result += thisValue < Value ? -thisValue : thisValue;
            Value = thisValue;
        }
        return result;
    }
// Этот метод преобразовывает римскую цифру в арабскую

    private static String arabToRim(int number) {
        String[] symbols = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();
        for (int i = 0; number > 0; i++) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(symbols[i]);
            }
        }
        return result.toString();
    }
//Этот метод преобразовывает арабские цифры в римские

    private static boolean isItWeNeedRim(String str) {
        if (str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!MyMap.containsKey(c)) {
                return false;
            }
        }
        int value = rimToArab(str);
        return value >= 1 && value <= 10;
    }
//Этот метод проверяет, является ли строка римской цифрой и в допустимом диапозоне

    private static boolean isItWeNeedArab(String str) {
        try {
            int value = Integer.parseInt(str);
            return value >= 1 && value <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }
// Этот метод проверяет, является ли строка арабской цифрой и в допустимом диапазоне

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (через пробел): ");
        String input = scanner.nextLine().trim();
        System.out.println (calc(input));
    }

    public static String calc(String input) {
        String[] array = input.split(" ");
        if (array.length != 3) {
            throw new IllegalArgumentException("Ошибка: неправильно введены значения.");
        }

        String input1 = array[0];
        char operator = array[1].charAt(0);
        String input2 = array[2];

        boolean isRim = isItWeNeedRim(input1);
        boolean isRim2 = isItWeNeedRim(input2);

        if ((!isRim && !isItWeNeedArab(input1)) || (!isRim2 && !isItWeNeedArab(input2))) {
            throw new ArithmeticException("Ошибка: нужно ввести числа от 1 до 10.");
        }

        int num1 = isRim ? rimToArab(input1) : Integer.parseInt(input1);
        int num2 = isRim2 ? rimToArab(input2) : Integer.parseInt(input2);

        if ((!isRim && isRim2) || (isRim && !isRim2)) {
            throw new IllegalArgumentException("Ошибка: оба числа должны быть либо арабскими, либо римскими.");
        }

        int allAtOnce = solution(num1, num2, operator);
        String resultStr = (isRim || isRim2) ? arabToRim(allAtOnce) : String.valueOf(allAtOnce);

        if (resultStr.isEmpty()) {
            throw new ArithmeticException("Ошибка: Результат вычисления римских чисел не может быть меньше или равным нулю.");
        }

        return ("Результат: " + resultStr);
    }
//Это основной метод, в который подают значение, он его обрабатывает и возвращает ответ

    public static int solution(int num1, int num2, char operator) {
        int result = 0;
        if (operator == '+') {
            result = num1 + num2;
        } else if (operator == '-') {
            result = num1 - num2;
        } else if (operator == '*') {
            result = num1 * num2;
        } else if (operator == '/') {
            result = num1 / num2;
        } else {
            throw new IllegalArgumentException ("Ошибка: неверная операция.");
        }
        return result;
    }
//Этот метод делает вычисление
}
