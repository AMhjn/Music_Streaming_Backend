package com.music_streaming.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class YouTubeService {

    private final String API_KEY = "AIzaSyAUrbZLja329Sbw2TVLOFSrEYVfwIprKq8";
    private final String BASE_URL = "https://www.googleapis.com/youtube/v3";

    public ResponseEntity<?> searchSongs(@RequestParam String query) {
        String url = "https://www.googleapis.com/youtube/v3/search"
                + "?part=snippet&type=video&q=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                + "&key=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<?> getSongDetails(@PathVariable String id) {
        String url = "https://www.googleapis.com/youtube/v3/videos"
                + "?part=snippet,contentDetails&id=" + id
                + "&key=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok(response);
    }

}

