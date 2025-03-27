package com.example.kafkaconsumerdatabase.consumer;

import com.example.kafkaconsumerdatabase.entity.WikimediaData;
import com.example.kafkaconsumerdatabase.repositories.WikimediaDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private WikimediaDataRepo wikimediaDataRepo;

    public KafkaDatabaseConsumer(WikimediaDataRepo wikimediaDataRepo) {
        this.wikimediaDataRepo = wikimediaDataRepo;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message){

        LOGGER.info("Event message received :: " + message);

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(message);
        wikimediaDataRepo.save(wikimediaData);
    }
}
