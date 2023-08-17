package com.iongroup.calculator;

import java.util.Stack;

public class Calculator {
    private final static IValidator VALIDATOR = new ValidatorImpl();
    Stack<Integer> operands = new Stack<>();
    Stack<Character> operators = new Stack<>();

    public int calculate(String str) {//11 * 18 + (20 - 2)
        String expr = formatExpression(str);
        VALIDATOR.validateExpression(expr);

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch)) {
                int digitCurrentIndex = i;
                while (digitCurrentIndex < expr.length() && Character.isDigit(expr.charAt(digitCurrentIndex))) {
                    digitCurrentIndex++;
                }
                int number = Integer.parseInt(expr.substring(i, digitCurrentIndex));
                i = digitCurrentIndex - 1;
                operands.push(number);
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    applyOperation(operands, operators.pop());
                }
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop(); //Delete (
                }
            } else if (isOperator(ch)) {
                while (!operators.isEmpty() && operatorsPrecedence(operators.peek()) >= operatorsPrecedence(ch)) {
                    applyOperation(operands, operators.pop());
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            applyOperation(operands, operators.pop());
        }
        return operands.pop();
    }

    private void applyOperation(Stack<Integer> stack, char operation) {
        int operand2 = stack.pop();
        int operand1 = stack.pop();
        switch (operation) {
            case '+' -> stack.push(operand1 + operand2);
            case '-' -> stack.push(operand1 - operand2);
            case '*' -> stack.push(operand1 * operand2);
            case '/' -> stack.push(operand1 / operand2);
            default -> throw new IllegalArgumentException("This operation " + operation + " does not exist!");
        }
        ;
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private int operatorsPrecedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }

        if (operator == '*' || operator == '/') {
            return 2;
        }

        return 0;
    }

    private String formatExpression(String expr) {
        String regexForNegative = "(?<![\\d)])-(?=\\d)";
        String regexForParentheses = "(?<=\\))(?=\\()";
        String regexForNegativeParentheses = "-\\(";

        return expr.replaceAll(" ", "").
                replaceAll(regexForNegative, "0-").
                replaceAll(regexForParentheses, "*").
                replaceAll(regexForNegativeParentheses, "0-(");
    }
}
