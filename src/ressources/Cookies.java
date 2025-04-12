package ressources;

import datas.entities.PlanEnum;

import java.util.Arrays;
import java.util.List;

public class Cookies {
    private final int GUID;
    private final PlanEnum PLANENUM;
    private final List<Integer> PLAYLISTSLIST;

    public Cookies(int GUID, PlanEnum PLANENUM, List<Integer> PLAYLISTSLIST) {
        this.GUID = GUID;
        this.PLANENUM = PLANENUM;
        this.PLAYLISTSLIST = PLAYLISTSLIST;
    }

    @Override
    public String toString() {
        return "Cookies{" +
                "GUID=" + GUID +
                ", PLANENUM='" + PLANENUM + '\'' +
                ", PLAYLISTSLIST=" + PLAYLISTSLIST +
                '}';
    }
}
