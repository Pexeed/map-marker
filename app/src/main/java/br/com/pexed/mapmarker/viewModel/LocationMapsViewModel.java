package br.com.pexed.mapmarker.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import br.com.pexed.mapmarker.model.model.Location;
import br.com.pexed.mapmarker.model.repository.LocationRepository;

public class LocationMapsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Location>> locationsData;
    private MutableLiveData<Location> mainLocationData;
    private Application application;

    public LocationMapsViewModel(Application application) {
        super(application);
        this.application = application;
    }

    public void setLocationList(List<Location> locationList) {
        if (locationsData == null) {
            locationsData = new MutableLiveData<>();
        }
        locationsData.setValue(locationList);
    }

    public void setMainLocation(Location location) {
        if (mainLocationData == null) {
            mainLocationData = new MutableLiveData<>();
        }
        mainLocationData.setValue(location);
    }

    public List<Location> getLocationList(){
        return locationsData.getValue();
    }

    public Location getMainLocation(){
        return mainLocationData.getValue();
    }

    public MutableLiveData<Location> isSaved(){
        return LocationRepository.isLocationSaved(application, getMainLocation());
    }

    public MutableLiveData<Location> saveLocation(){
        return LocationRepository.saveLocation(application, getMainLocation());
    }

    public void deleteLocation(){
        LocationRepository.deleteLocation(application, getMainLocation());
    }


}
