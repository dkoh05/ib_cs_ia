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


public class OrderHistoryGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	private JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private JButton usersListBtn = new JButton("USERS LIST");
	private JButton logoutBtn = new JButton("LOGOUT");
	
	String[][] allOrders = new String[1000][8];
	String[] columnNames = {"ID", "Username", "No. Of Guests", "Check-in Date", "Check-in Time", "Check-out Date",
			"Check-out Time", "Note" };
	JTable table = new JTable(allOrders, columnNames);

	JScrollPane sp = new JScrollPane(table);

	
	
	
	OrderHistoryGUI(){
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253 ));
		
		pendingOrderBtn.setBounds(100,45,175,25);
		pendingOrderBtn.addActionListener(this);
		panel.add(pendingOrderBtn);
		
		orderHistoryBtn.setBounds(350, 45,175,25);
		orderHistoryBtn.addActionListener(this);
		orderHistoryBtn.setBackground(Color.LIGHT_GRAY);
		panel.add(orderHistoryBtn);
		
		finInfoBtn.setBounds(600,45,175,25);
		finInfoBtn.addActionListener(this);
		panel.add(finInfoBtn);
		
		usersListBtn.setBounds(850,45,175,25);
		usersListBtn.addActionListener(this);
		panel.add(usersListBtn);
		
		logoutBtn.setBounds(1075, 20, 100, 25);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		Connection con = SQLConnect.connect();

		String query = "SELECT * from reservation where is_completed = 1;";

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			int count = 0;

			while (rs.next()) {
				String id = rs.getString("id");
				String username = rs.getString("username");
				String guestNum = rs.getString("guest_num");
				String checkinDate = rs.getString("checkin_date");
				String checkinTime = rs.getString("checkin_time");
				String checkoutDate = rs.getString("checkout_date");
				String checkoutTime = rs.getString("checkout_time");
				String note = rs.getString("note");
				String[] row = { id, username, guestNum, checkinDate, checkinTime, checkoutDate, checkoutTime, note };
				allOrders[count] = row;
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
		if(e.getSource() == pendingOrderBtn) {
			frame.dispose();
			PendingOrderGUI pendingOrderPage = new PendingOrderGUI();
		} else if(e.getSource() == finInfoBtn) {
			frame.dispose();
			FinInfoGUI finInfoPage = new FinInfoGUI();
		} else if(e.getSource() == usersListBtn) {
			frame.dispose();
			UsersListGUI usersListPage = new UsersListGUI();
		} else if(e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
		}
	}
}
