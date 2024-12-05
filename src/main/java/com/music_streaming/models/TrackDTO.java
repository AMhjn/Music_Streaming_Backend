package com.music_streaming.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigInteger;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackDTO {
    private BigInteger id;
    private String title;
    private String artistName; // Flattened artist name
    private String albumCover; // Flattened album cover URL
    private String songUrl;
}
