package view_templatePattern;

import data.entities.Artist;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

import java.util.List;

public class Search extends _SimplePageTemplate {

    public Search(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Search Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Search a song" + icon.lineBreak +
                icon.iconNbr(2) + "Search an artist" + icon.lineBreak +
                icon.iconNbr(3) + "Search a song gender";
    }

    @Override
    public void button1() {
        System.out.print(icon.iconSearch() + "Enter the title of the song : ");
        String songTitle = scanner.nextLine();

        List<Integer> foundedSongs = toolbox.getSongServ().searchSongByTitle(songTitle);
        toolbox.getPrintServ().printSongFound(foundedSongs, songTitle);

        //TODO : terminer
//        List<Integer> chosenSongs = songService.chooseFoundedSongs(foundedSongs);
//
//        Cookies_SingeltonPattern.setTemporaryPlaylist(chosenSongs);
//        spotifyPageFactory.actionFoundedSongs.templateMethode();
    }

    @Override
    public void button2() {
        System.out.println(icon.iconSearch() + "Search Artist Page");
        System.out.print(icon.lineBreak + "Enter the name of the artist : ");
        String artistName = scanner.nextLine();
        // TODO : idem que Song, chercher via le ArtistRep ou il faut créer une méthode
        //  "getArtistByName(String name) return liste" des correspondances

        Artist searchedArtist = toolbox.getArtistRepo().getArtistByName(artistName); //Pas juste à 100%
    }

    @Override
    public void button3() {
        pageService.searchGender.displayAllPage();
    }

}
