package net.thumbtack.school.functional;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class Task7_9Test {

    @Test
    void testTask8() {
        assertAll(
                () -> assertEquals(true, Task7_9.isEven.test(4)),
                () -> assertEquals(false, Task7_9.isEven.test(7))
        );
    }

    @Test
    void testTask9() {
        assertAll(
                () -> assertEquals(true, Task7_9.areEven.test(4, 6)),
                () -> assertEquals(false, Task7_9.areEven.test(7 , 8)),
                () -> assertEquals(false, Task7_9.areEven.test(10 , 3)),
                () -> assertEquals(false, Task7_9.areEven.test(5 , 11))
        );
    }

}