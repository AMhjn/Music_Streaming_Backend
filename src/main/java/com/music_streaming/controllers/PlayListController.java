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
import org.springframework.http.HttpStatus;
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

        return playListService.createPlaylist(userId, name);
    }


    @PostMapping("/playlist/{playlistId}/add-song")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable Long playlistId, @RequestBody SongItem songItem) {

        Track track = new Track(songItem.getVideoId(), songItem.getTitle(), songItem.getThumbnailUrl(), songItem.getChannelTitle());
        return playListService.addTrackToPlaylist(playlistId,track);

    }


    @GetMapping("/user/{userId}/playlists")
    public ResponseEntity<?> getUserPlaylists(@PathVariable Long userId) {

       return playListService.getUserPlaylists(userId);
    }


    @DeleteMapping("/playlist/{playlistId}/remove-song/{id}")
    public ResponseEntity<?> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable String id) {

     try{
         Long idToDelete = Long.valueOf(id);
         PlayList playlist = playListRepository.findById(playlistId)
                 .orElseThrow(() -> new RuntimeException("Playlist not found"));

         playlist.getTracks().removeIf(track -> track.getId().equals(idToDelete));
         playlist = playListRepository.save(playlist);

         return ResponseEntity.ok(playlist);
     }
     catch(Exception e){
         return new ResponseEntity<>("Unable To Remove Song!", HttpStatus.NOT_IMPLEMENTED);
     }

    }

    @DeleteMapping("/playlist/remove/{playlistId}")
    public ResponseEntity<?> removePlaylist(@PathVariable Long playlistId) {

        return playListService.deletePlaylist(playlistId);

    }


}