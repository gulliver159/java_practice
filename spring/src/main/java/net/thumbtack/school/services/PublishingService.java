package net.thumbtack.school.services;

import net.thumbtack.school.channells.PublishingChannel;
import net.thumbtack.school.model.Recording;
import net.thumbtack.school.model.TrackType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class PublishingService {

    PublishingChannel itunesChannel;
    PublishingChannel yandexMusicChannel;
    PublishingChannel youtubeMusicChannel;

    public PublishingService(@Qualifier("itunesChannel") PublishingChannel itunesChannel,
                             @Qualifier("yandexChannel") PublishingChannel yandexMusicChannel,
                             @Qualifier("youtubeChannel") PublishingChannel youtubeMusicChannel) {
        this.itunesChannel = itunesChannel;
        this.yandexMusicChannel = yandexMusicChannel;
        this.youtubeMusicChannel = youtubeMusicChannel;
    }

    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        if (recording.getTrackType() == TrackType.AUDIO) {
            itunesChannel.publish(recording, publishAvailableDate);
            yandexMusicChannel.publish(recording, publishAvailableDate);
            youtubeMusicChannel.publish(recording, publishAvailableDate);
        }
        else {
            youtubeMusicChannel.publish(recording, publishAvailableDate);
        }
    }
}
