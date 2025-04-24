package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SearchGender extends AbstractMenuPage {

    public SearchGender(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Search Gender";
        this.pageContent = backLineWith0 + lineBreak +
                "Select your desired gender";
    }

    @Override
    void displaySpecificContent (){
        //TODO: passer par un scanner pour avoir le choix final
        // afficher les genres disponibles depuis l'enum.
    }
}
