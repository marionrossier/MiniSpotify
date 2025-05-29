package clientSide.views;

import clientSide.services.ToolBoxView;
import common.entities.PlaylistEnum;
import common.entities.User;
import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import static clientSide.services.PrintHelper.*;

public class PlaylistCreation extends TemplateInversePage {
    private String playlistName;

    public PlaylistCreation(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Create Playlist Page";
        this.pageContent = "You're playlist will be : " + icon.lineBreak +
                icon.nbr1() + "Private" + icon.lineBreak +
                icon.nbr2() + "Public";
    }
    @Override
    public void displaySpecificContent() {
        printLNWhite("Enter the name of the playlist : ");
        playlistNameVerification();
    }

    @Override
    public void button1(){
        toolBoxView.getPlaylistServ().createNewPlaylist(playlistName, PlaylistEnum.PRIVATE);
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button2(){
        toolBoxView.getPlaylistServ().createNewPlaylist(playlistName, PlaylistEnum.PUBLIC);
        pageService.playlistPageOpen.displayAllPage();
    }

    private void playlistNameVerification (){

        this.playlistName = pageService.gotAnInputGoBackIf0(scanner.nextLine());

        User user = toolBoxView.getUserServ().getUserById(toolBoxView.getUserServ().getCurrentUserId());

        boolean playlistNameOk = toolBoxView.getPlaylistServ().verifyPlaylistName(playlistName, user);

        if (!playlistNameOk){
            printInfo("Playlist Name already exist in your playlists. Try again");
            this.printYourInput();
            playlistNameVerification();
        }
    }

}
