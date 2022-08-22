import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import java.sql.Connection;


public class PendingOrderGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	
	private static JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private static JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private static JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private static JButton usersListBtn = new JButton("USERS LIST");
	private static JButton logoutBtn = new JButton("LOGOUT");
	

	JLabel bookingLabel = new JLabel("Booking Reservation");
	JLabel guestNameLabel = new JLabel("Name: ");
	JTextField guestNameText = new JTextField();
	JLabel guestNumLabel = new JLabel("Number of Guests: ");
	SpinnerModel guestNumModel = new SpinnerNumberModel(1, 1, 10, 1);    
	JSpinner guestNumSpinner = new JSpinner(guestNumModel);
	
	JLabel checkinDateLabel = new JLabel("Check-in Date: ");
	DatePicker checkinDatePicker = new DatePicker();
	JLabel checkinTimeLabel = new JLabel("Check-in Time: ");
	TimePicker checkinTimePicker = new TimePicker();
	
	JLabel checkoutDateLabel = new JLabel("Check-out Date: ");
	DatePicker checkoutDatePicker = new DatePicker();
	JLabel checkoutTimeLabel = new JLabel("Check-out Time: ");
	TimePicker checkoutTimePicker = new TimePicker();
	
	JLabel noteLabel = new JLabel("Special Requests: ");
	JTextField noteText = new JTextField();
	
	JButton saveBtn = new JButton("SAVE");
	JButton cancelBtn = new JButton("CANCEL ORDER"); // cencel order
	JButton completedBtn = new JButton("COMPLETE ORDER"); // click when an order is completed
	
	
	PendingOrderGUI(){
		frame.setSize(1200, 800);
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
		
		guestNameLabel.setBounds(100, 550, 150, 25);
		guestNameText.setBounds(150, 550, 150, 25 );
		panel.add(guestNameLabel);
		panel.add(guestNameText);
		
		guestNumLabel.setBounds(100, 600, 150, 25);
		
		panel.add(guestNumLabel);

		guestNumSpinner.setBounds(250, 600, 50 , 25);
		Border line = BorderFactory.createLineBorder(Color.black, 1);
		guestNumSpinner.setBorder(line);
		panel.add(guestNumSpinner);
		
		checkinDateLabel.setBounds(350, 550, 150, 25);
		checkinDatePicker.setBounds(450, 550, 165, 25);
		panel.add(checkinDateLabel);
		panel.add(checkinDatePicker);
		
		checkinTimeLabel.setBounds(350, 600, 150, 25);
		checkinTimePicker.setBounds(450, 600, 150, 25);
		panel.add(checkinTimeLabel);
		panel.add(checkinTimePicker);
		
		checkoutDateLabel.setBounds(700, 550, 150, 25);
		checkoutDatePicker.setBounds(800, 550, 165, 25);
		panel.add(checkoutDateLabel);
		panel.add(checkoutDatePicker);
		
		checkoutTimeLabel.setBounds(700, 600, 150, 25);
		checkoutTimePicker.setBounds(800, 600, 150, 25);
		panel.add(checkoutTimeLabel);
		panel.add(checkoutTimePicker);
		
		noteLabel.setBounds(100, 650, 150, 25);
		noteText.setBounds(225, 650, 750, 25);
		panel.add(noteLabel);
		panel.add(noteText);
		
		saveBtn.setBounds(100, 700, 150, 25);
		cancelBtn.setBounds(300, 700, 150, 25);
		completedBtn.setBounds(500, 700, 150, 25);
		panel.add(saveBtn);
		panel.add(cancelBtn);
		panel.add(completedBtn);
		
		
		
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
