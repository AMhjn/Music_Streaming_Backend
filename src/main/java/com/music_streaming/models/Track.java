package com.music_streaming.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique track ID from the third-party API

    @Column(nullable = false)
    private String title;

    private String channelTitle;

    private String videoId;

    private String thumbnailUrl; // URL of the album cover or track image

    private String songUrlOnFirebase;

    private Long uploadedBy;

    @ManyToMany(mappedBy = "tracks")
    @JsonIgnore
    private List<PlayList> playlists; // Playlists containing this track

    public Track() {
    }

    public Track(String title, String channelTitle, String videoId, String thumbnailUrl) {
        this.title = title;
        this.channelTitle = channelTitle;
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
    }
    public Track(String title, String channelTitle,String thumbnailUrl ) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.channelTitle = channelTitle;
    }
}
