package serverSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;


public class ActionFoundedSongs extends _SimplePageTemplate {

    public ActionFoundedSongs(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Chose your action for the selected songs";
        this.pageContent = icon.goBack + " |  " + icon.goToHomepage + icon.lineBreak +
                    icon.iconNbr(1) + "Add to current playlist" + icon.lineBreak +
                    icon.iconNbr(2) + "Add to an other playlist" +icon.lineBreak +
                    icon.iconNbr(3) + "Create a new playlist" + icon.lineBreak;
    }

    @Override
    public void button1() {
        verificationAndThenAction();
    }

    @Override
    public void button2() {
        System.out.println("Your Playlists : ");
        toolbox.getPrintServ().printUserPlaylists(toolbox.getUserServ().getCurrentUserId());

        displayInput();

        int chosenPlaylist = toolbox.getPlaylistServ().takeAndValidationInputPlaylistChoice();
        toolbox.getPlaylistServ().setCurrentPlaylistId(chosenPlaylist);
        verificationAndThenAction();
    }

    private void verificationAndThenAction() {
        int currentPlaylistId = toolbox.getPlaylistServ().getCurrentPlaylistId();
        int temporaryPlaylistId = toolbox.getPlaylistServ().getTemporaryPlaylistId();

        if (toolbox.getPlaylistServ().isCurrentUserOwnerOfPlaylist(currentPlaylistId)) {
            toolbox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(temporaryPlaylistId,currentPlaylistId);
            pageService.playlistPageOpen.displayAllPage();
        }
        else {
            System.err.println("You're not the owner of this playlist.");
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
