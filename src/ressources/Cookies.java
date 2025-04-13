package ressources;

import data.entities.PlanEnum;

import java.util.List;

public class Cookies {
    private final int id;
    private final PlanEnum planEnum;
    private final List<Integer> playlistsList;

    public Cookies(int GUID, PlanEnum planEnum, List<Integer> playlistsList) {
        this.id = GUID;
        this.planEnum = planEnum;
        this.playlistsList = playlistsList;
    }

    @Override
    public String toString() {
        return "Cookies{" +
                "GUID=" + id +
                ", PLANENUM='" + planEnum + '\'' +
                ", PLAYLISTSLIST=" + playlistsList +
                '}';
    }
}
