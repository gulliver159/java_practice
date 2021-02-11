package net.thumbtack.school.functional;

import java.util.*;
import java.util.stream.Collectors;

class PersonForTask15 {
    private final String name;
    private final Integer age;

    public PersonForTask15(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
public class Task15_17 {
    public static List<String> task15(List<PersonForTask15> persons) {
        return persons.stream().filter(p -> p.getAge() > 30).map(PersonForTask15::getName).distinct()
                .sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
    }

    public static List<String> task16(List<PersonForTask15> persons) {
        return new ArrayList<>(persons.stream().filter(p -> p.getAge() > 30)
                .collect(Collectors.groupingBy(PersonForTask15::getName, LinkedHashMap::new, Collectors.counting()))
                .keySet());
    }

    public static Integer sum(List<Integer> list) {
        return list.stream().reduce(0, Integer::sum);
    }

    public static Integer product(List<Integer> list) {
        return list.stream().reduce(1, (x, y) -> x * y);
    }
}
