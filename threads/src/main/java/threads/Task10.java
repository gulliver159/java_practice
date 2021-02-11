package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Action10 {
    public static void add(List<Integer> list) {
        System.out.println(list.size());
        list.add((int) (Math.random() * 10000));
        System.out.println("Adding add number");
    }

    public static void remove(List<Integer> list) {
        System.out.println(list.size());
        if (list.size() != 0) {
            list.remove((int) (Math.random() * list.size()));
            System.out.println("Removing remove number");
        }
        else {
            System.out.println("Nothing");
        }
    }
}


class Adding10 extends Thread {
    private List<Integer> list;
    private Lock lock;

    public Adding10(List<Integer> list, Lock lock) {
        this.list = list;
        this.lock = lock;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                lock.lock();
                Action10.add(list);
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Adding exiting");
    }
}

class Removing10 extends Thread {
    private List<Integer> list;
    private Lock lock;

    public Removing10(List<Integer> list, Lock lock) {
        this.list = list;
        this.lock = lock;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                lock.lock();
                Action10.remove(list);
            }
            finally {
                lock.unlock();
            }
        }
        System.out.println("Removing exiting");
    }
}


public class Task10 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        Lock lock = new ReentrantLock();

        Adding10 adding = new Adding10(list, lock);
        Removing10 removing = new Removing10(list, lock);

        adding.start();
        removing.start();

        try {
            adding.join();
            removing.join();
        }
        catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

        System.out.println("Main thread exiting");
    }
}
