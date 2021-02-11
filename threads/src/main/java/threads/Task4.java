package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.List;

class Action4 {
    public static void add(List<Integer> list) {
        synchronized (list) {
            System.out.println(list.size());
            list.add((int) (Math.random() * 10000));
            System.out.println("Adding add number");
        }
    }

    public static void remove(List<Integer> list) {
        synchronized (list) {
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
}


class Adding extends Thread {
    private List<Integer> list;

    public Adding(List<Integer> list) {
        this.list = list;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            Action4.add(list);
        }
        System.out.println("Adding exiting");
    }
}

class Removing extends Thread {
    private List<Integer> list;

    public Removing(List<Integer> list) {
        this.list = list;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            Action4.remove(list);
        }
        System.out.println("Removing exiting");
    }
}


public class Task4 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        Adding adding = new Adding(list);
        Removing removing = new Removing(list);

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
