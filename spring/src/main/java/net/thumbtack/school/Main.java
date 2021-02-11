package net.thumbtack.school;

import net.thumbtack.school.channells.ItunesChannel;
import net.thumbtack.school.channells.PublishingChannel;
import net.thumbtack.school.channells.YandexMusicChannel;
import net.thumbtack.school.channells.YoutubeMusicChannel;
import net.thumbtack.school.storages.AudioRecordingStorage;
import net.thumbtack.school.storages.DataStorage;
import net.thumbtack.school.storages.VideoStorage;
import net.thumbtack.school.model.Recording;
import net.thumbtack.school.model.TrackType;
import net.thumbtack.school.services.PromotionService;
import net.thumbtack.school.services.PublishingService;
import net.thumbtack.school.services.RecordingDataHub;

import java.time.ZonedDateTime;

public class Main {
    public static void main(String[] args) {
        DataStorage audioStorage = new AudioRecordingStorage();
        DataStorage videoStorage = new VideoStorage();

        RecordingDataHub recordingDataHub = new RecordingDataHub(audioStorage, videoStorage);

        PublishingChannel yandexMusicChannel = new YandexMusicChannel();
        PublishingChannel itunesChannel = new ItunesChannel();
        PublishingChannel youtubeMusicChannel = new YoutubeMusicChannel();

        PublishingService publishingService = new PublishingService(yandexMusicChannel, itunesChannel,
                                                                    youtubeMusicChannel);

        PromotionService promotionService = new PromotionService();

        Recording recording = new Recording("Shnurov", TrackType.AUDIO, "Labutens");

        recordingDataHub.save(recording);
        publishingService.publish(recording, ZonedDateTime.now());
        promotionService.createCampaign(recording, ZonedDateTime.now());
    }
}
