package serverSide.view_templatePattern;

import clientSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class SongPlayer extends _SimplePageTemplate {

    public SongPlayer(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Song Player Page";
        this.pageContent =
                icon.iconBack() + " |  " + icon.goToHomepage +icon.lineBreak+
                icon.iconNbr(1) + ":"+ icon.iconShuffle() + " | " +
                icon.iconNbr(2) + ":"+ icon.iconPrevious() + " | " +
                icon.iconNbr(3) + ":"+ icon.iconPlayPause() +" | " +
                icon.iconNbr(4) + ":"+ icon.iconPlayBack() + " | " +
                icon.iconNbr(5) + ":"+ icon.iconNext() + " | " +
                icon.iconNbr(6) + ":"+ icon.iconRepeatOne();
    }

    @Override
    public void displaySpecificContent(){
        Playlist playlist = toolbox.getPlaylistServ().getPlaylistById(toolbox.getPlaylistServ().getCurrentPlaylistId());
        System.out.println(
                "Current Playlist : " + playlist.getName() +
                ", duration " + (playlist.getDurationSeconds()/60) + ":" + playlist.getDurationSeconds()%60 +
                ", size : " + playlist.getSize() + icon.lineBreak);
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

    @Override
    public void button8(){
    //No action !
    }

    void loop(){
        while (spotifyPlayer.isPlaying() || spotifyPlayer.isPaused()) {
            System.out.println(toolbox.getSongServ().getSongById(spotifyPlayer.getCurrentSongId()).getTitleAndArtist());
            displayInput();
            validateInput();
            switchPage();
        }
    }
}
