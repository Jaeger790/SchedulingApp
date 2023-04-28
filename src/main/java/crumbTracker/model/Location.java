package crumbTracker.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Location {
    private SimpleIntegerProperty locationId = new SimpleIntegerProperty();
    private SimpleStringProperty locationName = new SimpleStringProperty();


    public Location(int id, String name) {
        this.locationId = new SimpleIntegerProperty(id);
        this.locationName = new SimpleStringProperty(name);
    }

    public int getLocationId() {
        return locationId.get();
    }

    public SimpleIntegerProperty locationIdProperty() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId.set(locationId);
    }

    public String getLocationName() {
        return locationName.get();
    }

    public SimpleStringProperty locationNameProperty() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName.set(locationName);
    }
}
