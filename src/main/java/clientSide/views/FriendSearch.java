package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
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
        this.pageContent = PrintHelper.zeroBack + "\n" +
                PrintHelper.separator + "\n" +
                "What is your friend pseudonym ?";
    }

    @Override
    public void displaySpecificContent(){
        printYourInput();
        String input = pageService.gotAnInputGoBackIf0(scanner.nextLine());

        List<Integer> usersId = toolBoxView.getSearchServ().searchUserByPseudonym(input);
        toolBoxView.getPrintServ().printUsers(usersId);

        if (!usersId.isEmpty()) {
            printLNWhite("Choose your friend to add by entering his number, or enter 0 to go back.");
            printYourInput();

            String inputFriendIndex;
            int friendIndexInUsersId;

            do {
                inputFriendIndex = pageService.gotAnInputGoBackIf0(scanner.nextLine());
                friendIndexInUsersId = pageService.tryParseInt(inputFriendIndex) - 1;
                if (friendIndexInUsersId < 0 || friendIndexInUsersId >= usersId.size()) {
                    printInvalidInputTryAgainOrBack();
                    printYourInput();
                }
            } while (friendIndexInUsersId < 0 || friendIndexInUsersId >= usersId.size());

            toolBoxView.getUserServ().addFriend(usersId.get(friendIndexInUsersId));
        }
        pageService.friendsHomePage.displayAllPage();
    }
}
