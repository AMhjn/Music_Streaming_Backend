package com.music_streaming.repositories;

import com.music_streaming.models.PlayList;
import com.music_streaming.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {
    List<PlayList> findByUser(User user);
}
