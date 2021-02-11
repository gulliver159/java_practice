package net.thumbtack.school.threads;

class MyThread3 extends Thread {
    private String name;

    public MyThread3(String name) {
        this.name = name;
    }

    public void run() {
        try {
            for(int i = 0; i < 3; i++) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException e) {
            System.out.println(name + " interrupted");
        }

        System.out.println(name + " exiting");
    }
}

public class Task3 {
    public static void main(String[] args) {
        MyThread3 threadFirst = new MyThread3("First");
        MyThread3 threadSecond = new MyThread3("Second");
        MyThread3 threadThird = new MyThread3("Third");

        threadFirst.start();
        threadSecond.start();
        threadThird.start();

        try {
            threadFirst.join();
            threadSecond.join();
            threadThird.join();
        }
        catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

        System.out.println("Main thread exiting");
    }
}