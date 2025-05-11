package clientSide.view_templatePattern;

import clientSide.services.ViewToolBox;
import serverSide.entities.PlaylistEnum;
import serverSide.entities.User;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class PlaylistCreation extends _InversedPageTemplate {
    private String playlistName;

    public PlaylistCreation(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Create Playlist Page";
        this.pageContent = "You're playlist will be : " + icon.lineBreak +
                icon.nbr(1) + "Private" + icon.lineBreak +
                icon.nbr(2) + "Public";
    }
    @Override
    public void displaySpecificContent() {
        System.out.println("Enter the name of the playlist : ");
        playlistNameVerification();
    }

    @Override
    public void button1(){
        viewToolBox.getPlaylistServ().createNewPlaylist(playlistName, PlaylistEnum.PRIVATE);
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button2(){
        viewToolBox.getPlaylistServ().createNewPlaylist(playlistName, PlaylistEnum.PUBLIC);
        pageService.playlistPageOpen.displayAllPage();
    }

    private void playlistNameVerification (){

        this.playlistName = pageService.gotAnInput(scanner.nextLine());

        User user = viewToolBox.getUserServ().getUserById(viewToolBox.getUserServ().getCurrentUserId());

        boolean playlistNameOk = viewToolBox.getPlaylistServ().verifyPlaylistName(playlistName, user);

        if (!playlistNameOk){
            System.out.print("Playlist Name already exist in you're playlists. Try again");
            this.displayInput();
            playlistNameVerification();
        }
    }

}
