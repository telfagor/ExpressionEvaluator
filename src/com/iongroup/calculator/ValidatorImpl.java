package com.iongroup.calculator;

import java.util.Map;
import java.util.HashMap;

public class ValidatorImpl implements IValidator {

    @Override
    public void validateExpression(String expr) {
        if (!checkParentheses(expr)) {
            throw new InvalidExpressionException("The number of the parentheses are not equal!");
        }

        if (!checkOperationSequence(expr)) {
            throw new InvalidExpressionException("Two consecutive operations, or operation as last character are not allowed!");
        }

        if (!isArithmeticExpression(expr)) {
            throw new InvalidExpressionException("The expression is not arithmetic!");
        }

        if (!areTheParenthesesInTheRightOrder(expr)) {
            throw new InvalidExpressionException("The order of parentheses is incorrect!");
        }
    }

    private boolean checkParentheses(String expr) {
        int leftParenthesisCount = checkCharOccurrences(expr, "(");
        int rightParenthesisCount = checkCharOccurrences(expr, ")");

        return leftParenthesisCount == rightParenthesisCount;
    }

    private int checkCharOccurrences(String expr, String ch) {
        return expr.length() - expr.replace(ch, "").length();
    }

    private boolean checkOperationSequence(String expr) {
        boolean isCorrectSequence = true;
        for (int i = 0; i < expr.length() - 1; i++) {
            if (isOperation(expr.charAt(i)) && isOperation(expr.charAt(i + 1))) {
                isCorrectSequence = false;
                break;
            }
        }

        return isCorrectSequence;
    }

    private boolean isOperation(char ch) {
        return String.valueOf(ch).matches("[+\\-*/]");
    }

    private boolean isArithmeticExpression(String expr) {
        return expr.matches("[()+\\-*/\\d]+");

    }

    private boolean areTheParenthesesInTheRightOrder(String expr) {
        int countOfOpenParentheses = expr.length() - expr.replace("(", "").length();

        int[] indexesOfOpenParentheses = new int[countOfOpenParentheses];
        int[] indexesOfClosedParentheses = new int[countOfOpenParentheses];

        int index = 0;
        int index2 = 0;
        char[] charArray = expr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '(') {
                indexesOfOpenParentheses[index++] = i;
            } else if (charArray[i] == ')') {
                indexesOfClosedParentheses[index2++] = i;
            }
        }

        for (int i = 0; i < indexesOfOpenParentheses.length; i++) {
            if (indexesOfClosedParentheses[i] < indexesOfOpenParentheses[i]) {
                return false;
            }
        }
        return true;
    }
}
