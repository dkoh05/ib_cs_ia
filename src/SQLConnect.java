
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnect {
	
    public static Connection connect() {
        Connection conn = null;
        try {
            // database parameters
            String url = System.getenv("DB_URL"); // link to the database
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}

