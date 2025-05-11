package fakes;

import clientSide.player_StatePattern.file_player.IMusicPlayer;

public class FakeMusicPlayer implements IMusicPlayer {
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private String currentSongFileName = null;
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
    public void play(String songFileName) {
        currentSongFileName = songFileName;
        System.out.println("[FakeMusicPlayer] Playing: " + currentSongFileName);
        isPlaying = true;
        isPaused = false;
    }

    @Override
    public void pause() {
        if (isPlaying && !isPaused) {
            System.out.println("[FakeMusicPlayer] Paused: " + currentSongFileName);
            isPaused = true;
            isPlaying = false;
        }
    }

    @Override
    public void resume(String songPath) {
        if (isPaused) {
            System.out.println("[FakeMusicPlayer] Resumed: " + currentSongFileName);
            isPaused = false;
            isPlaying = true;
        }
        else {
            play(songPath);
        }
    }

    @Override
    public void stop() {
        System.out.println("[FakeMusicPlayer] Stopped: " + currentSongFileName);
        currentSongFileName = null;
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
        System.out.println("[FakeMusicPlayer] Song ended: " + currentSongFileName);
        isPlaying = false;
        if (onSongEndCallback != null) {
            onSongEndCallback.run();
        }
    }

    public String getCurrentSongFileName() {
        return currentSongFileName;
    }
}
