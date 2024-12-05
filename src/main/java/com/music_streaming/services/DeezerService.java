package com.music_streaming.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Http;
import com.music_streaming.models.TrackDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeezerService {

    private final RestTemplate restTemplate;

    @Value("${deezer_api_key}")
    private String apiKey;

    @Value("${deezer_api_host}")
    private String apiHost;

    public DeezerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> searchTracks(String query) {

        try{
            String url = String.format("https://deezerdevs-deezer.p.rapidapi.com/search?q=%s&limit=%d", query, 30);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", apiKey);
            headers.set("X-RapidAPI-Host", apiHost);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return new ResponseEntity<>(parseTracks(response.getBody()),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Sorry,Could Not Search the Song!",HttpStatus.NOT_FOUND);
        }
    }


    private List<TrackDTO> parseTracks(String responseBody) throws Exception {
        List<TrackDTO> tracks = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode data = root.get("data");

            if (data != null && data.isArray()) {
                for (JsonNode trackNode : data) {
                    TrackDTO track = new TrackDTO();
                    // Safely retrieve and set values
                    if( trackNode.has("id") && trackNode.get("id").asText() != null ){
                        track.setId( trackNode.get("id").asText());
                    }
                    else{
                        continue;
                    }

                    if( trackNode.has("title") && trackNode.get("title").asText() != null ){
                        track.setId( trackNode.get("title").asText());
                    }
                    else{
                        continue;
                    }


                    track.setTitle(trackNode.has("title") ? trackNode.get("title").asText() : null);

                    // Check nested artist object
                    JsonNode artistNode = trackNode.get("artist");
                    if (artistNode != null && artistNode.has("name") && artistNode.get("name").asText()!= null) {
                        track.setArtistName(artistNode.get("name").asText() );
                    }
                    else{
                        continue;
                    }

                    // Check nested album object
                    JsonNode albumNode = trackNode.get("album");
                    if (albumNode != null && albumNode.has("cover")) {
                        track.setAlbumCover( albumNode.get("cover").asText());
                    }
                    else{
                        continue;
                    }

                    if(trackNode.has("preview") && trackNode.get("preview").asText()!= null ){
                        track.setSongUrl(trackNode.get("preview").asText());
                    }
                    else{
                        continue;
                    }
                    tracks.add(track);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error while parsing tracks: " + e.getMessage());
        }
        return tracks;
    }


    public ResponseEntity<?> getTrackDetails(String trackId) {
        try{
            String url = "https://deezerdevs-deezer.p.rapidapi.com/track/" + trackId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", apiKey);
            headers.set("X-RapidAPI-Host", apiHost);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return new ResponseEntity<>(parseTrackDetails(response.getBody()), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Sorry,could not find that song!",HttpStatus.NOT_FOUND);
        }
    }

    private TrackDTO parseTrackDetails(String responseBody) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode trackNode = objectMapper.readTree(responseBody);

            TrackDTO track = new TrackDTO();
            // Safely retrieve and set values
            track.setId(trackNode.has("id") ? trackNode.get("id").asText() : null);
            track.setTitle(trackNode.has("title") ? trackNode.get("title").asText() : null);

            // Check nested artist object
            JsonNode artistNode = trackNode.get("artist");
            if (artistNode != null && artistNode.has("name") && artistNode.get("name").asText()!= null) {
                track.setArtistName(artistNode.get("name").asText() );
            }
            else{
                throw new Exception("Artist not present!");
            }

            // Check nested album object
            JsonNode albumNode = trackNode.get("album");
            if (albumNode != null && albumNode.has("cover")) {
                track.setAlbumCover( albumNode.get("cover").asText());
            }
            else{
                throw new Exception("COver not present!");
            }

            if(trackNode.has("preview") && trackNode.get("preview").asText()!= null ){
                track.setSongUrl(trackNode.get("preview").asText());
            }
            else{
                throw new Exception("Preview not found!");
            }


            return track;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String getArtistDetails(String artistId) {
        String url = "https://deezerdevs-deezer.p.rapidapi.com/artist/" + artistId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public ResponseEntity<?> getTopTracks() throws Exception {
        try{

            String url = "https://deezerdevs-deezer.p.rapidapi.com/search?q=love";

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", apiKey);  // Replace with your RapidAPI key
            headers.set("X-RapidAPI-Host", apiHost);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return new ResponseEntity<>(parseTracks(response.getBody()), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Browse Fucntionality Stopped!", HttpStatus.NOT_FOUND);
        }
    }
}
