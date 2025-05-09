package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;


public class ActionFoundedSongs extends _SimplePageTemplate {

    public ActionFoundedSongs(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Chose your action for the selected songs";
        this.pageContent = icon.goBack + " |  " + icon.goToHomepage + icon.lineBreak +
                    icon.iconNbr(1) + "Add to current playlist" + icon.lineBreak +
                    icon.iconNbr(2) + "Add to an other playlist (Ne fonctionne pas)" +icon.lineBreak +
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
        toolbox.getPlaylistServ().validatePlaylistIdInput(pageService, toolbox.getSongServ());

        verificationAndThenAction();
    }

    private void verificationAndThenAction() {
        int currentPlaylistId = toolbox.getPlaylistServ().getCurrentPlaylistId();

        if (toolbox.getPlaylistServ().isCurrentUserOwnerOfPlaylist(currentPlaylistId)) {
            toolbox.getPlaylistServ().addSongToPlaylistFromTemporaryPlaylist(toolbox.getPlaylistServ().getCurrentPlaylistId());
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
