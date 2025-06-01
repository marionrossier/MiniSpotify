package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import static clientSide.services.PrintHelper.printYourInput;

public abstract class TemplateInversePage extends TemplateSimplePage {

    public TemplateInversePage(PageService pageService, IPlaylistPlayer spotifyPlayer) {
        super(pageService, spotifyPlayer);
    }

    @Override
    public void displayAllPage() {
        pageService.pageIsPremium(isFree);
        pageService.addToStack(getPageId());
        displayTitle(pageTitle);
        displaySpecificContent();
        displayContent(pageContent);
        printYourInput();
        validateInput();
        switchPage();
    }
}
