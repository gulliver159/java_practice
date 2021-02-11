package net.thumbtack.school.threads;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//Будет ли эта реализация хуже ConcurrentHashMap, и если да, то почему ?
// Ну во-первых, очень маленькое кол-во методов:)
// Во-вторых, во время записи или удаления по какому-то ключу, я блокирую всю hashMap, а не только этот участок (сегмент),
// как в настоящем ConcurrentHashMap



class ConcurrentHashMap<K, V> {
    private HashMap<K, V> hashMap = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public V put(K key, V value) {
        lock.writeLock().lock();
        try {
            hashMap.put(key, value);
            System.out.println("Writer thread " + Thread.currentThread().getId() + " put value " + value + " on key " + key);
        } finally {
            System.out.println("Write lock unlocked by thread " + Thread.currentThread().getId());
            lock.writeLock().unlock();
        }
        return value;
    }

    public V remove(K key) {
        lock.writeLock().lock();
        V value;
        try {
            value = hashMap.remove(key);
            System.out.println("Writer thread " + Thread.currentThread().getId() + " remove value " + value + " on key " + key);
        } finally {
            System.out.println("Write lock unlocked by thread " + Thread.currentThread().getId());
            lock.writeLock().unlock();
        }
        return value;
    }

    public V get(K key) {
        lock.readLock().lock();
        V value;
        try {
            value = hashMap.get(key);
            System.out.println("Reader thread " + Thread.currentThread().getId() + " get value " + value + " on key " + key);
        } finally {
            System.out.println("Read lock unlocked by thread " + Thread.currentThread().getId());
            lock.readLock().unlock();
        }
        return value;
    }
}

public class Task12 {

    public static void main(String[] args) {
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        Runnable getTask = () -> {
            concurrentHashMap.get(2);
        };
        Runnable putTask = () -> {
            concurrentHashMap.put(2, "Два");
        };
        Runnable removeTask = () -> {
            concurrentHashMap.remove(2);
        };

        for (int i = 0; i < 100; i++) {
            new Thread(getTask).start();
            new Thread(putTask).start();
            new Thread(removeTask).start();
        }
    }
}
