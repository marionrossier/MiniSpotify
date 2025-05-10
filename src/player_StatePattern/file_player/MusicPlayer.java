package player_StatePattern.file_player;

import javazoom.jlgui.basicplayer.*;

import java.io.File;
import java.util.Map;

public class MusicPlayer implements IMusicPlayer, BasicPlayerListener {
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private final BasicPlayer player;
    private Runnable onSongEndAction;

    public MusicPlayer() {
        player = new BasicPlayer();
        player.addBasicPlayerListener(this);
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
    public void play(String songPath) {
        stop();
        try {
            player.open(new File(songPath));
            player.play();
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
                player.pause();
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
                player.resume();
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
            player.stop();
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


    @Override
    public void setOnSongEndAction(Runnable action) {
        this.onSongEndAction = action;
    }

    /* ===== Implementation of BasicPlayerListener ===== */

    @Override
    public void opened(Object stream, Map properties) {
        // rien à faire ici pour toi
    }

    @Override
    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
        // rien à faire ici pour toi
    }

    @Override
    public void stateUpdated(BasicPlayerEvent event) {
        if (event.getCode() == BasicPlayerEvent.EOM) {
            isPlaying = false;
            if (onSongEndAction != null) {
                onSongEndAction.run();
            }
        }
    }

    @Override
    public void setController(BasicController controller) {
        // rien à faire ici pour toi
    }

}
