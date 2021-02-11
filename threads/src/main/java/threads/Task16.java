package net.thumbtack.school.threads;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

interface Executable {
    void execute();
}

class Task implements Executable {
    private final String nameTask;

    public Task(String nameTask) {
        this.nameTask = nameTask;
    }

    public void execute() {
        System.out.println("Выполняется задача: " + nameTask);
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "nameTask='" + nameTask + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(nameTask, task.nameTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTask);
    }
}

class Developer implements Runnable {
    private final BlockingQueue<Executable> queue;
    private final int count;
    private final String name;

    public Developer(BlockingQueue<Executable> queue, int count, String name) {
        this.queue = queue;
        this.count = count;
        this.name = name;
    }

    public void run() {
        System.out.println("Producer " + name + " Started");
        try {
            for (int i = 0; i < count; i++) {
                Executable task = new Task("Task" + (i + 1));
                queue.put(task);
                System.out.println("Producer " + name + " added: " + task + " Totally Dates in queue = " + queue.size());
                Thread.sleep(50);
            }
            System.out.println("Producer " + name + " finished");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class Executor implements Runnable {
    private final BlockingQueue<Executable> queue;
    private final String name;

    public Executor(BlockingQueue<Executable> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    public void run() {
        System.out.println("Consumer " + name + " Started");
        while (true) {
            try {
                Executable task = queue.take();
                if (task.equals(new Task("End"))) {
                    System.out.println("Consumer " + name + " finished");
                    break;
                }
                System.out.println("Consumer " + name + " retrieved: " + task);
                task.execute();
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

public class Task16 {

    public static void main(String[] args) {
        BlockingQueue<Executable> queue = new LinkedBlockingQueue<>();
        Thread producer1 = new Thread(new Developer(queue, 10, "First"));
        Thread producer2 = new Thread(new Developer(queue, 10, "Second"));
        Thread consumer1 = new Thread(new Executor(queue, "First"));
        Thread consumer2 = new Thread(new Executor(queue, "Second"));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        try {
            producer1.join();
            producer2.join();
            queue.put(new Task("End"));
            queue.put(new Task("End"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
