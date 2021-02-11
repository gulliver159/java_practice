package net.thumbtack.school.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.dto.RecordingDto;
import net.thumbtack.school.model.TrackType;
import net.thumbtack.school.services.PublishingService;
import net.thumbtack.school.services.RecordingDataHub;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RecordingEndPoint.class)
class RecordingEndPointTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RecordingDataHub recordingDataHub;

    @MockBean
    private PublishingService publishingService;

    @Test
    void testSaveRecordingWithFail() throws Exception {
        RecordingDto recordingDto1 = new RecordingDto();
        recordingDto1.setManufactureYear(1900);

        RecordingDto recordingDto2 = new RecordingDto("Rew", TrackType.AUDIO, "Live", 2000,
                "rock", 123, null, null);

        MvcResult result1 = mvc.perform(post("/api/recordings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(recordingDto1)))
                .andReturn();

        MvcResult result2 = mvc.perform(post("/api/recordings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(recordingDto2)))
                .andReturn();

        assertAll(
                () -> assertEquals(result1.getResponse().getStatus(), 400),
                () -> assertEquals(result2.getResponse().getStatus(), 400)
        );
    }

    @Test
    void testSaveRecordingWithoutFail() throws Exception {
        RecordingDto recordingDto = new RecordingDto("Rew", TrackType.AUDIO, "Live", 2000,
                "rock", 123, "qwerty", null);

        MvcResult result1 = mvc.perform(post("/api/recordings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(recordingDto)))
                .andReturn();

        assertEquals(result1.getResponse().getStatus(), 200);
    }
}