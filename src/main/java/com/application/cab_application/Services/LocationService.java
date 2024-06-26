package com.application.cab_application.Services;

import com.application.cab_application.DAO.LocationDao;
import com.application.cab_application.Models.Location;
import com.google.gson.Gson;

public class LocationService {
    public static int addLocation(String json) {
        Gson gson = new Gson();
        Location location = gson.fromJson(json, Location.class);
        return LocationDao.createLocation(location);
    }


    public static boolean validateRideLocation(int id) {
        Location location = LocationDao.getLocation(id);
        return location.getId() != id;
    }
}
