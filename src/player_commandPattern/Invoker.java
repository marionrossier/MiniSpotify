package player_commandPattern;

import player_commandPattern.commands.ICommand;

import java.util.*;

public class Invoker {
    private String button;
    private List<Map<String, ICommand>> buttonAndCommand;
    private Stack<ICommand> commandHistoric;
    private Stack<String> songHistoric;

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public List<Map<String, ICommand>> getButtonAndCommand() {
        return buttonAndCommand;
    }

    public void setButtonAndCommand(List<Map<String, ICommand>> buttonAndCommand) {
        this.buttonAndCommand = buttonAndCommand;
    }

    public Stack<ICommand> getCommandHistoric() {
        return commandHistoric;
    }

    public void setCommandHistoric(Stack<ICommand> commandHistoric) {
        this.commandHistoric = commandHistoric;
    }

    public Stack<String> getSongHistoric() {
        return songHistoric;
    }

    public void setSongHistoric(Stack<String> songHistoric) {
        this.songHistoric = songHistoric;
    }

    public void pushNext(String button) {/*TODO*/}
    public void pushPause(String button) {/*TODO*/}
    public void pushPlay(String button) {/*TODO*/}
    public void pushPlayback(String button) {/*TODO*/}
    public void pushPrevious(String button) {/*TODO*/}
    public void pushRepeat(String button) {/*TODO*/}
    public void pushShuffle(String button) {/*TODO*/}

}
