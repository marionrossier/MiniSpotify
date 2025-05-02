package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class SearchGender extends AbstractMenuPage {

    public SearchGender(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Search Gender";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Select your desired gender";
    }

    @Override
    void displaySpecificContent (){
        //TODO: passer par un scanner pour avoir le choix final
        // afficher les genres disponibles depuis l'enum.
    }
}
