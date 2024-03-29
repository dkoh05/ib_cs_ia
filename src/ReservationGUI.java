import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReservationGUI implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	JLabel title = new JLabel("Reservation Page");

	JButton logoutBtn = new JButton("Logout");
	JLabel bookingLabel = new JLabel("Booking Reservation");
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

	JButton bookBtn = new JButton("Book Reservation");
	JLabel success = new JLabel();

	String username = "";

	Connection conn;

	ReservationGUI(String us, Connection con) {
		conn = con;
		frame.setSize(500, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253));

		title.setBounds(150, 15, 150, 25);
		panel.add(title);

		logoutBtn.setBounds(350, 15, 100, 25);
		logoutBtn.addActionListener((ActionListener) this);
		panel.add(logoutBtn);
		//
		guestNumLabel.setBounds(10, 50, 150, 25);
		panel.add(guestNumLabel);

		guestNumSpinner.setBounds(200, 50, 165, 25);
		Border line = BorderFactory.createLineBorder(Color.black, 1);
		guestNumSpinner.setBorder(line);
		panel.add(guestNumSpinner);

		checkinDateLabel.setBounds(10, 100, 100, 25);
		checkinDatePicker.setBounds(200, 100, 165, 25);
		panel.add(checkinDateLabel);
		panel.add(checkinDatePicker);

		checkinTimeLabel.setBounds(10, 150, 100, 25);
		checkinTimePicker.setBounds(200, 150, 165, 25);
		panel.add(checkinTimeLabel);
		panel.add(checkinTimePicker);

		checkoutDateLabel.setBounds(10, 200, 100, 25);
		checkoutDatePicker.setBounds(200, 200, 165, 25);
		panel.add(checkoutDateLabel);
		panel.add(checkoutDatePicker);

		checkoutTimeLabel.setBounds(10, 250, 100, 25);
		checkoutTimePicker.setBounds(200, 250, 165, 25);
		panel.add(checkoutTimeLabel);
		panel.add(checkoutTimePicker);

		noteLabel.setBounds(10, 300, 150, 25);
		noteText.setBounds(200, 300, 165, 25);
		noteText.setBorder(line);
		panel.add(noteLabel);
		panel.add(noteText);

		bookBtn.setBounds(10, 350, 165, 25);
		bookBtn.addActionListener(this);
		panel.add(bookBtn);

		success.setBounds(10, 375, 700, 25);
		panel.add(success);

		username = us;
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bookBtn) {
			// get values from textbox & store in a variable
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

			// check number of guests
			if (guestNum < 1 || guestNum > 10) {
				success.setText("Only a number between 1 to 10 guests allowed!");
				return;
			}
			// validate if check-in date is greater than check-out date	
			if ((checkoutDate.compareTo(checkinDate)) <= 0) {
				success.setText("Incorrect dates! ");
				return;
			}
			// validate if check-in time is less than check-out time
			if (checkinTime.compareTo(LocalTime.parse("14:00:00")) < 0) {
				success.setText("Check-in time is incorrect. Please enter a time later than 2PM.");
				return;
			} // vice versa to above
			if (checkoutTime.compareTo(LocalTime.parse("12:00:00")) > 0) {
				success.setText("Check-out time is incorrect. Please enter a time earlier than 12PM.");
				return;
			}

			String insertQuery = "INSERT INTO reservation (guest_num, checkin_date, checkin_time, checkout_date, checkout_time, note, username, is_completed, total_price, total_cost) "
					+ "values (?,?,?,?,?,?,?,?,?,?)";

			String reservationQuery = "select * from reservation where ? BETWEEN checkin_date AND checkout_date OR ? BETWEEN checkin_date AND checkout_date "
					+ "OR ? > checkin_date AND ? < checkout_date " + "OR ? < checkin_date AND ? > checkout_date;";

			try {
				// calculate no. of days diff. between checkin and checkout dates
				Duration diff = Duration.between(checkinDate.atStartOfDay(), checkoutDate.atStartOfDay());
				long diffDays = diff.toDays();

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
			} catch (Exception e3) {
				e3.printStackTrace();
			}

			try {
				// check if booking is available/not overlap with other booking
				PreparedStatement stmt = conn.prepareStatement(reservationQuery);
				stmt.setString(1, checkoutDateString);
				stmt.setString(2, checkinDateString);
				stmt.setString(3, checkinDateString);
				stmt.setString(4, checkoutDateString);
				stmt.setString(5, checkinDateString);
				stmt.setString(6, checkoutDateString);

				ResultSet rs = stmt.executeQuery();
				int count = 0;
				while (rs.next()) {
					count++;
				}
				if (count != 0) { // check if there is an existing reservation
					success.setText("An existing reservation is overlapping with the inputted reservation!");
					return;
				}
				// insert inputted values and execute insertQuery
				PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
				insertStmt.setInt(1, guestNum);
				insertStmt.setString(2, checkinDateString);
				insertStmt.setString(3, checkinTimeString);
				insertStmt.setString(4, checkoutDateString);
				insertStmt.setString(5, checkoutTimeString);
				insertStmt.setString(6, note);
				insertStmt.setString(7, username);
				insertStmt.setInt(8, 0);
				insertStmt.setInt(9, reservTotalPrice);
				insertStmt.setInt(10, reservTotalCost);

				int rowCount = insertStmt.executeUpdate();
				if (rowCount == 0) {
					success.setText("execute update error");
					return;
				}

			} catch (Exception e3) {
				e3.printStackTrace();
			}
			success.setText("Successful reservation!");
			frame.dispose();
			ThankYouGUI thankyou_page = new ThankYouGUI(username, conn);

		} else if (e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI(conn);
		}

	}

}
