package calculator;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MathematicalExpression {

    private static final String DELIMITER = " ";

    private Integer[] numbers;
    private Operator[] operators;

    public MathematicalExpression(String expression) {
        String[] arguments = splitExpression(expression);
        if (isNotValidExpression(arguments)) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_EXPECTED_ARGUMENT.getMessage());
        }
        this.numbers = filter(arguments, this::isNumber, Integer::parseInt).toArray(Integer[]::new);
        this.operators = filter(arguments, a -> !isNumber(a), Operator::of).toArray(Operator[]::new);
    }

    private String[] splitExpression(String expression) {
        if (Objects.isNull(expression) || expression.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.EMPTY_OR_NULL_ARGUMENT.getMessage());
        }
        return expression.split(DELIMITER);
    }

    private <T> Stream<T> filter(String[] arguments, Predicate<String> isNumberOrNot, Function<String, T> mapper) {
        return Arrays.stream(arguments)
                .filter(isNumberOrNot)
                .map(mapper);
    }

    private boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isNotValidExpression(String[] arguments) {
        if (arguments.length % 2 == 0) {
            return true;
        }
        return IntStream.range(0, arguments.length)
                .anyMatch(i -> (i % 2 == 0) != isNumber(arguments[i]));
    }

    public Integer[] getNumbers() {
        return numbers;
    }

    public Operator[] getOperators() {
        return operators;
    }
}
