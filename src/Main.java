import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
//		UsersListGUI users = new UsersListGUI();
//		PendingOrderGUI pending = new PendingOrderGUI();
		Connection con = SQLConnect.connect();
		FinInfoGUI finInfo = new FinInfoGUI(con);

	}

}
