package player_commandPattern.commands.player_state_pattern.states;

import player_commandPattern.commands.player_state_pattern.Context;

public class Shuffle implements IState {
    private Context context;

    public Shuffle(Context context) {
        this.context = context;
    }

    @Override
    public void next() {
        /*TODO : play random next + in historic*/
    }

    @Override
    public void previous() {
        /*TODO : use Stack songHistoric*/
    }

    @Override
    public void playback() {
        /*TODO : previous()*/
    }
}
