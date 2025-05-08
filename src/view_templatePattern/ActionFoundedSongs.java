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
        this.pageContent = icon.goBack + " |  " + icon.goToHomepage + icon.lineBreak +
                    icon.iconNbr(1) + "Add to current playlist" + icon.lineBreak +
                    icon.iconNbr(2) + "Add to an other playlist (Ne fonctionne pas)" +icon.lineBreak +
                    icon.iconNbr(3) + "Create a new playlist" + icon.lineBreak;
    }

    @Override
    public void button1() {
        toolbox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(toolbox.getPlaylistServ().getCurrentPlaylistId());
        pageService.playlistPage.displayAllPage();
    }

    @Override
    public void button2() { //TODO : ajuster car la playlist temporaire ne transmet pas ses chansons à l'autre playlist.
        System.out.println("Your Playlists : ");
        toolbox.getPrintServ().printUserPlaylists(toolbox.getUserServ().getCurrentUserId());

        displayInput();
        toolbox.getPlaylistServ().validatePlaylistIdInput(pageService, toolbox.getSongServ());

        toolbox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(toolbox.getPlaylistServ().getCurrentPlaylistId());
        pageService.playlistPage.displayAllPage();
    }

    @Override
    public void button3() {
        pageService.playlistCreation.displayAllPage();
    }

    @Override
    public void button8(){//no action !
    }
}
