package com.klef.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.dev.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> 
{
    Song findByTitle(String title);
    Song findByArtist(String artist);
}
