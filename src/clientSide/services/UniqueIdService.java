package clientSide.services;

import java.util.*;

public class UniqueIdService {

    public UniqueIdService(){
    }

    public int setUniqueId() {
        return Math.abs(UUID.randomUUID().hashCode());
    }
}