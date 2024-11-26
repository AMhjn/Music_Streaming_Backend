package com.music_streaming.controllers;

import com.music_streaming.services.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/youtube")
public class TrackController {

    @Autowired
    private YouTubeService youTubeService;

    @GetMapping("/search")
    public ResponseEntity<?> searchSong(@RequestParam String query) {
        return youTubeService.searchSongs(query);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getSongDetails(@PathVariable String id) {
        return youTubeService.getSongDetails(id);
    }
}


