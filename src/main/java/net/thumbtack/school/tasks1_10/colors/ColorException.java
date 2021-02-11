package net.thumbtack.school.tasks1_10.colors;

public class ColorException extends Exception {

    ColorErrorCode errorCode;

    public ColorException(ColorErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ColorErrorCode getErrorCode() {
        return errorCode;
    }
}
