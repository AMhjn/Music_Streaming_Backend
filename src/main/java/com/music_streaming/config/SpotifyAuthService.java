package com.music_streaming.config;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Base64;
import java.util.Map;

@Service
public class SpotifyAuthService {

    private final String clientId = "30a66e80e6cd400990211a6e962879c7";
    private final String clientSecret = "112fd007d1004c8b9c0c0f55aab18391";
    private final String tokenUrl = "https://accounts.spotify.com/api/token";
    private String accessToken;
    private long tokenExpiryTime; // Store token expiry time in milliseconds

    public String getAccessToken() {
        if (accessToken == null || isTokenExpired()) {
            accessToken = fetchAccessToken();
        }
        return accessToken;
    }

    private boolean isTokenExpired() {
        return System.currentTimeMillis() > tokenExpiryTime;
    }

    private String fetchAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        headers.set("Authorization", "Basic " + encodedCredentials);

        // Prepare request body
        String requestBody = "grant_type=client_credentials";

        // Create HTTP request
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // Update access token and expiry time
        accessToken = (String) responseBody.get("access_token");
        int expiresIn = (int) responseBody.get("expires_in"); // Expiry time in seconds
        tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000);

        return accessToken;
    }
}
