package data.entities;

import data.jsons.ArtistRepository;
import services.UniqueIdService;

public class Song {
    private String title;
    private String titleAndArtist;
    private Artist artist;
    private int durationSeconds;
    private MusicGender gender;
    private int songId;
    private String audioFilePath;
    private final UniqueIdService uniqueIdService = new UniqueIdService();
    ArtistRepository artistRepository = new ArtistRepository();

    public Song (){}

    public Song(int id, String title, String artistName, int durationSeconds, MusicGender gender,
                String audioFilePath) {
        this.songId = id;
        this.title = title;
        this.titleAndArtist = title+" - "+artistName;
        this.durationSeconds = durationSeconds;
        this.gender = gender;
        this.audioFilePath = "resources\\songsfiles\\"+audioFilePath;


        Artist existingArtist = artistRepository.getArtistByName(artistName);
        if (existingArtist != null) {
            this.artist = existingArtist;
        } else {
            this.artist = new Artist(artistName);
            artistRepository.addArtist(this.artist);
        }
        this.artist.getArtistSongsID().add(this.songId);
    }

    public Song(String title, String artistName, int durationSeconds, MusicGender gender,
                String audioFilePath) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.gender = gender;
        this.songId = uniqueIdService.setUniqueId();
        this.audioFilePath = "resources\\songsfiles\\"+audioFilePath;


        Artist existingArtist = artistRepository.getArtistByName(artistName);
        if (existingArtist != null) {
            this.artist = existingArtist;
        } else {
            this.artist = new Artist(artistName);
            artistRepository.addArtist(this.artist);
        }
        this.artist.getArtistSongsID().add(this.songId);
    }

    public String getTitleAndArtist() {
        return titleAndArtist;
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

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }
}
