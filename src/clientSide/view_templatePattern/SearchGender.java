package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

public class SearchGender extends _SimplePageTemplate {

    public SearchGender(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Select your desired gender";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                icon.nbr(1) + "Soul RnB" + icon.lineBreak +
                icon.nbr(2) + "Pop" + icon.lineBreak +
                icon.nbr(3) + "Hip Hop" + icon.lineBreak +
                icon.nbr(4) + "Rock" + icon.lineBreak +
                icon.nbr(5) + "French Variety" + icon.lineBreak +
                icon.nbr(6) + "Electro" + icon.lineBreak +
                icon.nbr(7) + "Disco" + icon.lineBreak +
                icon.nbr(8) + "Reggae" + icon.lineBreak;
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
