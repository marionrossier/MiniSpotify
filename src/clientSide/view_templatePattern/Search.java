package clientSide.view_templatePattern;

import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import java.util.LinkedList;
import java.util.List;

public class Search extends _SimplePageTemplate {

    public Search(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Search Page";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.iconNbr(1) + "Search by song title" + icon.lineBreak +
                icon.iconNbr(2) + "Search by artist" + icon.lineBreak +
                icon.iconNbr(3) + "Search by song gender" + icon.lineBreak +
                icon.iconNbr(4) + "Search by public playlist";
    }

    @Override
    public void button1() {
        System.out.print(icon.lineBreak + icon.iconSearch() + "Enter the title of the song : ");
        String songTitle = scanner.nextLine();
        System.out.println();

        toolbox.getSearchService().searchSong(songTitle, "byTitle", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        System.out.print(icon.lineBreak + icon.iconSearch() + "Enter the name of the artist : " + icon.lineBreak);
        String artistName = scanner.nextLine();
        System.out.println();

        toolbox.getSearchService().searchSong(artistName, "byArtist", getPageId(), pageService, toolbox.getPlaylistServ());
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

        List<Playlist> playlist = toolbox.getPlaylistServ().getPublicPlaylists();
        toolbox.getPrintServ().printPlaylist(playlist);
        this.displayInput();
        LinkedList<Integer> chosenPlaylists = toolbox.getSearchService().chooseFoundedPlaylist(playlist, pageService);

        for (Integer playlistIndex : chosenPlaylists) {
            int playlistId = playlist.get(playlistIndex).getPlaylistId();
            if (!toolbox.getUserServ().getUserById(toolbox.getUserServ().getCurrentUserId())
                    .getPlaylists()
                    .contains(playlistId)) {
                toolbox.getUserServ().addOnePlaylist(playlistId);
            }
        }
        System.out.println("Playlist.s has been added.");
        pageService.playlistHomePage.displayAllPage();
    }
}
