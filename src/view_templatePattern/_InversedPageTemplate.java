package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public abstract class _InversedPageTemplate extends _SimplePageTemplate {

    private final PageService pageManager;

    public _InversedPageTemplate(PageService pageManager, IPlaylistPlayer spotifyPlayer) {
        super(pageManager, spotifyPlayer);
        this.pageManager = pageManager;
    }

    @Override
    public void displayAllPage(){
        pageManager.getMenuPages().push(getPageId());
        displayTitle(pageTitle);
        displaySpecificContent();
        displayContent(pageContent);
        displayInput();
        validateInput();
        switchPage();
    }
}
