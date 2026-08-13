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
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("/exit")) {
                break;
            } else if (input.isEmpty()) {
                continue;
            } else if (input.equals("/help")) {
                help();
                continue;
            }

            filter.filter(input);
            calculator.compute();
        }
        System.out.println("Bye!");
    }

    private void help() {
        System.out.println("The program calculates the sum of numbers and subtractions");
    }
}