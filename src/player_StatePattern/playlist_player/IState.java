package player_StatePattern.playlist_player;

import data.entities.Song;

interface IState {
    Song getNextSong();
}
