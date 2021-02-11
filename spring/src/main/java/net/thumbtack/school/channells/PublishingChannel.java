package net.thumbtack.school.channells;

import net.thumbtack.school.model.Recording;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public interface PublishingChannel {
    void publish(Recording recording, ZonedDateTime publishAvailableDate);
}
