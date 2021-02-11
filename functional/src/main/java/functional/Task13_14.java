package net.thumbtack.school.functional;

import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Task13_14 {

    public static IntStream transformTask13(IntStream stream, IntUnaryOperator op) {
        return stream.map(op);
    }

    public static IntStream transformTask14(IntStream stream, IntUnaryOperator op) {
        return stream.parallel().map(op);
    }

    public static void main(String[] args) {
        transformTask13(IntStream.of(50, 60, 70, 80, 90, 100, 110, 120), x -> x + 5).forEach(System.out::println);
        System.out.println();
        transformTask14(IntStream.of(50, 60, 70, 80, 90, 100, 110, 120), x -> x + 5).forEach(System.out::println);
    }
}
