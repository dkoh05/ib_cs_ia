import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class UsersListGUI implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	private JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private JButton usersListBtn = new JButton("USERS LIST");
	private JButton logoutBtn = new JButton("LOGOUT");
	
	String[][] usersList = new String[1000][5];
	String[] columnNames = {"Username", "Password", "Full Name", "Email Address", "Phone Number"};
	JTable table = new JTable(usersList, columnNames);

	JScrollPane sp = new JScrollPane(table);

	UsersListGUI() {
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253 ));

		pendingOrderBtn.setBounds(100, 45, 175, 25);
		pendingOrderBtn.addActionListener(this);
		panel.add(pendingOrderBtn);

		orderHistoryBtn.setBounds(350, 45, 175, 25);
		orderHistoryBtn.addActionListener(this);
		panel.add(orderHistoryBtn);

		finInfoBtn.setBounds(600, 45, 175, 25);
		finInfoBtn.addActionListener(this);
		panel.add(finInfoBtn);

		usersListBtn.setBounds(850, 45, 175, 25);
		usersListBtn.addActionListener(this);
		usersListBtn.setBackground(Color.LIGHT_GRAY);
		panel.add(usersListBtn);

		logoutBtn.setBounds(1075, 20, 100, 25);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		Connection con = SQLConnect.connect();

		String query = "SELECT * from user;";

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			int count = 0;

			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String fullName = rs.getString("full_name");
				String email = rs.getString("email_address");
				String phoneNum = rs.getString("phone_num");
				
				String[] row = {username, password, fullName, email, phoneNum};
				usersList[count] = row;
				count++;
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}
		
		sp.setBounds(100, 100, 900, 400);
		panel.add(sp);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pendingOrderBtn) {
			frame.dispose();
			PendingOrderGUI pendingOrderPage = new PendingOrderGUI();
		} else if (e.getSource() == orderHistoryBtn) {
			frame.dispose();
			OrderHistoryGUI orderHistoryPage = new OrderHistoryGUI();
		} else if (e.getSource() == finInfoBtn) {
			frame.dispose();
			FinInfoGUI finInfoPage = new FinInfoGUI();
		} else if (e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
		}
	}
}
