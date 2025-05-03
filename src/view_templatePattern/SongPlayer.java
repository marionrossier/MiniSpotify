package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;

public class SongPlayer extends _SimplePageTemplate {

    public SongPlayer(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Song Player Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak+
                "Your song player ! " +icon.lineBreak+
                icon.iconNbr(1) + ":"+ icon.iconShuffle() + " |" +
                icon.iconNbr(2) + ":"+ icon.iconPrevious() + " |" +
                icon.iconNbr(3) + ":"+ icon.iconPlayPause() +" |" +
                icon.iconNbr(4) + ":"+ icon.iconPlayBack() + " |" +
                icon.iconNbr(5) + ":"+ icon.iconNext() + " |" +
                icon.iconNbr(6) + ":"+ icon.iconRepeatOne() + " |";
    }

    @Override
    public void button1() {
        spotifyPlayer.setShuffleMode();
        loop();
    }

    @Override
    public void button2() {
        spotifyPlayer.previous();
        loop();
    }

    @Override
    public void button3() {
        spotifyPlayer.playOrPause(Cookies_SingletonPattern.getInstance().getCurrentSongId());
        loop();
    }

    @Override
    public void button4() {
        spotifyPlayer.playback();
        loop();
    }

    @Override
    public void button5() {
        spotifyPlayer.next();
        loop();
    }

    @Override
    public void button6() {
        spotifyPlayer.setRepeatMode();
        loop();
    }

    void loop(){
        while (spotifyPlayer.getMusicPlayer().isPlaying() || spotifyPlayer.getMusicPlayer().isPaused()) {
            System.out.println(toolbox.getSongRepo().getSongById(spotifyPlayer.getRunningSongId()).getSongName());
            displayInput();
            validateInput();
            switchPage();
        }
    }
}
