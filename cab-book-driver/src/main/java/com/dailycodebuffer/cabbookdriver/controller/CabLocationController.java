package com.dailycodebuffer.cabbookdriver.controller;

import com.dailycodebuffer.cabbookdriver.service.CabLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/location")
public class CabLocationController {

    private static final Logger logger = LoggerFactory.getLogger(CabLocationController.class);

    @Autowired
    private CabLocationService cabLocationService;

    private final AtomicBoolean running = new AtomicBoolean(true);

    @PutMapping
    public ResponseEntity<Map<String, String>> updateLocation() throws InterruptedException {
        logger.info("Starting location updates");

        int range = 100;
        while (range > 0 && running.get()) {
            String location = Math.random() + " , " + Math.random();
            boolean success = cabLocationService.updateLocation(location);

            if (!success) {
                logger.warn("Failed to send location update");
            }

            Thread.sleep(1000);
            range--;
        }

        logger.info("Finished location updates");
        return new ResponseEntity<>(Map.of("message", "Location Updated"), HttpStatus.OK);
    }

    // Additional endpoint to stop the location updates
    @PutMapping("/stop")
    public ResponseEntity<Map<String, String>> stopUpdates() {
        running.set(false);
        logger.info("Stopping location updates");
        return new ResponseEntity<>(Map.of("message", "Stopping Location Updates"), HttpStatus.OK);
    }
}
