package calculator.core;

import java.util.HashMap;

public class VariableStore {
    private final HashMap<String, Integer> variables = new HashMap<>();

    public void set(String name, int value) {
        variables.put(name, value);
    }

    public Integer get(String name) {
        return variables.get(name);
    }

    public boolean has(String name) {
        return variables.containsKey(name);
    }

    public boolean isEmpty() {
        return variables.isEmpty();
    }
}
