package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class SearchGender extends _SimplePageTemplate {

    public SearchGender(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Search Gender";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                "Select your desired gender";
    }

    @Override
    public void displaySpecificContent(){
        //TODO: passer par un scanner pour avoir le choix final
        // afficher les genres disponibles depuis l'enum.
    }
}
