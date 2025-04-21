package data.entities;

import data.jsons.ArtistRepository;
import services.TransverseService;

public class Song {
    private String title;
    private String songName;
    private Artist artist;
    private String album;
    private int seconds;
    private MusicGender gender;
    private int songId;
    private String audioFilePath;
    private final TransverseService transverseService = new TransverseService();
    ArtistRepository repository = new ArtistRepository();


    public Song (){}


    public Song(String title, String artistName, String album, int seconds, MusicGender gender,
                String audioFilePath) {
        this.title = title;
        this.songName = title+" | "+artistName+" | "+album+" | "+gender+" | "+ seconds;
        this.album = album;
        this.seconds = seconds;
        this.gender = gender;
        this.songId = transverseService.setUniqueId();
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

    public String getSongName() {
        return songName;
    }

    public void setSongName() {
        this.songName = title+" | "+artist.getArtistName()+" | "+album+" | "+gender+" | "+ seconds;
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

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
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

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }
}
