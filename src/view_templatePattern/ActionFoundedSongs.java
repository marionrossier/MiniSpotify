package view_templatePattern;

import data.entities.Playlist;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;
import services.PageService;

import java.util.LinkedList;

public class ActionFoundedSongs extends _SimplePageTemplate {

    public ActionFoundedSongs(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Chose your action for the founded songs";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                    icon.iconNbr(1) + "Play selected songs" + icon.lineBreak +
                    icon.iconNbr(2) + "Add to current playlist" +
                    icon.lineBreak + icon.iconNbr(9) + "Go to Home Page";
        ;
    }

    @Override
    public void button1() {
        Playlist temporaryPlaylist = toolbox.getPlaylistRepo().getPlaylistByName("temporaryPlaylist");
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
        toolbox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(Cookies_SingletonPattern.getInstance().getCurrentPlaylistId());
        pageService.playlistDisplay.displayAllPage();
    }

    @Override
    public void button9(){
        pageService.homePage.displayAllPage();
    }
}
