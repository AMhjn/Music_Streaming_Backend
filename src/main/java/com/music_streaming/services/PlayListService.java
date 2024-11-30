package com.music_streaming.services;

import com.music_streaming.models.ExceptionResponse;
import com.music_streaming.models.PlayList;
import com.music_streaming.models.Track;
import com.music_streaming.models.User;
import com.music_streaming.repositories.PlayListRepository;
import com.music_streaming.repositories.TrackRepository;
import com.music_streaming.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackRepository trackRepository;

    public ResponseEntity<?> createPlaylist(Long userId, String playlistName) {
        try{
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            PlayList playlist = new PlayList();
            playlist.setName(playlistName);
            playlist.setUser(user);
            playlist = playlistRepository.save(playlist);

            return new ResponseEntity<>(playlist, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity(new ExceptionResponse("Error in Creating Playlist !"),HttpStatus.NOT_IMPLEMENTED);
        }
    }

    public ResponseEntity<?> getUserPlaylists(Long userId) {
        try{
            Optional<User> userOptional = userRepository.findById(userId);

            if(userOptional.isEmpty()){
                return new ResponseEntity<>(new ExceptionResponse("User Not FOund !"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userOptional.get().getPlaylists(),HttpStatus.FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(new ExceptionResponse("Unable to fetch user Playlists!"), HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<?> addTrackToPlaylist(Long playlistId, Track track) {
        try{

            PlayList playlist = playlistRepository.findById(playlistId)
                    .orElseThrow(() -> new RuntimeException("Playlist not found"));

            trackRepository.save(track);
            playlist.getTracks().add(track);
            return new ResponseEntity<>(playlistRepository.save(playlist),HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ExceptionResponse("ERROR: Unable to add Song to Playlist!"), HttpStatus.NOT_IMPLEMENTED);
        }
    }

    public ResponseEntity<?> deletePlaylist(Long playlistId) {
        try{
            playlistRepository.findById(playlistId).orElseThrow(()-> new RuntimeException("ERROR: Playlist not FOund!"));
            playlistRepository.deleteById(playlistId);
            return new ResponseEntity<>("Playlist deleted!", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
