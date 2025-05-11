package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class SearchGender extends _SimplePageTemplate {

    public SearchGender(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Select your desired gender";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Soul RnB" + icon.lineBreak +
                icon.iconNbr(2) + "Pop" + icon.lineBreak +
                icon.iconNbr(3) + "Hip Hop" + icon.lineBreak +
                icon.iconNbr(4) + "Rock" + icon.lineBreak +
                icon.iconNbr(5) + "French Variety" + icon.lineBreak +
                icon.iconNbr(6) + "Electro" + icon.lineBreak +
                icon.iconNbr(7) + "Disco" + icon.lineBreak +
                icon.iconNbr(8) + "Reggae" + icon.lineBreak;
    }

    @Override
    public void button1() {
        toolbox.getSearchService().searchSong("SOUL_RNB", "byGender", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        toolbox.getSearchService().searchSong("POP", "byGender", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button3() {
        toolbox.getSearchService().searchSong("HIP_HOP", "byGender", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button4() {
        toolbox.getSearchService().searchSong("ROCK", "byGender", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button5() {
        toolbox.getSearchService().searchSong("FRENCH_VARIETY", "byGender", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button6() {
        toolbox.getSearchService().searchSong("ELECTRO", "byGender", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button7() {
        toolbox.getSearchService().searchSong("DISCO", "byGender", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button8() {
        toolbox.getSearchService().searchSong("REGGAE", "byGender", getPageId(), pageService, toolbox.getPlaylistServ());
        pageService.actionFoundedSongs.displayAllPage();
    }
}
