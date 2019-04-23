package br.com.pexed.mapmarker.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.pexed.mapmarker.model.model.Location;
@Dao
public interface LocationDao {
    @Query("SELECT * FROM locations")
    List<Location> getAll();

    @Query("SELECT * FROM locations WHERE lat = :lat " +
            "AND lng = :lng ")
    Location getLocation(Double lat, Double lng);

    @Insert
    void insert(Location location);

    @Delete
    void delete(Location location);
}
