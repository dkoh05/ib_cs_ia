	import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
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
import javax.swing.RowFilter;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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

	JButton saveBtn = new JButton("SAVE EDITS");
	JButton cancelBtn = new JButton("CANCEL ORDER"); // cencel order
	JButton completedBtn = new JButton("COMPLETE ORDER"); // click when an order is completed

	String[][] pendingOrders = new String[1000][10];
	String[] columnNames = { "ID", "Username", "No. Of Guests", "Check-in Date", "Check-in Time", "Check-out Date",
			"Check-out Time", "Note", "Total Price", "Total Cost" };
	JTable table = new JTable(pendingOrders, columnNames);
	
	TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
	JScrollPane sp = new JScrollPane(table);
	JLabel searchTitle = new JLabel("Search for a specific value: ");
	JTextField searchBar = new JTextField();
	
	int updatedDayDiff = 0;

	JLabel success = new JLabel();

	int index;
	
	Connection conn;

	PendingOrderGUI(Connection con) {
		conn = con;
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setBackground(new Color(187, 224, 253));
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

		success.setBounds(100, 725, 400, 25);
		panel.add(success);
		
		searchTitle.setBounds(100, 90, 175, 25);
		panel.add(searchTitle);
		
		searchBar.setBounds(300, 90, 750, 25);
		panel.add(searchBar);
		

		// change the data and column names to correspond with database
		String query = "SELECT * from reservation where is_completed = 0;";
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
				String totalPrice = rs.getString("total_price");
				String totalCost = rs.getString("total_cost");
				
				String[] row = { id, username, guestNum, checkinDate, checkinTime, checkoutDate, checkoutTime, note, totalPrice, totalCost};
				pendingOrders[count] = row;
				count++;
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = table.getSelectedRow();
				if (pendingOrders[index][0] != null) {
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
				throw new UnsupportedOperationException("Not supported yet."); 
			}

		});
		
		sp.setBounds(100, 150, 950, 350);
		panel.add(sp);

		frame.add(panel);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == orderHistoryBtn) {
			frame.dispose();
			OrderHistoryGUI orderHistoryPage = new OrderHistoryGUI(conn);
		} else if (e.getSource() == finInfoBtn) {
			frame.dispose();
			FinInfoGUI finInfoPage = new FinInfoGUI(conn);
		} else if (e.getSource() == usersListBtn) {
			frame.dispose();
			UsersListGUI usersListPage = new UsersListGUI(conn);
		} else if (e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI(conn);
		} else if (e.getSource() == saveBtn && pendingOrders[index][0]!=null) {
			

			int id = Integer.parseInt(pendingOrders[index][0]);
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
			
			int reservTotalPrice = 0, reservTotalCost = 0;
			try {
				Duration diff = Duration.between(checkinDate.atStartOfDay(), checkoutDate.atStartOfDay());
				long diffDays = diff.toDays();
				System.out.println(diffDays);

				String financialQuery = "select * from financial where id=1;";
				PreparedStatement stmt = conn.prepareStatement(financialQuery);
				ResultSet rs = stmt.executeQuery();

				int reservPrice = rs.getInt("price");
				int reservRentCost = rs.getInt("rent_cost");
				int reservUtilitiesCost = rs.getInt("utilities_cost");
				int reservGardeningCost = rs.getInt("gardening_cost");
				int reservCleaningCost = rs.getInt("cleaning_cost");

				// calculate total price and costs
				reservTotalPrice = (int) (reservPrice * diffDays);
				reservTotalCost = (int) ((reservRentCost + reservUtilitiesCost + reservGardeningCost
						+ reservCleaningCost) * diffDays);
				
				String updateQuery = "UPDATE reservation set username = ?,"
						+ "guest_num = ?, checkin_date = ?, checkin_time = ?, "
						+ "checkout_date = ?, checkout_time = ?, note = ?, total_price = ?, total_cost = ? WHERE id=?";	

				PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
				updateStmt.setString(1, usernameText.getText());
				updateStmt.setInt(2, guestNum);
				updateStmt.setString(3, checkinDateString);
				updateStmt.setString(4, checkinTimeString);
				updateStmt.setString(5, checkoutDateString);
				updateStmt.setString(6, checkoutTimeString);
				updateStmt.setString(7, note);
				updateStmt.setInt(8, reservTotalPrice);
				updateStmt.setInt(9, reservTotalCost);
				updateStmt.setInt(10, id);
				
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

			pendingOrders[index][2] = Integer.toString(guestNum);
			pendingOrders[index][3] = checkinDateString;
			pendingOrders[index][4] = checkinTimeString;
			pendingOrders[index][5] = checkoutDateString;
			pendingOrders[index][6] = checkoutTimeString;
			pendingOrders[index][7] = note;
			pendingOrders[index][8] = Integer.toString(reservTotalPrice);
			pendingOrders[index][9] = Integer.toString(reservTotalCost);
			
			
			table.repaint(); // update data on the UI

		} else if (e.getSource() == cancelBtn && pendingOrders[index][0] != null) {
			int canId = Integer.parseInt(pendingOrders[index][0]);
			try {
				if(pendingOrders[index][0]!= null) {
					String delQuery = "DELETE FROM reservation WHERE id=?";
					PreparedStatement delStmt = conn.prepareStatement(delQuery);
					delStmt.setInt(1, canId);
					
					int delCount = delStmt.executeUpdate();
					if (delCount == 0) {
						success.setText("Nothing has been updated!");
						return;
					}	
				}
			} catch(Exception e2) {
				success.setText("Nothing has been updated.");
				e2.printStackTrace();
			}
			success.setText("You have deleted reservation ID: " + canId);
			// delete row from pendingOrder 2d array
			for (int i = index;i<pendingOrders.length-1;i++) {
				pendingOrders[i] = pendingOrders[i+1];
				if(pendingOrders[i+1][0] == null) {
					break;
				}
			}
			table.repaint();

		} else if (e.getSource() == completedBtn && pendingOrders[index][0]!=null) {
			int compId= Integer.parseInt(pendingOrders[index][0]);
			try {
				String completedQuery = "UPDATE reservation SET is_completed = 1 WHERE id=?";
				PreparedStatement completedStmt = conn.prepareStatement(completedQuery);
				completedStmt.setInt(1, compId);
				
				
				int cmStmtExe = completedStmt.executeUpdate();
				
			} catch (Exception e2){
				e2.printStackTrace();
			}
			success.setText("Order ID " + compId + " has been classified as a completed order.");
			
			for(int i = index;i<pendingOrders.length-1;i++) {
				pendingOrders[i] = pendingOrders[i+1];
				if(pendingOrders[i+1][0] == null) {
					break;
				}
			}
			table.repaint();
			
			
		}
		

	}
}
