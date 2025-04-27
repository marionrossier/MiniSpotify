package player_StatePattern.file_player;

public interface IMusicPlayer {
    void play(String songPath);

    void pause();

    void resume();

    void stop();

    boolean isPlaying();

    void setOnSongEndAction(Runnable action);
}
