package net.thumbtack.school.tasks1_10.ttschool;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class TraineeQueue {
    private final Queue<Trainee> queue;

    public TraineeQueue() {
        queue = new LinkedList<>();
    }

    public void addTrainee(Trainee trainee) {
        queue.add(trainee);
    }

    public Trainee removeTrainee() throws TrainingException {
        try {
            return queue.remove();
        } catch (NoSuchElementException e) {
            throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
