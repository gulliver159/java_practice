package net.thumbtack.school.threads.forTask9;

import net.thumbtack.school.tasks1_10.ttschool.TrainingErrorCode;
import net.thumbtack.school.tasks1_10.ttschool.TrainingException;

import java.util.*;
import java.util.concurrent.locks.Lock;

public class Group {
    private String name;
    private String room;
    private final List<Trainee> trainees;
    private final Lock lock;

    public Group(String name, String room, Lock lock) throws TrainingException {
        this.lock = lock;
        try {
            this.lock.lock();
            setName(name);
            setRoom(room);
            trainees = Collections.synchronizedList(new ArrayList<>());
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
                throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
            }
            this.name = name;
        }
        finally {
            this.lock.unlock();
        }
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) throws TrainingException {
        try {
            lock.lock();
            if (room == null || room.equals("")) {
                throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
            }
            this.room = room;
        }
        finally {
            this.lock.unlock();
        }
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void addTrainee(Trainee trainee) {
        try {
            lock.lock();
            trainees.add(trainee);
        }
        finally {
            this.lock.unlock();
        }
    }

    public void removeTrainee(Trainee trainee) throws TrainingException {
        try {
            lock.lock();
            if (!trainees.remove(trainee)) {
                throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    public void removeTrainee(int index) throws TrainingException {
        try {
            lock.lock();
            trainees.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        finally {
            this.lock.unlock();
        }
    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        try {
            lock.lock();
            for (Trainee trainee : trainees) {
                if (trainee.getFirstName().equals(firstName)) {
                    return trainee;
                }
            }
        }
        finally {
            this.lock.unlock();
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException {
        try {
            lock.lock();
            for (Trainee trainee : trainees) {
                if (trainee.getFullName().equals(fullName)) {
                    return trainee;
                }
            }
        }
        finally {
            this.lock.unlock();
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void sortTraineeListByFirstNameAscendant() {
        try {
            lock.lock();
            trainees.sort(Comparator.comparing(Trainee::getFirstName));
        }
        finally {
            this.lock.unlock();
        }
    }

    public void sortTraineeListByRatingDescendant() {
        try {
            lock.lock();
            trainees.sort(Comparator.comparing(trainee -> -trainee.getRating()));
        }
        finally {
            this.lock.unlock();
        }
    }

    public void reverseTraineeList() {
        try {
            lock.lock();
            System.out.println("Thread " + Thread.currentThread().getId() + " reverse Trainee List");
            Collections.reverse(trainees);
            System.out.println("Lock unlocked by thread " + Thread.currentThread().getId());
        }
        finally {
            this.lock.unlock();
        }
    }

    public void rotateTraineeList(int positions) {
        try {
            lock.lock();
            Collections.rotate(trainees, positions);
        }
        finally {
            this.lock.unlock();
        }
    }

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
        try {
            lock.lock();
            int maxRating = Collections.max(trainees, Comparator.comparing(Trainee::getRating)).getRating();
            List<Trainee> listReturn = new ArrayList<>();
            for (Trainee trainee : trainees) {
                if (trainee.getRating() == maxRating) {
                    listReturn.add(trainee);
                }
            }
            return listReturn;
        } catch (NoSuchElementException e) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        finally {
            this.lock.unlock();
        }
    }

    public boolean hasDuplicates() {
        try {
            lock.lock();
            return new HashSet<>(trainees).size() < trainees.size();
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) &&
                Objects.equals(room, group.room) &&
                Objects.equals(trainees, group.trainees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, trainees);
    }
}
