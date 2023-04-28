package crumbTracker.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Division extends Location {
    private SimpleIntegerProperty divisionId = new SimpleIntegerProperty();
    private SimpleStringProperty divisionName = new SimpleStringProperty();
    private SimpleIntegerProperty countryId = new SimpleIntegerProperty();

    /**
     * constructors for division objects
     */
    public Division(int divisionId, String divisionName, int countryId) {
        super(divisionId, divisionName);
        this.divisionId = new SimpleIntegerProperty(divisionId);
        this.divisionName = new SimpleStringProperty(divisionName);
        this.countryId = new SimpleIntegerProperty(countryId);
    }

    @Override
    public String toString() {
        return divisionId.getValue() + " " + divisionName.getValue();
    }

    /**
     * getters for division data
     */
    public int getDivisionId() {
        return divisionId.get();
    }

    public SimpleIntegerProperty divisionIdProperty() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId.set(divisionId);
    }

    public String getDivisionName() {
        return divisionName.get();
    }

    public SimpleStringProperty divisionNameProperty() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName.set(divisionName);
    }

    public int getCountryId() {
        return countryId.get();
    }

    public SimpleIntegerProperty countryIdProperty() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }
}
