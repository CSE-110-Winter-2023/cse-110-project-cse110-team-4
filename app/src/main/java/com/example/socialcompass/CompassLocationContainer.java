/**
 * Represents a container for all CompassLocationObjects currently present.
 * Allows for easy management and access of specific location objects,
 * as well as functionality to perform actions on all of them.
 */

package com.example.socialcompass;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.location.Location;

import androidx.annotation.AnyThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

public class CompassLocationContainer implements Iterable<CompassLocationObject> {
    private List<CompassLocationObject> locationList;

    /**
     * Constructs a new instance of CompassLocationContainer.
     * Initializes the locationList with an empty ArrayList of CompassLocationObject.
     */
    public CompassLocationContainer() {
        locationList = new ArrayList<>();
    }

    /**
     * Adds a new location to the list.
     *
     * @param location the CompassLocationObject to add to the list
     */
    public void addLocation(CompassLocationObject location) {
        locationList.add(location);
    }

    /**
     * Gets the number of locations in the list.
     *
     * @return the number of locations in the list
     */
    public int getLocationCount() {
        return locationList.size();
    }

    /**
     * Gets the CompassLocationObject at the given index.
     *
     * @param index the index of the location to retrieve
     * @return the CompassLocationObject at the specified index
     */
    public CompassLocationObject getLocationAt(int index) {
        return locationList.get(index);
    }

    /**
     * Removes the CompassLocationObject at the given index.
     *
     * @param index the index of the location to remove
     */
    public void removeLocationAt(int index) {
        locationList.remove(index);
    }

    /**
     * Removes all the locations from the list.
     */
    public void clearLocations() {
        locationList.clear();
    }

    /**
     * Gets all the locations in the list.
     *
     * @return a List of all the CompassLocationObjects in the container
     */
    public List<CompassLocationObject> getAllLocations() {
        return locationList;
    }

    /**
     * Creates a new CompassLocationObject with the given name, latitude, longitude and controller,
     * and adds it to the list.
     *
     * @param publickey remote public key
     * @param controller the controller of the compass UI for this location
     */
    public void createAndAddLocation(String publickey, CompassUIController controller) {
        CompassLocationObject compassLocation = new CompassLocationObject(publickey, controller);
        locationList.add(compassLocation);
    }

    /**
     * Synchronizes the names of all the locations with their corresponding TextViews in their controllers.
     */
    public void syncAllNames() {
        for (CompassLocationObject loc : locationList) {
            loc.syncName();
        }
    }

    /**
     * Returns an iterator for the locationList.
     *
     * @return an iterator over the CompassLocationObjects in the container
     */
    @Override
    public Iterator<CompassLocationObject> iterator() {
        return locationList.iterator();
    }
}
