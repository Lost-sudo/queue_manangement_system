package com.lostsudo.queuesystem;

import com.lostsudo.queuesystem.config.DotenvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueueSystemApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QueueSystemApplication.class);
        app.addInitializers(new DotenvConfig());
        app.run(args);
    }

}
