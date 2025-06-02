package clientSide.player.file_player;

import common.repository.IAudioRepository;
import javazoom.jlgui.basicplayer.*;

import java.util.*;

public class MusicPlayer implements IMusicPlayer, BasicPlayerListener {
    private final IAudioRepository audioRepository;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private final BasicPlayer basicPlayer;
    private final List<Runnable> songEndObservers = new ArrayList<>();

    public MusicPlayer(IAudioRepository audioRepository, BasicPlayer basicPlayer) {
        this.audioRepository = audioRepository;
        this.basicPlayer = basicPlayer;
        this.basicPlayer.addBasicPlayerListener(this);
    }

    public void addSongEndObserver(Runnable observer) {
        songEndObservers.add(observer);
    }

    public void playOrPause (String songPath) {
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
        stop();
        try {
            basicPlayer.open(audioRepository.getStream(songFileName));
            basicPlayer.play();
            isPlaying = true;
            isPaused = false;
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        if (isPlaying && !isPaused) {
            try {
                basicPlayer.pause();
                isPaused = true;
                isPlaying = false;
            } catch (BasicPlayerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void resume(String songPath) {
        if (isPaused) {
            try {
                basicPlayer.resume();
                isPaused = false;
                isPlaying = true;
            } catch (BasicPlayerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        try {
            basicPlayer.stop();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
        isPaused = false;
        isPlaying = false;
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public boolean isPaused(){
        return isPaused;
    }

    /* ===== Implementation of BasicPlayerListener ===== */

    @Override
    public void opened(Object stream, Map properties) {
        // nothing to do here
    }

    @Override
    public void progress(int bytesRead, long microseconds, byte[] pcmData, Map properties) {
        // nothing to do here
    }

    @Override
    public void stateUpdated(BasicPlayerEvent event) {
        if (event.getCode() == BasicPlayerEvent.EOM) {
            isPlaying = false;
            for (Runnable observer : songEndObservers) {
                observer.run();
            }
        }
    }

    @Override
    public void setController(BasicController controller) {
        // nothing to do here
    }

}
