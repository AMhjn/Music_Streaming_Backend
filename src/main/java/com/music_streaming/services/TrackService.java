package com.music_streaming.services;

import com.google.firebase.cloud.StorageClient;
import com.music_streaming.models.ExceptionResponse;
import com.music_streaming.models.SongItem;
import com.music_streaming.models.Track;
import com.music_streaming.models.User;
import com.music_streaming.repositories.TrackRepository;
import com.music_streaming.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TrackService {


    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> uploadSong(Long userId,String title, String channelTitle, String thumbnailUrl, MultipartFile file) throws IOException {

        try {
            Track song = new Track(title,channelTitle,thumbnailUrl);
            Optional<User> user = userRepository.findById(userId);

            if(user.isEmpty()){
                return new ResponseEntity<>(new ExceptionResponse("UserID not found !"),HttpStatus.NOT_FOUND);
            }

            // Upload song to firebase and get the public url
            // Generate a unique file name
            String fileName = song.getTitle();

            // Upload the file to Firebase Storage
            StorageClient.getInstance().bucket().create(fileName, file.getInputStream(), file.getContentType());

            // Generate the public download URL
            String firebaseUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                    StorageClient.getInstance().bucket().getName(),
                    fileName.replace("/", "%2F"));




            song.setSongUrlOnFirebase(firebaseUrl);
            song.setUploadedBy(userId);
            song = trackRepository.save(song);

            return ResponseEntity.ok(song);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_IMPLEMENTED);
        }


    }

    public ResponseEntity<?> getUploadedSongs(Long userId) {
        try {

            Optional<User> user = userRepository.findById(userId);

            if(user.isEmpty()){
                return new ResponseEntity<>(new ExceptionResponse("UserID not found !"),HttpStatus.NOT_FOUND);
            }
            List<Track> uploadedSOngs = trackRepository.findByUploadedBy(userId);
            return ResponseEntity.ok(uploadedSOngs);
        }catch(Exception e){
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
