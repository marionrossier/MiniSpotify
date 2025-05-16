package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;
import serverSide.entities.User;

import java.util.List;
import java.util.Objects;

public class FriendSearch extends _SimplePageTemplate {
    public FriendSearch(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Search Friend Page";
        this.pageContent = icon.zeroBack + icon.nineHomepage + icon.lineBreak +
                "What is your friend pseudonym ?";
    }

    @Override
    public void displaySpecificContent(){
        displayInput();
        String input = pageService.gotAnInput(scanner.nextLine());

        List<Integer> usersId = toolBoxView.getSearchServ().searchUserByPseudonyme(input);
        toolBoxView.getPrintServ().printUsers(usersId);

        if (!usersId.isEmpty()) {
            System.out.println("Choose your friend to add by entering his number, or enter 0 to go back.");
            displayInput();

            String inputFriendIndex = pageService.gotAnInput(scanner.nextLine());
            if (Objects.equals(inputFriendIndex, "9")){
                pageService.homePage.displayAllPage();
                return;
            }
            int friendIndexInUsersId = Integer.parseInt(inputFriendIndex) - 1;

            while (friendIndexInUsersId >= usersId.size()) {
                System.out.println("Invalid selection. Please try again.");
                displayInput();
                friendIndexInUsersId = Integer.parseInt(pageService.gotAnInput(scanner.nextLine()));
            }

            User user = toolBoxView.getUserServ().getUserById(toolBoxView.getUserServ().getCurrentUserId());
            toolBoxView.getUserServ().addFriend(usersId.get(friendIndexInUsersId));
        }
        pageService.friendsHomePage.displayAllPage();
    }
}
