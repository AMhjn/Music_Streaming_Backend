package com.music_streaming.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TrackService {

    private final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1";
    private final String DEEZER_BASE_URL = "https://api.deezer.com";
    private final String API_KEY = "YOUR_API_KEY_HERE"; // Replace with your actual key.

    public Map<String, Object> searchTracks(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = SPOTIFY_BASE_URL + "/search?q=" + query + "&type=track";

        Map<String, Object> response = new HashMap<>();
        try {
            response = restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    public Map<String, Object> getTrackDetails(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = SPOTIFY_BASE_URL + "/tracks/" + id;

        Map<String, Object> response = new HashMap<>();
        try {
            response = restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }
}
