package net.thumbtack.school.channells;

import net.thumbtack.school.model.Recording;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component("youtubeChannel")
public class YoutubeMusicChannel implements PublishingChannel {
    @Override
    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        System.out.println(YoutubeMusicChannel.class + " опубликует композицию " + recording
                + " в " + publishAvailableDate.toString());
    }
}
