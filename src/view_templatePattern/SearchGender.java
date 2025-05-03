package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class SearchGender extends _SimplePageTemplate {

    public SearchGender(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
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
