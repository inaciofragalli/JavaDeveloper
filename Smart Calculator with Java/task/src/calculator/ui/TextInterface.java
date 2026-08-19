package calculator.ui;

import java.util.Scanner;
import calculator.core.Calculator;
import calculator.core.InputProcessor;
import calculator.core.InputStatus;
import calculator.core.VariableStore;

public class TextInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final VariableStore variables = new VariableStore();
    private final Calculator calculator = new Calculator(variables);
    private final InputProcessor processor = new InputProcessor(calculator, variables);

    public void start() {
        loop:
        while (true) {
            String input = scanner.nextLine().trim();

            switch (handleInput(input)) {
                case EXIT -> {
                    System.out.println("Bye!");
                    break loop;
                }
                case HELP -> help();
                case UNKNOWN_COMMAND -> System.out.println("Unknown command");
                case INVALID_EXPRESSION -> System.out.println("Invalid expression");
                case INVALID_IDENTIFIER -> System.out.println("Invalid identifier");
                case INVALID_ASSIGNMENT -> System.out.println("Invalid assignment");
                case UNKNOWN_VARIABLE -> System.out.println("Unknown variable");
                case VARIABLE_VALUE -> System.out.println(variables.get(input));
                case EXPRESSION -> {
                    Integer result = calculator.compute();
                    System.out.println(result == null ? "Unknown variable" : result);
                }
                case ASSIGNMENT, SKIP -> {
                }
            }
        }
    }

    private void help() {
        System.out.println("The program calculates the sum of numbers and subtractions");
        System.out.println("Valid expression: {number} {operator} and so on. However single numbers are also valid.");
        System.out.println("Type /exit to exit");
    }

    private InputStatus handleInput(String input) {
        if (input.isEmpty()) {
            return InputStatus.SKIP;
        }
        if (processor.validCommand(input)) {
            return input.equals("/exit") ? InputStatus.EXIT : InputStatus.HELP;
        }
        if (input.startsWith("/")) {
            return InputStatus.UNKNOWN_COMMAND;
        }
        if (input.matches("[a-zA-Z]+")) {
            return variables.has(input) ? InputStatus.VARIABLE_VALUE : InputStatus.UNKNOWN_VARIABLE;
        }
        if (processor.isVariable(input)) {
            return processor.process(input);
        }
        if (processor.invalidIdentifier(input)) {
            return InputStatus.INVALID_IDENTIFIER;
        }
        if (input.contains("=") && !processor.isVariable(input)) {
            return InputStatus.INVALID_ASSIGNMENT;
        }
        if (input.matches("[a-zA-Z]+\\d+\\w*")) {
            return InputStatus.INVALID_IDENTIFIER;
        }
        if (processor.invalidExpression(input)) {
            return InputStatus.INVALID_EXPRESSION;
        }
        processor.tokenize(input);
        return InputStatus.EXPRESSION;
    }
}
