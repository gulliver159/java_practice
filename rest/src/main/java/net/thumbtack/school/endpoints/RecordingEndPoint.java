package net.thumbtack.school.endpoints;

import net.thumbtack.school.exceptions.MyExceptionResponse;
import net.thumbtack.school.dto.RecordingDto;
import net.thumbtack.school.model.Recording;
import net.thumbtack.school.model.TrackType;
import net.thumbtack.school.services.PublishingService;
import net.thumbtack.school.services.RecordingDataHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/recordings")
public class RecordingEndPoint {

    private final RecordingDataHub recordingDataHub;
    private final PublishingService publishingService;

    @Autowired
    public RecordingEndPoint(RecordingDataHub recordingDataHub, PublishingService publishingService) {
        this.recordingDataHub = recordingDataHub;
        this.publishingService = publishingService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String save(@RequestBody @Valid RecordingDto recordingDto) {
        System.out.println(recordingDto);
        return recordingDataHub.save(recordingDto);
    }

    @PutMapping(value = "/{path}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void publish(@PathVariable("path") String path,
                        @RequestBody ZonedDateTime zonedDateTime) {
        Recording recording = new Recording("Rewq", TrackType.AUDIO, "Live", 1999, "rock",
                1223, path, null); // Достаем из БД
        publishingService.publish(recording, zonedDateTime);
    }
    
    @DeleteMapping(value = "/{path}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeFromChannel(@PathVariable("path") String path,
                        @RequestBody ZonedDateTime zonedDateTime) {
        Recording recording = new Recording("Rewq", TrackType.AUDIO, "Live", 1999, "rock",
                1223, path, null); // Достаем из БД
        publishingService.remove(recording, zonedDateTime);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyExceptionResponse methodArgumentNotValidError(MethodArgumentNotValidException exc) {
        return new MyExceptionResponse(exc.getMessage());
    }
}
