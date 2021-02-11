package net.thumbtack.school.hiring.dto.request;

public class StartAndStopDtoRequest {
    private final String fileName;

    public StartAndStopDtoRequest(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
