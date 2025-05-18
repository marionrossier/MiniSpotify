package clientSide.view_templatePattern;

import clientSide.services.ToolBoxView;
import common.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.*;

import static clientSide.services.PrintHelper.*;

public class SongPlayer extends TemplateSimplePage {

    public SongPlayer(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Song Player Page";
        this.pageContent =
                        icon.backHomePageMusicPlayer + icon.lineBreak +
                        icon.separator + icon.lineBreak +
                        icon.nbr1() + icon.sequential() + " / " + icon.shuffle() + " / " + icon.repeatOne() + icon.lineBreak +
                                icon.nbr2() + icon.playPause() + icon.lineBreak +
                                icon.nbr3()+ icon.previous() + "\t" +
                                icon.nbr4() + " " + icon.next() + "\t " + icon.nbr5() + icon.playBack();

    }

    @Override
    public void displaySpecificContent(){
        System.out.println();
        Playlist playlist = toolBoxView.getPlaylistServ().getPlaylistById(toolBoxView.getPlaylistServ().getCurrentPlaylistId());
        printLNBlue(
                "Current Playlist : " + playlist.getName() +
                ", duration " + (toolBoxView.getPlaylistServ().setDurationSeconds(playlist.getPlaylistId())/60) + " minutes " +
                        toolBoxView.getPlaylistServ().setDurationSeconds(playlist.getPlaylistId())%60 +
                " seconds, size : " + playlist.getPlaylistSongsListWithId().size() + " songs.");
    }

    @Override
    public void button1() {

        String currentState = spotifyPlayer.getCurrentState();
        switch (currentState) {
            case "sequential":
                spotifyPlayer.setShuffleMode();
                break;
            case "shuffle":
                spotifyPlayer.setRepeatMode();
                break;
            case "repeat":
                spotifyPlayer.setSequentialMode();
                break;
        }
        loop();
    }

    @Override
    public void button2() {
        spotifyPlayer.playOrPause(toolBoxView.getSongServ().getCurrentSongId());
        loop();
    }

    @Override
    public void button3() {
        spotifyPlayer.previous();
        loop();
    }

    @Override
    public void button4() {
        spotifyPlayer.next();
        loop();
    }

    @Override
    public void button5() {
        spotifyPlayer.playback();
        loop();
    }

    @Override
    public void button8(){
    //No action !
    }

    void loop() {
        while (spotifyPlayer.isPlaying() || spotifyPlayer.isPaused()) {
            int currentSongId = spotifyPlayer.getCurrentSongId();
            String prefix = spotifyPlayer.isPaused() ? "Song paused : " : "Song played : ";

            printLNBlue(prefix + toolBoxView.getSongServ().getSongById(currentSongId).getTitle() + " - " +
                    toolBoxView.getArtistServ().getArtistNameBySong(currentSongId) + ". ");
            printWhite("Next input : ");

            validateInput();
            switchPage();
        }
        while (!spotifyPlayer.isPlaying() || !spotifyPlayer.isPaused()) {
            validateInput();
            switchPage();
        }
    }
}
