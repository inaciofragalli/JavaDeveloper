package calculator.core;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {
    private final ArrayList<String> tokens = new ArrayList<>();
    private final Calculator calculator;
    private final VariableStore variables;

    public InputProcessor(Calculator calculator, VariableStore variables) {
        this.calculator = calculator;
        this.variables = variables;
    }

    public InputStatus process(String input) {
        String[] split = input.trim().split("=", 2);
        String name = split[0].trim();
        String rawValue = split[1].trim();

        if (rawValue.matches("[+-]?\\d+")) {
            variables.set(name, Integer.parseInt(rawValue));
            return InputStatus.ASSIGNMENT;
        }

        Integer value = variables.get(rawValue);
        if (value != null) {
            variables.set(name, value);
            return InputStatus.ASSIGNMENT;
        }

        return InputStatus.UNKNOWN_VARIABLE;
    }

    public void tokenize(String input) {
        tokens.clear();

        Matcher matcher = Pattern.compile("[+\\-]+").matcher(input);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(result, " " + (countMinus(matcher.group()) % 2 == 0 ? "+" : "-") + " ");
        }
        matcher.appendTail(result);
        input = result.toString();

        String[] parts = input.split("\\s+");
        for (String part : parts) {
            if (!part.isEmpty()) {
                tokens.add(part);
            }
        }
        calculator.loadExpression(tokens);
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

    public boolean validCommand(String input) {
        return input.matches("/exit|/help");
    }

    public boolean isVariable(String input) {
        return input.matches("^[a-zA-Z]+\\s*=\\s*([+-]?\\d+|[a-zA-Z]+)$");
    }

    public boolean invalidIdentifier(String input) {
        if (!input.contains("=")) {
            return false;
        }
        String left = input.trim().split("=", 2)[0].trim();
        return !left.matches("[a-zA-Z]+");
    }

    public boolean invalidExpression(String input) {
        return !isVariable(input) && (input.matches(".*[+\\-]$")
                || input.matches(".*\\d\\s+\\d.*"));
    }
}
