package br.com.pexed.mapmarker.model.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Container implements Serializable {
    private List<Location> locations = new LinkedList<>();

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
