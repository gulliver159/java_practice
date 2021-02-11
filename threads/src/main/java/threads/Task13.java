package net.thumbtack.school.threads;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class Formatter {
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    public void set(SimpleDateFormat simpleDateFormat) {
        threadLocal.set(simpleDateFormat);
    }

    public void format(String name, Date date) {
        System.out.println("Thread " + name + " " + threadLocal.get().format(date));
    }
}

class TestThread extends Thread {
    private String name;
    private Formatter formatter;
    private Date date;
    private SimpleDateFormat simpleDateFormat;

    public TestThread(String name, Formatter formatter, Date date, SimpleDateFormat simpleDateFormat) {
        this.name = name;
        this.formatter = formatter;
        this.date = date;
        this.simpleDateFormat = simpleDateFormat;
    }

    @Override
    public void run() {
        formatter.set(simpleDateFormat);
        formatter.format(name, date);
    }
}

public class Task13 {
    public static void main(String[] args) {
        Formatter formatter = new Formatter();

        new TestThread("First", formatter, new Date(88, Calendar.FEBRUARY, 9),
                new SimpleDateFormat("yyyy-MM-dd")).start();
        new TestThread("Second",formatter, new Date(25, Calendar.JULY, 30),
                new SimpleDateFormat("yyyy.MM.dd")).start();
        new TestThread("Third",formatter, new Date(79, Calendar.JANUARY, 2),
                new SimpleDateFormat("yyyy^MM^dd")).start();
        new TestThread("Four",formatter, new Date(99, Calendar.OCTOBER, 1),
                new SimpleDateFormat("yyyy:MM:dd")).start();
        new TestThread("Five",formatter, new Date(75, Calendar.NOVEMBER, 12),
                new SimpleDateFormat("yyyy=MM=dd")).start();
    }
}
