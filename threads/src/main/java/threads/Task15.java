package net.thumbtack.school.threads;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Data {
    private final int[] myArray;

    public Data(int[] myArray) {
        this.myArray = myArray;
    }

    public int[] get() {
        return myArray;
    }

    @Override
    public String toString() {
        return "Data{" +
                "myArray=" + Arrays.toString(myArray) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Arrays.equals(myArray, data.myArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(myArray);
    }
}

class Producer implements Runnable {
    private final BlockingQueue<Data> queue;
    private final int count;
    private final String name;

    public Producer(BlockingQueue<Data> queue, int count, String name) {
        this.queue = queue;
        this.count = count;
        this.name = name;
    }

    public void run() {
        System.out.println("Producer " + name + " Started");
        try {
            for (int i = 0; i < count; i++) {
                Data data = new Data(new int[]{ i });
                queue.put(data);
                System.out.println("Producer " + name + " added: " + data + " Totally Dates in queue = " + queue.size());
                Thread.sleep(50);
            }
            System.out.println("Producer " + name + " finished");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Data> queue;
    private final String name;

    public Consumer(BlockingQueue<Data> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    public void run() {
        System.out.println("Consumer " + name + " Started");
        while (true) {
            try {
                Data data = queue.take();
                if (Arrays.equals(data.get(), new int[]{-1})) {
                    System.out.println("Consumer " + name + " finished");
                    break;
                }
                System.out.println("Consumer " + name + " retrieved: " + data);
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

public class Task15 {

    public static void main(String[] args) {
        BlockingQueue<Data> queue = new LinkedBlockingQueue<>();
        Thread producer1 = new Thread(new Producer(queue, 10, "First"));
        Thread producer2 = new Thread(new Producer(queue, 10, "Second"));
        Thread consumer1 = new Thread(new Consumer(queue, "First"));
        Thread consumer2 = new Thread(new Consumer(queue, "Second"));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        try {
            producer1.join();
            producer2.join();
            queue.put(new Data(new int[]{-1}));
            queue.put(new Data(new int[]{-1}));
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
