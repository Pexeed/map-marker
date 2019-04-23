package br.com.pexed.mapmarker.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.pexed.mapmarker.R;
import br.com.pexed.mapmarker.model.model.Container;
import br.com.pexed.mapmarker.model.model.Location;
import br.com.pexed.mapmarker.viewModel.LocationMapsViewModel;

public class LocationMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String type;
    LocationMapsViewModel vm;
    MenuItem saveMenuItem;
    MenuItem deleteMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maps);
        vm = ViewModelProviders.of(this).get(LocationMapsViewModel.class);

        Intent intent = getIntent();
        type = (String) intent.getSerializableExtra(Location.TYPE);
        //Get main location when it exists
        if (type.equals(Location.ITEM)) {
            Location mainLocation = (Location) intent.getSerializableExtra(Location.ITEM);
            vm.setMainLocation(mainLocation);
        }
        //Get list of locations
        Container container = (Container) intent.getSerializableExtra(Location.LIST);
        vm.setLocationList(container.getLocations());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save_delete, menu);

        saveMenuItem = menu.findItem(R.id.action_save);
        deleteMenuItem = menu.findItem(R.id.action_delete);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                vm.saveLocation();
                this.invalidateOptionsMenu();
                return true;
            case R.id.action_delete:
                deleteDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(type.equals(Location.LIST)){
            saveMenuItem.setVisible(false); // hide save button
            deleteMenuItem.setVisible(false); // show delete button
        }else {
            isLocationSaved();
        }
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Creating bounds for Display All centralization
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Location item : vm.getLocationList()) {
            builder.include(getLatLng(item));
            placeMarker(item);
        }

        if(type.equals(Location.LIST)) {
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.10); //Padding 10% of screen
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
        }else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLatLng(vm.getMainLocation()), 5f));
        }

    }

    void placeMarker(Location item){
        mMap.addMarker(new MarkerOptions()
                .position(getLatLng(item))
                .title(item.getFormattedAddress()));
    }

    LatLng getLatLng(Location item){
        return new LatLng(
                item.getLat(),
                item.getLng());
    }

    void isLocationSaved(){
        vm.isSaved().observe(LocationMapsActivity.this, location -> {
            if (location!=null){
                saveMenuItem.setVisible(false); // hide save button
                deleteMenuItem.setVisible(true); // show delete button
            }else{
                saveMenuItem.setVisible(true); // show save button
                deleteMenuItem.setVisible(false); // hide delete button
            }
        });
    }

    void deleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_msg).
                setTitle(R.string.delete_title).
                setCancelable(true).
                setPositiveButton(getString(R.string.delete_confirmation),
                        (dialogInterface, i) -> {
                    vm.deleteLocation();
                    this.invalidateOptionsMenu();
                }).
                setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog d = builder.create();
        d.show();
    }

}
