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

import java.util.*;

public class ReservationGUI implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
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


	
	
	ReservationGUI(String us){
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		logoutBtn.setBounds(1080, 25, 100, 25);
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
		checkinDatePicker.setBounds(200, 100, 165,25);
		panel.add(checkinDateLabel);
		panel.add(checkinDatePicker);
		
		checkinTimeLabel.setBounds(10, 150, 100, 25);
		checkinTimePicker.setBounds(200, 150, 165,25);
		panel.add(checkinTimeLabel);
		panel.add(checkinTimePicker);
		
		checkoutDateLabel.setBounds(10, 200, 100, 25);
		checkoutDatePicker.setBounds(200, 200, 165,25);
		panel.add(checkoutDateLabel);
		panel.add(checkoutDatePicker);
		
		checkoutTimeLabel.setBounds(10, 250, 100, 25);
		checkoutTimePicker.setBounds(200, 250, 165,25);
		panel.add(checkoutTimeLabel);
		panel.add(checkoutTimePicker);


		noteLabel.setBounds(10, 300, 150, 25);
		noteText.setBounds(200, 300, 165, 25);
		noteText.setBorder(line);
		panel.add(noteLabel);
		panel.add(noteText);
		
		bookBtn.setBounds(10,350,165,25);
		bookBtn.addActionListener(this);
		panel.add(bookBtn);
		
		success.setBounds(10, 400, 700, 25);
		panel.add(success);
		
		username = us;
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bookBtn) {
			// get values from user
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
			
			// check number of guests
			if(guestNum<1 || guestNum > 10) {
				success.setText("Only a number between 1 to 10 guests allowed!");
				return;
			}
			// validate if check-in is greater than check-out time 
			if((checkoutDate.compareTo(checkinDate)) < 0) {
				success.setText("Check-in date is greater than the check-out date!");
				return;
			}
			if(checkinTime.compareTo(LocalTime.parse("14:00:00")) < 0) {
				success.setText("Check-in time is greater than the check-out time");
				return;
			}
			if(checkoutTime.compareTo(LocalTime.parse("12:00:00")) > 0) {
				success.setText("Check-out time is greater than the check-in time");
				return;
			}
			
			String insertQuery = "INSERT INTO reservation (guest_num, checkin_date, checkin_time, checkout_date, checkout_time, note, username, is_completed) "
					+ "values (?,?,?,?,?,?,?,?)";
			
			
			Connection con = SQLConnect.connect();
			String query = "select * from reservation where ? BETWEEN checkin_date AND checkout_date OR ? BETWEEN checkin_date AND checkout_date "
					+ "OR ? > checkin_date AND ? < checkout_date "
					+ "OR ? < checkin_date AND ? > checkout_date;";
			
			try {
				// check if booking is available/not overlap with other booking
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, checkoutDateString);
				stmt.setString(2, checkinDateString);
				stmt.setString(3, checkinDateString);
				stmt.setString(4, checkoutDateString);
				stmt.setString(5, checkinDateString);
				stmt.setString(6, checkoutDateString);
				
				ResultSet rs = stmt.executeQuery();
				
				// check if there is an existing reservation
				int count = 0;
				while(rs.next()) {
					count++;
				}
				if(count !=0) {
					success.setText("An existing reservation is overlapping with the inputted reservation!");
					return;
				}
				// insert
				PreparedStatement insertStmt = con.prepareStatement(insertQuery);
				insertStmt.setInt(1, guestNum);
				insertStmt.setString(2, checkinDateString);
				insertStmt.setString(3, checkinTimeString);
				insertStmt.setString(4, checkoutDateString);
				insertStmt.setString(5, checkoutTimeString);
				insertStmt.setString(6, note);
				insertStmt.setString(7, username);
				insertStmt.setInt(8, 0);
				int rowCount = insertStmt.executeUpdate();
				if(rowCount ==0) {
					success.setText("execute update error");
					return;
				}
				
			}
			catch (Exception e3){
				e3.printStackTrace();
			}
			success.setText("Successful reservation!");
			// opening a frame asking user if they want reciept
			Object[] options = { "Yes, please", "No, thanks"};
			int popup = JOptionPane.showOptionDialog(frame, "Would you like a reciept of your reservation? ",
					"", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[1]);
			if(popup == JOptionPane.YES_OPTION) {
				// send email
			} 
			frame.dispose();
			ThankYouGUI thankyou_page = new ThankYouGUI(username);
		} else if (e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
		}
		
		
	}

}
