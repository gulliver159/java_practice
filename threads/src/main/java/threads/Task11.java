package net.thumbtack.school.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PrintPingPong11 {
    private Lock lock = new ReentrantLock();
    private Condition lastPing = lock.newCondition();
    private Condition lastPong = lock.newCondition();

    public void printPing() {
        lock.lock();
        try {
            System.out.println("ping");
            lastPing.signal();
            lastPong.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printPong() {
        lock.lock();
        try {
            System.out.println("pong");
            lastPong.signal();
            lastPing.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Ping11 extends Thread {
    private PrintPingPong11 p;

    public Ping11(PrintPingPong11 p) {
        this.p = p;
    }

    public void run() {
        while(true) {
            p.printPing();
        }
    }
}

class Pong11 extends Thread {
    private PrintPingPong11 p;

    public Pong11(PrintPingPong11 p) {
        this.p = p;
    }

    public void run() {
        while(true) {
            p.printPong();
        }
    }
}

public class Task11 {

    public static void main(String[] args) {
        PrintPingPong11 p = new PrintPingPong11();
        new Ping11(p).start();
        new Pong11(p).start();
    }
}
