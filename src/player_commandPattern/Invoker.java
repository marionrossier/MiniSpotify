package player_commandPattern;

import player_commandPattern.commands.ICommand;

import java.util.*;

public class Invoker {
    private String button;
    private List<Map<String, ICommand>> buttonAndCommand;
    private String undo;
    private Stack<?> historic;

    public void Getter() {/*TODO*/}
    public void Setter() {/*TODO*/}
    public void pushButton(String button) {/*TODO*/}
    public void pushUndo(String undo) {/*TODO*/}
}
