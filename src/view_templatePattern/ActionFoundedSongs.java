package view_templatePattern;

import data.entities.Playlist;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

import java.util.LinkedList;

public class ActionFoundedSongs extends _SimplePageTemplate {

    public ActionFoundedSongs(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Chose your action for the selected songs";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                    icon.iconNbr(1) + "Play selected songs" + icon.lineBreak +
                    icon.iconNbr(2) + "Add to current playlist" + icon.lineBreak +
                    icon.iconNbr(3) + "Add to an other playlist" +icon.lineBreak +
                    icon.iconNbr(4) + "Create a new playlist" + icon.lineBreak +
                    icon.lineBreak + icon.iconNbr(9) + "Dismiss and go to Home Page";
        ;
    }

    @Override
    public void button1() {
        Playlist temporaryPlaylist = toolbox.getPlaylistServ().getPlaylistByName("temporaryPlaylist");
        LinkedList <Integer> songs = temporaryPlaylist.getPlaylistSongsListWithId();

        if (songs != null && !songs.isEmpty()) {
                pageService.songPlayer.displayAllPage();
        } else {
            System.out.println("No songs found to play.");
        }
        pageService.search.displayAllPage();
    }

    @Override
    public void button2() {
        toolbox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(toolbox.getPlaylistServ().getCurrentPlaylistId());
        pageService.playlistDisplay.displayAllPage();
    }

    @Override
    public void button3() {
        System.out.println("Your Playlists : ");
        toolbox.getPrintServ().printUserPlaylists(toolbox.getUserServ().getCurrentUserId());

        displayInput();
        toolbox.getPlaylistServ().validatePlaylistIdInput(pageService);

        toolbox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(toolbox.getPlaylistServ().getCurrentPlaylistId());
        pageService.playlistDisplay.displayAllPage();
    }

    @Override
    public void button4() {
        pageService.playlistCreation.displayAllPage();
    }

    @Override
    public void button9(){
        pageService.homePage.displayAllPage();
    }
}
