package com.ecgouvea.observability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class CustomInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        String version = "";

        try (InputStream inputStream = CustomInfoContributor.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line != null && line.contains("Implementation-Version:")) {

                            version += line.split(":\\s*")[1];
                        }
                        System.out.println(line);
                    }
                }
            } else {
                System.err.println("/META-INF/MANIFEST.MF file not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("version: " + version);

        builder.withDetail("app", Map.of(
                "name", "My Spring Boot Application for Observability",
                "description", "This is a sample Spring Boot application.",
                "version", version));
    }
}
