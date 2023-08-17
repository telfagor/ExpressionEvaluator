package com.iongroup;

import com.iongroup.calculator.Calculator;

public class App {
    public static void main(String[] args) {
        String expr = "(28 + 4) * (34 - 11)";
        Calculator calculator = new Calculator();

        int result = calculator.calculate(expr);
        System.out.println("result = " + result);
    }
}
