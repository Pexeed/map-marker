package br.com.pexed.mapmarker.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

import br.com.pexed.mapmarker.model.model.Location;
import br.com.pexed.mapmarker.model.repository.LocationRepository;

public class ListLocationsViewModel extends ViewModel {

    private MutableLiveData<List<Location>> locationList;
    private String searchAddress = "";

    public LiveData<List<Location>> getLocationList() {
        if (locationList == null) {
            locationList = new MutableLiveData<>();
        }
        loadLocations();
        return locationList;
    }

    public List<Location> getLocationValue(){
        //Creates ArrayList if empty to avoid NullPointerException
        if (locationList == null) {
            locationList = new MutableLiveData<>();
            locationList.setValue(new LinkedList<>());
            return locationList.getValue();
        }
        return locationList.getValue();
    }

    private void loadLocations() {
        // Get locations using repository async call
        locationList = LocationRepository.getLocationList(searchAddress);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void updateSearchAddress(String searchAddress) {
        this.searchAddress = searchAddress;
    }
}