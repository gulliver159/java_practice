package net.thumbtack.school.functional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

class PersonForTask5 {
    private final String name;
    public PersonForTask5(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonForTask5 that = (PersonForTask5) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

public class Task1_6 {
    public static Function<String, List<String>> splitTask1 = (String s) -> Arrays.asList(s.split(" "));
    public static Function<List<?>, Integer> countTask1 = (List<?> s) -> s.size();

    public static Function<String, List<String>> splitTask2 = s -> Arrays.asList(s.split(" "));
    public static Function<List<?>, Integer> countTask2 = s -> s.size();
    // Мы можем избавиться от декларации типов в параметрах функции,
    // так как они уже объявлены в diamond интерфейса, например: Function<String, List<String>>

    public static Function<String, List<String>> splitTask3 = s -> Arrays.asList(s.split(" "));
    public static Function<List<?>, Integer> countTask3 = List::size;
    // Существует 3 возможных варианта:
    // object::instanceMethod, например prefix::concat
    // Class::staticMethod, как в List::size
    // Class::instanceMethod, например String::toUpperCase, в данном случае, передается скрытый параметр this
    // Во всех трех вариантах, методы должны быть с соответствующей сигнатурой

    public static Function<String, Integer> splitAndCount1 = splitTask3.andThen(countTask3);
    public static Function<String, Integer> splitAndCount2 = countTask3.compose(splitTask3);
    // Таким способом можно получать множество различных функций, комбинированных разными способами
    // или передавать различное количество функций, не заботясь об их дальнейшем комбинировании

    public static Function<String, PersonForTask5> create1 = s -> new PersonForTask5(s);
    public static Function<String, PersonForTask5> create2 = PersonForTask5::new;

    public static BiFunction<Integer, Integer, Integer> max = Math::max;
}