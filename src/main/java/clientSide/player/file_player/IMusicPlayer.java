package clientSide.player.file_player;

public interface IMusicPlayer {

    void addSongEndObserver(Runnable observer);

    void playOrPause(String songPath);

    void play(String songPath);

    void pause();

    void resume(String songPath);

    void stop();

    boolean isPlaying();

    boolean isPaused();

}
