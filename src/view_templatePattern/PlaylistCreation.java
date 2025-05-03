package view_templatePattern;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;

public class PlaylistCreation extends AbstractMenuPage {
    private final Playlist playlist = new Playlist();
    private final PlaylistRepository playlistRepository = new PlaylistRepository();

    public PlaylistCreation(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Create Playlist Page";
        this.pageContent = "Enter the name of the playlist : ";
    }
    @Override
    void displayContent(String pageContent) {
        System.out.print(pageContent);
        String playlistName = in.nextLine();

        playlist.setPlaylistName(playlistName);
        playlistRepository.savePlaylist(playlist);

        System.out.println(icon.iconOk() + "Playlist saved successfully !");

        //TODO : search a song by songName !
        //TODO : add back to the menu "icon.iconNbr(0) + icon.iconBack() + icon.lineBreak"
    }

}
