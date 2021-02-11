package net.thumbtack.school.threads;

import java.util.concurrent.Semaphore;

class Book {
    static Semaphore semReader = new Semaphore(0);
    static Semaphore semWriter = new Semaphore(1);

    public void get() {
        try {
            semReader.acquire();
            System.out.println("Reader get book");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
        finally {
            semWriter.release();
        }
    }

    public void put() {
        try {
            semWriter.acquire();
            System.out.println("Writer put book");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
        finally {
            semReader.release();
        }
    }
}

class Reader extends Thread {
    private Book b;

    public Reader(Book b) {
        this.b = b;
    }

    public void run() {
        while(true) {
            b.get();
        }
    }
}

class Writer extends Thread {
    private Book b;

    public Writer(Book b) {
        this.b = b;
    }

    public void run() {
        while(true) {
            b.put();
        }
    }
}

public class Task8 {
    public static void main(String[] args) {
        Book b = new Book();
        new Reader(b).start();
        new Writer(b).start();
    }
}
