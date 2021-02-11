package net.thumbtack.school.functional;

import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class Task7_9 {

    public static Supplier<Date> getCurrentDate = () -> new Date();

    public static Predicate<Integer> isEven = a -> a % 2 == 0;

    public static BiPredicate<Integer, Integer> areEven = (a, b) -> a % 2 == 0 && b % 2 == 0;

}
