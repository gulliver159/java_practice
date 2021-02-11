package net.thumbtack.school.threads;

class MyThread2 extends Thread {
    public void run() {
        try {
            for(int i = 0; i < 3; i++) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException e) {
            System.out.println("Secondary interrupted");
        }

        System.out.println("Secondary exiting");
    }
}

public class Task2 {
    public static void main(String[] args) {
        MyThread2 thread = new MyThread2();
        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

        System.out.println("Main thread exiting");
    }
}
