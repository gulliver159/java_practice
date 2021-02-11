package net.thumbtack.school.threads.task17_18;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Developer18 implements Runnable {
    private final BlockingQueue<Executable> queue;
    private final BlockingQueue<NoticeType> queueNotices;
    private final int count;
    private final String name;

    public Developer18(BlockingQueue<NoticeType> queueNotices, BlockingQueue<Executable> queue,
                       int count, String name) {
        this.queueNotices = queueNotices;
        this.queue = queue;
        this.count = count;
        this.name = name;
    }

    public void run() {
        System.out.println("Producer " + name + " Started");
        try {
            queueNotices.put(NoticeType.DEVELOPER_START);
            for (int i = 0; i < count; i++) {
                if (Math.random() < 0.5) {
                    List<Executable> tasks = Collections.synchronizedList(new LinkedList<>());
                    int numberOfSubtasks = 1 + (int) (Math.random() * 4);
                    for (int k = 0; k < numberOfSubtasks; k++) {
                        tasks.add(new Subtask("Subtask" + (i + 1) + (k + 1)));
                    }

                    Executable task = new Task("Task" + (i + 1), tasks);
                    queue.put(task);
                    queueNotices.put(NoticeType.TASK_DELIVERY);
                    System.out.println("Producer " + name + " added: " + task + " Totally Dates in queue = " + queue.size());
                    Thread.sleep(50);
                }
                else {
                    new Thread(new Developer18(queueNotices, queue, 5, "Sub" + name)).start();
                }
            }
            System.out.println("Producer " + name + " finished");
            queueNotices.put(NoticeType.DEVELOPER_FINISH);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}


class Executor18 implements Runnable {
    private final BlockingQueue<Executable> queue;
    private final BlockingQueue<NoticeType> queueNotices;
    private final String name;

    public Executor18(BlockingQueue<NoticeType> queueNotices, BlockingQueue<Executable> queue, String name) {
        this.queueNotices = queueNotices;
        this.queue = queue;
        this.name = name;
    }

    public void run() {
        System.out.println("Consumer " + name + " Started");
        while (true) {
            try {
                Task task = (Task) queue.take();
                if (task.equals(new Task("End"))) {
                    System.out.println("Consumer " + name + " finished");
                    break;
                }

                Executable subtask = task.getTasks().remove(0);
                if (task.getTasks().size() != 0) {
                    queue.put(task);
                } else {
                    queueNotices.put(NoticeType.TASK_COMPLETE);
                }
                subtask.execute();

                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

public class Task18 {
    public static void main(String[] args) {
        BlockingQueue<Executable> queue = new LinkedBlockingQueue<>();
        BlockingQueue<NoticeType> queueNotices = new LinkedBlockingQueue<>();
        Thread developer1 = new Thread(new Developer18(queueNotices, queue, 2, "First"));
        Thread developer2 = new Thread(new Developer18(queueNotices, queue, 2, "Second"));
        Thread executor1 = new Thread(new Executor18(queueNotices, queue,"First"));
        Thread executor2 = new Thread(new Executor18(queueNotices, queue, "Second"));

        developer1.start();
        developer2.start();
        executor1.start();
        executor2.start();

        int numberStartedDevelopers = 0;
        int numberFinishedDevelopers = 0;
        int numberCompletedTasks = 0;
        int numberDeliveredTasks = 0;

        do {
            try {
                System.out.println("numberStartedDevelopers = " + numberStartedDevelopers);
                System.out.println("numberFinishedDevelopers = " + numberFinishedDevelopers);
                System.out.println("numberCompletedTasks = " + numberCompletedTasks);
                System.out.println("numberDeliveredTasks = " + numberDeliveredTasks);
                NoticeType notice = queueNotices.take();
                switch (notice) {
                    case DEVELOPER_START: numberStartedDevelopers++; break;
                    case DEVELOPER_FINISH: numberFinishedDevelopers++; break;
                    case TASK_COMPLETE: numberCompletedTasks++; break;
                    case TASK_DELIVERY: numberDeliveredTasks++; break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(numberDeliveredTasks != numberCompletedTasks || numberStartedDevelopers != numberFinishedDevelopers);


        try {
            queue.put(new Task("End"));
            queue.put(new Task("End"));

            developer1.join();
            developer2.join();
            executor1.join();
            executor2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
