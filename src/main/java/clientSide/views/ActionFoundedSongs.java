package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;
import static clientSide.services.PrintHelper.*;

public class ActionFoundedSongs extends TemplateSimplePage {

    public ActionFoundedSongs(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Chose your action for the selected songs";
        this.pageContent = PrintHelper.zeroBack + " |  " + PrintHelper.nineHomepage + PrintHelper.lineBreak +
                PrintHelper.separator + PrintHelper.lineBreak +
                    PrintHelper.nbr1 + "Add to current playlist" + PrintHelper.lineBreak +
                    PrintHelper.nbr2 + "Add to an other playlist" +PrintHelper.lineBreak +
                    PrintHelper.nbr3 + "Create a new playlist";
    }

    @Override
    public void button1() {
        verificationAndThenAction();
    }

    @Override
    public void button2() {
        printLN();
        printLNWhite("Your Playlists : ");
        toolBoxView.getPrintServ().printUserPlaylists(toolBoxView.getUserServ().getCurrentUserId());

        printYourInput();

        int userId = toolBoxView.getUserServ().getCurrentUserId();
        int totalPlaylist = toolBoxView.getUserServ().getUserById(userId).getPlaylists().size();

        int chosenPlaylist = toolBoxView.getPlaylistServ().takeAndValidateInputChoice(totalPlaylist, pageService);
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
            printLNInfo("You're not the owner of your current playlist \"" +
                    toolBoxView.getPlaylistServ().getPlaylistById(currentPlaylistId).getName() +
                    "\". You can't add songs to it.");
            printLNInfo("Please choose another option.");
            handelInvalidIndex();
        }
    }

    @Override
    public void button3() {
        pageService.playlistCreation.displayAllPage();
    }

    @Override
    public void button8(){//no action !
    }

    @Override
    public void handelInvalidIndex() {
        printYourInput();
        validateInput();
        switchPage();
    }
}
