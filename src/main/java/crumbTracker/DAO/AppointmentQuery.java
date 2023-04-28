package crumbTracker.DAO;

import crumbTracker.model.Appointment;
import crumbTracker.model.CalendarModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentQuery {

    /**
     * generates id based on current id count in database.
     */
    public static int newAppointmentId() {
        String sql = "SELECT * from appointments";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int appointment = 0;
            while (rs.next()) {
                if (rs.isLast()) {
                    appointment = rs.getInt("Appointment_ID");
                    appointment++;
                }
            }
            return appointment;
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Queries database for all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String aptTitle = rs.getString("Title");
                String aptDescription = rs.getString("Description");
                String aptLocation = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contact = ContactQuery.getAptContact(contactId);
                String aptType = rs.getString("Type");
                LocalDateTime aptStart = rs.getTimestamp("Start_Date").toLocalDateTime();
                LocalDateTime aptEnd = rs.getTimestamp("End_Date").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment apt = new Appointment(aptTitle, aptDescription, aptLocation, aptType, contact,
                                                  appointmentId,
                                                  customerId, contactId, userId, aptStart, aptEnd);
                allAppointments.add(apt);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * selects all appointments associated with a customer id.
     */

    public static ObservableList<Appointment> customerAppointments(int customerId) {
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        String sql = "Select * FROM appointments WHERE Customer_ID = " + customerId;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String aptTitle = rs.getString("Title");
                String aptDescription = rs.getString("Description");
                String aptLocation = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contact = ContactQuery.getAptContact(contactId);
                String aptType = rs.getString("Type");
                LocalDateTime aptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime aptEnd = rs.getTimestamp("End").toLocalDateTime();
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment apt = new Appointment(aptTitle, aptDescription, aptLocation, aptType, contact,
                                                  appointmentId,
                                                  custId, contactId, userId, aptStart, aptEnd);
                customerAppointments.add(apt);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return customerAppointments;
    }

    /**
     * Selects all appointments by start date that occur within the current month of machines localDateTime.
     */
    public static ObservableList<Appointment> monthAppointments() {
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ? ";

        LocalDateTime monthEnd = CalendarModel.getMonthEnd();
        LocalDateTime monthBegin = CalendarModel.getMonthBegin();

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(monthBegin));
            ps.setTimestamp(2, Timestamp.valueOf(monthEnd));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String aptTitle = rs.getString("Title");
                String aptDescription = rs.getString("Description");
                String aptLocation = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contact = ContactQuery.getAptContact(contactId);
                String aptType = rs.getString("Type");
                LocalDateTime aptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime aptEnd = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment apt = new Appointment(aptTitle, aptDescription, aptLocation, aptType, contact,
                                                  appointmentId,
                                                  customerId, contactId, userId, aptStart, aptEnd);
                monthAppointments.add(apt);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return monthAppointments;
    }

    /**
     * Selects all appointments by start date that occur within the next 7 days of machines localDateTime.
     */
    public static ObservableList<Appointment> weekAppointments() {
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ? ";

        LocalDateTime weekEnd = CalendarModel.getWeekEnd();
        LocalDateTime today = CalendarModel.getLocalDateTime();

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(today));
            ps.setTimestamp(2, Timestamp.valueOf(weekEnd));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String aptTitle = rs.getString("Title");
                String aptDescription = rs.getString("Description");
                String aptLocation = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contact = ContactQuery.getAptContact(contactId);
                String aptType = rs.getString("Type");
                LocalDateTime aptStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime aptEnd = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment apt = new Appointment(aptTitle, aptDescription, aptLocation, aptType, contact,
                                                  appointmentId,
                                                  customerId, contactId, userId, aptStart, aptEnd);
                weekAppointments.add(apt);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return weekAppointments;
    }


    /**
     * Query database for appointments occurring during the current local datetime.
     */
    public static ObservableList<Appointment> todayApt() {
        ObservableList<Appointment> todayAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start_Date BETWEEN ? AND ? ";

        LocalDateTime closeTime = Appointment.getCloseBusinessTime().toLocalDateTime();
        LocalDateTime openTime = Appointment.getOpenBusinessTime().toLocalDateTime();

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(openTime));
            ps.setTimestamp(2, Timestamp.valueOf(closeTime));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String aptTitle = rs.getString("Title");
                String aptDescription = rs.getString("Description");
                String aptLocation = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contact = ContactQuery.getAptContact(contactId);
                String aptType = rs.getString("Type");
                LocalDateTime aptStart = rs.getTimestamp("Start_Date").toLocalDateTime();
                LocalDateTime aptEnd = rs.getTimestamp("End_Date").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment apt = new Appointment(aptTitle, aptDescription, aptLocation, aptType, contact,
                                                  appointmentId,
                                                  customerId, contactId, userId, aptStart, aptEnd);
                todayAppointments.add(apt);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return todayAppointments;
    }

    /**
     * inserts data obtained from appointment form fields into database.
     */
    public static void insert(int aptId, String title, String description, String location, int contactId, String type,
                              LocalDateTime localStartTime, LocalDateTime localEndTime, int customerId, int userId) {
        String sql = "INSERT INTO appointments (Appointment_ID,Title,Description,Location,Type,Start_Date,End_Date," +
                "Customer_ID,User_ID,Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, aptId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, Timestamp.valueOf(localStartTime));
            ps.setTimestamp(7, Timestamp.valueOf(localEndTime));
            ps.setInt(8, customerId);
            ps.setInt(9, userId);
            ps.setInt(10, contactId);
            ps.executeUpdate();

        } catch(SQLException s) {
            s.printStackTrace();
        }
    }

    /**
     * takes data input from appointment form fields and updates database record using appointment ID as identifying key
     */
    public static void updateAppointment(String title, String description, String location, int contactId, String type,
                                         LocalDateTime localStartTime, LocalDateTime localEndTime, int customerId, int userId, int aptId) {
        String sql = "UPDATE appointments SET Title=?,Description=?,Location=?,Type=?,Start_Date=?,End_Date=?," +
                "Customer_ID=?, " +
                "User_ID=?, Contact_ID=? Where Appointment_ID =?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(localStartTime));
            ps.setTimestamp(6, Timestamp.valueOf(localEndTime));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setInt(10, aptId);
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * deletes selected appointment from database.  Uses appointment id from selected appointment as identifying key.
     */
    public static void cancelAppointment(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID =?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        } catch(SQLException s) {
            s.printStackTrace();
        }
    }

    /**
     * deletes all appointment records associated with a customer id obtained from customer table.
     */
    public static void cancelAllAppointments(int customerId) {
        String sql = "DELETE FROM appointments WHERE Customer_ID =?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


}
