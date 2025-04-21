package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class HomePagePlaylist extends AbstractMenuPage {

    public HomePagePlaylist(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Home Page Playlist";
        this.pageContent =
                backLineWith0 + lineBreak +
                nb1 + "Choose your playlist" + lineBreak +
                nb2 + "Create a playlist";
    }

    @Override
    void button1() {
        spotifyPageFactory.choseYourPlaylist.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.createPlaylist.templateMethode();
    }

}
