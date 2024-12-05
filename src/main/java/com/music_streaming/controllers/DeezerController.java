package com.music_streaming.controllers;

import com.music_streaming.services.DeezerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeezerController {

    @Autowired
    private DeezerService deezerService;

    @GetMapping("/api/deezer/search")
    public ResponseEntity<?> searchTracks(@RequestParam String query) {
        return deezerService.searchTracks(query);
    }

    @GetMapping("/api/deezer/track")
    public ResponseEntity<?> getTrackDetails(@RequestParam String trackId) {
        return deezerService.getTrackDetails(trackId);
    }

//    @GetMapping("/api/deezer/artist")
//    public String getArtistDetails(@RequestParam String artistId) {
//        return deezerService.getArtistDetails(artistId);
//    }

    @GetMapping("/api/deezer/browse")
    public ResponseEntity<?> getTopTracks() throws Exception {
        return deezerService.getTopTracks();
    }
}
