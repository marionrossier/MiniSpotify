package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

import java.util.List;

import static clientSide.services.PrintHelper.*;

public class FriendSearch extends TemplateSimplePage {
    public FriendSearch(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Search Friend Page";
        this.pageContent = icon.zeroBack + icon.nineHomepage + icon.lineBreak +
                icon.separator + icon.lineBreak +
                "What is your friend pseudonym ?";
    }

    @Override
    public void displaySpecificContent(){
        displayYourInput();
        String input = pageService.gotAnInputGoBackIf0(scanner.nextLine());

        List<Integer> usersId = toolBoxView.getSearchServ().searchUserByPseudonym(input);
        toolBoxView.getPrintServ().printUsers(usersId);

        if (!usersId.isEmpty()) {
            printLNWhite("Choose your friend to add by entering his number, or enter 0 to go back.");
            displayYourInput();

            String inputFriendIndex;
            int friendIndexInUsersId;

            do {
                inputFriendIndex = pageService.gotAnInputGoBackIf0(scanner.nextLine());
                friendIndexInUsersId = pageService.tryParseInt(inputFriendIndex) - 1;
                if (friendIndexInUsersId < 0 || friendIndexInUsersId >= usersId.size()) {
                    printInvalidInputTryAgainOrBack();
                    displayYourInput();
                }
            } while (friendIndexInUsersId < 0 || friendIndexInUsersId >= usersId.size());

            toolBoxView.getUserServ().addFriend(usersId.get(friendIndexInUsersId));
        }
        pageService.friendsHomePage.displayAllPage();
    }
}
