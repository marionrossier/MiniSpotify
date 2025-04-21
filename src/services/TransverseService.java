package services;

import java.util.*;

public class TransverseService {

    public int setUniqueId() {
        return Math.abs(UUID.randomUUID().hashCode());
    }

    public TransverseService(){
    }
}