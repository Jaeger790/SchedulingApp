package crumbTracker.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Country extends Location {
    private SimpleIntegerProperty countryId = new SimpleIntegerProperty();
    private SimpleStringProperty countryName = new SimpleStringProperty();

    public Country(int countryId, String countryName) {
        super(countryId, countryName);
        this.countryId = new SimpleIntegerProperty(countryId);
        this.countryName = new SimpleStringProperty(countryName);
    }

    @Override
    public String toString() {
        return countryId.getValue() + " " + countryName.getValue();
    }

    /**
     * getters for Country data
     */
    public int getCountryId() {
        return countryId.get();
    }

    public SimpleIntegerProperty countryIdProperty() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    public String getCountryName() {
        return countryName.get();
    }

    public SimpleStringProperty countryNameProperty() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName.set(countryName);
    }

}
