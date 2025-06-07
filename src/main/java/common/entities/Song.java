package common.entities;

import common.services.UniqueIdService;

public class Song {
    private String title;
    private int artistId;
    private int durationSeconds;
    private MusicGender gender;
    private String album;
    private int songId;
    private String audioFileName;
    private final UniqueIdService uniqueIdService = new UniqueIdService();

    public Song (){}

    public Song(int id, String title, int artistId, String album, int durationSeconds, MusicGender gender,
                String audioFileName) {
        this.songId = id;
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.gender = gender;
        this.album = album;
        this.audioFileName = audioFileName;
        this.artistId = artistId;
    }

    public Song(String title, int artistId, int durationSeconds, MusicGender gender,
                String audioFileName) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.gender = gender;
        this.songId = uniqueIdService.setUniqueId();
        this.audioFileName = audioFileName;
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
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

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
