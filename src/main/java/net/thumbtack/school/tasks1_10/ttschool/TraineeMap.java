package net.thumbtack.school.tasks1_10.ttschool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TraineeMap {
    private final Map<Trainee, String> map;

    public TraineeMap() {
        map = new HashMap<>();
    }

    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (map.containsKey(trainee)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
        map.put(trainee, institute);
    }

    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        throwExceptionIfContainsKey(trainee);
        map.replace(trainee, institute);
    }

    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        throwExceptionIfContainsKey(trainee);
        map.remove(trainee);
    }

    public int getTraineesCount() {
        return map.size();
    }

    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        throwExceptionIfContainsKey(trainee);
        return map.get(trainee);
    }

    public Set<Trainee> getAllTrainees() {
        return map.keySet();
    }

    public Set<String> getAllInstitutes() {
        return new HashSet<>(map.values());
    }

    public boolean isAnyFromInstitute(String institute) {
        return map.containsValue(institute);
    }

    private void throwExceptionIfContainsKey(Trainee trainee) throws TrainingException {
        if (!map.containsKey(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }
}
