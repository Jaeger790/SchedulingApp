package crumbTracker.DAO;

import crumbTracker.model.Country;
import crumbTracker.model.Division;
import crumbTracker.model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationQuery {


    public static ObservableList<Location> getAllDivisions() {
        ObservableList<Location> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM divisions";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Location ds = new Division(divisionId, divisionName, countryId);
                divisionList.add(ds);

            }

        }catch(SQLException e){e.printStackTrace();}

        return divisionList;
    }

    public static ObservableList<Location> getAllCountries()  {
        ObservableList<Location> countriesList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Location cs = new Country(countryId, countryName);
                countriesList.add(cs);
            }

        }catch(SQLException e){e.printStackTrace();}
        return countriesList;
    }

    public static ObservableList<Location> filterDivisions(int countryId) {
        ObservableList<Location> divisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM divisions WHERE Country_ID=" + countryId;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Location addDivision = new Division(divisionId, divisionName, countryId);
                divisions.add(addDivision);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }
}