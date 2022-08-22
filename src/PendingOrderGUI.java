import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.sql.Connection;


public class PendingOrderGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	
	private static JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private static JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private static JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private static JButton usersListBtn = new JButton("USERS LIST");
	private static JButton logoutBtn = new JButton("LOGOUT");
	
	
	PendingOrderGUI(){
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
//		
		pendingOrderBtn.setBounds(100,45,175,25);
		pendingOrderBtn.addActionListener(this);
		pendingOrderBtn.setBackground(Color.LIGHT_GRAY);
		panel.add(pendingOrderBtn);
		
		orderHistoryBtn.setBounds(350, 45,175,25);
		orderHistoryBtn.addActionListener(this);
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
		
		// change the data and column names to correspond with database

        
		Connection con = SQLConnect.connect();
		
		String query = "SELECT * from reservation where is_completed = 0;";
		
		String[][] pendingOrders = new String[1000][8];
		
		
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			int count = 0;
			
			while(rs.next()) {
				String id = rs.getString("id");
				String username = rs.getString("username");
				String guestNum = rs.getString("guest_num");
				String checkinDate = rs.getString("checkin_date");
				String checkinTime = rs.getString("checkin_time");
				String checkoutDate = rs.getString("checkout_date");
				String checkoutTime = rs.getString("checkout_time");
				String note = rs.getString("note");
				String[] row = {id, username, guestNum, checkinDate, checkinTime, checkoutDate, checkoutTime, note};
				pendingOrders[count] = row;
				count++;
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}
		
		
		
        
        // Column Names
        String[] columnNames = {"ID", "Username", "No. Of Guests", "Check-in Date", "Check-in Time", "Check-out Date", "Check-out Time", "Note"};
		
		JTable table = new JTable(pendingOrders, columnNames);
//		table.setBounds(100, 500, 900, 400);
		
//		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
//		table.setFillsViewportHeight(true);
		
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(100, 100, 900, 400);
        panel.add(sp);
		
		frame.add(panel);
		frame.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == orderHistoryBtn) {
			frame.dispose();
			OrderHistoryGUI orderHistoryPage = new OrderHistoryGUI();
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
