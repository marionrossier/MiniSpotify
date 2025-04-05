package entities;

public class Song {
    private String title;
    private String artist;
    private String album;
    private double duration;
    private String gender;
    private String songGuId;

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

    public String getSongGuId() {
        return songGuId;
    }

    public void setSongGuId(String songGuId) {
        this.songGuId = songGuId;
    }
}
