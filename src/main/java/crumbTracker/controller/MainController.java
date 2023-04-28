package crumbTracker.controller;

import crumbTracker.DAO.AppointmentQuery;
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
import crumbTracker.model.CalendarModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Appointment> todayAppointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> aptIdCol;
    @FXML
    private TableColumn<Appointment, String> aptTitleCol;
    @FXML
    private TableColumn<Appointment, String> aptDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> aptLocationCol;
    @FXML
    TableColumn<Appointment, String> aptContactCol;
    @FXML
    TableColumn<Appointment, String> aptTypeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> aptStartCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> aptEndCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;
    @FXML
    private Label appointmentMessage;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button calendarButton;

    private void customerButtonHandler() {
        customersButton.setOnAction(this::customersScene);
    }

    /**
     * opens customer screen
     */
    private void customersScene(ActionEvent actionEvent) {
        CustomerController customersController = new CustomerController();
        try {
            Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/crumbTracker/layout/customers.fxml")));
            loader.setController(customersController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            customerStage.setScene(scene);
            customerStage.setTitle("Customers");
            customerStage.show();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * opens report screen
     */
    private void reportScene() {
        calendarButton.setOnAction(actionEvent -> {
            ReportsController calendarController = new ReportsController();
            try {
                Stage reportStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader =
                        new FXMLLoader((getClass().getResource("/crumbTracker/layout/reports.fxml")));
                loader.setController(calendarController);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                reportStage.setScene(scene);
                reportStage.setTitle("Reports");
                reportStage.show();
            } catch(IOException i) {
                i.printStackTrace();
            }
        });
    }

    /**
     * opens appointments screen
     */
    private void appointmentScene() {
        appointmentsButton.setOnAction(actionEvent -> {
            AppointmentController appointmentController = new AppointmentController();
            try {
                Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/crumbTracker/layout/appointments.fxml")));
                loader.setController(appointmentController);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                appointmentStage.setScene(scene);
                appointmentStage.setTitle("Appointments");
                appointmentStage.show();
            } catch(IOException i) {
                i.printStackTrace();
            }
        });
    }

    /**
     * loads updated information from database
     */
    private void refreshAppointmentTable() {
        DateTimeFormatter dateDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

        ObservableList<Appointment> todayAppointments;

        todayAppointments = AppointmentQuery.todayApt();

        todayAppointmentTable.setItems(todayAppointments);
    }

    /**
     * alerts user with a label message that an appointment is scheduled within 15 minutes of current login if there
     * is such an appointment scheduled.  Otherwise, it displays a default message
     */
    private void appointmentAlert() {
        ObservableList<Appointment> todayAppointments = AppointmentQuery.todayApt();
        LocalDateTime loginTime = LocalDateTime.now();
        LocalDateTime appointmentWarning = loginTime.plusMinutes(15);
        appointmentMessage.setText("There are no upcoming appointments");
        appointmentMessage.getStyleClass().add("noAppointmentMessage");


        for (Appointment a : todayAppointments) {
            String id = String.valueOf(a.getAptIdP());
            String type = String.valueOf(a.getAptTypeP());
            LocalDateTime testDateTime = a.getAptStartP();

            if (testDateTime.equals(appointmentWarning) || (testDateTime.isBefore(
                    appointmentWarning) && testDateTime.isAfter(loginTime))) {
                appointmentMessage.getStyleClass().add("upcomingAppointmentMessage");
                appointmentMessage.setText(
                        "Upcoming appointment!   Apt ID:  " + id + "\tStart:  " + testDateTime.format(
                                CalendarModel.getDateDTF()) + "\tType:  " + type);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshAppointmentTable();
        customersButton.setDefaultButton(true);
        customerButtonHandler();
        reportScene();
        appointmentScene();
        appointmentAlert();
    }
}
