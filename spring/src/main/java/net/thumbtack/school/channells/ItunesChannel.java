package net.thumbtack.school.channells;

import net.thumbtack.school.model.Recording;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component("itunesChannel")
public class ItunesChannel implements PublishingChannel {
    @Override
    public void publish(Recording recording, ZonedDateTime publishAvailableDate) {
        System.out.println(ItunesChannel.class + " опубликует композицию " + recording 
                + " в " + publishAvailableDate.toString());
    }
}
