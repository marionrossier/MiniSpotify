package view_templatePattern;

import data.entities.MusicGender;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

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
        toolbox.getSongServ().searchSong(MusicGender.SOUL_RNB.getDisplayName(), "byGender", getPageId());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        toolbox.getSongServ().searchSong(MusicGender.POP.getDisplayName(), "byGender", getPageId());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button3() {
        toolbox.getSongServ().searchSong(MusicGender.HIP_HOP.getDisplayName(), "byGender", getPageId());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button4() {
        toolbox.getSongServ().searchSong(MusicGender.ROCK.getDisplayName(), "byGender", getPageId());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button5() {
        toolbox.getSongServ().searchSong(MusicGender.FRENCH_VARIETY.getDisplayName(), "byGender", getPageId());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button6() {
        toolbox.getSongServ().searchSong(MusicGender.ELECTRO.getDisplayName(), "byGender", getPageId());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button7() {
        toolbox.getSongServ().searchSong(MusicGender.DISCO.getDisplayName(), "byGender", getPageId());
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button8() {
        toolbox.getSongServ().searchSong(MusicGender.REGGAE.getDisplayName(), "byGender", getPageId());
        pageService.actionFoundedSongs.displayAllPage();
    }
}
