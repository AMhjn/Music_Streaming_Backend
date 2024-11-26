package com.music_streaming.controllers;

import com.music_streaming.models.Track;
import com.music_streaming.services.TrackService;
import com.music_streaming.services.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/youtube")
public class TrackController {

    @Autowired
    private YouTubeService youTubeService;

    @Autowired
    private TrackService trackService;

    @GetMapping("/search")
    public ResponseEntity<?> searchSong(@RequestParam String query) {
        return youTubeService.searchSongs(query);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getSongDetails(@PathVariable String id) {
        return youTubeService.getSongDetails(id);
    }

    @GetMapping("/browse")
    public ResponseEntity<?> fetchPopularSongs() throws IOException {
        return youTubeService.fetchPopularSongs();
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadSong(@PathVariable String id, @RequestBody Track song) throws Exception {
        Long userId = Long.valueOf(id);
        return trackService.uploadSong(userId,song);
    }

    @GetMapping("/get-uploads/{id}")
    public ResponseEntity<?> getUploadedSong(@PathVariable String id) throws Exception {
        Long userId = Long.valueOf(id);
        return trackService.getUploadedSongs(userId);
    }
}


