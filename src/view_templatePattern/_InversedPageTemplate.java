package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public abstract class _InversedPageTemplate extends _SimplePageTemplate {

    public _InversedPageTemplate(PageService pageManager, IPlaylistPlayer spotifyPlayer) {
        super(pageManager, spotifyPlayer);
    }

    @Override
    public void displayAllPage(){
        pageService.getMenuPages().push(getPageId());
        displayTitle(pageTitle);
        displaySpecificContent();
        displayContent(pageContent);
        displayInput();
        validateInput();
        switchPage();
    }
}
