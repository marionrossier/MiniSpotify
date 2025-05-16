package clientSide.view_templatePattern;

import clientSide.services.ToolBoxView;
import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import serverSide.entities.User;

import java.util.LinkedList;
import java.util.List;

public class Search extends _SimplePageTemplate {

    public Search(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
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

        toolBoxView.getSearchServ().searchSong(songTitle, "byTitle", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        System.out.print(icon.lineBreak + icon.search() + "Enter the name of the artist : " + icon.lineBreak);
        String artistName = scanner.nextLine();
        System.out.println();

        toolBoxView.getSearchServ().searchSong(artistName, "byArtist", getPageId(), pageService, toolBoxView.getPlaylistServ());
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

        List<Integer> playlist = toolBoxView.getPlaylistServ().getPublicPlaylists();
        User user = toolBoxView.getUserServ().getUserById(toolBoxView.getUserServ().getCurrentUserId());
        toolBoxView.getPrintServ().printPlaylist(playlist);
        this.displayInput();
        LinkedList<Integer> chosenPlaylists = toolBoxView.getSearchServ().chooseFoundedPlaylist(playlist, pageService);
        toolBoxView.getPlaylistServ().getAndAddSelectionOfPlaylistsToCurrentUserPlaylists(playlist, chosenPlaylists, toolBoxView);
        pageService.playlistHomePage.displayAllPage();
    }
}
