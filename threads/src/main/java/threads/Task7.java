package net.thumbtack.school.threads;

import java.util.concurrent.Semaphore;

class PrintPingPong {
    static Semaphore semPing = new Semaphore(1);
    static Semaphore semPong = new Semaphore(0);

    public void printPing() {
        try {
            semPing.acquire();
            System.out.println("ping");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
        finally {
            semPong.release();
        }
    }

    public void printPong() {
        try {
            semPong.acquire();
            System.out.println("pong");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
        finally {
            semPing.release();
        }
    }
}

class Ping extends Thread {
    private PrintPingPong p;

    public Ping(PrintPingPong p) {
        this.p = p;
    }

    public void run() {
        while(true) {
            p.printPing();
        }
    }
}

class Pong extends Thread {
    private PrintPingPong p;

    public Pong(PrintPingPong p) {
        this.p = p;
    }

    public void run() {
        while(true) {
            p.printPong();
        }
    }
}

public class Task7 {

    public static void main(String[] args) {
        PrintPingPong p = new PrintPingPong();
        new Ping(p).start();
        new Pong(p).start();
    }
}
