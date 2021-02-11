package net.thumbtack.school.threads;


import net.thumbtack.school.tasks1_10.ttschool.TrainingException;
import net.thumbtack.school.threads.forTask9.Group;
import net.thumbtack.school.threads.forTask9.School;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task9 {

    public static void main(String[] args) throws TrainingException {
        Lock lock1 = new ReentrantLock();
        School school1 = new School("Thumbtack", 1999, lock1);
        Group group1 = new Group("Web", "A-16", lock1);
        Group group2 = new Group("FullStack", "B-16", lock1);

        Runnable addGroupTask = () -> {
            try {
                school1.addGroup(group1);
            } catch (TrainingException e) {
                e.printStackTrace();
            }
        };
        Runnable updateGroupTask = () -> {
            for (Group group : school1.getGroups()) {
                group.reverseTraineeList();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(addGroupTask).start();
            new Thread(updateGroupTask).start();
        }
    }
}
