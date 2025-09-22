package com.klef.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.klef.dev.entity.Song;
import com.klef.dev.service.SongService;

@RestController
@RequestMapping("/playlistapi")
@CrossOrigin(origins = "*")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/")
    public String home() {
        return "Music Playlist API is running!";
    }

    @PostMapping("/add")
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        Song savedSong = songService.addSong(song);
        return new ResponseEntity<>(savedSong, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSongById(@PathVariable int id) {
        Song song = songService.getSongById(id);
        if (song != null) {
            return new ResponseEntity<>(song, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Song with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

   @PutMapping("/update/{id}")
public ResponseEntity<?> updateSong(@PathVariable int id, @RequestBody Song song) {
    Song existing = songService.getSongById(id);
    if (existing != null) {
        song.setId(id); // force ID from URL
        Song updatedSong = songService.updateSong(song);
        return new ResponseEntity<>(updatedSong, HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Cannot update. Song with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }
}


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable int id) {
        Song existing = songService.getSongById(id);
        if (existing != null) {
            songService.deleteSongById(id);
            return new ResponseEntity<>("Song with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete. Song with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
