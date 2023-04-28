/**
 * @author Brad Rott
 * @version 1.0
 */
package crumbTracker.controller;

import crumbTracker.DAO.ContactQuery;
import crumbTracker.DAO.ReportQuery;
import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    @FXML
    private TableView<Appointment> monthReportTable;
    @FXML
    private TableColumn<Appointment, Integer> yearCol;
    @FXML
    private TableColumn<Appointment, Integer> monthCol;
    @FXML
    private TableColumn<Appointment, Integer> monthTotalCol;

    @FXML
    private TableView<Appointment> typeReportTable;
    @FXML
    private TableColumn<Appointment, String> typeReportCol;
    @FXML
    private TableColumn<Appointment, Integer> totalTypeCol;

    @FXML
    private TableView<Appointment> locationReportTable;
    @FXML
    private TableColumn<Appointment, String> locationReportCol;
    @FXML
    private TableColumn<Appointment, Integer> locationReportTotalCol;

    @FXML
    private TableView<Customer> customerLocationTable;
    @FXML
    private TableColumn<Customer, String> custLocationCol;
    @FXML
    private TableColumn<Customer, Integer> totalCustCol;


    @FXML
    private TableView<Appointment> contactScheduleTable;
    @FXML
    private TableColumn<Appointment, Integer> aptIdCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;
    @FXML
    private TableColumn<Appointment, Integer> custIdCol;
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;
    @FXML
    private ComboBox<Contact> contactCB;
    @FXML
    private Button homeButton;

    /**
     * closes current screen and opens home screen
     */
    private void homeScene() {
        homeButton.setOnAction(actionEvent -> {
            try {
                Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/crumbTracker/layout/mainScreen" +
                                                                                  ".fxml"));
                Parent root = loader.load();
                Scene mainScene = new Scene(root);
                homeStage.setScene(mainScene);
                homeStage.setTitle("Welcome to Scheduling");
                homeStage.show();
            } catch(IOException i) {
                i.printStackTrace();
            }
        });

    }

    /**
     * fills contact combo box with contact data from database
     */
    private void fillContactCombo() {
        ObservableList<Contact> allContacts = ContactQuery.contactList();
        contactCB.setItems(allContacts);
    }

    /**
     * sets contactScheduleTable with data collected from database.  Uses combo box to select which contact to query
     * the database with.
     */
    private void setContactScheduleTable() {
        ObservableList<Contact> allContacts = ContactQuery.contactList();
        contactCB.setItems(allContacts);
        contactCB.setOnAction(actionEvent -> {
            int selectedContact = contactCB.getSelectionModel().getSelectedItem().getContactId();
            for (Contact c : allContacts) {
                if (c.getContactId() == selectedContact) {
                    contactScheduleTable.setItems(ContactQuery.contactAppointments(selectedContact));
                }
            }

        });
    }

    /**
     * sets contactAppointmentTable with data queried from database.  Formats localdatetime format to something more
     * easily readable.
     */
    private void refreshContactAppointmentTable() {
        DateTimeFormatter dateDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        aptIdCol.setCellValueFactory(cellData -> cellData.getValue().aptIdPProperty().asObject());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().aptTitlePProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().aptDescriptionPProperty());
        locationCol.setCellValueFactory(cellData -> cellData.getValue().aptLocationPProperty());
        typeCol.setCellValueFactory(cellData -> cellData.getValue().aptTypePProperty());
        contactCol.setCellValueFactory(cellData -> cellData.getValue().aptContactPProperty());
        startCol.setCellValueFactory(cellData -> cellData.getValue().aptStartPProperty());
        startCol.setCellFactory(format -> new TableCell<>() {
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

        endCol.setCellValueFactory(cellData -> cellData.getValue().aptEndPProperty());
        endCol.setCellFactory(format -> new TableCell<>() {
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
        custIdCol.setCellValueFactory(cellData -> cellData.getValue().customerIdPProperty().asObject());
        userIdCol.setCellValueFactory(cellData -> cellData.getValue().userIdPProperty().asObject());
        setContactScheduleTable();
    }

    private void refreshMonthReportTable() {
        ObservableList<Appointment> monthTotalList = ReportQuery.totalMonthAppointments();

        monthReportTable.setItems(monthTotalList);
        yearCol.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        yearCol.setCellFactory(format -> new TableCell<>() {
            @Override
            protected void updateItem(Integer startDateTime, boolean emptyCell) {
                super.updateItem(startDateTime, emptyCell);
                if (emptyCell) {
                    setText(null);
                } else {
                    if (startDateTime == null) {
                        setText(null);
                        return;
                    }
                    setText(String.valueOf(Year.of(startDateTime)));
                }
            }
        });
        monthCol.setCellValueFactory(cellData -> cellData.getValue().monthProperty().asObject());
        monthCol.setCellFactory(format -> new TableCell<>() {
            @Override
            protected void updateItem(Integer startDateTime, boolean emptyCell) {
                super.updateItem(startDateTime, emptyCell);
                if (emptyCell) {
                    setText(null);
                } else {
                    if (startDateTime == null) {
                        setText(null);
                        return;
                    }
                    setText(String.valueOf(Month.of(startDateTime)));
                }
            }
        });
        monthTotalCol.setCellValueFactory(cellData -> cellData.getValue().totalMonthAppointmentsProperty().asObject());
    }

    /**
     * sets table with data queried from database.  Displays total appointments by appointment type.
     */
    private void refreshTypeReportTable() {
        ObservableList<Appointment> typeTotalList = ReportQuery.totalTypeAppointments();
        typeReportTable.setItems(typeTotalList);
        typeReportCol.setCellValueFactory(cellData -> cellData.getValue().aptTypePProperty());
        totalTypeCol.setCellValueFactory(cellData -> cellData.getValue().totalTypeAppointmentsProperty().asObject());
    }

    /**
     * sets table with data queried from database.  Displays total appointments by appointment location.
     */
    private void refreshLocationReportTable() {
        ObservableList<Appointment> locationTotalList = ReportQuery.totalLocationAppointments();
        locationReportTable.setItems(locationTotalList);
        locationReportCol.setCellValueFactory(cellData -> cellData.getValue().aptLocationPProperty());
        locationReportTotalCol.setCellValueFactory(
                cellData -> cellData.getValue().totalLocationAppointmentsProperty().asObject());
    }

    /**
     * sets table with data queried from database.  Displays total customers by division.
     */
    private void refreshCustomerLocationReportTable() {
        ObservableList<Customer> customerLocationTotal = ReportQuery.totalCustomersByDivision();
        customerLocationTable.setItems(customerLocationTotal);
        custLocationCol.setCellValueFactory(cellDAta -> cellDAta.getValue().locationNameProperty());
        totalCustCol.setCellValueFactory(cellData -> cellData.getValue().totalCustomersLocationProperty().asObject());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeScene();
        fillContactCombo();
        refreshContactAppointmentTable();
        refreshMonthReportTable();
        refreshTypeReportTable();
        refreshLocationReportTable();
        refreshCustomerLocationReportTable();
    }
}
