package br.com.pexed.mapmarker.model.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import br.com.pexed.mapmarker.model.model.Container;
import br.com.pexed.mapmarker.model.model.Location;

public class ContainerParser implements JsonDeserializer<Container> {
    @Override
    public Container deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        //LinkedList to maintain the item order
        List<Location> locationList = new LinkedList<>();

        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonArray results = jsonObject.get("results").getAsJsonArray();

        for (JsonElement result : results) {
            final JsonObject resultJson =  result.getAsJsonObject();

            //Getting formatted address
            final String formattedAddress = resultJson.get("formatted_address").getAsString();

            JsonObject geometryJson = resultJson.get("geometry").getAsJsonObject();
            JsonObject locationJson = geometryJson.get("location").getAsJsonObject();
            //Getting latitude and longitude
            final Double lat = locationJson.get("lat").getAsDouble();
            final Double lng = locationJson.get("lng").getAsDouble();

            //Creating location object and adding to list
            final Location location = new Location(formattedAddress, lat, lng);
            locationList.add(location);
        }
        //Creating container and populating with the location list
        Container container = new Container();
        container.setLocations(locationList);
        return container;
    }
}