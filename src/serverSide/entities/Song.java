package serverSide.entities;

import serverSide.repositories.ArtistRepository;
import clientSide.services.UniqueIdService;

public class Song {
    private String title;
    private String titleAndArtist;
    private int artistId;
    private int durationSeconds;
    private MusicGender gender;
    private int songId;
    private String audioFileName;
    private final UniqueIdService uniqueIdService = new UniqueIdService();
    ArtistRepository artistRepository = new ArtistRepository();

    public Song (){}

    public Song(int id, String title, String artistName, int durationSeconds, MusicGender gender,
                String audioFileName) {
        this.songId = id;
        this.title = title;
        this.titleAndArtist = title+" - "+artistName;
        this.durationSeconds = durationSeconds;
        this.gender = gender;
        this.audioFileName = audioFileName;

        Artist existingArtist = artistRepository.getArtistByName(artistName);
        if (existingArtist != null) {
            this.artistId = existingArtist.getArtistId();
        } else {
            this.artistId = new Artist(artistName).getArtistId();
            artistRepository.addArtist(new Artist(artistName));
        }
        artistRepository.getArtistById(this.artistId).getArtistSongsID().add(this.songId);
    }

    public Song(String title, String artistName, int durationSeconds, MusicGender gender,
                String audioFileName) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.gender = gender;
        this.songId = uniqueIdService.setUniqueId();
        this.audioFileName = audioFileName;

        Artist existingArtist = artistRepository.getArtistByName(artistName);
        if (existingArtist != null) {
            this.artistId = existingArtist.getArtistId();
        } else {
            this.artistId = new Artist(artistName).getArtistId();
            artistRepository.addArtist(new Artist(artistName));
        }
        artistRepository.getArtistById(this.artistId).getArtistSongsID().add(this.songId);
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
}
