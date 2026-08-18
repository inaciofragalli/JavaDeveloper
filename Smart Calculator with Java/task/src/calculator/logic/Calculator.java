package calculator.logic;

import java.util.ArrayList;
import java.util.HashMap;

public class Calculator {
    private ArrayList<String> calculation;
    private HashMap<String, String> variables;

    public Calculator() {
        this.calculation = new ArrayList<>();
        this.variables = new HashMap<>();
    }

    protected void setNumbers(ArrayList<String> filtered) {
        this.calculation = filtered;
    }


    protected void setVariables(String var, String value) {
        this.variables.put(var, value);
    }

    public void compute() {
        if (!variables.isEmpty()) {
            swapVarCalc(variables, calculation);
        }

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

    void swapVarCalc(HashMap<String, String> variables, ArrayList<String> calc) {
        for (String s : calc) {
            if (variables.containsKey(s)) {
                int index = calc.indexOf(s);

                if (variables.get(s).matches("[a-zA-Z]")) {
                    calc.set(index, variables.get(variables.get(s)));
                    continue;
                }

                calc.set(index, variables.get(s));
            }
        }

        System.out.println(calc);
    }

    protected void setNumbers(ArrayList<String> filtered) {
        this.calculation = filtered;
    }

    protected void setVariables(String var, String value) {
        this.variables.put(var, value);
    }

    public int getVarValue(String variable) {
        return Integer.parseInt(variables.get(variable));
    }

    public HashMap<String, String> getVariables() {
        return variables;
    }
}
