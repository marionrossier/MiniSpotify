package player_StatePattern.playlist_player;

public interface IPlaylistPlayer {

    void playOrPause(int songId);

    void play(int playlistId, int songId);

    void pause();
    void resume(int currentSongId);

    void playback();

    void next();

    void previous();

    void setShuffleMode();

    void setRepeatMode();
    void setSequentialMode();

    int getRunningPlaylistId();

    int getRunningSongId();

    void stop();

    boolean isPlaying();

    boolean isPaused();
}
