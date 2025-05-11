package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public abstract class _InversedPageTemplate extends _SimplePageTemplate {

    private final PageService pageManager;

    public _InversedPageTemplate(PageService pageService, IPlaylistPlayer spotifyPlayer) {
        super(pageService, spotifyPlayer);
        this.pageManager = pageService;
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
