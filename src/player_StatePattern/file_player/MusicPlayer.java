package player_StatePattern.file_player;

import javazoom.jlgui.basicplayer.*;

import java.io.File;
import java.util.Map;

public class MusicPlayer implements IMusicPlayer, BasicPlayerListener {
    private BasicPlayer player;
    private Runnable onSongEndAction;
    private boolean isPlaying = false;
    private boolean isPaused = false;

    public MusicPlayer() {
        player = new BasicPlayer();
        player.addBasicPlayerListener(this); // ECOUTE les événements du player
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
    public void resume() {
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
        isPlaying = false;
        isPaused = false;
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public void setOnSongEndAction(Runnable action) {
        this.onSongEndAction = action;
    }

    /* ===== Implémentation de BasicPlayerListener ===== */

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
        if (event.getCode() == BasicPlayerEvent.EOM) { // FIN DE CHANSON détectée
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
