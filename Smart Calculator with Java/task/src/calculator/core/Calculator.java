package calculator.core;

import java.util.ArrayList;

public class Calculator {
    private ArrayList<String> expression;
    private final VariableStore variables;

    public Calculator(VariableStore variables) {
        this.variables = variables;
    }

    public void loadExpression(ArrayList<String> expression) {
        this.expression = expression;
    }

    public Integer compute() {
        try {
            int result = 0;
            int operand = 1;
            for (String token : expression) {
                if (token.matches("\\d+")) {
                    result += Integer.parseInt(token) * operand;
                } else if (token.matches("[+-]")) {
                    operand = token.equals("-") ? -1 : 1;
                } else if (token.matches("[+-]\\d+")) {
                    result += Integer.parseInt(token);
                } else if (variables.has(token)) {
                    result += variables.get(token) * operand;
                } else {
                    return null;
                }
            }
            return result;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}