/**
 * @author Brad Rott
 * @version 1.0
 */
package crumbTracker.DAO;

import crumbTracker.model.Appointment;
import crumbTracker.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportQuery {
    /**
     * Selects Year and month parts from appointment timestamp and counts how many appointment ids occur within each
     * month range.
     */
    public static ObservableList<Appointment> totalMonthAppointments() {
        ObservableList<Appointment> totalAppointmentsMonth = FXCollections.observableArrayList();
        String sql = "SELECT YEAR(Start_Date) as Year,MONTH(Start_Date) as Month,count(Appointment_ID) as Total FROM " +
                "appointments GROUP BY YEAR(Start_Date),MONTH(Start_Date);";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int year = rs.getInt("Year");

                int month = rs.getInt("Month");
                int total = rs.getInt("Total");

                Appointment apt = new Appointment(year, month, total);
                totalAppointmentsMonth.add(apt);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return totalAppointmentsMonth;
    }

    /**
     * selects type of appointment and counts how many appointment ids occur with that appointment type.
     */
    public static ObservableList<Appointment> totalTypeAppointments() {
        ObservableList<Appointment> totalAppointmentTypes = FXCollections.observableArrayList();
        String sql = "SELECT Type,COUNT(Appointment_ID) as Total FROM appointments GROUP BY Type";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                int total = rs.getInt("Total");

                Appointment apt = new Appointment(type, total);
                totalAppointmentTypes.add(apt);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return totalAppointmentTypes;
    }

    /**
     * selects appointments by location and counts how many appointment ids are associated to each location.
     */
    public static ObservableList<Appointment> totalLocationAppointments() {
        ObservableList<Appointment> totalAppointmentLocations = FXCollections.observableArrayList();
        String sql = "SELECT Location,COUNT(Appointment_ID) as Total FROM appointments GROUP BY Location";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String location = rs.getString("Location");
                int total = rs.getInt("Total");

                Appointment apt = new Appointment(total, location);
                totalAppointmentLocations.add(apt);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return totalAppointmentLocations;
    }

    /**
     * Selects customers by division id and counts how many customer ids have that division id associated with it.
     * Then method queries database for division name associated with each division id.
     */
    public static ObservableList<Customer> totalCustomersByDivision() {
        ObservableList<Customer> customerLocationList = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, COUNT(Customer_ID) as TotalCustomers FROM customers GROUP BY Division_ID";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int location = rs.getInt("Division_ID");
                int total = rs.getInt("TotalCustomers");
                Customer apt = new Customer(total, location, CustomerQuery.divisionNameLook(location));
                customerLocationList.add(apt);
            }
        } catch(SQLException s) {
            s.printStackTrace();
        }
        return customerLocationList;
    }


}
