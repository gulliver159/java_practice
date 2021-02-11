package net.thumbtack.school.threads.task17_18;

import java.util.*;

public class Task implements Executable {
    private final String nameTask;

    private List<Executable> tasks;

    public Task(String nameTask, List<Executable> tasks) {
        this.nameTask = nameTask;
        this.tasks = tasks;
    }

    public Task(String nameTask) {
        this.nameTask = nameTask;
        this.tasks = Collections.synchronizedList(new LinkedList<>());
    }

    public void execute() {
        System.out.println("Выполняется задача: " + nameTask);
    }

    public List<Executable> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "nameTask='" + nameTask + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(nameTask, task.nameTask) && Objects.equals(tasks, task.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTask, tasks);
    }
}