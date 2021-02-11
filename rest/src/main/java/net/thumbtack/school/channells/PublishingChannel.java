package net.thumbtack.school.channells;

import net.thumbtack.school.model.Recording;

import java.time.ZonedDateTime;

public interface PublishingChannel {
    void publish(Recording recording, ZonedDateTime publishAvailableDate);

    void remove(Recording recording, ZonedDateTime publishAvailableDate);
}
