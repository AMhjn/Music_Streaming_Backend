package com.music_streaming.controllers;

import com.music_streaming.models.PlayList;
import com.music_streaming.models.Track;
import com.music_streaming.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlayListController {

    @Autowired
    private PlayListService playlistService;

    @PostMapping("/create")
    public PlayList createPlaylist(@RequestParam String name, Principal principal) {
        return playlistService.createPlaylist(principal.getName(), name);
    }

    @GetMapping
    public List<PlayList> getUserPlaylists(Principal principal) {
        return playlistService.getUserPlaylists(principal.getName());
    }

    @PostMapping("/{playlistId}/add-track")
    public PlayList addTrackToPlaylist(@PathVariable Long playlistId, @RequestBody Track track) {
        return playlistService.addTrackToPlaylist(playlistId, track);
    }

    @DeleteMapping("/{playlistId}")
    public void deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
    }
}