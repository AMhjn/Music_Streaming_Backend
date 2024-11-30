package com.music_streaming.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            // Load JSON from environment variable
            String firebaseConfig = System.getenv("FIREBASE_CONFIG");
            if (firebaseConfig == null) {
                throw new IllegalStateException("FIREBASE_CONFIG environment variable is not set");
            }

            // Convert JSON string to InputStream
            ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("songs_melodify")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

