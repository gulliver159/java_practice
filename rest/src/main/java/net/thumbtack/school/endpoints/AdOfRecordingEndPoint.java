package net.thumbtack.school.endpoints;

import net.thumbtack.school.model.Recording;
import net.thumbtack.school.model.TrackType;
import net.thumbtack.school.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/recordings")
public class AdOfRecordingEndPoint {

    private final PromotionService promotionService;

    @Autowired
    public AdOfRecordingEndPoint(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping(value = "{path}/advertising", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public int createCampaign(@PathVariable("path") String path,
                        @RequestBody ZonedDateTime zonedDateTime) {
        Recording recording = new Recording("Rewq", TrackType.AUDIO, "Live", 1999, "rock",
                1223, path, null); // Достаем из БД
        return promotionService.createCampaign(recording, zonedDateTime);
    }

    @PutMapping(value = "{path}/advertising/{id}/stopping", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void stopCampaign(@PathVariable("path") String path,
                             @PathVariable("id") int id,
                        @RequestBody ZonedDateTime zonedDateTime) {
        Recording recording = new Recording("Rewq", TrackType.AUDIO, "Live", 1999, "rock",
                1223, path, null); // Достаем из БД
        promotionService.stopCampaign(recording, id, zonedDateTime);
    }

    @DeleteMapping(value = "{path}/advertising/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeCampaign(@PathVariable("path") String path,
                               @PathVariable("id") int id,
                        @RequestBody ZonedDateTime zonedDateTime) {
        Recording recording = new Recording("Rewq", TrackType.AUDIO, "Live", 1999, "rock",
                1223, path, null); // Достаем из БД
        promotionService.removeCampaign(recording, id, zonedDateTime);
    }

    @GetMapping(value = "{path}/advertising/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getInfoAboutCampaign(@PathVariable("path") String path,
                                       @PathVariable("id") int id) {
        Recording recording = new Recording("Rewq", TrackType.AUDIO, "Live", 1999, "rock",
                1223, path, null); // Достаем из БД
        return promotionService.getInfoAboutCampaign(recording, id);
    }
}
