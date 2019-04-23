package br.com.pexed.mapmarker.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import java.util.List;

import br.com.pexed.mapmarker.R;
import br.com.pexed.mapmarker.databinding.ActivityListLocationsBinding;
import br.com.pexed.mapmarker.model.model.Container;
import br.com.pexed.mapmarker.model.model.Location;
import br.com.pexed.mapmarker.view.adapter.ListLocationsAdapter;
import br.com.pexed.mapmarker.viewModel.ListLocationsViewModel;

public class ListLocationsActivity extends AppCompatActivity{

    ActivityListLocationsBinding b;
    ListLocationsViewModel vm;
    ListLocationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_list_locations);

        vm = ViewModelProviders.of(this).get(ListLocationsViewModel.class);

        b.btSearch.setOnClickListener(view -> searchLocation());
        b.etSearchLocation.setOnEditorActionListener((textView, action, keyEvent) -> {
            if( action == EditorInfo.IME_ACTION_DONE){
                searchLocation();
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Load objects from viewModel
        setListView(vm.getLocationValue());
    }

    void searchLocation(){
        b.pbLoadingList.setVisibility(View.VISIBLE);
        //Passing search location to ViewModel
        vm.updateSearchAddress(b.etSearchLocation.getText().toString());
        //Requesting Maps List
        vm.getLocationList().observe(ListLocationsActivity.this, locationList -> {
            // update ListView on list change
            setListView(locationList);
            b.pbLoadingList.setVisibility(View.GONE);
        });
    }

    void setListView (List<Location> locations){
        if(adapter == null) {
            adapter = new ListLocationsAdapter(this, locations);
        }else{
            adapter.setLocations(locations);
        }

        //No Results criteria
        b.lvLocationList.setEmptyView(b.tvEmpty);
        b.lvLocationList.setAdapter(adapter);
        b.lvLocationList.setOnItemClickListener((adapterView, view, position, l) -> {
            //Setting container to pass list of locations
            Container container = new Container();
            container.setLocations(locations);

            //Preparing intent with list of locations
            Intent intent = new Intent(ListLocationsActivity.this, LocationMapsActivity.class);
            intent.putExtra(Location.LIST, container);

            //Display all selected
            if(position == locations.size()){
                intent.putExtra(Location.TYPE, Location.LIST);
                startActivity(intent);
            }else{ //Specific location selected
                intent.putExtra(Location.TYPE, Location.ITEM);
                intent.putExtra(Location.ITEM, locations.get(position));
                startActivity(intent);
            }
        });
    }

    public ListLocationsViewModel getVm() {
        return vm;
    }
}
