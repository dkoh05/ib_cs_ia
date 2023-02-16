import java.sql.Connection;

public class Main {

	public static void main(String[] args) {

//		make a connection with SQL database and display the login page
		Connection con = SQLConnect.connect();
		LoginGUI login = new LoginGUI(con);

	}

}
