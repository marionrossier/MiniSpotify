package player_commandPattern.commands;

public interface ICommand {

    void execute(String button);
    void undo(String undo);
}
