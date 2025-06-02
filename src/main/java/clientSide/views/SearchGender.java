package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;

public class SearchGender extends TemplateSimplePage {

    public SearchGender(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Select your desired gender";
        this.pageContent = PrintHelper.zeroBack + PrintHelper.lineBreak + PrintHelper.separator + PrintHelper.lineBreak +
                PrintHelper.nbr1 + "Soul RnB" + PrintHelper.lineBreak +
                PrintHelper.nbr2 + "Pop" + PrintHelper.lineBreak +
                PrintHelper.nbr3 + "Hip Hop" + PrintHelper.lineBreak +
                PrintHelper.nbr4 + "Rock" + PrintHelper.lineBreak +
                PrintHelper.nbr5 + "French Variety" + PrintHelper.lineBreak +
                PrintHelper.nbr6 + "Electro" + PrintHelper.lineBreak +
                PrintHelper.nbr7 + "Disco" + PrintHelper.lineBreak +
                PrintHelper.nbr8 + "Reggae" + PrintHelper.lineBreak;
    }

    @Override
    public void button1() {
        toolBoxView.getSearchServ().searchSong("SOUL_RNB", "byGender", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        toolBoxView.getSearchServ().searchSong("POP", "byGender", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button3() {
        toolBoxView.getSearchServ().searchSong("HIP_HOP", "byGender", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button4() {
        toolBoxView.getSearchServ().searchSong("ROCK", "byGender", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button5() {
        toolBoxView.getSearchServ().searchSong("FRENCH_VARIETY", "byGender", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button6() {
        toolBoxView.getSearchServ().searchSong("ELECTRO", "byGender", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button7() {
        toolBoxView.getSearchServ().searchSong("DISCO", "byGender", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button8() {
        toolBoxView.getSearchServ().searchSong("REGGAE", "byGender", getPageId(), pageService, toolBoxView.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }
}
