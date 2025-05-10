package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class PlaylistChoseList extends _SimplePageTemplate {

    public PlaylistChoseList(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Chose Your Playlist Page";
        this.pageContent = icon.goBack + icon.lineBreak +
                "Chose your Playlist below";
    }

    @Override
    public void displaySpecificContent() {
        toolbox.getPrintServ().printUserPlaylists(toolbox.getUserServ().getCurrentUserId());
    }

    @Override
    public void validateInput() {
        toolbox.getPlaylistServ().playlistPageRouter(pageService, toolbox.getSongServ());
    }

    @Override
    public void button8(){//no action
    }

    @Override
    public void button9(){//no action
    }
}
