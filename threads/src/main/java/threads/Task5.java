package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.List;

enum Type {
    ADDING,
    REMOVING
}

class Action5 {
    private final List<Integer> list;

    public Action5(List<Integer> list) {
        this.list = list;
    }

    public synchronized void add() {
        System.out.println(list.size());
        list.add((int) (Math.random() * 10000));
        System.out.println("Adding add number");
    }

    public synchronized void remove() {
        System.out.println(list.size());
        if(list.size() != 0) {
            list.remove((int) (Math.random() * list.size()));
            System.out.println("Removing remove number");
        }
        else {
            System.out.println("Nothing");
        }
    }
}

class MyThread extends Thread {
    private final Type type;
    private final Action5 action5;

    public MyThread(Type type, Action5 action5) {
        this.type = type;
        this.action5 = action5;
    }

    public void run() {
        if (type == Type.ADDING) {
            for (int i = 0; i < 1000; i++) {
                action5.add();
            }
        }
        else {
            for (int i = 0; i < 1000; i++) {
                action5.remove();
            }
        }
    }
}


public class Task5 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Action5 action5 = new Action5(list);

        MyThread adding = new MyThread(Type.ADDING, action5);
        MyThread removing = new MyThread(Type.REMOVING, action5);

        adding.start();
        removing.start();

        try {
            adding.join();
            removing.join();
        }
        catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
    }
}
