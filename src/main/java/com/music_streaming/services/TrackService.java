package com.music_streaming.services;

import com.music_streaming.config.SpotifyAuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class TrackService {

    private final SpotifyAuthService spotifyAuthService;
    private final String searchUrl = "https://api.spotify.com/v1/search";

    public TrackService(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }

    private final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1";

    public String searchSongs(String query) {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyAuthService.getAccessToken());

        // Create request entity
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Prepare URL
        String url = searchUrl + "?q=" + query + "&type=track";

        // Send GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return response.getBody(); // Return JSON response as String
    }


    public String   getSongById(String songId) {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyAuthService.getAccessToken());

        // Create request entity
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Prepare URL
        String url = "https://api.spotify.com/v1/tracks/" + songId;

        // Send GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return response.getBody(); // Return JSON response as String
    }

}
