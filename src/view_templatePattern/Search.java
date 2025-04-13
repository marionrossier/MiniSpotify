package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

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
        // TODO : comment tu veux faire pour la suite ?
    }

    @Override
    void button2() {
        System.out.println(search + "Search Artist Page");
        System.out.print(lineBreak + "Enter the name of the artist : ");
        String artistName = in.nextLine();
        // TODO : comment tu veux faire pour la suite ?
    }

    @Override
    void button3() {
        System.out.println(search + "Search Gender");
        System.out.print(lineBreak + "Enter the name of the song gender : ");
        String songGenderName = in.nextLine();
        // TODO : comment tu veux faire pour la suite ? Peut être afficher d'office une liste pour choisir ?
        //  Dans ce cas en effet une classe SearchGender serait plus appropriée.
    }

}
