import java.io.IOException;
import java.util.Scanner;

public class Calculator {
    static int result;
    static int firstNumber;
    static int secondNumber;


    public static void main(String[] args) throws IOException {


        System.out.println("Введите выражение");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        expression = expression.toUpperCase();

        String[] symbols = expression.split(" ");
            if(symbols.length < 3) { throw new ArrayIndexOutOfBoundsException("Строка не является операцией"); }


        String operator = symbols[1];
        if(!(operator.equals("+")) && !(operator.equals("-")) && !(operator.equals("*")) && !(operator.equals("/"))){
            throw new IOException("Неверный оператор:" + operator);
        }

        if (symbols.length > 3) { throw new IOException("Формат математической операции не удовлетворяет заданию"); }

        if(symbols[0].startsWith("X") || symbols[0].startsWith("V") || symbols[0].startsWith("I")){
            if (symbols[2].startsWith("X") || symbols[2].startsWith("V") || symbols[2].startsWith("I")) {
                String[] firstNumeral = symbols[0].split("");
                String[] secondNumeral = symbols[2].split("");
                firstNumber = getRomanValues(firstNumeral);
                secondNumber = getRomanValues(secondNumeral);
                result = calculate(firstNumber, secondNumber, operator);
                if (result < 1) { throw new IOException("В римской системе нет отрицательных чисел (и ноля)"); }
                String romanResult = getArabValue(result);
                System.out.println(expression + " = " + romanResult);
                System.exit(0);
                } else {
                    throw new IOException("Неверное выражение");
                }

            }
        if(symbols[0].startsWith("L") || symbols[0].startsWith("C") || symbols[0].startsWith("D")) {
            throw new IOException("числа от 1 до 10 включительно, не более");
        }
        if(symbols[2].startsWith("L") || symbols[2].startsWith("C") || symbols[2].startsWith("D")) {
            throw new IOException("числа от 1 до 10 включительно, не более");
        }

        try {
            firstNumber = Integer.parseInt(symbols[0]);
            secondNumber = Integer.parseInt(symbols[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Неверное выражение");
            System.exit(1);
        }
        if (firstNumber > 10 || secondNumber > 10) { throw new IOException("числа от 1 до 10 включительно, не более"); }

        result = calculate(firstNumber, secondNumber, operator);

        System.out.println(expression + " = " + result);
}

static int getRomanValues(String[] numeral) throws IOException {
        int value = 0;
        int currValue = 0;
        int prev = 0;

        for (int i = 0; i < numeral.length; i++ ) {


            switch(numeral[i]){
                case "I" :
                    currValue += 1;
                        break;
                case "V" :
                    currValue += 5;
                    break;
                case "X" :
                    currValue += 10;
                    break;
            }
            if (currValue <= prev) { value += currValue;} else if (i==0) {value = currValue;} else {value = currValue - prev; }
            prev = currValue;
            currValue = 0;

        }
        if (value > 10) { throw new IOException("числа от 1 до 10 включительно, не более"); }
    return value;
}

static int calculate(int firstNumber, int secondNumber, String operator) {
    int result = 0;

    switch(operator) {
        case "+":
            result = firstNumber + secondNumber;
            break;
        case "-":
            result = firstNumber - secondNumber;
            break;
        case "*":
            result = firstNumber * secondNumber;
            break;
        case "/":
            result = firstNumber / secondNumber;
            break;
    }
            return result;

}

static String getArabValue(int intResult) {
        String romanResult;
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        if (intResult == 100) { romanResult = "C"; }
        else if (intResult >= 10) { romanResult = tens[(intResult % 100) / 10] + units[intResult % 10]; }
        else { romanResult = units[intResult % 10];}

        return romanResult;
}
}
