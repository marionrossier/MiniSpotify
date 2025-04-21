package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class ChoseYourPlaylist extends AbstractMenuPage {

    public ChoseYourPlaylist(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = "Chose your playlist or " + backLineWith0;
    }

    @Override
    void displaySpecificContent() {
        // TODO : display the list of playlists
    }
}
