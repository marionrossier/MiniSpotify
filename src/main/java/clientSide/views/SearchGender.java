package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

public class SearchGender extends TemplateSimplePage {

    public SearchGender(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Select your desired gender";
        this.pageContent = icon.zeroBack + icon.lineBreak + icon.separator + icon.lineBreak +
                icon.nbr1() + "Soul RnB" + icon.lineBreak +
                icon.nbr2() + "Pop" + icon.lineBreak +
                icon.nbr3() + "Hip Hop" + icon.lineBreak +
                icon.nbr4() + "Rock" + icon.lineBreak +
                icon.nbr5() + "French Variety" + icon.lineBreak +
                icon.nbr6() + "Electro" + icon.lineBreak +
                icon.nbr7() + "Disco" + icon.lineBreak +
                icon.nbr8() + "Reggae" + icon.lineBreak;
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
