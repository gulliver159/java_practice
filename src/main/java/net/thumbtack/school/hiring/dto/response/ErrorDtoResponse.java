package net.thumbtack.school.hiring.dto.response;

import net.thumbtack.school.hiring.exception.ServerErrorCode;

public class ErrorDtoResponse {
    private ServerErrorCode errorCode;

    public ErrorDtoResponse(ServerErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
