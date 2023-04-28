/**
 * @author Brad Rott
 * @version 1.0
 */
package crumbTracker.controller;

import crumbTracker.DAO.ContactQuery;
import crumbTracker.DAO.CustomerQuery;
import crumbTracker.DAO.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import crumbTracker.model.Appointment;
import crumbTracker.model.Contact;
import crumbTracker.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> aptIdCol;
    @FXML
    private TableColumn<Appointment, String> aptTitleCol;
    @FXML
    private TableColumn<Appointment, String> aptDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> aptLocationCol;
    @FXML
    private TableColumn<Appointment, String> aptContactCol;
    @FXML
    private TableColumn<Appointment, String> aptTypeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> aptStartCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> aptEndCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;
    @FXML
    private Label aptIdLabel;
    @FXML
    private TextField titleTB;
    @FXML
    private TextField descriptionTB;
    @FXML
    private ComboBox<String> typeCB;
    @FXML
    private ComboBox<Contact> contactCB;
    @FXML
    private ComboBox<Customer> customerCB;
    @FXML
    private TextField locationTB;
    @FXML
    private DatePicker startDateDP;
    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private DatePicker endDateDP;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button modifyAppointmentButton;
    @FXML
    private Button cancelAppointmentButton;
    @FXML
    private Button clearFormButton;
    @FXML
    private Button homeButton;
    @FXML
    private Label cancelAppointmentLabel;
    @FXML
    private RadioButton allAptRadio;
    @FXML
    private RadioButton monthAptRadio;
    @FXML
    private RadioButton weekAptRadio;

    public AppointmentController() {
    }

    /**
     * Alert for detecting an invalid appointment modification
     */
    private static void noSelectionAlert() {
        Alert noSelection = new Alert(Alert.AlertType.ERROR);
        noSelection.setHeaderText("No selection made");
        noSelection.setContentText("Please select an appointment record to proceed");
        noSelection.show();
    }

    /**
     * Alert for detecting empty forms
     */
    protected static void emptyFormsAlert() {
        Alert emptyForm = new Alert(Alert.AlertType.ERROR);
        emptyForm.setHeaderText("Empty Forms");
        emptyForm.setContentText("Please fill out all of the form items to proceed");
        emptyForm.show();
    }

    /**
     * Sets values for appointment table
     */
    private void refreshAppointmentTable() {
        DateTimeFormatter dateDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> allAppointments = null;
        allAppointments = AppointmentQuery.getAllAppointments();
        appointmentTable.setItems(allAppointments);

        aptIdCol.setCellValueFactory(cellData -> cellData.getValue().aptIdPProperty().asObject());
        aptTitleCol.setCellValueFactory(cellData -> cellData.getValue().aptTitlePProperty());
        aptDescriptionCol.setCellValueFactory(cellData -> cellData.getValue().aptDescriptionPProperty());
        aptLocationCol.setCellValueFactory(cellData -> cellData.getValue().aptLocationPProperty());
        aptTypeCol.setCellValueFactory(cellData -> cellData.getValue().aptTypePProperty());
        aptContactCol.setCellValueFactory(cellData -> cellData.getValue().aptContactPProperty());
        aptStartCol.setCellValueFactory(cellData -> cellData.getValue().aptStartPProperty());
        aptStartCol.setCellFactory(format -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime startDateTime, boolean emptyCell) {
                super.updateItem(startDateTime, emptyCell);
                if (emptyCell) {
                    setText(null);
                } else {
                    if (startDateTime == null) {
                        setText(null);
                        return;
                    }
                    setText(dateDTF.format(startDateTime));
                }
            }
        });

        aptEndCol.setCellValueFactory(cellData -> cellData.getValue().aptEndPProperty());
        aptEndCol.setCellFactory(format -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime endDateTime, boolean emptyCell) {
                super.updateItem(endDateTime, emptyCell);
                if (emptyCell) {
                    setText(null);
                } else {
                    if (endDateTime == null) {
                        setText(null);
                        return;
                    }
                    setText(dateDTF.format(endDateTime));
                }

            }
        });
        customerIdCol.setCellValueFactory(cellData -> cellData.getValue().customerIdPProperty().asObject());
        userIdCol.setCellValueFactory(cellData -> cellData.getValue().userIdPProperty().asObject());
        allAptRadioSelected();
        monthAptRadioSelected();
        weekAptRadioSelected();

    }

    /**
     * Sets table to show all appointments listed in the database
     */
    private void allAptRadioSelected() {
        allAptRadio.setOnAction(actionEvent -> {
            ObservableList<Appointment> allAppointments = null;
            allAppointments = AppointmentQuery.getAllAppointments();
            appointmentTable.setItems(allAppointments);
        });
    }

    /**
     * Filters appointment table to appointments that occur during the current machines local month value.
     */
    private void monthAptRadioSelected() {
        monthAptRadio.setOnAction(actionEvent -> {

            ObservableList<Appointment> monthAppointments = AppointmentQuery.monthAppointments();
            appointmentTable.setItems(monthAppointments);
        });
    }

    /**
     * Filters appointment table to appointments that occur within one week of the local machines date time value.
     */
    private void weekAptRadioSelected() {
        weekAptRadio.setOnAction(actionEvent -> {

            ObservableList<Appointment> weekAppointments = AppointmentQuery.weekAppointments();
            appointmentTable.setItems(weekAppointments);
        });
    }

    /**
     * switches screen back to the home screen.
     */
    private void homeButtonHandler() {
        homeButton.setOnAction(this::homeScene);
    }

    private void homeScene(ActionEvent actionEvent) {
        try {
            Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crumbTracker/layout/mainScreen.fxml"));
            Parent root = loader.load();
            Scene mainScene = new Scene(root);
            homeStage.setScene(mainScene);
            homeStage.setTitle("Welcome to Scheduling");
            homeStage.show();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Queries the database to fill the contact combo box values.
     */
    private void fillContactCombo() {
        contactCB.setItems(ContactQuery.contactList());
    }

    /**
     * fills the form values using data selected from the appointment table.
     */
    private void fillFields() {
        appointmentTable.setOnMouseClicked(mouseEvent -> {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            if (selectedAppointment != null) {
                aptIdLabel.setText(selectedAppointment.aptIdPProperty().getValue().toString());
                titleTB.setText(selectedAppointment.aptTitlePProperty().getValue());
                descriptionTB.setText(selectedAppointment.aptDescriptionPProperty().getValue());
                for (Contact c : ContactQuery.contactList()) {
                    if (c.contactIdProperty().getValue() == selectedAppointment.contactIdPProperty().getValue()) {
                        contactCB.getSelectionModel().select(c);
                    }
                }
                for (Customer c : CustomerQuery.getAllCustomers()) {
                    if (c.customerIdProperty().getValue() == selectedAppointment.customerIdPProperty().getValue()) {
                        customerCB.getSelectionModel().select(c);
                    }
                }
                typeCB.setValue(selectedAppointment.aptTypePProperty().getValue());
                startTimeCombo.setValue(selectedAppointment.aptStartPProperty().getValue().toLocalTime());
                endTimeCombo.setValue(selectedAppointment.aptEndPProperty().getValue().toLocalTime());
                startDateDP.setValue(selectedAppointment.aptStartPProperty().getValue().toLocalDate());
                endDateDP.setValue(selectedAppointment.aptEndPProperty().getValue().toLocalDate());
            }
        });
    }

    /**
     * Validate date and time values from date pickers and time combo boxes
     */
    private boolean checkTimeAllocation() {
        //create general alert
        Alert timeError = new Alert(Alert.AlertType.ERROR);
        timeError.setHeaderText("Incorrect Time Allocation");
        timeError.setContentText("General Time Allocation Error");
        //get values from form
        LocalDate startDate = startDateDP.getValue();
        LocalTime startTime = startTimeCombo.getSelectionModel().getSelectedItem();
        LocalDate endDate = endDateDP.getValue();
        LocalTime endTime = endTimeCombo.getSelectionModel().getSelectedItem();
        //check that end dates and times are not set to values before the start date and time values return false if
        // condition passes.
        if (startTime.isAfter(endTime) && startDate.isEqual(endDate)) {
            timeError.setContentText("Start Time cannot be after End Time");
            timeError.show();
            return false;
        } else if (startDate.isAfter(endDate)) {
            timeError.setContentText("Start date cannot be after end date");
            timeError.show();
            return false;
        }
        return true;

    }

    /**
     * compares appointment times inputted into the form to appointments already in the database.  Returns false if
     * any appointment in the database overlaps with the form values. The comparisons are based on customer id.
     */
    private boolean overlapValidation() {
        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
        overlapAlert.setHeaderText("Appointment Overlap Detected");
        overlapAlert.setContentText("General overlap error");

//        int formAptId = Integer.parseInt(aptIdLabel.getText());
        LocalTime formStartTime = startTimeCombo.getSelectionModel().getSelectedItem();
        LocalDate formStartDate = startDateDP.getValue();
        LocalDateTime formStartDateTime = LocalDateTime.of(formStartDate, formStartTime);
        LocalTime endFormTime = endTimeCombo.getSelectionModel().getSelectedItem();
        LocalDate endFormDate = endDateDP.getValue();
        LocalDateTime formEndDateTime = LocalDateTime.of(endFormDate, endFormTime);
        int customerId = customerCB.getSelectionModel().getSelectedItem().getCustomerId();
        ObservableList<Appointment> customerAppointments = AppointmentQuery.customerAppointments(customerId);

        for (Appointment a : customerAppointments) {
            LocalDate csLD = a.getAptStartP().toLocalDate();
            LocalTime csLT = a.getAptStartP().toLocalTime();
            LocalDateTime csLDT = LocalDateTime.of(csLD, csLT);
            LocalDate ceLD = a.getAptEndP().toLocalDate();
            LocalTime ceLT = a.getAptEndP().toLocalTime();
            LocalDateTime ceLDT = LocalDateTime.of(ceLD, ceLT);

            //overlap for form start being equal to the start of an appointment in the list of customer appointments
            if ((formStartDateTime.isEqual(csLDT) && formEndDateTime.isEqual(
                    ceLDT)) && a.getCustomerId() != customerId) {
                overlapAlert.setContentText("Appointment with that date and time already exists for this customer. \n" +
                                                    "Existing appointment Id: " + a.getAptIdP());
                overlapAlert.show();
                return false;
            }
            /*overlap for form appointment starts before an appointment in list of customer appointments and ends
              inside of it*/
            else if (formStartDateTime.isBefore(csLDT) && formEndDateTime.isAfter(
                    csLDT) && a.getCustomerId() != customerId) {
                overlapAlert.setContentText("Another appointment is already scheduled during this time.");
                overlapAlert.show();
                return false;
            }
            //form appointment time falls completely within the timeframe of a currently set appointment.
            else if (formStartDateTime.isAfter(csLDT) && formEndDateTime.isBefore(
                    ceLDT) && a.getCustomerId() != customerId) {
                overlapAlert.setContentText("Another appointment falls within the one being set.");
                overlapAlert.show();
                return false;
                //form appointment time has other appointments scheduled in the same time period
            } else if (formStartDateTime.isBefore(csLDT) && formEndDateTime.isAfter(ceLDT)) {
                overlapAlert.setContentText("There is another appointment set within this appointments time.");
                overlapAlert.show();
                return false;
                //form dateTime starts after list appointment and ends after list appointment
            } else if (formStartDateTime.isAfter(csLDT) && formEndDateTime.equals(ceLDT)) {
                overlapAlert.setContentText("This appointment starts during another scheduled appointment");
                overlapAlert.show();
                return false;
            }
        }
        return true;
    }

    /**
     * check if forms have values in them. Returns false if any form is empty, returns true if all form fields
     * contain a valid value
     */
    protected boolean checkEmptyFormValues() {
        if (titleTB.getText().isEmpty() || descriptionTB.getText().isEmpty() || typeCB.getSelectionModel().getSelectedItem() == null
                || contactCB.getSelectionModel().getSelectedItem() == null || startDateDP.getValue() == null
                || startTimeCombo.getSelectionModel().getSelectedItem() == null || endDateDP.getValue() == null || endTimeCombo.getSelectionModel().getSelectedItem() == null) {

            emptyFormsAlert();
            return false;
        }
        return true;
    }


    /**
     * Calls validation check methods to validate user input.  If all tests pass, an Appointment object is built and
     * inserted into database.
     */
    private void addAppointment() {
        addAppointmentButton.setOnAction(actionEvent -> {
            if (!checkEmptyFormValues() || !checkTimeAllocation() || !overlapValidation()) return;
            int newAptId = AppointmentQuery.newAppointmentId();
            int customerId = customerCB.getSelectionModel().getSelectedItem().getCustomerId();
            String title = titleTB.getText();
            String description = descriptionTB.getText();
            int divisionId = CustomerQuery.divisionIdLook(customerId);
            int countryId = CustomerQuery.countryIdFkLook(divisionId);
            String divisionName = CustomerQuery.divisionNameLook(divisionId);
            String countryName = CustomerQuery.countryNameLook(countryId);
            String location = divisionName + ", " + countryName;
            String type = String.valueOf(typeCB.getSelectionModel().getSelectedItem());
            LocalDate startDate = startDateDP.getValue();
            LocalTime startTime = startTimeCombo.getSelectionModel().getSelectedItem();
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDate endDate = endDateDP.getValue();
            LocalTime endTime = endTimeCombo.getSelectionModel().getSelectedItem();
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            int userId = LoginController.currentUser().getUserId();


            int contactId = contactCB.getSelectionModel().getSelectedItem().getContactId();
            Appointment newAppointment = new Appointment(newAptId, title, description, location, type, startDateTime,
                                                         endDateTime, customerId, contactId, userId);

            AppointmentQuery.insert(newAppointment.getAptID(), newAppointment.getAptTitle(),
                                    newAppointment.getAptDescription(), newAppointment.getAptLocation(),
                                    newAppointment.getContactId(), newAppointment.getAptType(),
                                    newAppointment.getAptStart(), newAppointment.getAptEnd(),
                                    newAppointment.getCustomerId(), newAppointment.getUserId());


            refreshAppointmentTable();
            clearForm();
        });
    }

    /**
     * modify selected appointment from appointmentTable.  Gathers data modified from form values and updates
     * database based on appointment id.
     */
    private void modifyAppointment() {
        modifyAppointmentButton.setOnAction(actionEvent -> {

            //validate from fields, if checks pass, modify appointment executes.
            if (!checkEmptyFormValues() || !checkTimeAllocation() || !overlapValidation()) return;
            //initialize variables for appointment object
            int customerId = customerCB.getSelectionModel().getSelectedItem().getCustomerId();
            String title = titleTB.getText();
            String description = descriptionTB.getText();
            //Query database for customer location to set as appointment location.
            int divisionId = CustomerQuery.divisionIdLook(customerId);
            int countryId = CustomerQuery.countryIdFkLook(divisionId);
            String divisionName = CustomerQuery.divisionNameLook(divisionId);
            String countryName = CustomerQuery.countryNameLook(countryId);
            String location = divisionName + ", " + countryName;
            String type = String.valueOf(typeCB.getSelectionModel().getSelectedItem());
            //build localDateTime objects with values gathered from date pickers and time combo boxes
            LocalDate startDate = startDateDP.getValue();
            LocalTime startTime = startTimeCombo.getSelectionModel().getSelectedItem();
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDate endDate = endDateDP.getValue();
            LocalTime endTime = endTimeCombo.getSelectionModel().getSelectedItem();
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            int contactId = contactCB.getSelectionModel().getSelectedItem().getContactId();
            int userId = LoginController.currentUser().getUserId();
            int aptId = Integer.parseInt(aptIdLabel.getText());
            //build appointment object
            Appointment modAppointment = new Appointment(title, description, location, type, startDateTime, endDateTime,
                                                         customerId, contactId, userId, aptId);
            //execute query using generated appointment object.
            AppointmentQuery.updateAppointment(modAppointment.getAptTitle(), modAppointment.getAptDescription(),
                                               modAppointment.getAptLocation(), modAppointment.getContactId(),
                                               modAppointment.getAptType(), modAppointment.getAptStart(),
                                               modAppointment.getAptEnd(), modAppointment.getCustomerId(),
                                               modAppointment.getUserId(), modAppointment.getAptID());
            //refresh the appointment table and clear the form values.
            refreshAppointmentTable();
            clearForm();
        });
    }


    /**
     * cancels selected appointment from the database. Shows a confirmation dialog box to confirm deletion, presents
     * an error dialog box if no appointment is selected on the appointmentTable. Refreshes the customer table to
     * reflect the deleted appointment.
     */
    private void cancelAppointment() {
        cancelAppointmentButton.setOnAction(actionEvent -> {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            if (selectedAppointment == null) {
                noSelectionAlert();
                return;
            }
            Alert deleteWarning = new Alert(Alert.AlertType.CONFIRMATION);
            deleteWarning.setTitle("Are you sure?");
            deleteWarning.setHeaderText("Cancel this Appointment?" + " \"" + selectedAppointment.getAptTitle() + " \"");
            deleteWarning.setContentText("Do you wish to cancel this appointment?");
            //show alert and get user response
            deleteWarning.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {

                    cancelAppointmentLabel.setText(
                            selectedAppointment.getAptIdP() + " " + selectedAppointment.getAptTypeP() + " was " + "canceled.");
                    AppointmentQuery.cancelAppointment(selectedAppointment.getAptIdP());
                }
            }));
            refreshAppointmentTable();
            clearForm();
        });
    }

    /**
     * clears the cancel appointment message from the UI.
     */
    private void clearDeleteMessage() {
        if (cancelAppointmentLabel != null) cancelAppointmentLabel.setText(null);
    }

    /**
     * Sets all form fields to null.
     */
    private void clearForm() {
        clearFormButton.setOnAction(actionEvent -> {
            titleTB.setText(null);
            descriptionTB.setText(null);
            typeCB.setValue(null);
            contactCB.getSelectionModel().select(null);
            startDateDP.setValue(null);
            startTimeCombo.getSelectionModel().select(null);
            endDateDP.setValue(null);
            endTimeCombo.getSelectionModel().select(null);
            typeCB.getSelectionModel().select(null);
            customerCB.getSelectionModel().select(null);
        });
    }

    /**
     * Builds a list of LocalTime objects with 30 minute intervals from 08:00 - 21:30
     */
    private void fillStartTimeCombo() {
        ObservableList<LocalTime> operationHours = FXCollections.observableArrayList();
        LocalTime interval = Appointment.getOpenBusinessTime().toLocalTime();
        while (interval.isBefore(Appointment.getCloseBusinessTime().toLocalTime())) {

            operationHours.add(interval);
            interval = interval.plusMinutes(30);

        }
        startTimeCombo.setItems(operationHours);
    }

    /**
     * Builds a list of LocalTime objects with 30 minute intervals from 08:30 - 22:00
     */
    private void fillEndTimeCombo() {
        ObservableList<LocalTime> operationHours = FXCollections.observableArrayList();
        LocalTime interval = Appointment.getOpenBusinessTime().toLocalTime().plusMinutes(30);
        while (interval.isBefore(Appointment.getCloseBusinessTime().toLocalTime().plusMinutes(30))) {

            operationHours.add(interval);
            interval = interval.plusMinutes(30);

        }
        endTimeCombo.setItems(operationHours);
    }

    private void fillTypeCB() {
        typeCB.setItems(Appointment.getAllAppointmentTypes());
    }

    private void fillCustomerCB() {
        customerCB.setItems(CustomerQuery.getAllCustomers());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allAptRadio.setSelected(true);
        refreshAppointmentTable();

        fillContactCombo();
        fillFields();
        addAppointment();
        modifyAppointment();
        cancelAppointment();
        clearDeleteMessage();
        homeButtonHandler();
        homeButton.setCancelButton(true);
        fillStartTimeCombo();
        fillEndTimeCombo();
        fillTypeCB();
        fillCustomerCB();
        clearForm();
    }
}
