package common.entities;

import common.services.UniqueIdService;

import java.util.*;

public class Playlist {
    private String name;
    private int playlistId;
    private LinkedList<Integer> listSongsId = new LinkedList<>();
    private int ownerId;
    private PlaylistEnum status;
    private final UniqueIdService uniqueIdService = new UniqueIdService();

    public Playlist (){}

    public Playlist(String name, PlaylistEnum status) {
        this.name = name;
        this.playlistId = uniqueIdService.setUniqueId();
        this.status = status;
    }

    public Playlist(String name, LinkedList <Integer> listSongsId, int ownerId, PlaylistEnum status) {
        this.name = name;
        this.playlistId = uniqueIdService.setUniqueId();
        this.ownerId = 0;
        this.listSongsId = listSongsId;
        this.ownerId = ownerId;
        this.status = status;
    }

    //Only for tests and Populate
    public Playlist(String name, int playlistId, LinkedList <Integer> listSongsId, int ownerId, PlaylistEnum status) {
        this.name = name;
        this.playlistId = playlistId;
        this.ownerId = 0;
        this.listSongsId = listSongsId;
        this.ownerId = ownerId;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public LinkedList<Integer> getPlaylistSongsListWithId() {
        return listSongsId;
    }

    public void setListSongsId(LinkedList<Integer> listSongsId) {
        this.listSongsId = listSongsId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public PlaylistEnum getStatus() {
        return status;
    }

    public void setStatus(PlaylistEnum status) {
        this.status = status;
    }
}
