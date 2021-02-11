package net.thumbtack.school.functional;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class Task13_14Test {

    @Test
    void testTransformTask13() {
        List<Integer> expectedStream = IntStream.of(55, 65, 75, 85, 95, 105, 115, 125).boxed().collect(Collectors.toList());
        assertEquals(expectedStream,
                Task13_14.transformTask13(IntStream.of(50, 60, 70, 80, 90, 100, 110, 120), x -> x + 5).boxed().collect(Collectors.toList()));
    }

    @Test
    void testtTransformTask14() {
        List<Integer> expectedStream = IntStream.of(55, 65, 75, 85, 95, 105, 115, 125).boxed().collect(Collectors.toList());
        assertEquals(expectedStream,
                Task13_14.transformTask14(IntStream.of(50, 60, 70, 80, 90, 100, 110, 120), x -> x + 5).boxed().collect(Collectors.toList()));
    }
}