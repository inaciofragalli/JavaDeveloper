package calculator.logic;

import java.util.ArrayList;

public class Calculator {
    private ArrayList<String> calculation;

    public Calculator() {
        this.calculation = new ArrayList<>();
    }

    protected void setNumbers(ArrayList<String> filtered) {
        this.calculation = filtered;
    }

    public void compute() {
        int result = 0;
        int operand = 1;
        for (String s : calculation) {
            if (s.matches("\\d+")) {
                result += (Integer.parseInt(s) * operand);
            } else if (s.matches("\\+")) {
                operand = 1;
            } else if (s.matches("-")) {
                operand = -1;
            } else if (s.matches("-\\d+") || s.matches("\\+\\d+")) {
                result += (Integer.parseInt(s));
            }
        }

        System.out.println(result);
    }
}
