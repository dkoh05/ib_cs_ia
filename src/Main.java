import java.sql.Connection;

public class Main {

	public static void main(String[] args) {

//		PendingOrderGUI pending = new PendingOrderGUI();
		Connection con = SQLConnect.connect();
//		LoginGUI login = new LoginGUI(con);
		UsersListGUI users = new UsersListGUI(con);
		

	}

}
