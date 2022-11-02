import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;


public class OrderHistoryGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
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

	JButton saveBtn = new JButton("SAVE EDITS");
	
	String[][] allOrders = new String[1000][8];
	String[] columnNames = {"ID", "Username", "No. Of Guests", "Check-in Date", "Check-in Time", "Check-out Date",
			"Check-out Time", "Note" };
	JTable table = new JTable(allOrders, columnNames);

	TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
	JScrollPane sp = new JScrollPane(table);
	JLabel searchTitle = new JLabel("Search for a specific value: ");
	JTextField searchBar = new JTextField();

	int index;

	JLabel success = new JLabel();
	Connection conn;

	OrderHistoryGUI(Connection con) {
		conn = con;
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
		panel.add(saveBtn);
		
		searchTitle.setBounds(100, 90, 175, 25);
		panel.add(searchTitle);
		
		searchBar.setBounds(300, 90, 700, 25);
		panel.add(searchBar);

		String query = "SELECT * from reservation where is_completed = 1;";

		try {
			PreparedStatement stmt = conn.prepareStatement(query);
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
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = table.getSelectedRow();
				if (allOrders[index][0] != null) {
					usernameText.setText(allOrders[index][1]);

					String checkinDate = allOrders[index][3];
					LocalDate localCheckinDate = LocalDate.parse(checkinDate);
					checkinDatePicker.setDate(localCheckinDate);
					checkinTimePicker.setText(allOrders[index][4]);

					String checkoutDate = allOrders[index][5];
					LocalDate localCheckoutDate = LocalDate.parse(checkoutDate);
					checkoutDatePicker.setDate(localCheckoutDate);
					checkoutTimePicker.setText(allOrders[index][6]);

					guestNumModel.setValue(Integer.parseInt(allOrders[index][2]));
					noteText.setText(allOrders[index][7]);
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
		
		// searching for a specific value functionality
		table.setRowSorter(rowSorter);

		searchBar.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = searchBar.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = searchBar.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
																				// choose Tools | Templates.
			}

		});

		sp.setBounds(100, 150, 900, 350);
		panel.add(sp);
		
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pendingOrderBtn) {
			frame.dispose();
			PendingOrderGUI pendingOrderPage = new PendingOrderGUI(conn);
		} else if(e.getSource() == finInfoBtn) {
			frame.dispose();
			FinInfoGUI finInfoPage = new FinInfoGUI(conn);
		} else if(e.getSource() == usersListBtn) {
			frame.dispose();
			UsersListGUI usersListPage = new UsersListGUI(conn);
		} else if(e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI(conn);
		} else if(e.getSource() == saveBtn && allOrders[index][0] != null) {
			int id = Integer.parseInt(allOrders[index][0]);
			int guestNum = (Integer) guestNumSpinner.getValue();
			LocalDate checkinDate = checkinDatePicker.getDate();
			String checkinDateString = checkinDate.toString();
			LocalTime checkinTime = checkinTimePicker.getTime();
			String checkinTimeString = checkinTime.toString();
			LocalDate checkoutDate = checkoutDatePicker.getDate();
			String checkoutDateString = checkoutDate.toString();
			LocalTime checkoutTime = checkoutTimePicker.getTime();
			String checkoutTimeString = checkoutTime.toString();
			String note = noteText.getText();
			try {
				String updateQuery = "UPDATE reservation set username = ?,"
						+ "guest_num = ?, checkin_date = ?, checkin_time = ?, "
						+ "checkout_date = ?, checkout_time = ?, note = ? WHERE id=?";

				
				PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
				

				
				updateStmt.setString(1, usernameText.getText());
				updateStmt.setInt(2, guestNum);
				updateStmt.setString(3, checkinDateString);
				updateStmt.setString(4, checkinTimeString);
				updateStmt.setString(5, checkoutDateString);
				updateStmt.setString(6, checkoutTimeString);
				updateStmt.setString(7, note);
				updateStmt.setInt(8, id);
				
				int updateCount = updateStmt.executeUpdate();
				if (updateCount == 0) {
					success.setText("Nothing has been updated!");
					return;
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			success.setText("Your reservation data has been changed.");
			// update pendingOrder so that updated data displays on table for admin

			allOrders[index][2] = Integer.toString(guestNum);
			allOrders[index][3] = checkinDateString;
			allOrders[index][4] = checkinTimeString;
			allOrders[index][5] = checkoutDateString;
			allOrders[index][6] = checkoutTimeString;
			allOrders[index][7] = note;
			table.repaint(); // update data on the UI
		}
	}
}
