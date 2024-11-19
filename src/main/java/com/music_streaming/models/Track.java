package com.music_streaming.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Track {

    @Id
    private String id; // Unique track ID from the third-party API

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    private String album;

    private String duration;

    private String imageUrl; // URL of the album cover or track image

    @ManyToMany(mappedBy = "tracks")
    private List<PlayList> playlists; // Playlists containing this track
}
