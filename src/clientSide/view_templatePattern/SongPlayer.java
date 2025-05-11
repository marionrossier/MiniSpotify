package clientSide.view_templatePattern;

import clientSide.services.ViewToolBox;
import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class SongPlayer extends _SimplePageTemplate {

    public SongPlayer(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Song Player Page";
        this.pageContent =
                icon.zeroBack + " |  " + icon.nineHomepage +icon.lineBreak+
                icon.nbr(1) + ":"+ icon.shuffle() + " | " +
                icon.nbr(2) + ":"+ icon.previous() + " | " +
                icon.nbr(3) + ":"+ icon.playPause() +" | " +
                icon.nbr(4) + ":"+ icon.playBack() + " | " +
                icon.nbr(5) + ":"+ icon.next() + " | " +
                icon.nbr(6) + ":"+ icon.repeatOne();
    }

    @Override
    public void displaySpecificContent(){
        Playlist playlist = viewToolBox.getPlaylistServ().getPlaylistById(viewToolBox.getPlaylistServ().getCurrentPlaylistId());
        System.out.println(
                "Current Playlist : " + playlist.getName() +
                ", duration " + (viewToolBox.getPlaylistServ().setDurationSeconds(playlist.getPlaylistId())/60) + ":" +
                        viewToolBox.getPlaylistServ().setDurationSeconds(playlist.getPlaylistId())%60 +
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
        spotifyPlayer.playOrPause(viewToolBox.getSongServ().getCurrentSongId());
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
            int currentSongId = spotifyPlayer.getCurrentSongId();
            System.out.println("Current lecture : " + viewToolBox.getSongServ().getSongById(currentSongId).getTitle() + " - " +
                    viewToolBox.getArtistServ().getArtistNameBySong(currentSongId));
            displayInput();
            validateInput();
            switchPage();
        }
    }
}
