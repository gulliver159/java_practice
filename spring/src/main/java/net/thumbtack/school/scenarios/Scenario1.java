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

@Component
public class Scenario1 implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(Scenario1.class);

    private final RecordingDataHub recordingDataHub;

    @Autowired
    public Scenario1(RecordingDataHub recordingDataHub) {
        this.recordingDataHub = recordingDataHub;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Start Scenario 1");
        LOGGER.info("Runner was started");

        Recording recording = new Recording("Shnurov", TrackType.AUDIO, "Labutens");
        recordingDataHub.save(recording);

        LOGGER.info("Runner was stopped");
        LOGGER.info("End Scenario 1");
    }
}
