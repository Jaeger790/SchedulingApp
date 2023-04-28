package crumbTracker.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

public class Appointment {

    private int aptID, customerId, contactId, userId;
    private String aptTitle, aptDescription, aptLocation, aptType, aptContact;
    private SimpleStringProperty aptTitleP, aptDescriptionP, aptLocationP, aptTypeP, aptContactP;
    private SimpleIntegerProperty aptIdP, customerIdP, contactIdP, userIdP;
    private SimpleIntegerProperty totalMonthAppointments, totalTypeAppointments, totalLocationAppointments, month, year;
    private SimpleObjectProperty<LocalDateTime> aptStartP, aptEndP;
    private LocalDateTime aptStart, aptEnd;

    private static ObservableList<String> allAppointmentTypes = FXCollections.observableArrayList("Initial", "Planning",
                                                                                                  "Report", "Deliver");

    private static ZonedDateTime openBusinessTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0),
                                                                     ZoneId.of("America/New_York"));
    private static ZonedDateTime closeBusinessTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of(
            "America/New_York"));

    public Appointment(int aptID, int customerId, int contactId, int userId, String aptTitle, String aptDescription,
                       String aptLocation, String aptType, String aptContact, LocalDateTime aptStart,
                       LocalDateTime aptEnd) {
        this.aptID = aptID;
        this.customerId = customerId;
        this.contactId = contactId;
        this.userId = userId;
        this.aptTitle = aptTitle;
        this.aptDescription = aptDescription;
        this.aptLocation = aptLocation;
        this.aptType = aptType;
        this.aptContact = aptContact;
        this.aptStart = aptStart;
        this.aptEnd = aptEnd;
    }

    public Appointment(int aptID, String aptTitle, String aptDescription,
                       String aptLocation, String aptType,
                       LocalDateTime aptStart, LocalDateTime aptEnd, int customerId, int contactId,
                       int userId) {
        this.aptID = aptID;
        this.customerId = customerId;
        this.contactId = contactId;
        this.userId = userId;
        this.aptTitle = aptTitle;
        this.aptDescription = aptDescription;
        this.aptLocation = aptLocation;
        this.aptType = aptType;
        this.aptStart = aptStart;
        this.aptEnd = aptEnd;
    }


    public Appointment(String aptTitleP, String aptDescriptionP, String aptLocationP,
                       String aptTypeP, String aptContactP, int aptIdP,
                       int customerIdP, int contactIdP, int userIdP, LocalDateTime aptStart, LocalDateTime aptEnd) {

        this.aptTitleP = new SimpleStringProperty(aptTitleP);
        this.aptDescriptionP = new SimpleStringProperty(aptDescriptionP);
        this.aptLocationP = new SimpleStringProperty(aptLocationP);
        this.aptTypeP = new SimpleStringProperty(aptTypeP);
        this.aptContactP = new SimpleStringProperty(aptContactP);
        this.aptIdP = new SimpleIntegerProperty(aptIdP);
        this.customerIdP = new SimpleIntegerProperty(customerIdP);
        this.contactIdP = new SimpleIntegerProperty(contactIdP);
        this.userIdP = new SimpleIntegerProperty(userIdP);
        this.aptStartP = new SimpleObjectProperty<>(aptStart);
        this.aptEndP = new SimpleObjectProperty<>(aptEnd);
    }

    public Appointment(int year, int month, int totalAppointments) {
        this.year = new SimpleIntegerProperty(year);
        this.month = new SimpleIntegerProperty(month);
        this.totalMonthAppointments = new SimpleIntegerProperty(totalAppointments);
    }

    public Appointment(String aptType, int totalAppointments) {
        this.aptTypeP = new SimpleStringProperty(aptType);
        this.totalTypeAppointments = new SimpleIntegerProperty(totalAppointments);
    }

    public Appointment(int totalAppointments, String aptLocation) {
        this.aptLocationP = new SimpleStringProperty(aptLocation);
        this.totalLocationAppointments = new SimpleIntegerProperty(totalAppointments);
    }

    public Appointment(String title, String description, String location, String type, LocalDateTime startDateTime,
                       LocalDateTime endDateTime, int customerId, int contactId, int userId, int aptID) {
        this.customerId = customerId;
        this.contactId = contactId;
        this.userId = userId;
        this.aptTitle = title;
        this.aptDescription = description;
        this.aptLocation = location;
        this.aptType = type;
        this.aptStart = startDateTime;
        this.aptEnd = endDateTime;
        this.aptID = aptID;

    }

    public String getAptTitleP() {
        return aptTitleP.get();
    }

    public SimpleStringProperty aptTitlePProperty() {
        return aptTitleP;
    }

    public String getAptDescriptionP() {
        return aptDescriptionP.get();
    }

    public SimpleStringProperty aptDescriptionPProperty() {
        return aptDescriptionP;
    }

    public String getAptLocationP() {
        return aptLocationP.get();
    }

    public SimpleStringProperty aptLocationPProperty() {
        return aptLocationP;
    }

    public String getAptTypeP() {
        return aptTypeP.get();
    }

    public SimpleStringProperty aptTypePProperty() {
        return aptTypeP;
    }

    public String getAptContactP() {
        return aptContactP.get();
    }

    public SimpleStringProperty aptContactPProperty() {
        return aptContactP;
    }

    public int getAptIdP() {
        return aptIdP.get();
    }

    public SimpleIntegerProperty aptIdPProperty() {
        return aptIdP;
    }

    public int getCustomerIdP() {
        return customerIdP.get();
    }

    public SimpleIntegerProperty customerIdPProperty() {
        return customerIdP;
    }

    public int getContactIdP() {
        return contactIdP.get();
    }

    public SimpleIntegerProperty contactIdPProperty() {
        return contactIdP;
    }

    public int getUserIdP() {
        return userIdP.get();
    }

    public SimpleIntegerProperty userIdPProperty() {
        return userIdP;
    }

    @Override
    public String toString() {
        return aptID + " " + aptTitle + " " + aptType;
    }

    public int getAptID() {
        return aptID;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getContactId() {
        return contactId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAptTitle() {
        return aptTitle;
    }

    public String getAptDescription() {
        return aptDescription;
    }

    public String getAptLocation() {
        return aptLocation;
    }

    public String getAptType() {
        return aptType;
    }

    public String getAptContact() {
        return aptContact;
    }

    public LocalDateTime getAptStart() {
        return aptStart;
    }

    public LocalDateTime getAptEnd() {
        return aptEnd;
    }

    public LocalDateTime getAptStartP() {
        return aptStartP.get();
    }

    public SimpleObjectProperty<LocalDateTime> aptStartPProperty() {
        return aptStartP;
    }

    public LocalDateTime getAptEndP() {
        return aptEndP.get();
    }

    public SimpleObjectProperty<LocalDateTime> aptEndPProperty() {
        return aptEndP;
    }

    public int getTotalMonthAppointments() {
        return totalMonthAppointments.get();
    }

    public SimpleIntegerProperty totalMonthAppointmentsProperty() {
        return totalMonthAppointments;
    }

    public int getMonth() {
        return month.get();
    }

    public SimpleIntegerProperty monthProperty() {
        return month;
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public int getTotalTypeAppointments() {
        return totalTypeAppointments.get();
    }

    public SimpleIntegerProperty totalTypeAppointmentsProperty() {
        return totalTypeAppointments;
    }

    public int getTotalLocationAppointments() {
        return totalLocationAppointments.get();
    }

    public SimpleIntegerProperty totalLocationAppointmentsProperty() {
        return totalLocationAppointments;
    }

    public static ZonedDateTime getOpenBusinessTime() {
        return openBusinessTime;
    }

    public static ZonedDateTime getCloseBusinessTime() {
        return closeBusinessTime;
    }


    public static ObservableList<String> getAllAppointmentTypes() {
        return allAppointmentTypes;
    }
}
