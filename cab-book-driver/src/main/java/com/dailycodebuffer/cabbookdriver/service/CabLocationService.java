package com.dailycodebuffer.cabbookdriver.service;

import com.dailycodebuffer.cabbookdriver.constant.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CabLocationService {

    private static final Logger logger = LoggerFactory.getLogger(CabLocationService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public boolean updateLocation(String location) {
        try {
            kafkaTemplate.send(AppConstant.CAB_LOCATION, location);
            logger.info("Location update sent: {}", location);
            return true;
        } catch (Exception e) {
            logger.error("Failed to send location update", e);
            return false;
        }
    }
}
