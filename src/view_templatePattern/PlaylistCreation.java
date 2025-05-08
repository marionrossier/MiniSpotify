package view_templatePattern;

import data.entities.Playlist;
import data.entities.User;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class PlaylistCreation extends _SimplePageTemplate {
    private final Playlist playlist = new Playlist();

    public PlaylistCreation(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Create Playlist Page";
        this.pageContent = "Enter the name of the playlist : ";
    }
    @Override
    public void displaySpecificContent() {
        newPlaylist();
    }

    private void newPlaylist (){
        String playlistName = pageService.gotAnInput(scanner.nextLine());

        User user = toolbox.getUserServ().getUserById(toolbox.getUserServ().getCurrentUserId());
        Boolean playlistNameOk = toolbox.getPlaylistServ().verifyPlaylistName(playlistName, user);

        if (playlistNameOk) {
            toolbox.getPlaylistServ().createPlaylistWithTemporaryPlaylist(playlistName);
            int playlistId = toolbox.getPlaylistServ().getPlaylistByName(playlistName).getPlaylistId();
            toolbox.getPlaylistServ().setCurrentPlaylistId(playlistId);

            System.out.println();
            System.out.println(icon.iconOk() + "Playlist saved successfully !");
            pageService.playlistPage.displayAllPage();
        }
        else {
            System.out.print("Playlist Name already exist in you're playlists. Try again");
            this.displayInput();
            newPlaylist();
        }
    }

}
