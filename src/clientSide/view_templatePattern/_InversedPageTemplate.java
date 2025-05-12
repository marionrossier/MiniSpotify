package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public abstract class _InversedPageTemplate extends _SimplePageTemplate {

    public _InversedPageTemplate(PageService pageService, IPlaylistPlayer spotifyPlayer) {
        super(pageService, spotifyPlayer);
    }

    @Override
    public void displayAllPage() {
        pageService.pageIsPremium(isFree);
        pageService.addToStack(getPageId());
        displayTitle(pageTitle);
        displaySpecificContent();
        displayContent(pageContent);
        displayInput();
        validateInput();
        switchPage();
    }
}
