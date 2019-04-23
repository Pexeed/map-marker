package br.com.pexed.mapmarker.model.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.pexed.mapmarker.model.dao.LocationDao;
import br.com.pexed.mapmarker.model.database.AppDatabase;
import br.com.pexed.mapmarker.model.model.Container;
import br.com.pexed.mapmarker.model.model.Location;
import br.com.pexed.mapmarker.model.parser.ContainerParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationRepository {

    private final static String API_KEY = "YOUR-KEY";
    private final static String HTTPS_GOOGLE_MAPS_API = "https://maps.googleapis.com/maps/api/geocode/";
    private final static String FALSE = "false";

    public static MutableLiveData<List<Location>> getLocationList(String address) {
        final String TAG = LocationRepository.class.getSimpleName();

        //Setting container typeAdapter
        final Gson gson = new GsonBuilder().registerTypeAdapter(Container.class, new ContainerParser()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HTTPS_GOOGLE_MAPS_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GoogleMapsService service = retrofit.create(GoogleMapsService.class);
        final MutableLiveData<List<Location>> data = new MutableLiveData<>();

        service.getLocationByAddress(address, FALSE, API_KEY).enqueue(new Callback<Container>() {
            @Override
            public void onResponse(Call<Container> call, Response<Container> response) {
                Container container = response.body();
                if ( container != null) {
                    data.setValue(container.getLocations());
                }else{
                    data.setValue(new ArrayList<>());
                    Log.d(TAG,"Empty list");
                }
            }

            @Override
            public void onFailure(Call<Container> call, Throwable t) {
                data.setValue(new ArrayList<>());
                Log.d(TAG,"Request error.");
            }
        });
        return data;
    }

    public static MutableLiveData<Location> isLocationSaved(Application application, Location location){
        final MutableLiveData<Location> data = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            LocationDao dao = AppDatabase.getDatabaseInstance(application).locationDao();
            Location dbLocation = dao.getLocation(location.getLat(), location.getLng());
            data.postValue(dbLocation);
        });
        return data;
    }

    public static MutableLiveData<Location> saveLocation(Application application, Location location){
        final MutableLiveData<Location> data = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            LocationDao dao = AppDatabase.getDatabaseInstance(application).locationDao();
            dao.insert(location);
            data.postValue(location);
        });
        return data;
    }

    public static void deleteLocation(Application application, Location location){
        AsyncTask.execute(() -> {
            LocationDao dao = AppDatabase.getDatabaseInstance(application).locationDao();
            dao.delete(location);
        });
    }
}
