package com.example.demo.experiment.controller;

import com.example.demo.experiment.entity.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/error")
public class TriggerOOMController {
    private static final Logger logger = LoggerFactory.getLogger(TriggerOOMController.class);

    @GetMapping("/oom")
    public void triggerOOM() throws InterruptedException {
        try {
            List<Item> itemList = new ArrayList<>();

            for (long i = 0; i < 100000000L; i++) {
                itemList.add(new Item(i, "Item" + i, 1000));
            }
        } catch (OutOfMemoryError error) {
            logger.error("OOM Error Occurred", error);
            long startTime = System.nanoTime();
            System.gc();
            long endTime = System.nanoTime();
            logger.info((endTime - startTime) / 1000000 + "milliseconds took for GC");
            throw new CustomException("out of Memory occurred", error);
        }
    }

    @GetMapping("/stackoverflow")
    public void triggerStackOverFlow() {
        try {
            recursiveMethod();
        } catch (StackOverflowError error) {
            logger.error("StackOverFlowError Occurred", error);
            throw new CustomException("StackOverFlowError occurred", error);
        }
    }

    public void recursiveMethod() {
        recursiveMethod();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class CustomException extends RuntimeException {
        public CustomException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
