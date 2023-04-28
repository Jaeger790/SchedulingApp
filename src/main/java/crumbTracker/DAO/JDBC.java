/**
 * @author Brad Rott
 * @version 1.0
 */
package crumbTracker.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBC {
    /**
     *
     */
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "datacrumbs";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "root"; // Username connection
    private static String password = "freyawolf"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * opens connection to database using connection data in class.
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * closes connection to database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
