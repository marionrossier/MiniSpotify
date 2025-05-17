package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

import java.util.List;
import java.util.Objects;

import static clientSide.services.PrintHelper.*;

public class FriendSearch extends TemplateSimplePage {
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

        List<Integer> usersId = toolBoxView.getSearchServ().searchUserByPseudonym(input);
        toolBoxView.getPrintServ().printUsers(usersId);

        if (!usersId.isEmpty()) {
            printLNWhite("Choose your friend to add by entering his number, or enter 0 to go back.");
            displayInput();

            String inputFriendIndex = pageService.gotAnInput(scanner.nextLine());
            if (Objects.equals(inputFriendIndex, "9")){
                pageService.homePage.displayAllPage();
                return;
            }
            int friendIndexInUsersId = Integer.parseInt(inputFriendIndex) - 1;

            while (friendIndexInUsersId >= usersId.size()) {
                printLNInfo("Invalid selection. Please try again.");
                displayInput();
                friendIndexInUsersId = Integer.parseInt(pageService.gotAnInput(scanner.nextLine()));
            }

            toolBoxView.getUserServ().addFriend(usersId.get(friendIndexInUsersId));
        }
        pageService.friendsHomePage.displayAllPage();
    }
}
