package clientSide.view_templatePattern;

import clientSide.services.ToolBoxView;
import serverSide.entities.Playlist;
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
                icon.zeroBack + " |  " + icon.nineHomepage +icon.lineBreak+
                icon.nbr1() + icon.sequential() + "/" + icon.shuffle() + "/" + icon.repeatOne()+ " | " +
                icon.nbr2() + icon.previous() + " | " +
                icon.nbr3() +  icon.playPause() +" | " +
                icon.nbr4() +  icon.playBack() + " | " +
                icon.nbr5() +  icon.next();}

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
        spotifyPlayer.previous();
        loop();
    }

    @Override
    public void button3() {
        spotifyPlayer.playOrPause(toolBoxView.getSongServ().getCurrentSongId());
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
