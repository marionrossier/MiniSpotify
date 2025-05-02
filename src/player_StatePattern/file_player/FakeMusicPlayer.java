package player_StatePattern.file_player;

public class FakeMusicPlayer implements IMusicPlayer {
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private String currentSongPath = null;
    private Runnable onSongEndCallback;

    @Override
    public void playOrPause(String songPath) {
        if (isPlaying){
            pause();
            return;
        }
        if (isPaused){
            resume(songPath);
            return;
        }
        play(songPath);
    }

    @Override
    public void play(String songPath) {
        currentSongPath = songPath;
        System.out.println("[FakeMusicPlayer] Playing: " + currentSongPath);
        isPlaying = true;
        isPaused = false;
    }

    @Override
    public void pause() {
        if (isPlaying && !isPaused) {
            System.out.println("[FakeMusicPlayer] Paused: " + currentSongPath);
            isPaused = true;
            isPlaying = false;
        }
    }

    @Override
    public void resume(String songPath) {
        if (isPaused) {
            System.out.println("[FakeMusicPlayer] Resumed: " + currentSongPath);
            isPaused = false;
            isPlaying = true;
        }
        else {
            play(songPath);
        }
    }

    @Override
    public void stop() {
        System.out.println("[FakeMusicPlayer] Stopped: " + currentSongPath);
        currentSongPath = null;
        isPlaying = false;
        isPaused = false;
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public void setOnSongEndAction(Runnable action) {
        this.onSongEndCallback = action;
    }

    public void triggerSongEnd() {
        System.out.println("[FakeMusicPlayer] Song ended: " + currentSongPath);
        isPlaying = false;
        if (onSongEndCallback != null) {
            onSongEndCallback.run();
        }
    }

    public String getCurrentSongPath() {
        return currentSongPath;
    }
}
