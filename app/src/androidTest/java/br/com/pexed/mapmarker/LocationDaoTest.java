package br.com.pexed.mapmarker;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.pexed.mapmarker.service.database.AppDatabase;
import br.com.pexed.mapmarker.model.model.Location;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocationDaoTest {
    private AppDatabase appDatabase;

    @Before
    public void initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void insertLocationInDB_getLocationFromDB() {
        Location location = new Location("Test 1", 0.5, 0.5);
        appDatabase.locationDao().insert(location);

        Location locationFromDatabase = appDatabase.locationDao().getLocation(location.getLat(), location.getLng());
        assert(locationFromDatabase != null);
    }

    @Test
    public void insertLocationInDB_deleteLocationFromDB() {
        Location location = new Location("Test 2", 0.7, 0.7);
        appDatabase.locationDao().insert(location);
        appDatabase.locationDao().delete(location);

        //Checking if still on DB
        Location locationFromDatabase = appDatabase.locationDao().getLocation(location.getLat(), location.getLng());
        assert(locationFromDatabase == null);
    }

}
