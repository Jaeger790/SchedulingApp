package crumbTracker;

import crumbTracker.DAO.CustomerQuery;
import crumbTracker.DAO.JDBC;
import crumbTracker.controller.CustomerController;
import crumbTracker.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerControllerTest {

    @Test
    @DisplayName("Correct customer insertion.")

    void addCustomerCorrect() {
        JDBC.openConnection();

        int customerId = CustomerQuery.newCustomerId();
        String customerName = "Jerky";
        String address = "12 pine street";
        String phone = "123456789";
        String postal = "4241";
        LocalDateTime created = LocalDateTime.now();
        int divisionId = 1;
        int countryId = CustomerQuery.countryIdFkLook(divisionId);

        Customer test = new Customer(customerId,customerName,address,postal,phone,created,countryId,divisionId);

        CustomerQuery.insert(test.getCustomerId(), test.getCustomerName(), test.getCustomerAddress(),
                             test.getCustomerPostalCode(), test.getCustomerPhoneNumber(),
                             Timestamp.valueOf(test.getCreateDateTime()),
                             test.getCustomerDivisionId());

        JDBC.closeConnection();
    }
    @Test
    @DisplayName("Customer name is too long.")
    void addCustomerLongName() {
        JDBC.openConnection();

        int customerId = CustomerQuery.newCustomerId();
        String customerName = "a".repeat(81);
        String address = "12 pine street";
        String phone = "123456789";
        String postal = "4241";
        LocalDateTime created = LocalDateTime.now();
        int divisionId = 1;
        int countryId = CustomerQuery.countryIdFkLook(divisionId);

        Customer test = new Customer(customerId,customerName,address,postal,phone,created,countryId,divisionId);

        CustomerQuery.insert(test.getCustomerId(), test.getCustomerName(), test.getCustomerAddress(),
                             test.getCustomerPostalCode(), test.getCustomerPhoneNumber(),
                             Timestamp.valueOf(test.getCreateDateTime()),
                             test.getCustomerDivisionId());

        JDBC.closeConnection();
    }
    @Test
    @DisplayName("Customer address is too long.")
    void addressTooLong() {
        JDBC.openConnection();
        int customerId = CustomerQuery.newCustomerId();
        String customerName = "";
        String address = "1".repeat(121);
        String phone = "123456789";
        String postal = "4241";
        LocalDateTime created = LocalDateTime.now();
        int divisionId = 1;
        int countryId = CustomerQuery.countryIdFkLook(divisionId);


        Customer test = new Customer(customerId,customerName,address,postal,phone,created,countryId,divisionId);

        CustomerQuery.insert(test.getCustomerId(), test.getCustomerName(), test.getCustomerAddress(),
                             test.getCustomerPostalCode(), test.getCustomerPhoneNumber(),
                             Timestamp.valueOf(test.getCreateDateTime()),
                             test.getCustomerDivisionId());

        CustomerQuery.deleteAllCustomers();

        JDBC.closeConnection();
    }

    @Test
    @DisplayName("Phone number too long")
    void phoneTooLong(){
        JDBC.openConnection();
        int customerId = CustomerQuery.newCustomerId();
        String customerName = "";
        String address = "12 pine street";
        String phone = "1".repeat(81);
        String postal = "4241";
        LocalDateTime created = LocalDateTime.now();
        int divisionId = 1;
        int countryId = CustomerQuery.countryIdFkLook(divisionId);


        Customer test = new Customer(customerId,customerName,address,postal,phone,created,countryId,divisionId);

        CustomerQuery.insert(test.getCustomerId(), test.getCustomerName(), test.getCustomerAddress(),
                             test.getCustomerPostalCode(), test.getCustomerPhoneNumber(),
                             Timestamp.valueOf(test.getCreateDateTime()),
                             test.getCustomerDivisionId());

        CustomerQuery.deleteAllCustomers();

        JDBC.closeConnection();
    }

    @Test
    @DisplayName("Postal code is too long")
    void postalTooLong(){
        JDBC.openConnection();
        int customerId = CustomerQuery.newCustomerId();
        String customerName = "";
        String address = "12 pine street";
        String phone = "123456789";
        String postal = "1".repeat(21);
        LocalDateTime created = LocalDateTime.now();
        int divisionId = 1;
        int countryId = CustomerQuery.countryIdFkLook(divisionId);


        Customer test = new Customer(customerId,customerName,address,postal,phone,created,countryId,divisionId);

        CustomerQuery.insert(test.getCustomerId(), test.getCustomerName(), test.getCustomerAddress(),
                             test.getCustomerPostalCode(), test.getCustomerPhoneNumber(),
                             Timestamp.valueOf(test.getCreateDateTime()),
                             test.getCustomerDivisionId());

        CustomerQuery.deleteAllCustomers();

        JDBC.closeConnection();
    }

    @Test
    @DisplayName("Improper Date. Month value out of range.")
    void improperDate(){
        DateTimeFormatter dateDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JDBC.openConnection();
        int customerId = CustomerQuery.newCustomerId();
        String customerName = "";
        String address = "12 pine street";
        String phone = "123456789";
        String postal = "19254";
        LocalDateTime created = LocalDate.parse("2020-13-20",dateDTF).atStartOfDay();
        int divisionId = 1;
        int countryId = CustomerQuery.countryIdFkLook(divisionId);


        Customer test = new Customer(customerId,customerName,address,postal,phone,created,countryId,divisionId);

        CustomerQuery.insert(test.getCustomerId(), test.getCustomerName(), test.getCustomerAddress(),
                             test.getCustomerPostalCode(), test.getCustomerPhoneNumber(),
                             Timestamp.valueOf(test.getCreateDateTime()),
                             test.getCustomerDivisionId());

        CustomerQuery.deleteAllCustomers();

        JDBC.closeConnection();
    }

    @Test
    @DisplayName("Location Error.")
    void incorrectLocationMatch(){

        JDBC.openConnection();
        int customerId = CustomerQuery.newCustomerId();
        String customerName = "";
        String address = "12 pine street";
        String phone = "123456789";
        String postal = "1A452";
        LocalDateTime created = LocalDateTime.now();
        int divisionId = 100;
        int countryId = CustomerQuery.countryIdFkLook(divisionId);


        Customer test = new Customer(customerId,customerName,address,postal,phone,created,countryId,divisionId);

        CustomerQuery.insert(test.getCustomerId(), test.getCustomerName(), test.getCustomerAddress(),
                             test.getCustomerPostalCode(), test.getCustomerPhoneNumber(),
                             Timestamp.valueOf(test.getCreateDateTime()),
                             test.getCustomerDivisionId());

        CustomerQuery.deleteAllCustomers();

        JDBC.closeConnection();
    }

    @Test
    @DisplayName("Letters in phone number")
    void phonePatternTest(){
        String phoneString1 = "8d83283727";
        assertTrue(CustomerController.phonePattern(phoneString1));


    }
    @Test
    @DisplayName("Special Characters in phone number")
    void phoneCharTest(){
        String phoneString2 = "39495;3938";
        assertTrue(CustomerController.phonePattern(phoneString2));


    }
    @Test
    @DisplayName("Hyphen phone format not accepted")
    void phoneHyphenFormat(){
        String phoneString3 = "123-456-7890";
        assertTrue(CustomerController.phonePattern(phoneString3));
    }
    @Test
    @DisplayName("Parenthesis not accepted")
    void parenPhoneFormat(){
        String phoneString4 = "(123)456789";
        assertTrue(CustomerController.phonePattern(phoneString4));
    }

    @Test
    @DisplayName("correct phone format")
    void correctPhoneFormat(){
        String phoneString5 = "1234567890";
        assertTrue(CustomerController.phonePattern(phoneString5));

    }

    @Test
    @DisplayName("Null form field check")
    void nullCheck(){

    }



}