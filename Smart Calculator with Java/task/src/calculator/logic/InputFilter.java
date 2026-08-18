package calculator.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFilter {
    private ArrayList<String> filtered;
    private Calculator calculator;

    public InputFilter(Calculator calculator) {
        this.filtered = new ArrayList<>();
        this.calculator = calculator;
    }

    public void filter(String input) {
        filtered.clear();

        if (isVariable(input)) {
            String[] split = input.trim().split("=");
            if (split[1].trim().matches("[a-zA-Z]")) {
                String variable = split[1].trim();
                if (calculator.getVariables().containsKey(variable)) {
                    calculator.setVariables(split[0].trim(), calculator.getVariables().get(variable));
                } else {
                    System.out.println("Invalid assingment");
                }
            } else {
                calculator.setVariables(split[0].trim(), split[1].trim());
            }

        } else if (input.matches("^[a-zA-Z]+\\s*=\\s*$")) {
            System.out.println("Invalid assignment");

        } else if (input.matches("[a-zA-Z]\\d+\\w*")) {
            System.out.println("Invalid identifier");
        } else {
            Matcher matcher = Pattern.compile("[+\\-]+").matcher(input);
            StringBuilder result = new StringBuilder();
            while (matcher.find()) {
                matcher.appendReplacement(result, countMinus(matcher.group()) % 2 == 0 ? "+" : "-");
            }
            matcher.appendTail(result);
            input = result.toString();
            String[] parts = input.split("\\s+");

            filtered.addAll(Arrays.asList(parts));
            calculator.setNumbers(filtered);
        }
    }

    private int countMinus(String operators) {
        int count = 0;
        for (char c : operators.toCharArray()) {
            if (c == '-') {
                count++;
            }
        }
        return count;
    }

    public boolean invalidExpression(String input) {
        return !isVariable(input) && ((input.matches(".*[+\\-]$")
                || input.matches(".*\\d\\s+\\d.*")));
    }

    public boolean validCommand(String input) {
        return input.matches("/exit|/help");
    }

    public boolean isVariable(String input) {
        return input.matches("^[a-zA-Z]+\\s*=\\s*(\\d+|[a-zA-Z]+)$");
    }
}
