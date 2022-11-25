import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws Exception {
        String[] romeNumbers= {"I","II","III","IV","V","VI","VII","IX","X"};

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringParser parser = new StringParser();

        ParsedString parsedString = parser.Parse(reader.readLine());

        System.out.println(parsedString.firstString);
        System.out.println(parsedString.secondString);
        System.out.println(parsedString.arithmeticSign);
    }
}
class ParsedString
{
    String firstString;
    String secondString;
    String arithmeticSign;
}

class StringParser
{
    public ParsedString Parse(String str) throws Exception {
            ParsedString result = new ParsedString();
            String finalStr = str.replaceAll("\\s+", "");
            String tmpString = "";
            boolean arithmeticSignExistFlag=false;
            for (var i = 0; i < finalStr.length(); i++) {
                if (finalStr.toCharArray()[i] == '+' ||
                        finalStr.toCharArray()[i] == '-' ||
                        finalStr.toCharArray()[i] == '/' ||
                        finalStr.toCharArray()[i] == '*') {
                    arithmeticSignExistFlag=true;
                    result.arithmeticSign = String.valueOf(finalStr.toCharArray()[i]);
                    result.firstString = tmpString;
                    tmpString = "";

                    for (var j = i + 1; j < finalStr.length(); j++) {
                        if (finalStr.toCharArray()[j] == '+' ||
                                finalStr.toCharArray()[j] == '-' ||
                                finalStr.toCharArray()[j] == '/' ||
                                finalStr.toCharArray()[j] == '*') {
                            throw new Exception("input not correct, more that one arithmetic sign");
                        } else {
                            tmpString = tmpString + finalStr.toCharArray()[j];
                        }
                    }
                    result.secondString = tmpString;
                    break;
                } else {
                    tmpString = tmpString + finalStr.toCharArray()[i];
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
    };
}

