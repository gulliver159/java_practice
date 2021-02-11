package net.thumbtack.school.tasks1_10.colors;

public enum ColorErrorCode {
    WRONG_COLOR_STRING("Передан несуществующий цвет"),
    NULL_COLOR("Передан null");

    String errorString;

    ColorErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
