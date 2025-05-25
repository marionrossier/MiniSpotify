package clientSide.player.playlist_player;

import common.entities.Song;

interface IState {
    Song getNextSong();

    String getStateName ();
}
