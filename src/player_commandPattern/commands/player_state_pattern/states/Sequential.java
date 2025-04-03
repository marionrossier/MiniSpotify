package player_commandPattern.commands.player_state_pattern.states;

import player_commandPattern.commands.player_state_pattern.Context;

public class Sequential implements IState{
    private Context context;

    public Sequential(Context context) {
        this.context = context;
    }

    @Override
    public void next() {
        /*TODO : play next song from the list + add on songHistoric*/
    }

    @Override
    public void previous() {
        /*TODO : play previous song from the list*/
    }

    @Override
    public void playback() {
        /*TODO : previous()*/
    }
}
