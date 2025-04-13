package data.entities;

import services.TransverseCode;

public class Song {
    private String title;
    //TODO : est-ce qu'on ferait pas une classe séparé our l'artiste ? Comme ça on limite les
    // doublons et on peut sortir une liste des artistes dans le search...
    private String artist;
    //TODO : idem que pour les artistes mais pour les albums
    private String album;
    private double duration;
    //TODO : est-ce qu'on fait un enum pour le genre ? Comme ça on peut faire un switch pour la page Search...
    private String gender;
    private int songId;
    private String audioFilePath;
    private TransverseCode transverseCode = new TransverseCode();

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public Song (){}

    public Song(String title, String artist, String album, double duration, String gender,
                String audioFilePath) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.gender = gender;
        this.songId = transverseCode.setUniqueId();
        this.audioFilePath = audioFilePath;
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
