package view_templatePattern;

import data.entities.Artist;
import data.jsons.ArtistRepository;
import player_commandPattern.SpotifyPlayer;

public class Search extends AbstractMenuPage {

    public Search(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Search Page";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Search a song" + lineBreak +
                nb2 + "Search an artist" + lineBreak +
                nb3 + "Search a song gender";
    }

    @Override
    void button1() {
        System.out.println(search + "Search Song Page");
        System.out.print(lineBreak + "Enter the name of the song : ");
        String songName = in.nextLine();
        // TODO : chercher via SongRepo dans le json tous les songName qui ressembles et permettre le choix.
    }

    @Override
    void button2() {
        System.out.println(search + "Search Artist Page");
        System.out.print(lineBreak + "Enter the name of the artist : ");
        String artistName = in.nextLine();
        // TODO : idem que Song, chercher via le ArtistRep ou il faut créer une méthode
        //  "getArtistByName(String name) return liste" des correspondances

        ArtistRepository artistRepository = new ArtistRepository();
        Artist searchedArtist = artistRepository.findArtistByName(artistName); //Pas juste à 100%
    }

    @Override
    void button3() {
        spotifyPageFactory.searchGender.templateMethode();
    }

}
