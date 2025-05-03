package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public abstract class _ComplexPageTemplate extends _SimplePageTemplate {

    public _ComplexPageTemplate(PageService pageManager, IPlaylistPlayer spotifyPlayer) {
        super(pageManager, spotifyPlayer);
    }

    @Override
    public void displaySpecificContent(){}

    @Override
    public void validateInput(){
        try{
            index = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
        }catch (Exception e){
            System.out.println("Invalid input, try again.");
            scanner.nextLine(); // Clear the invalid input
            displayInput();
            validateInput();
        }
    }
}
