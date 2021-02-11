package net.thumbtack.school.exceptions;

public class MyExceptionResponse {

    private final String message;

    public MyExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
