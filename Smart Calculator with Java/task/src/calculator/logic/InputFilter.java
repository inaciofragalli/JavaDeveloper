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

    private int countMinus(String operators) {
        int count = 0;
        for (char c : operators.toCharArray()) {
            if (c == '-') {
                count++;
            }
        }
        return count;
    }

    public ArrayList<String> getFiltered() {
        return filtered;
    }

    public boolean invalidExpression(String input) {
        return input.matches("^[a-zA-Z].*")
                || input.matches(".*[+\\-]$")
                || input.matches(".*\\d\\s+\\d.*");
    }

    public boolean validCommand(String input) {
        return input.matches("/exit|/help");
    }
}
