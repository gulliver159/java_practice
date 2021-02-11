package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Action6 {
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


class Adding6 extends Thread {
    private List<Integer> list;

    public Adding6(List<Integer> list) {
        this.list = list;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            list.add((int) Math.random() * 10000);
            // Функционал добавления числа в список, используя Collections.synchronizedList, возможно реализовать
        }
        System.out.println("Adding exiting");
    }
}

class Removing6 extends Thread {
    private List<Integer> list;

    public Removing6(List<Integer> list) {
        this.list = list;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            Action6.remove(list);

            // list.remove((int) (Math.random() * list.size()));
            // Такой вариант будет выбрасывать исключение, когда list пустой

//            if (list.size() != 0) {
//                list.remove((int) (Math.random() * list.size()));
//            }
            //В данном варианте 3 атомарные операции присутствуют, но в сумме они не являются атомарной операцией,
            // соответственно не синхронизированны

            //Соответственно, реализовать функционал удаления из списка, используя Collections.synchronizedList,
            // не представляется возможным
        }
        System.out.println("Removing exiting");
    }
}


public class Task6 {

    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        Adding6 adding = new Adding6(list);
        Removing6 removing = new Removing6(list);

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
