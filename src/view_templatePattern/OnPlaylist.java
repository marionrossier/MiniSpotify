package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class OnPlaylist extends AbstractMenuPage{

    public OnPlaylist(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Playlist Page : ";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Play the playlist" + lineBreak +
                nb2 + "Edit the playlist" + lineBreak +
                nb3 + "Delete the playlist";
    }

    @Override
    void button0() {
        spotifyPageFactory.choseYourPlaylist.templateMethode();
    }

    @Override
    void button1() {
        spotifyPageFactory.songPlayer.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.editPlaylist.templateMethode();
    }

    @Override
    void button3() {
        spotifyPageFactory.deletePlaylist.templateMethode();
    }
}
