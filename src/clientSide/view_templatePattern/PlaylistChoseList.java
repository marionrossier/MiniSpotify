package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

public class PlaylistChoseList extends _SimplePageTemplate {

    public PlaylistChoseList(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                "Chose your Playlist below";
    }

    @Override
    public void displaySpecificContent() {
        toolBoxView.getPrintServ().printUserPlaylists(toolBoxView.getUserServ().getCurrentUserId());
    }

    @Override
    public void validateInput() {
        toolBoxView.getPlaylistServ().playlistPageRouter(pageService, toolBoxView.getSongServ());
    }

    @Override
    public void button8(){//no action
    }

    @Override
    public void button9(){//no action
    }
}
