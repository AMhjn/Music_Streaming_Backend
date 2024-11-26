package com.music_streaming.controllers;

import com.music_streaming.models.PlayList;
import com.music_streaming.models.SongItem;
import com.music_streaming.models.Track;
import com.music_streaming.models.User;
import com.music_streaming.repositories.PlayListRepository;
import com.music_streaming.repositories.TrackRepository;
import com.music_streaming.repositories.UserRepository;
import com.music_streaming.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/playlists")
public class PlayListController {

    @Autowired
    private PlayListService playlistService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayListService playListService;

    @Autowired
    private PlayListRepository playListRepository;

    private TrackRepository trackRepository;

    @PostMapping("/playlist")
    public ResponseEntity<?> createPlaylist(@RequestBody Map<String, String> payload) {
        Long userId = Long.valueOf(payload.get("userId"));
        String name = payload.get("name");


        PlayList createdPlaylist = playListService.createPlaylist(userId, name);

        return ResponseEntity.ok(createdPlaylist);
    }


    @PostMapping("/playlist/{playlistId}/add-song")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable Long playlistId, @RequestBody SongItem songItem) {
        PlayList playlist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Track track = new Track(songItem.getVideoId(), songItem.getTitle(), songItem.getThumbnailUrl(), songItem.getChannelTitle());
        playListService.addTrackToPlaylist(playlistId,track);

        return ResponseEntity.ok(playlist);
    }


    @GetMapping("/user/{userId}/playlists")
    public ResponseEntity<?> getUserPlaylists(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));


        return ResponseEntity.ok(user.getPlaylists());
    }


    @DeleteMapping("/playlist/{playlistId}/remove-song/{id}")
    public ResponseEntity<?> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable String id) {

        Long idToDelete = Long.valueOf(id);
        PlayList playlist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlist.getTracks().removeIf(track -> track.getId().equals(idToDelete));
        playlist = playListRepository.save(playlist);

        return ResponseEntity.ok(playlist);
    }

}