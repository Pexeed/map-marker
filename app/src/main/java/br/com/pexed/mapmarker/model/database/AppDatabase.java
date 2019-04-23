package br.com.pexed.mapmarker.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.pexed.mapmarker.model.dao.LocationDao;
import br.com.pexed.mapmarker.model.model.Location;

@Database(entities = {Location.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract LocationDao locationDao();
    private static AppDatabase sInstance;

    public static synchronized AppDatabase getDatabaseInstance(Context context) {
        if (sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    private static AppDatabase create(Context context) {
        RoomDatabase.Builder<AppDatabase> builder = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class,
                Location.TABLE_NAME);
        return builder.build();
    }
}
