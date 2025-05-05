package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class SongPlayer extends _SimplePageTemplate {

    public SongPlayer(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Song Player Page";
        this.pageContent =
                icon.iconNbr(0) + icon.iconBack() + " | " + icon.iconNbr(9) + "Go to Home Page" +icon.lineBreak+
                "Your song player ! " +icon.lineBreak+
                icon.iconNbr(1) + ":"+ icon.iconShuffle() + " | " +
                icon.iconNbr(2) + ":"+ icon.iconPrevious() + " | " +
                icon.iconNbr(3) + ":"+ icon.iconPlayPause() +" | " +
                icon.iconNbr(4) + ":"+ icon.iconPlayBack() + " | " +
                icon.iconNbr(5) + ":"+ icon.iconNext() + " | " +
                icon.iconNbr(6) + ":"+ icon.iconRepeatOne();
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
        spotifyPlayer.playOrPause(toolbox.getSongServ().getCurrentSongId());
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
        while (spotifyPlayer.isPlaying() || spotifyPlayer.isPaused()) {
            System.out.println(toolbox.getSongServ().getSongById(spotifyPlayer.getCurrentSongId()).getSongName());
            displayInput();
            validateInput();
            switchPage();
        }
    }

    @Override
    public void button9(){
        pageService.homePage.displayAllPage();
    }
}
