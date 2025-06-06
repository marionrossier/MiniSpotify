package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;
import java.util.List;

import static clientSide.services.PrintHelper.*;

public class ActionFoundedSongs extends TemplateSimplePage {

    public ActionFoundedSongs(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Chose your action for the selected songs";
        this.pageContent = PrintHelper.zeroBack + " |  " + PrintHelper.nineHomepage + "\n" +
                PrintHelper.separator + "\n" +
                    PrintHelper.b1 + "Add to current playlist" + "\n" +
                    PrintHelper.b2 + "Add to an other playlist" +"\n" +
                    PrintHelper.b3 + "Create a new playlist";
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
        List<Integer> playlistIds = toolBoxView.getUserServ().getUserById(userId).getPlaylists();
        int totalPlaylist = playlistIds.size();

        int chosenIndex = toolBoxView.getPlaylistServ().takeAndValidateInputChoice(totalPlaylist, pageService);

        int chosenPlaylistId = playlistIds.get(chosenIndex - 1);
        toolBoxView.getPlaylistServ().setCurrentPlaylistId(chosenPlaylistId);

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
