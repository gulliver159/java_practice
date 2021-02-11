package net.thumbtack.school.scenarios;

import net.thumbtack.school.model.Recording;
import net.thumbtack.school.model.TrackType;
import net.thumbtack.school.services.PromotionService;
import net.thumbtack.school.services.PublishingService;
import net.thumbtack.school.services.RecordingDataHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class Scenario3 implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(Scenario3.class);

    private final PublishingService publishingService;
    private final PromotionService promotionService;

    @Autowired
    public Scenario3(PublishingService publishingService, PromotionService promotionService) {
        this.publishingService = publishingService;
        this.promotionService = promotionService;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Start Scenario 3");
        LOGGER.info("Runner was started");

        Recording recording = new Recording("Andrei", TrackType.AUDIO, "Dead");
        ZonedDateTime now = ZonedDateTime.now();

        publishingService.publish(recording, now);
        promotionService.createCampaign(recording, now.plusWeeks(1));

        LOGGER.info("Runner was stopped");
        LOGGER.info("End Scenario 3");
    }
}
