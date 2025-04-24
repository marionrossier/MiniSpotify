package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class FriendsHomePage extends AbstractMenuPage {

    public FriendsHomePage(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Friends Home Page";
        this.pageContent = backLineWith0 + lineBreak + //TODO : bien refactorer la classe et ses boutons !
                nb1 + "Display friends" + lineBreak +
                nb2 + "Add a friend" + lineBreak +
                nb3 + "Remove a friend" + lineBreak +
                nb4 + "Commune Playlists" + lineBreak ;
    }

    @Override
    void button1() {
        spotifyPageFactory.friendsDisplayFriends.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.friendsCommunePlaylists.templateMethode();
    }

    @Override
    void button3() {
        spotifyPageFactory.friendAddAFriend.templateMethode();
    }

    @Override
    void button4() {
        spotifyPageFactory.friendAddPlaylist.templateMethode();
    }
}
