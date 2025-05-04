package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class Search extends _SimplePageTemplate {

    public Search(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Search Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Search a song" + icon.lineBreak +
                icon.iconNbr(2) + "Search an artist" + icon.lineBreak +
                icon.iconNbr(3) + "Search a song gender" + icon.goToMusicPlayer;
    }

    @Override
    public void button1() {
        System.out.print(icon.lineBreak + icon.iconSearch() + "Enter the title of the song : " + icon.lineBreak);
        String songTitle = scanner.nextLine();

        toolbox.getSongServ().searchSong(songTitle, "byTitle", getPageId(), pageService);
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button2() {
        System.out.print(icon.lineBreak + icon.iconSearch() + "Enter the name of the artist : " + icon.lineBreak);
        String artistName = scanner.nextLine();

        toolbox.getSongServ().searchSong(artistName, "byArtist", getPageId(), pageService);
        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button3() {
        pageService.searchGender.displayAllPage();
    }
}
