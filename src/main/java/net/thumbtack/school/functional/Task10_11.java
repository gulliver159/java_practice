package net.thumbtack.school.functional;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface MyFunction<T, K> {
    K apply(T arg);
//    K apply(T arg1, T arg2);
}
public class Task10_11 {

    public static MyFunction<String, List<String>> split = s -> Arrays.asList(s.split(" "));
    public static MyFunction<List<?>, Integer> count = List::size;

}
