package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;


public class ActionFoundedSongs extends _SimplePageTemplate {

    public ActionFoundedSongs(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
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
        System.out.println("Your Playlists : ");
        viewToolBox.getPrintServ().printUserPlaylists(viewToolBox.getUserServ().getCurrentUserId());

        displayInput();

        int chosenPlaylist = viewToolBox.getPlaylistServ().takeAndValidationInputPlaylistChoice();
        viewToolBox.getPlaylistServ().setCurrentPlaylistId(chosenPlaylist);
        verificationAndThenAction();
    }

    private void verificationAndThenAction() {
        int currentPlaylistId = viewToolBox.getPlaylistServ().getCurrentPlaylistId();
        int temporaryPlaylistId = viewToolBox.getPlaylistServ().getTemporaryPlaylistId();

        if (viewToolBox.getPlaylistServ().isCurrentUserOwnerOfPlaylist(currentPlaylistId)) {
            viewToolBox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(temporaryPlaylistId,currentPlaylistId);
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
