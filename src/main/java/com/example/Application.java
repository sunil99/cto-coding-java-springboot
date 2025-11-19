package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.event.EventListener;

import java.net.URI;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ServletWebServerApplicationContext webCtx;

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        if (!webCtx.isRunning()) {
            webCtx.start();
        }
        log.info("Application started at: " + getUri());
    }

    public void stop() {
        if (webCtx.isRunning()) {
            webCtx.stop();
            log.info("Application stopped.");
        }
    }

    public static void main(String[] args) {
        log.info("Hello and welcome!");
        SpringApplication.run(Application.class, args);
    }

    public int getPort() {
        return webCtx.getWebServer().getPort();
    }

    public Boolean isRunning() {
        return webCtx.isRunning();
    }

    public URI getUri() {
        return URI.create("http://localhost:" + getPort());
    }
}

