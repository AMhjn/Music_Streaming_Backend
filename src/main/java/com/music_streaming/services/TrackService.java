package com.music_streaming.services;

import com.music_streaming.config.SpotifyAuthService;
import com.music_streaming.models.Track;
import com.music_streaming.models.User;
import com.music_streaming.repositories.TrackRepository;
import com.music_streaming.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class TrackService {

    private final SpotifyAuthService spotifyAuthService;
    private final String searchUrl = "https://api.spotify.com/v1/search";

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    public TrackService(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }

    private final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1";

    public ResponseEntity<?> uploadSong(Long userId, Track song) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return new ResponseEntity<>("UserID not found !",HttpStatus.NOT_FOUND);
        }

        // Upload song to firebase and get the public url
        String firebaseUrl = "";
        song.setSongUrlOnFirebase(firebaseUrl);
        song.setUploadedBy(userId);
        song = trackRepository.save(song);

        return ResponseEntity.ok(song);
    }

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

    public ResponseEntity<?> getUploadedSongs(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return new ResponseEntity<>("UserID not found !",HttpStatus.NOT_FOUND);
        }
        List<Track> uploadedSOngs = (List<Track>) trackRepository.findByUploadedBy(userId);
        return ResponseEntity.ok(uploadedSOngs);
    }
}
