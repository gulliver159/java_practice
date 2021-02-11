package net.thumbtack.school.threads.task17_18;

public class Subtask implements Executable {
    private final String nameTask;

    public Subtask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void execute() {
        System.out.println("Выполняется задача: " + nameTask);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "nameTask='" + nameTask + '\'' +
                '}';
    }
}
