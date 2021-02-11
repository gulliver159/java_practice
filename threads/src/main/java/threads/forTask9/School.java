package net.thumbtack.school.threads.forTask9;

import net.thumbtack.school.tasks1_10.ttschool.TrainingErrorCode;
import net.thumbtack.school.tasks1_10.ttschool.TrainingException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class School {
    private String name;
    private int year;
    private final Set<Group> groups;
    private final Lock lock;

    public School(String name, int year, Lock lock) throws TrainingException {
        this.lock = lock;
        try {
            this.lock.lock();
            setName(name);
            setYear(year);
            groups = Collections.synchronizedSet(new HashSet<>());
        }
        finally {
            this.lock.unlock();
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) throws TrainingException {
        try {
            lock.lock();
            if (name == null || name.equals("")) {
                throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
            }
            this.name = name;
        }
        finally {
            this.lock.unlock();
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        try {
            lock.lock();
            this.year = year;
        }
        finally {
            this.lock.unlock();
        }
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) throws TrainingException {
        try {
            lock.lock();
            System.out.println("Thread " + Thread.currentThread().getId() + " add group");
//        if (containsGroup(group)) {
//            throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
//        }
            groups.add(group);
            System.out.println("Lock unlocked by thread " + Thread.currentThread().getId());
        }
        finally {
            this.lock.unlock();
        }
    }

    public void removeGroup(Group group) throws TrainingException {
        try {
            lock.lock();
            if (!groups.remove(group)) {
                throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    public void removeGroup(String name) throws TrainingException {
        try {
            lock.lock();
            if (!groups.removeIf(group -> group.getName().equals(name))) {
                throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    public boolean containsGroup(Group group) {
        try {
            lock.lock();
            for (Group gr : groups) {
                if (gr.getName().equals(group.getName())) {
                    return true;
                }
            }
        }
        finally {
            this.lock.unlock();
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return year == school.year &&
                Objects.equals(name, school.name) &&
                Objects.equals(groups, school.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, groups);
    }
}
