package calculator.ui;

import java.util.Scanner;
import calculator.logic.InputFilter;
import calculator.logic.Calculator;

public class TextInterface {
    private Scanner scanner;
    private Calculator calculator;
    private InputFilter filter;

    public TextInterface() {
        this.scanner = new Scanner(System.in);
        this.calculator = new Calculator();
        this.filter = new InputFilter(this.calculator);
    }

    public void start() {
        loop:
        while (true) {
            String input = scanner.nextLine();

            switch (handleInput(input)) {
                case "exit" -> { break loop; }
                case "unknown" -> System.out.println("Unknown command");
                case "invalid" -> System.out.println("Invalid expression");
                case "ok" -> {
                    filter.filter(input);
                    calculator.compute();
                }
            }
        }
    }

    private void help() {
        System.out.println("The program calculates the sum of numbers and subtractions");
        System.out.println("Valid expression: {number} {operator} and so on. However single numbers are also valid.");
        System.out.println("Type /exit to exit");
    }

    private String handleInput(String input) {
        if (input.isEmpty()) {
            return "skip";
        }
        if (filter.validCommand(input)) {
            if (input.equals("/exit")) {
                return "exit";
            }
            help();
            return "skip";
        }
        if (input.startsWith("/")) {
            return "unknown";
        }
        if (filter.invalidExpression(input)) {
            return "invalid";
        }
        return "ok";
    }
}