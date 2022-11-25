import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringParser parser = new StringParser();
        ParsedString parsedString = parser.Parse(reader.readLine());
        ConsoleCalculator consoleCalculator = new ConsoleCalculator(parsedString);

        System.out.println(consoleCalculator.Calculate());
    }
}

class ParsedString {
    String firstString;
    String secondString;
    String arithmeticSign;
}

class StringParser {
    public ParsedString Parse(String str) throws Exception {
        ParsedString result = new ParsedString();
        String finalStr = str.replaceAll("\\s+", "");
        StringBuilder tmpString = new StringBuilder();
        boolean arithmeticSignExistFlag = false;
        for (var i = 0; i < finalStr.length(); i++) {
            if (finalStr.toCharArray()[i] == '+' ||
                    finalStr.toCharArray()[i] == '-' ||
                    finalStr.toCharArray()[i] == '/' ||
                    finalStr.toCharArray()[i] == '*') {
                arithmeticSignExistFlag = true;
                result.arithmeticSign = String.valueOf(finalStr.toCharArray()[i]);
                result.firstString = tmpString.toString();
                tmpString = new StringBuilder();

                for (var j = i + 1; j < finalStr.length(); j++) {
                    if (finalStr.toCharArray()[j] == '+' ||
                            finalStr.toCharArray()[j] == '-' ||
                            finalStr.toCharArray()[j] == '/' ||
                            finalStr.toCharArray()[j] == '*') {
                        throw new Exception("input not correct, more that one arithmetic sign");
                    } else {
                        tmpString.append(finalStr.toCharArray()[j]);
                    }
                }
                result.secondString = tmpString.toString();
                break;
            } else {
                tmpString.append(finalStr.toCharArray()[i]);
            }
        }
        if (!arithmeticSignExistFlag) {
            throw new Exception("input not correct, arithmetic sign not entered");
        }
        if (result.firstString.length() < 1) {
            throw new Exception("input not correct, first number not entered");
        }
        if (result.secondString.length() < 1) {
            throw new Exception("input not correct, second number not entered");
        }
        return result;
    }
}

class ConsoleCalculator {
    String[] romeInputNumbers = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    ParsedString parsedString;
    IntToRomanConverter converter = new IntToRomanConverter();

    public ConsoleCalculator(ParsedString parsedString) {
        this.parsedString = parsedString;
    }

    public String Calculate() throws Exception {
        int firstNumber = 0;
        int secondNumber = 0;
        boolean firstRomeNumberFlag = false;
        boolean secondRomeNumberFlag = false;
        int arithmeticResult = 0;
        String result = "";

        for (var i = 0; i < romeInputNumbers.length; i++) {
            if (Objects.equals(romeInputNumbers[i], parsedString.firstString)) {
                firstNumber = i + 1;
                firstRomeNumberFlag = true;

                for (var j = 0; j < romeInputNumbers.length; j++) {
                    if (Objects.equals(romeInputNumbers[j], parsedString.secondString)) {
                        secondNumber = j + 1;
                        secondRomeNumberFlag = true;
                    }
                }
            }
        }

        if ((firstRomeNumberFlag && !secondRomeNumberFlag) || (!firstRomeNumberFlag && secondRomeNumberFlag)) {
            throw new Exception("input not correct, numbers in different number systems");
        }

        if (firstRomeNumberFlag && secondRomeNumberFlag) {
            switch (parsedString.arithmeticSign) {
                case "+":
                    arithmeticResult = firstNumber + secondNumber;
                    break;
                case "-":
                    if (firstNumber < secondNumber) {
                        throw new Exception("input not correct, first number more then second number, there are no negative numbers in the roman numeral system");
                    } else {
                        arithmeticResult = firstNumber - secondNumber;
                    }
                    break;
                case "*":
                    arithmeticResult = firstNumber * secondNumber;
                    break;
                case "/":
                    arithmeticResult = firstNumber % secondNumber;
                    break;
            }
            result=converter.IntToRoman(arithmeticResult);
        }

        if (!firstRomeNumberFlag && !secondRomeNumberFlag) {
            try {
                firstNumber = Integer.parseInt(parsedString.firstString);
            } catch (NumberFormatException e) {
                throw new Exception("input not correct, first number is not a number");
            }

            try {
                secondNumber = Integer.parseInt(parsedString.secondString);
            } catch (NumberFormatException e) {
                throw new Exception("input not correct, second number is not a number");
            }

            if ((firstNumber > 10) || (secondNumber > 10) || (firstNumber < 1) || (secondNumber < 1)) {
                throw new Exception("input not correct, one of numbers more then 10 or less then 1");
            } else {
                switch (parsedString.arithmeticSign) {
                    case "+":
                        arithmeticResult = firstNumber + secondNumber;
                        break;
                    case "-":
                        arithmeticResult = firstNumber - secondNumber;
                        break;
                    case "*":
                        arithmeticResult = firstNumber * secondNumber;
                        break;
                    case "/":
                        arithmeticResult = firstNumber % secondNumber;
                        break;
                }
                result = String.valueOf(arithmeticResult);
            }
        }
        return result;
    }
}

class IntToRomanConverter {
    public String IntToRoman(int num) {
        String[] keys = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        var vals = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        while (ind < keys.length) {
            while (num >= vals[ind]) {
                var d = num / vals[ind];
                num = num % vals[ind];
                for (int i = 0; i < d; i++)
                    ret.append(keys[ind]);
            }
            ind++;
        }
        return ret.toString();
    }
}