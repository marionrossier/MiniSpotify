package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

import static clientSide.services.PrintHelper.*;

public class ActionFoundedSongs extends TemplateSimplePage {

    public ActionFoundedSongs(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Chose your action for the selected songs";
        this.pageContent = icon.zeroBack + " |  " + icon.nineHomepage + icon.lineBreak +
                    icon.nbr(1) + "Add to current playlist" + icon.lineBreak +
                    icon.nbr(2) + "Add to an other playlist" +icon.lineBreak +
                    icon.nbr(3) + "Create a new playlist" + icon.lineBreak;
    }

    @Override
    public void button1() {
        verificationAndThenAction();
    }

    @Override
    public void button2() {
        printLNWhite("Your Playlists : ");
        toolBoxView.getPrintServ().printUserPlaylists(toolBoxView.getUserServ().getCurrentUserId());

        displayInput();

        int chosenPlaylist = toolBoxView.getPlaylistServ().takeAndValidationInputPlaylistChoice();
        toolBoxView.getPlaylistServ().setCurrentPlaylistId(chosenPlaylist);
        verificationAndThenAction();
    }

    private void verificationAndThenAction() {
        int currentPlaylistId = toolBoxView.getPlaylistServ().getCurrentPlaylistId();
        int temporaryPlaylistId = toolBoxView.getPlaylistServ().getTemporaryPlaylistId();

        if (toolBoxView.getPlaylistServ().isCurrentUserOwnerOfPlaylist(currentPlaylistId)) {
            toolBoxView.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(temporaryPlaylistId,currentPlaylistId);
            pageService.playlistPageOpen.displayAllPage();
        }
        else {
            printLNInfo("You're not the owner of this playlist.");
            pageService.actionFoundedSongs.displayAllPage();
        }
    }

    @Override
    public void button3() {
        pageService.playlistCreation.displayAllPage();
    }

    @Override
    public void button8(){//no action !
    }
}
