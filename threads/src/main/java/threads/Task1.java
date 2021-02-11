package net.thumbtack.school.threads;

public class Task1 {
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println("Id = " + t.getId());
        System.out.println("Name = " + t.getName());
        System.out.println("Priority = " + t.getPriority());
        System.out.println("Stack Trace = " + t.getStackTrace());
        System.out.println("State = " + t.getState());
        System.out.println("Thread Group = " + t.getThreadGroup());
        System.out.println("Uncaught Exception Handler = " + t.getUncaughtExceptionHandler());
    }
}
