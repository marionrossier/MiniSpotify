package clientSide.views;

import clientSide.services.ToolBoxView;
import common.entities.Playlist;
import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.*;
import common.entities.Song;

import static clientSide.services.PrintHelper.*;

public class SongPlayer extends TemplateSimplePage {

    public SongPlayer(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Song Player Page";
        this.pageContent =
                        PrintHelper.backHomePageMusicPlayer + "\n" +
                        PrintHelper.separator + "\n" +
                        PrintHelper.b1 + PrintHelper.sequential + " / " + PrintHelper.shuffle + " / " + PrintHelper.repeatOne + "\n" +
                                PrintHelper.b2 + PrintHelper.playPause + "\n" +
                                PrintHelper.b3 + PrintHelper.previous + "\t" +
                                PrintHelper.b4 + " " + PrintHelper.next + "\t " + PrintHelper.b5 + PrintHelper.playBack;
    }

    @Override
    public void displaySpecificContent(){
        Playlist playlist = toolBoxView.getPlaylistServ().getPlaylistById(toolBoxView.getPlaylistServ().getCurrentPlaylistId());
        Song song = toolBoxView.getSongServ().getSongById(toolBoxView.getSongServ().getCurrentSongId());
        printLNBlue(toolBoxView.getPrintServ().printPlaylist(playlist));
        printLNBlue("Next " + toolBoxView.getPrintServ().printSong(song));
        toolBoxView.getPrintServ().printSong(song);
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
