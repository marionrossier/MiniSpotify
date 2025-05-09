package view_templatePattern;

import data.entities.Playlist;
import data.entities.PlaylistEnum;
import data.entities.User;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class PlaylistCreation extends _InversedPageTemplate {
    private final Playlist playlist = new Playlist();
    private String playlistName;

    public PlaylistCreation(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Create Playlist Page";
        this.pageContent = "You're playlist will be : " + icon.lineBreak +
                icon.iconNbr(1) + "Private" + icon.lineBreak +
                icon.iconNbr(2) + "Public";
    }
    @Override
    public void displaySpecificContent() {
        System.out.println("Enter the name of the playlist : ");
        playlistNameVerification();
    }

    @Override
    public void button1(){
        toolbox.getPlaylistServ().createNewPlaylist(playlistName, PlaylistEnum.PRIVATE);
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button2(){
        toolbox.getPlaylistServ().createNewPlaylist(playlistName, PlaylistEnum.PUBLIC);
        pageService.playlistPageOpen.displayAllPage();
    }

    private void playlistNameVerification (){

        this.playlistName = pageService.gotAnInput(scanner.nextLine());

        User user = toolbox.getUserServ().getUserById(toolbox.getUserServ().getCurrentUserId());

        Boolean playlistNameOk = toolbox.getPlaylistServ().verifyPlaylistName(playlistName, user);

        if (!playlistNameOk){
            System.out.print("Playlist Name already exist in you're playlists. Try again");
            this.displayInput();
            playlistNameVerification();
        }
    }

}
