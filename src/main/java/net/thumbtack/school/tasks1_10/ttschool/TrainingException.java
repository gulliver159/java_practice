package net.thumbtack.school.tasks1_10.ttschool;

public class TrainingException extends Exception {

    TrainingErrorCode errorCode;

    public TrainingException(TrainingErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public TrainingErrorCode getErrorCode() {
        return errorCode;
    }
}
