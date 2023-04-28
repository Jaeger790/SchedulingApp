package crumbTracker.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleIntegerProperty contactId;
    private SimpleStringProperty contactName;


    public Contact(int contactId, String contactName) {
        this.contactId = new SimpleIntegerProperty(contactId);
        this.contactName = new SimpleStringProperty(contactName);
    }

    @Override
    public String toString() {
        return contactId.getValue() + " " + contactName.getValue();
    }

    public int getContactId() {
        return contactId.get();
    }

    public SimpleIntegerProperty contactIdProperty() {
        return contactId;
    }

    public String getContactName() {
        return contactName.get();
    }

    public SimpleStringProperty contactNameProperty() {
        return contactName;
    }
}
