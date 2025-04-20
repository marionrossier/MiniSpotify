package data.entities;

import data.storage.ArtistRepository;
import services.TransverseCode;

public class Song {
    private String title;
    private Artist artist;
    private String album;
    private double duration;
    private MusicGender gender;
    private int songId;
    private String audioFilePath;
    private final TransverseCode transverseCode = new TransverseCode();
    ArtistRepository repository = new ArtistRepository();


    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public Song (){}


    public Song(String title, String artistName, String album, double duration, MusicGender gender,
                String audioFilePath) {
        this.title = title;
        this.album = album;
        this.duration = duration;
        this.gender = gender;
        this.songId = transverseCode.setUniqueId();
        this.audioFilePath = audioFilePath;


        Artist existingArtist = repository.findArtistByName(artistName);
        if (existingArtist != null) {
            this.artist = existingArtist;
        } else {
            this.artist = new Artist(artistName);
            repository.addArtist(this.artist);
        }
        this.artist.getArtistSongsID().add(this.songId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public MusicGender getGender() {
        return gender;
    }

    public void setGender(MusicGender gender) {
        this.gender = gender;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
