package com.music_streaming.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music_streaming.models.SongItem;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YouTubeService {

    private final String API_KEY = "AIzaSyAUrbZLja329Sbw2TVLOFSrEYVfwIprKq8";
    private final String BASE_URL = "https://www.googleapis.com/youtube/v3";

    public ResponseEntity<?> searchSongs(@RequestParam String query) {
        try {
            // Construct YouTube API search URL
            String url = "https://www.googleapis.com/youtube/v3/search"
                    + "?part=snippet&type=video&q=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                    + "&key=" + API_KEY;

            RestTemplate restTemplate = new RestTemplate();

            // Make the GET request to YouTube API and get the response as a Map
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            // Extract items from response
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

            // Create a list of SongItem with title, videoId, thumbnailUrl (default), and channelTitle
            List<SongItem> songItems = new ArrayList<>();
            for (Map<String, Object> item : items) {
                Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");
                String title = (String) snippet.get("title");
                String videoId = ((Map<String, Object>) item.get("id")).get("videoId").toString();

                // Check if "thumbnails" field exists and contains the "default" size
                Map<String, Object> thumbnails = (Map<String, Object>) snippet.get("thumbnails");
                String thumbnailUrl = null;
                if (thumbnails != null && thumbnails.containsKey("default")) {
                    thumbnailUrl = (String) ((Map<String, Object>) thumbnails.get("default")).get("url");
                }

                String channelTitle = (String) snippet.get("channelTitle");

                // Create a SongItem object and add it to the list
                songItems.add(new SongItem(title, videoId, thumbnailUrl, channelTitle));
            }

            // Return the simplified list of songs
            return ResponseEntity.ok(songItems);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Error occurred: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }


    public ResponseEntity<?> getSongDetails(@PathVariable String id) {
        String url = "https://www.googleapis.com/youtube/v3/videos"
                + "?part=snippet,contentDetails&id=" + id
                + "&key=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> fetchPopularSongs() throws IOException {
        // YouTube API URL for fetching popular videos
        String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&regionCode=US&videoCategoryId=10&maxResults=30&key="
                + API_KEY;

        // Fetch data from YouTube API using RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Parse the YouTube API response to extract video details
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode items = root.path("items");

        // Prepare a list of song details (videoId, title, channelName, thumbnail)
        List<Map<String, String>> songResults = new ArrayList<>();
        for (JsonNode item : items) {
            String videoId = item.path("id").asText();
            String title = item.path("snippet").path("title").asText();
            String channelName = item.path("snippet").path("channelTitle").asText();
            String thumbnail = item.path("snippet").path("thumbnails").path("default").path("url").asText();

            // Add video details to the result list
            Map<String, String> song = new HashMap<>();
            song.put("videoId", videoId);
            song.put("title", title);
            song.put("channelName", channelName);
            song.put("thumbnail", thumbnail);
            songResults.add(song);
        }

        // Return the list of songs (videos) as JSON response
        return ResponseEntity.ok(songResults);
    }

}

