package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public abstract class TemplateInversedPage extends TemplateSimplePage {

    public TemplateInversedPage(PageService pageService, IPlaylistPlayer spotifyPlayer) {
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
