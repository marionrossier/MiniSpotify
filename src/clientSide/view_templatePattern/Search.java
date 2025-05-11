package clientSide.view_templatePattern;

import clientSide.services.ViewToolBox;
import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import java.util.LinkedList;
import java.util.List;

public class Search extends _SimplePageTemplate {

    public Search(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Search Page";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.nbr(1) + "Search by song title" + icon.lineBreak +
                icon.nbr(2) + "Search by artist" + icon.lineBreak +
                icon.nbr(3) + "Search by song gender" + icon.lineBreak +
                icon.nbr(4) + "Search by public playlist";
    }

    @Override
    public void button1() {
        System.out.print(icon.lineBreak + icon.search() + "Enter the title of the song : ");
        String songTitle = scanner.nextLine();
        System.out.println();

        viewToolBox.getSearchServ().searchSong(songTitle, "byTitle", getPageId(), pageService, viewToolBox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        System.out.print(icon.lineBreak + icon.search() + "Enter the name of the artist : " + icon.lineBreak);
        String artistName = scanner.nextLine();
        System.out.println();

        viewToolBox.getSearchServ().searchSong(artistName, "byArtist", getPageId(), pageService, viewToolBox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button3() {
        pageService.searchGender.displayAllPage();
    }

    @Override
    public void button4(){
        System.out.println();
        System.out.println("Select your Playlist to add by entering their number and press \"enter\" between each song." + icon.lineBreak +
                "End selection with an \"x\"." + icon.lineBreak);

        List<Playlist> playlist = viewToolBox.getPlaylistServ().getPublicPlaylists();
        viewToolBox.getPrintServ().printPlaylist(playlist);
        this.displayInput();
        LinkedList<Integer> chosenPlaylists = viewToolBox.getSearchServ().chooseFoundedPlaylist(playlist, pageService);

        for (Integer playlistIndex : chosenPlaylists) {
            int playlistId = playlist.get(playlistIndex).getPlaylistId();
            if (!viewToolBox.getUserServ().getUserById(viewToolBox.getUserServ().getCurrentUserId())
                    .getPlaylists()
                    .contains(playlistId)) {
                viewToolBox.getUserServ().addOnePlaylist(playlistId);
            }
        }
        System.out.println("Playlist.s has been added.");
        pageService.playlistHomePage.displayAllPage();
    }
}
