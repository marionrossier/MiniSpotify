package player_StatePattern.file_player;

public interface IMusicPlayer {

    void playOrPause(String songPath);

    void play(String songPath);

    void pause();

    void resume(String songPath);

    void stop();

    boolean isPlaying();

    void setOnSongEndAction(Runnable action);

    boolean isPaused();

}
