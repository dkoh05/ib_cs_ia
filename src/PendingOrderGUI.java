import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.lang.Object;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import java.sql.Connection;

public class PendingOrderGUI implements ActionListener {
	public JFrame frame = new JFrame();
	public JPanel panel = new JPanel();
	

	private JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private JButton usersListBtn = new JButton("USERS LIST");
	private JButton logoutBtn = new JButton("LOGOUT");

	JLabel bookingLabel = new JLabel("Booking Reservation");
	JLabel usernameLabel = new JLabel("Name: ");
	JTextField usernameText = new JTextField();
	
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

	String[][] pendingOrders = new String[1000][8];
	String[] columnNames = { "ID", "Username", "No. Of Guests", "Check-in Date", "Check-in Time", "Check-out Date",
			"Check-out Time", "Note" };
	JTable table = new JTable(pendingOrders, columnNames);

	JScrollPane sp = new JScrollPane(table);
	
	JLabel success = new JLabel();
	
	Connection con = SQLConnect.connect();


	PendingOrderGUI() {
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setBackground(new Color(187, 224, 253 ));
		panel.setLayout(null);
		
//		
		pendingOrderBtn.setBounds(100, 45, 175, 25);
		pendingOrderBtn.addActionListener(this);
		pendingOrderBtn.setBackground(Color.LIGHT_GRAY);
		panel.add(pendingOrderBtn);

		orderHistoryBtn.setBounds(350, 45, 175, 25);
		orderHistoryBtn.addActionListener(this);
		panel.add(orderHistoryBtn);

		finInfoBtn.setBounds(600, 45, 175, 25);
		finInfoBtn.addActionListener(this);
		panel.add(finInfoBtn);

		usersListBtn.setBounds(850, 45, 175, 25);
		usersListBtn.addActionListener(this);
		panel.add(usersListBtn);

		logoutBtn.setBounds(1075, 20, 100, 25);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);

		usernameLabel.setBounds(100, 550, 150, 25);
		usernameText.setBounds(150, 550, 150, 25);
		panel.add(usernameLabel);
		panel.add(usernameText);

		guestNumLabel.setBounds(100, 600, 150, 25);
		panel.add(guestNumLabel);

		guestNumSpinner.setBounds(250, 600, 50, 25);
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
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		completedBtn.addActionListener(this);
		panel.add(saveBtn);
		panel.add(cancelBtn);
		panel.add(completedBtn);
		
		success.setBounds(100, 725, 150, 25);
		panel.add(success);

		// change the data and column names to correspond with database

		String query = "SELECT * from reservation where is_completed = 0;";

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
				pendingOrders[count] = row;
				count++;
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				if(pendingOrders[index][0] != null) {
					usernameText.setText(pendingOrders[index][1]);
					
					String checkinDate = pendingOrders[index][3];
					LocalDate localCheckinDate = LocalDate.parse(checkinDate);
					checkinDatePicker.setDate(localCheckinDate);
					checkinTimePicker.setText(pendingOrders[index][4]);
					
					String checkoutDate = pendingOrders[index][5];
					LocalDate localCheckoutDate = LocalDate.parse(checkoutDate);
					checkoutDatePicker.setDate(localCheckoutDate);
					checkoutTimePicker.setText(pendingOrders[index][6]);
					
					guestNumModel.setValue(Integer.parseInt(pendingOrders[index][2]));
					noteText.setText(pendingOrders[index][7]);
					usernameText.setEditable(false);
				} else {
					usernameText.setText(null);
					checkinDatePicker.setDate(null);
					checkinTimePicker.setText(null);
					
					checkoutDatePicker.setDate(null);
					checkoutTimePicker.setText(null);
					
					guestNumModel.setValue(1);
					noteText.setText(null);
					usernameText.setEditable(false);
					
				}
			}
		});

		sp.setBounds(100, 100, 900, 400);
		panel.add(sp);
		
		frame.add(panel);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("hello world" + e.getSource());
		if (e.getSource() == orderHistoryBtn) {
			frame.dispose();
			OrderHistoryGUI orderHistoryPage = new OrderHistoryGUI();
		} else if (e.getSource() == finInfoBtn) {
			frame.dispose();
			FinInfoGUI finInfoPage = new FinInfoGUI();
		} else if (e.getSource() == usersListBtn) {
			frame.dispose();
			UsersListGUI usersListPage = new UsersListGUI();
		} else if (e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
		} else if(e.getSource() == saveBtn) {
			System.out.println("print me");
			
			String updateQuery = "UPDATE reservation set username = ?,"
					+ "guest_num = ?, checkin_date = ?, checkin_time = ?, "
					+ "checkout_date = ?, checkout_time = ?, note = ? WHERE id=?";
			
			try {
				PreparedStatement updateStmt = con.prepareStatement(updateQuery);
				updateStmt.setString(1, usernameText.getText());
				updateStmt.setInt(2,(Integer) guestNumModel.getValue());
				updateStmt.setString(3, checkinDatePicker.getText());
				updateStmt.setString(4, checkinTimePicker.getText());
				updateStmt.setString(5, checkoutDatePicker.getText());
				updateStmt.setString(6, checkoutTimePicker.getText());
				updateStmt.setString(7, noteText.getText());
				
				int updateCount = updateStmt.executeUpdate();
				if(updateCount == 0) {
					success.setText("Nothing has been updated!");
					System.out.println("debug1");
					return;
				} 
				System.out.println("debug2");
				success.setText("You have successfully updated!");
				
			} catch (Exception e2){
				e2.printStackTrace();
			}

		}
		

	}
}
