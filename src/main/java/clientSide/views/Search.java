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
        this.pageContent = PrintHelper.backHomePageMusicPlayer + PrintHelper.lineBreak + PrintHelper.separator + PrintHelper.lineBreak +
                PrintHelper.nbr1 + "Search by song title" + PrintHelper.lineBreak +
                PrintHelper.nbr2 + "Search by artist" + PrintHelper.lineBreak +
                PrintHelper.nbr3 + "Search by song gender" + PrintHelper.lineBreak +
                PrintHelper.nbr4 + "Search by public playlist";
    }

    @Override
    public void button1() {
        printWhite(PrintHelper.lineBreak + PrintHelper.search + "Enter the title of the song : ");
        String songTitle = scanner.nextLine();
        printLN();

        toolBoxView.getSearchServ().searchSong(songTitle, "byTitle", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        printWhite(PrintHelper.lineBreak + PrintHelper.search + "Enter the name of the artist : " + PrintHelper.lineBreak);
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
        printLNWhite("Select your playlist to add by entering their number and press \"enter\" between each playlist." + PrintHelper.lineBreak +
                "End selection with an \"x\"." + PrintHelper.lineBreak);

        List<Integer> playlist = toolBoxView.getPlaylistServ().getPublicPlaylists();
        toolBoxView.getPrintServ().printPlaylist(playlist);

        printYourInput();

        LinkedList<Integer> chosenPlaylists = toolBoxView.getSearchServ().chooseFoundedPlaylist(playlist, pageService);
        toolBoxView.getPlaylistServ().getAndAddSelectionOfPlaylistsToCurrentUserPlaylists(playlist, chosenPlaylists, toolBoxView);
        pageService.playlistHomePage.displayAllPage();
    }
}
