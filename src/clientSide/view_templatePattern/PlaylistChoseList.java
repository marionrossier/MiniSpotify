package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ViewToolBox;

public class PlaylistChoseList extends _SimplePageTemplate {

    public PlaylistChoseList(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                "Chose your Playlist below";
    }

    @Override
    public void displaySpecificContent() {
        viewToolBox.getPrintServ().printUserPlaylists(viewToolBox.getUserServ().getCurrentUserId());
    }

    @Override
    public void validateInput() {
        viewToolBox.getPlaylistServ().playlistPageRouter(pageService, viewToolBox.getSongServ());
    }

    @Override
    public void button8(){//no action
    }

    @Override
    public void button9(){//no action
    }
}
