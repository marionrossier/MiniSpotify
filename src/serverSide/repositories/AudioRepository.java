package serverSide.repositories;

public class AudioRepository {

    private final String audioFilePath;
    private final SongRepository songRepository;

    public AudioRepository(SongRepository songRepository) {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/songsfiles/", songRepository);

    }

    public AudioRepository(String filePath, SongRepository songRepository) {
        this.songRepository = songRepository;
        this.audioFilePath = filePath;
    }

    public String getAudioFilePathAndName(int songId) {
        String audioFileName = songRepository.getSongById(songId).getAudioFileName();
        return audioFilePath+audioFileName;
    }

    public void setAudioFilePathAndName(int songId, String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public void getFileStream (){

    }
}
