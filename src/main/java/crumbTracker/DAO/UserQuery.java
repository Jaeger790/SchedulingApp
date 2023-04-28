package crumbTracker.DAO;

import crumbTracker.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQuery {
    public static User getUser(String userName) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE User_Name =?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String password = rs.getString("_Password");
                user.setUserId(id);
                user.setUserName(userName);
                user.setPassword(password);
                user.toString();
            }

        } catch(SQLException s) {
            s.printStackTrace();
        }
        return user;
    }


}
