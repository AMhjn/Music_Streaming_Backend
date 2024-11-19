package com.music_streaming.controllers;

import com.music_streaming.services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/music")
public class TrackController {

    @Autowired
    private TrackService musicService;

    @GetMapping("/search")
    public Map<String, Object> searchTracks(@RequestParam String query) {
        return musicService.searchTracks(query);
    }

    @GetMapping("/track/{id}")
    public Map<String, Object> getTrackDetails(@PathVariable String id) {
        return musicService.getTrackDetails(id);
    }
}

