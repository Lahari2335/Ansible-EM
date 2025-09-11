package com.klef.dev.service;

import java.util.List;
import com.klef.dev.entity.Song;

public interface SongService {
    Song addSong(Song song);
    List<Song> getAllSongs();
    Song getSongById(int id);
    Song updateSong(Song song);
    void deleteSongById(int id);
}
