package crumbTracker.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import crumbTracker.model.CalendarModel;
import crumbTracker.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomerQuery {

    /**
     * @return returns total list of customers saved on the DB
     * @apiNote Queries the database places the query results into an observable list and returns it.
     */
    public static ObservableList<Customer> getAllCustomers() {
        //create list to fill with data from DB
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        try {
            //make query
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            //iterate through query result
            while (rs.next()) {
                //assign cell values from database to local variables
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                LocalDateTime localDateTime = CalendarModel.getLocalDateTime();
                int divisionId = divisionIdLook(customerId);
                String divisionName = divisionNameLook(divisionId);
                int countryId = countryIdFkLook(divisionId);
                String countryName = countryNameLook(countryId);
                //Create customer object to add query variables.
                Customer cs = new Customer(customerId, customerName, customerAddress, customerPostal, customerPhone,
                                           localDateTime, divisionId, divisionName, countryId, countryName);
                //add customer object to customer list.
                customerList.add(cs);
            }

            return customerList;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries database for next available customerID
     */
    public static int newCustomerId() {
        String sql = "SELECT * from customers";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int custId = 1;
            while (rs.next()) {
                if (rs.isLast()) {
                    custId = rs.getInt("Customer_ID");
                    custId++;
                }
            }
            return custId;
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * @apiNote processes input gathered from CustomerController.saveCustomer() and writes it to the database
     */
    public static int insert(int custId, String name, String address, String postalCode, String phoneNumber, Timestamp createDate, int divisionId) {
        //create INSERT SQL statement
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address,Postal_Code,Phone," + "Create_Date," +
                "Division_ID)" + "VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            //set parameters for database insertion
            ps.setInt(1,custId);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, postalCode);
            ps.setString(5, phoneNumber);
            ps.setTimestamp(6, createDate);

            ps.setInt(7, divisionId);
            return ps.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Writes over Customer info obtained from modify customer button/form.
     */
    public static int updateCustomer(int customerId, String customerName, String customerAddress, String customerPostal, String customerPhone, int divisionId, Timestamp lastUpdatedDateTime) {
        String sql = "UPDATE customers SET Customer_Name=?,Address=?,Postal_Code=?,Phone=?,Division_ID=?," + "Last_Update =? " + " WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(7, customerId);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostal);
            ps.setString(4, customerPhone);
            ps.setInt(5, divisionId);
            ps.setTimestamp(6, lastUpdatedDateTime);
            return ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -7;
    }

    /**
     *  deletes customer from table and database using customerId
     */
    public static void deleteCustomer(int customerId) {
        deleteAppointments(customerId);
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllCustomers(){
        ObservableList<Customer> customers = getAllCustomers();
        for(Customer c : customers){
            deleteAppointments(c.getCustomerId());
            deleteCustomer(c.getCustomerId());
        }
    }

    /**
     * deletes appointments based on customer ID
     */
    public static void deleteAppointments(int customerId) {
        String sql = "DELETE FROM appointments WHERE Customer_ID =" + customerId;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch(SQLException s) {
            s.printStackTrace();
        }
    }

    /**
     * Queries database for division name using string from comboBox choice
     */
    public static String selectDivisionName(String divisionChoice) {
        String sql = "SELECT Division FROM divisions WHERE Division =?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, divisionChoice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("Division");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * selects division id using division choice from comboBox.
     */
    public static int selectDivisionId(String divisionChoice) {
        String sql = "SELECT Division_ID FROM divisions WHERE Division =?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, divisionChoice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                return rs.getInt("Division_ID");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -9;
    }

    /**
     * selects a customers division id.
     */
    public static int divisionIdLook(int customerId) {
        String sql = "SELECT Division_ID FROM customers WHERE Customer_ID=" + customerId;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("Division_ID");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return -11;
    }

    /**
     * Selects a division name using a division id.
     */
    public static String divisionNameLook(int divisionId) {
        String sql = "Select Division FROM divisions WHERE Division_ID=" + divisionId;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String divisionName = rs.getString("Division");
                return divisionName;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries database for country name using country id.
     */
    public static String countryNameLook(int countryId) {
        String sql = "SELECT Country FROM countries WHERE Country_ID=" + countryId;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String countryName = rs.getString("Country");
                return countryName;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries database for country id using division id as identifying key.
     */

    public static int countryIdFkLook(int divisionId) {
        String sql = "SELECT Country_ID FROM divisions WHERE Division_ID=" + divisionId;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("Country_ID");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -10;
    }

    public static String getCustomerLocation(int divisionId) {
        String sql = "SELECT * FROM customers WHERE Division_ID = " + divisionId;
        return null;
    }


}
