package net.thumbtack.school.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.school.services.PromotionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdOfRecordingEndPoint.class)
class AdOfRecordingEndPointTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PromotionService promotionService;

    @Test
    void testGetInfoAboutCampaignWithoutException() throws Exception {
        mvc.perform(get("/api/recordings/asd/advertising")).andExpect(status().isOk());
    }

    @Test
    void testGetInfoAboutCampaignWithException() throws Exception {
        mvc.perform(get("/api/recordings/asd/adv")).andExpect(status().is4xxClientError());
    }
}