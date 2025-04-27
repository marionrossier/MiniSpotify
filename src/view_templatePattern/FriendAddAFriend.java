package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class FriendAddAFriend extends AbstractMenuPage {
    public FriendAddAFriend(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Follow Friend Page";
        this.pageContent = backLineWith0 + lineBreak +
                "Tip the friend's pseudo to follow him"; //outil de recherche par pseudo
        //TODO : rechercher via UserRepo. les utilisateurs avec ce pseudo ou un bout et les
        // afficher et permettre la selection et donc l'ajout.
    }
}
