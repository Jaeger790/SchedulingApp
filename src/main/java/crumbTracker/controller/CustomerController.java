package crumbTracker.controller;

import crumbTracker.DAO.AppointmentQuery;
import crumbTracker.DAO.CustomerQuery;
import crumbTracker.DAO.LocationQuery;
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
import crumbTracker.model.CalendarModel;
import crumbTracker.model.Customer;
import crumbTracker.model.Location;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerController implements Initializable {
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> idCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, String> divisionCol;
    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private Button homeButton, addCustomerButton, modifyCustomerButton, deleteCustomerButton;

    @FXML
    private TextField custNameTB, custPhoneTB, custAddressTB, custPostalTB;

    @FXML
    private Label customerIdLabel;
    @FXML
    private Label customerDeleteLabel;

    @FXML
    private ComboBox<Location> divisionComboBox;
    @FXML
    private ComboBox<Location> countryCombo;

    @FXML
    private Button clearButton;



    public CustomerController() {
    }

    /**
     * General alert used when a user has attempted to start an operation that requires a selected customer.
     */
    private static void noSelectionAlert() {
        Alert noSelection = new Alert(Alert.AlertType.ERROR);
        noSelection.setHeaderText("No selection made");
        noSelection.setContentText("Please select a customer record to proceed");
        noSelection.show();
    }

    /**
     * Takes user input and compares it to regex pattern,
     *
     * @param input user input from phone number text box.
     * @return returns true if pattern matches, false if not.
     */

    public static boolean phonePattern(final String input) {
        // Compile regular expression
        final Pattern phonePattern = Pattern.compile("^(\\d{1,3}[- ]?)?(\\d{1,3}[- ])?\\d{3,4}[- ]?\\d{4}$",
                                                     Pattern.CASE_INSENSITIVE);
        // Match regex against input
        final Matcher matcher = phonePattern.matcher(input);

        return matcher.matches();
    }

    /**
     * Takes user input and compares it to regex pattern,
     *
     * @param input user input from postal code text box.
     * @return returns true if pattern matches, false if not.
     */
    public static boolean postalPattern(final String input) {
        // Compile regular expression
        final Pattern phonePattern = Pattern.compile("([A-Za-z0-9]{1,6})", Pattern.CASE_INSENSITIVE);
        // Match regex against input
        final Matcher matcher = phonePattern.matcher(input);
        return matcher.matches();
    }

    /**
     * fills the division combo box if the country combo box is not empty based on which country is selected.
     */
    private void fillDivisionCombo() {
        try {
            if (countryCombo != null) {
                int countryId = countryCombo.getSelectionModel().getSelectedItem().getLocationId();
                System.out.println(countryId);
                divisionComboBox.setItems(LocationQuery.filterDivisions(countryId));
                divisionComboBox.setVisibleRowCount(5);
            }
            if (countryCombo.getSelectionModel().getSelectedIndex() == 0) {
                divisionComboBox.getSelectionModel().selectFirst();
            } else if (countryCombo.getSelectionModel().getSelectedIndex() == 1) {
                divisionComboBox.getSelectionModel().selectFirst();
            }

        } catch(NullPointerException n) {
            System.out.println("no selection catch for fillDivisionCombo");
        }
    }

    /**
     * fills the country combo box with data from db and then fills the division combo box based on which country is
     * currently selected in the country combo box.
     */
    private void fillCountryCombo() {
        countryCombo.setItems(LocationQuery.getAllCountries());
        fillDivisionCombo();
        countryCombo.setOnAction(actionEvent -> fillDivisionCombo());
    }

    /**
     * creates a new customer object and inserts input field data into it.
     * Then calls insert method from CustomerQuery class
     */
    protected void addCustomerHandler() {
        addCustomerButton.setOnAction(actionEvent -> {
            try {
                if (!checkEmptyFormValues()) return;
                if (customerDeleteLabel != null) customerDeleteLabel.setText(null);

                int newId = CustomerQuery.newCustomerId();
                String selectedName = custNameTB.getText();
                String selectedAddress = custAddressTB.getText();
                String selectedPostal = custPostalTB.getText();
                String selectedPhone = custPhoneTB.getText();
                LocalDateTime createLocalDateTime = CalendarModel.getLocalDateTime();
                int divisionId = CustomerQuery.selectDivisionId(
                        divisionComboBox.getSelectionModel().getSelectedItem().getLocationName());

                int countryId = CustomerQuery.countryIdFkLook(divisionId);
                Customer newCustomer = new Customer(newId,selectedName,selectedAddress,selectedPostal,selectedPhone,
                                                    createLocalDateTime,countryId,divisionId);

                CustomerQuery.insert(newCustomer.getCustomerId(), newCustomer.getCustomerName(),
                                     newCustomer.getCustomerAddress(), newCustomer.getCustomerPostalCode(),
                                     newCustomer.getCustomerPhoneNumber(), Timestamp.valueOf(createLocalDateTime),
                                     newCustomer.getCustomerDivisionId());
                refreshCustomerTable();
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * updates customer information using data collected from form fields.  Selected customer id is used as
     * identifier for database.
     */
    protected void modifyCustomerHandler() {
        modifyCustomerButton.setOnAction(actionEvent -> {
            clearDeleteMessage();
            if (customerTable.getSelectionModel().getSelectedItem() == null) {
                noSelectionAlert();
                return;
            }
            if (!checkEmptyFormValues()) return;

            int selectedId = Integer.parseInt(customerIdLabel.getText());
            String selectedName = custNameTB.getText();
            String selectedAddress = custAddressTB.getText();
            String selectedPostal = custPostalTB.getText();
            String selectedPhone = custPhoneTB.getText();
            LocalDateTime lastUpdatedDateTime = CalendarModel.getLocalDateTime();
            int divisionId = CustomerQuery.selectDivisionId(
                    divisionComboBox.getSelectionModel().getSelectedItem().getLocationName());
            String divisionName = CustomerQuery.selectDivisionName(
                    divisionComboBox.getSelectionModel().getSelectedItem().getLocationName());
            int countryId = CustomerQuery.countryIdFkLook(divisionId);
            String countryName = countryCombo.getSelectionModel().getSelectedItem().getLocationName();

            Customer modCustomer = new Customer(selectedId, selectedName, selectedAddress, selectedPostal,
                                                selectedPhone,
                                                divisionId, divisionName, countryId, countryName);

            CustomerQuery.updateCustomer(modCustomer.getCustomerId(), modCustomer.getCustomerName(),
                                         modCustomer.getCustomerAddress(), modCustomer.getCustomerPostalCode(),
                                         modCustomer.getCustomerPhoneNumber(), modCustomer.getCustomerDivisionId(),
                                         Timestamp.valueOf(lastUpdatedDateTime));
            refreshCustomerTable();
        });
    }

    /**
     * checks from fields for empty text and returns false if any empty fields are found.`
     */
    protected boolean checkEmptyText() {
        Location selectedDivision = divisionComboBox.getSelectionModel().getSelectedItem();
        Location selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
        if (selectedDivision == null || selectedCountry == null || custNameTB.getText().isEmpty() || custAddressTB.getText().isEmpty() || custPhoneTB.getText().isEmpty() || custPostalTB.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty fields");
            alert.setContentText("Please fill out all the fields to save a customer record.");
            alert.show();
            return false;
        }
        return true;
    }

    /**
     * Deletes customer from database and updates the application table to reflect
     * results.
     * Event handler for the deleteCustomerButton. deletes the customer and then
     * refreshes the table.
     */
    private void deleteCustomerHandler() {
        deleteCustomerButton.setOnAction(actionEvent -> {
            // get selected data from customer table
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            // display error dialog box if no customer is selected.
            if (selectedCustomer == null) {
                noSelectionAlert();
                return;
            }
            // instantiate confirmation dialog box for deleting a customer.
            Alert deleteWarning = new Alert(Alert.AlertType.CONFIRMATION);
            deleteWarning.setTitle("Are you sure?");
            deleteWarning.setHeaderText("Delete this customer" + " \"" + selectedCustomer.getCustomerName() + " \"");
            deleteWarning.setContentText(
                    "Deleting a customer record will also delete all appointments associated with " + "that customer.  Do you wish to proceed?");
            //show alert and get user response
            deleteWarning.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    AppointmentQuery.cancelAllAppointments(selectedCustomer.getCustomerId());
                    CustomerQuery.deleteCustomer(selectedCustomer.getCustomerId());
                    customerDeleteLabel.setText(
                            selectedCustomer.getCustomerId() + " " + selectedCustomer.getCustomerName() + " was deleted.");

                }
            }));
            refreshCustomerTable();
            clearSelection();
        });
    }

    /**
     * Checks form fields for empty values.  Returns false if any forms are empty.
     */
    private boolean checkEmptyFormValues() {
        return phonePattern(custPhoneTB.getText()) && postalPattern(custPostalTB.getText()) && checkEmptyText();
    }

    /**
     * queries the database for all customers and resets the customers table to
     * reflect the db.
     */
    private void refreshCustomerTable() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().customerAddressProperty());
        postalCol.setCellValueFactory(cellData -> cellData.getValue().customerPostalCodeProperty());
        phoneCol.setCellValueFactory(cellData -> cellData.getValue().customerPhoneNumberProperty());
        divisionCol.setCellValueFactory(cellData -> cellData.getValue().divisionNameProperty());
        countryCol.setCellValueFactory(cellData -> cellData.getValue().countryNameProperty());

        // initialize list for updated customer list from db.
        ObservableList<Customer> customerList = null;
        // query database for all customers
        customerList = CustomerQuery.getAllCustomers();
        // set updated data to customers table.
        customerTable.setItems(customerList);
    }


    /**
     * Event handler for home button. Switches scene from customers scene to main
     * scene.
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
            throw new RuntimeException();
        }
    }

    /**
     * fills form fields when a customer is selected from the customers table
     */
    private void fillFields() {
        customerTable.setOnMouseClicked(actionEvent -> {

            // get selected customer from table
            Customer selectedCells = customerTable.getSelectionModel().getSelectedItem();

            // handle exception for no customer selected
            try {
                if (selectedCells != null) {
                    // fill form fields with data from db
                    customerIdLabel.setText(selectedCells.customerIdProperty().getValue().toString());
                    custNameTB.setText(selectedCells.getCustomerName());
                    custPhoneTB.setText(selectedCells.getCustomerPhoneNumber());
                    custAddressTB.setText(selectedCells.getCustomerAddress());
                    custPostalTB.setText(selectedCells.getCustomerPostalCode());
                    //match country id from table with id in db and set country combo box to the matching value
                    for (Location l : LocationQuery.getAllCountries()) {
                        if (l.getLocationId() == selectedCells.getCountryId()) {
                            countryCombo.getSelectionModel().select(l);
                        }
                    }
                    //match division id with id in database and set division combo box to the matching value.
                    for (Location l : LocationQuery.getAllDivisions()) {
                        if (l.getLocationId() == selectedCells.getCustomerDivisionId()) {
                            divisionComboBox.getSelectionModel().select(l);
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * Set prompt text for all textBoxes in the add/modify customer form.
     */
    private void setPromptText() {
        //set prompt text to match accepted forms of input.
        customerIdLabel.setText("Customer ID");
        custNameTB.setPromptText("John Doe");
        custPhoneTB.setPromptText("123-456-7890");
        custAddressTB.setPromptText("123 Tall Tree Road, Squatchland");
        custPostalTB.setPromptText("12345");
        countryCombo.setPromptText("Select Country");
        divisionComboBox.setPromptText("Select Division");
        clearDeleteMessage();


    }

    /**
     * @void clears the data from the customer form fields when clear button is
     * clicked.
     */
    private void clearSelection() {
        // event handler for clear button
        clearButton.setOnMouseClicked(mouseEvent -> {
            // set all fields to null or default values.
            customerTable.getSelectionModel().select(null);
            customerIdLabel.setText("Customer ID");
            custNameTB.setText(null);
            custPhoneTB.setText(null);
            custAddressTB.setText(null);
            custPostalTB.setText(null);
            //set country and first level division combo boxes to default value
            countryCombo.getSelectionModel().select(null);
            divisionComboBox.getSelectionModel().select(null);
            clearDeleteMessage();
        });
    }

    /**
     * sets the deleteCustomer label to null
     */
    private void clearDeleteMessage() {
        if (customerDeleteLabel != null) customerDeleteLabel.setText(null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshCustomerTable();
        fillFields();
        fillCountryCombo();
        setPromptText();
        clearSelection();
        homeButtonHandler();
        addCustomerHandler();
        modifyCustomerHandler();
        deleteCustomerHandler();
        homeButton.setCancelButton(true);
    }
}
