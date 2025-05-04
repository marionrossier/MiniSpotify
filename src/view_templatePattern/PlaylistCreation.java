package view_templatePattern;

import data.entities.Playlist;
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
    public void displayContent(String pageContent) {
        System.out.print(pageContent);
        String playlistName = scanner.nextLine();

        playlist.setPlaylistName(playlistName);
        toolbox.getPlaylistRepo().savePlaylist(playlist);

        System.out.println(icon.iconOk() + "Playlist saved successfully !");

        //TODO : search a song by songName !
        //TODO : add back to the menu "icon.iconNbr(0) + icon.iconBack() + icon.lineBreak"
    }

}
