/**
 * Represents a location object with a name, location, and UI controller for a compass app.
 */
package com.example.socialcompass;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;

public class CompassLocationObject {
    private RemoteKey publickey;
    private String locationName;
    private Location location;
    private CompassUIController controller;

    private LiveData<RemoteLocation> remote;

    /**
     * Constructor for creating a CompassLocationObject
     * @param publickey public key from remmote
     * @param controller controller object to handle updating UI for this location
     */
    public CompassLocationObject(RemoteKey publickey, CompassUIController controller) {
        this.publickey = publickey;
        this.controller = controller;
        this.location = new Location(publickey.getPublicKey());

        remote = this.publickey.initRemoteScheduler();
        remote.observeForever(this::updateFromRemote);
    }

    public void updateFromRemote(RemoteLocation remoteLoc) {
        Log.i("UPDATE",remoteLoc.toJSON());
        this.location.setLatitude(remoteLoc.latitude);
        this.location.setLongitude(remoteLoc.longitude);
        this.locationName = remoteLoc.label;

        this.syncName();
    }

    /**
     * Returns the name of the location object.
     * @return the name of the location object
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Returns the location object.
     * @return the location object
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location object.
     * @param location the new location for the location object
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns the UI controller for the compass.
     * @return the UI controller for the compass
     */
    public CompassUIController getController() {
        return controller;
    }

    /**
     * Sets the UI controller for the compass.
     * @param controller the new UI controller for the compass
     */
    public void setController(CompassUIController controller) {
        this.controller = controller;
    }

    /**
     * Updates the name of the location object with the current text in the UI controller's TextView.
     */
    public void syncName() {
        if (this.controller.getTextView() != null) {
            controller.getTextView().setText(this.locationName);
        }
    }

    public void destroy() {
        remote.removeObserver(this::updateFromRemote);
    }

}
