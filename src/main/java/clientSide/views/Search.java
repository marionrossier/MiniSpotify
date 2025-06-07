package clientSide.views;

import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;
import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import java.util.LinkedList;
import java.util.List;

import static clientSide.services.PrintHelper.*;

public class Search extends TemplateSimplePage {

    public Search(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Search Page";
        this.pageContent = PrintHelper.backHomePageMusicPlayer + "\n" + PrintHelper.separator + "\n" +
                PrintHelper.b1 + "Search by song title" + "\n" +
                PrintHelper.b2 + "Search by artist" + "\n" +
                PrintHelper.b3 + "Search by song gender" + "\n" +
                PrintHelper.b4 + "Search by public playlist";
    }

    @Override
    public void button1() {
        printWhite("\n" + "Enter the title of the song : ");
        String songTitle = scanner.nextLine();
        printLN();

        toolBoxView.getSearchServ().searchSong(songTitle, "byTitle", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        printWhite("\n" + "Enter the name of the artist : ");
        String artistName = scanner.nextLine();
        printLN();

        toolBoxView.getSearchServ().searchSong(artistName, "byArtist", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button3() {
        pageService.searchGender.displayAllPage();
    }

    @Override
    public void button4(){
        printLN();
        printLNWhite("Select your playlist to add by entering their number and press \"enter\" between each playlist." + "\n" +
                "End selection with an \"x\"." + "\n");

        List<Integer> playlists = toolBoxView.getPlaylistServ().getPublicPlaylists(toolBoxView.getUserServ().getCurrentUserId());
        toolBoxView.getPrintServ().printPlaylists(playlists);

        printYourInput();

        LinkedList<Integer> chosenPlaylists = toolBoxView.getSearchServ().chooseFoundedPlaylist(playlists, pageService);
        toolBoxView.getPlaylistServ().getAndAddSelectionOfPlaylistsToCurrentUserPlaylists(playlists, chosenPlaylists, toolBoxView);
        pageService.playlistHomePage.displayAllPage();
    }
}
