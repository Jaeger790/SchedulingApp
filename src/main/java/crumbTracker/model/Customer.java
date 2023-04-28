package crumbTracker.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Customer {
    private SimpleIntegerProperty customerId;

    private SimpleStringProperty customerName = new SimpleStringProperty();
    private SimpleStringProperty customerAddress = new SimpleStringProperty();
    private SimpleStringProperty customerPhoneNumber = new SimpleStringProperty();
    private SimpleStringProperty customerPostalCode = new SimpleStringProperty();
    private SimpleIntegerProperty customerDivisionId = new SimpleIntegerProperty();
    private SimpleStringProperty divisionName = new SimpleStringProperty();
    private SimpleStringProperty countryName = new SimpleStringProperty();
    private SimpleIntegerProperty countryId = new SimpleIntegerProperty();

    private SimpleIntegerProperty totalCustomersLocation = new SimpleIntegerProperty();
    private SimpleIntegerProperty divisionIdCount = new SimpleIntegerProperty();
    private SimpleStringProperty locationName = new SimpleStringProperty();

    private LocalDateTime createDateTime = LocalDateTime.now();



    /**
     * Constructors for Customer objects
     */


    public Customer(int custId, String customerName, String customerAddress, String customerPostalCode,
                    String customerPhoneNumber, LocalDateTime createDateTime,
                    int customerCountryId,
                    int customerDivisionId) {
        this.customerId = new SimpleIntegerProperty(custId);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerAddress = new SimpleStringProperty(customerAddress);
        this.customerPostalCode = new SimpleStringProperty(customerPostalCode);
        this.customerPhoneNumber = new SimpleStringProperty(customerPhoneNumber);
        this.createDateTime = createDateTime;
        this.countryId = new SimpleIntegerProperty(customerCountryId);
        this.customerDivisionId = new SimpleIntegerProperty(customerDivisionId);
    }

    /**
     * Customer Constructor for inserting customer data
     */
    public Customer(int custId, String customerName, String customerAddress, String customerPostalCode,
                    String customerPhoneNumber, LocalDateTime createDateTime,
                    int customerDivisionId, String divisionName) {
        this.customerId = new SimpleIntegerProperty(custId);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerAddress = new SimpleStringProperty(customerAddress);
        this.customerPostalCode = new SimpleStringProperty(customerPostalCode);
        this.customerPhoneNumber = new SimpleStringProperty(customerPhoneNumber);
        this.createDateTime = createDateTime;
        this.customerDivisionId = new SimpleIntegerProperty(customerDivisionId);
        this.divisionName = new SimpleStringProperty(divisionName);
    }

    public Customer(int custId, String customerName, String customerAddress, String customerPostalCode,
                    String customerPhoneNumber, LocalDateTime createDateTime,
                    int customerDivisionId, String divisionName, int countryId, String countryName) {
        this.customerId = new SimpleIntegerProperty(custId);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerAddress = new SimpleStringProperty(customerAddress);
        this.customerPostalCode = new SimpleStringProperty(customerPostalCode);
        this.customerPhoneNumber = new SimpleStringProperty(customerPhoneNumber);
        this.createDateTime = createDateTime;
        this.customerDivisionId = new SimpleIntegerProperty(customerDivisionId);
        this.divisionName = new SimpleStringProperty(divisionName);
        this.countryId = new SimpleIntegerProperty(countryId);
        this.countryName = new SimpleStringProperty(countryName);
    }



    /**
     * Customer Constructor for modifying customer data
     */
    public Customer(int custId, String customerName, String customerAddress, String customerPostalCode,
                    String customerPhoneNumber,
                    int customerDivisionId, String divisionName, int countryId,
                    String countryName) {
        this.customerId = new SimpleIntegerProperty(custId);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerAddress = new SimpleStringProperty(customerAddress);
        this.customerPostalCode = new SimpleStringProperty(customerPostalCode);
        this.customerPhoneNumber = new SimpleStringProperty(customerPhoneNumber);
        this.customerDivisionId = new SimpleIntegerProperty(customerDivisionId);
        this.divisionName = new SimpleStringProperty(divisionName);
        this.countryId = new SimpleIntegerProperty(countryId);
        this.countryName = new SimpleStringProperty(countryName);

    }

    public Customer(int total, int location, String locationName) {
        this.totalCustomersLocation = new SimpleIntegerProperty(total);
        this.divisionIdCount = new SimpleIntegerProperty(location);
        this.locationName = new SimpleStringProperty(locationName);
    }

    public Customer() {

    }


    @Override
    public String toString() {
        return customerId.getValue() + " " + customerName.getValue();
    }

    /**
     * getters and setters for customer data
     */
    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName.get();
    }
    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }
    public String getCustomerAddress() {
        return customerAddress.get();
    }
    public SimpleStringProperty customerAddressProperty() {
        return customerAddress;
    }
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber.get();
    }

    public SimpleStringProperty customerPhoneNumberProperty() {
        return customerPhoneNumber;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode.get();
    }

    public SimpleStringProperty customerPostalCodeProperty() {
        return customerPostalCode;
    }

    public int getCustomerDivisionId() {
        return customerDivisionId.get();
    }

    public String getDivisionName() {
        return divisionName.get();
    }

    public SimpleStringProperty divisionNameProperty() {
        return divisionName;
    }

    public SimpleIntegerProperty customerDivisionIdProperty() {
        return customerDivisionId;
    }

    public String getCountryName() {
        return countryName.get();
    }

    public SimpleStringProperty countryNameProperty() {
        return countryName;
    }

    public int getCountryId() {
        return countryId.get();
    }

    public SimpleIntegerProperty countryIdProperty() {
        return countryId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress.set(customerAddress);
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber.set(customerPhoneNumber);
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode.set(customerPostalCode);
    }

    public void setCustomerDivisionId(int customerDivisionId) {
        this.customerDivisionId.set(customerDivisionId);
    }

    public void setDivisionName(String divisionName) {
        this.divisionName.set(divisionName);
    }

    public void setCountryName(String countryName) {
        this.countryName.set(countryName);
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    public void setTotalCustomersLocation(int totalCustomersLocation) {
        this.totalCustomersLocation.set(totalCustomersLocation);
    }

    public void setDivisionIdCount(int divisionIdCount) {
        this.divisionIdCount.set(divisionIdCount);
    }

    public void setLocationName(String locationName) {
        this.locationName.set(locationName);
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }



    public int getTotalCustomersLocation() {
        return totalCustomersLocation.get();
    }

    public SimpleIntegerProperty totalCustomersLocationProperty() {
        return totalCustomersLocation;
    }

    public int getDivisionIdCount() {
        return divisionIdCount.get();
    }

    public SimpleIntegerProperty divisionIdCountProperty() {
        return divisionIdCount;
    }

    public String getLocationName() {
        return locationName.get();
    }

    public SimpleStringProperty locationNameProperty() {
        return locationName;
    }



    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }
}