package com.music_streaming.services;

import com.music_streaming.models.PlayList;
import com.music_streaming.models.Track;
import com.music_streaming.models.User;
import com.music_streaming.repositories.PlayListRepository;
import com.music_streaming.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    public PlayList createPlaylist(String username, String playlistName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PlayList playlist = new PlayList();
        playlist.setName(playlistName);
        playlist.setUser(user);
        return playlistRepository.save(playlist);
    }

    public List<PlayList> getUserPlaylists(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return playlistRepository.findByUser(user);
    }

    public PlayList addTrackToPlaylist(Long playlistId, Track track) {
        PlayList playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlist.getTracks().add(track);
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }
}
