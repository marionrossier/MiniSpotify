package ressources;

import datas.entities.PlanEnum;

import java.util.Arrays;

public class Cookies {
    private final int GUID;
    private final PlanEnum PLANENUM;
    private final int[] PLAYLISTSLIST;

    public Cookies(int GUID, PlanEnum PLANENUM, int[] PLAYLISTSLIST) {
        this.GUID = GUID;
        this.PLANENUM = PLANENUM;
        this.PLAYLISTSLIST = PLAYLISTSLIST;
    }

    @Override
    public String toString() {
        return "Cookies{" +
                "GUID=" + GUID +
                ", PLANENUM='" + PLANENUM + '\'' +
                ", PLAYLISTSLIST=" + Arrays.toString(PLAYLISTSLIST) +
                '}';
    }
}
