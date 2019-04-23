package br.com.pexed.mapmarker.model.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "locations", primaryKeys = {"lat", "lng"})
public class Location implements Serializable{

    public static final String TABLE_NAME= "locations";
    public static final String ITEM= "ITEM";
    public static final String LIST= "LIST";
    public static final String TYPE= "TYPE";

    @ColumnInfo(name = "formatted_address")
    private String formattedAddress;
    @NonNull @ColumnInfo(name = "lat")
    private Double lat;
    @NonNull @ColumnInfo(name = "lng")
    private Double lng;

    public Location(String formattedAddress, @NonNull Double lat, @NonNull Double lng) {
        this.formattedAddress = formattedAddress;
        this.lat = lat;
        this.lng = lng;
    }

    @NonNull
    public Double getLat() {
        return lat;
    }

    public void setLat(@NonNull Double lat) {
        this.lat = lat;
    }

    @NonNull
    public Double getLng() {
        return lng;
    }

    public void setLng(@NonNull Double lng) {
        this.lng = lng;
    }

    public String getFormattedAddress() { return formattedAddress; }

    public void setFormattedAddress(String formattedAddress) { this.formattedAddress = formattedAddress; }

}
