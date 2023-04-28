package crumbTracker.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import crumbTracker.model.Appointment;
import crumbTracker.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ContactQuery {

    /**
     * Queries database for all contacts
     */
    public static ObservableList<Contact> contactList() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contact contact = new Contact(contactId, contactName);
                contacts.add(contact);
            }

        } catch(SQLException e) {
            System.out.println("contactList SQL failure");
        }
        return contacts;
    }

    /**
     * Queries database for all appointments associated with a contactId
     */
    public static ObservableList<Appointment> contactAppointments(int contactId) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String aptTitle = rs.getString("Title");
                String aptDescription = rs.getString("Description");
                String aptLocation = rs.getString("Location");
                int contactIds = rs.getInt("Contact_ID");
                String contact = ContactQuery.getAptContact(contactIds);
                String aptType = rs.getString("Type");
                LocalDateTime aptStart = rs.getTimestamp("Start_Date").toLocalDateTime();
                LocalDateTime aptEnd = rs.getTimestamp("End_Date").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment apt = new Appointment(aptTitle, aptDescription, aptLocation, aptType, contact,
                                                  appointmentId,
                                                  customerId, contactIds, userId, aptStart, aptEnd);
                contactAppointments.add(apt);
            }
        } catch(SQLException s) {
            s.printStackTrace();
        }
        return contactAppointments;
    }

    /**
     * Queries database for a contact name using a contact id.
     */
    public static String getAptContact(int contactId) {
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID =" + contactId;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("Contact_Name");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
