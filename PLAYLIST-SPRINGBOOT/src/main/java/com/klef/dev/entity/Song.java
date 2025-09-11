package com.klef.dev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "song_table")
public class Song {
    
    @Id
    @Column(name = "song_id")
    private int id;

    @Column(name = "song_title", nullable = false, length = 100)
    private String title;

    @Column(name = "song_artist", nullable = false, length = 50)
    private String artist;

    @Column(name = "song_album", length = 50)
    private String album;

    @Column(name = "song_genre", length = 30)
    private String genre;

    @Column(name = "song_year")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Song [id=" + id + ", title=" + title + ", artist=" + artist 
                + ", album=" + album + ", genre=" + genre + ", year=" + year + "]";
    }
}
