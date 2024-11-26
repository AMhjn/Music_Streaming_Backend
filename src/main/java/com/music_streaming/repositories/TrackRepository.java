package com.music_streaming.repositories;

import com.music_streaming.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByUploadedBy(Long userId);
}