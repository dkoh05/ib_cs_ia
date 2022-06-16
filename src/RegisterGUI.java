import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class RegisterGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JLabel signupLabel = new JLabel("Sign-up");
	JLabel usernameLabel = new JLabel("Username: ");
	JTextField usernameText = new JTextField();
	JLabel passwordLabel = new JLabel("Password: ");
	JTextField passwordText = new JPasswordField();
	JLabel confirmPasswordLabel = new JLabel("Confirm Password: ");
	JTextField confirmPasswordText = new JPasswordField();
	JLabel fullNameLabel = new JLabel("Full Name: ");
	JTextField fullNameText = new JTextField();
	JLabel phoneNumLabel = new JLabel("Phone Number: ");
	JTextField phoneNumText = new JTextField();
	JLabel emailLabel = new JLabel("E-mail address: ");
	JTextField emailText = new JTextField();
	JButton signupBtn = new JButton("Sign-up");
	JLabel success = new JLabel("");
	
	RegisterGUI(){
		frame.setSize(450, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		signupLabel.setBounds(10, 20, 80, 25);
		panel.add(signupLabel);
		
		usernameLabel.setBounds(10, 50, 80, 25);
		panel.add(usernameLabel);

		usernameText.setBounds(100, 50, 165, 25);
		Border line = BorderFactory.createLineBorder(Color.black, 1);
		usernameText.setBorder(line);
		panel.add(usernameText);

		passwordLabel.setBounds(10, 90, 80, 25);
		panel.add(passwordLabel);

		passwordText.setBounds(100, 90, 165, 25);
		passwordText.setBorder(line);
		panel.add(passwordText);
		 //
		confirmPasswordLabel.setBounds(10, 130, 80, 25);
		panel.add(confirmPasswordLabel);

		confirmPasswordText.setBounds(100, 130, 165, 25);
		confirmPasswordText.setBorder(line);
		panel.add(confirmPasswordText);

		fullNameLabel.setBounds(10, 170, 80, 25);
		panel.add(fullNameLabel);

		fullNameText.setBounds(100, 170, 165, 25);
		fullNameText.setBorder(line);
		panel.add(fullNameText);
		//
		phoneNumLabel.setBounds(10, 210, 80, 25);
		panel.add(phoneNumLabel);

		phoneNumText.setBounds(100, 210, 165, 25);
		phoneNumText.setBorder(line);
		panel.add(phoneNumText);

		emailLabel.setBounds(10, 250, 80, 25);
		panel.add(emailLabel);

		emailText.setBounds(100, 250, 165, 25);
		emailText.setBorder(line);
		panel.add(emailText);
		
		signupBtn.setBounds(10, 290, 100, 25);
		signupBtn.addActionListener(this);
		panel.add(signupBtn);
		
		success.setBounds(10, 320, 300, 25);
		panel.add(success);
		success.setText(null);
		
		frame.getRootPane().setDefaultButton(signupBtn);

		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == signupBtn) {
			// get input from textbook 
			String username = usernameText.getText().toLowerCase();
			String password = passwordText.getText();
			String confirmPassword = confirmPasswordText.getText();
			String fullName = fullNameText.getText().toUpperCase();
			String phoneNum = phoneNumText.getText();
			String email = emailText.getText();
			
// validation on each text field
			// username/password length check
			if (username.length() < 3 || password.length() < 8) {
				success.setText("Username/password not long enough");
				return;
			}
			// confirming password
			if (!confirmPassword.equals(password)) {
				success.setText("Your confirmation password doesn't match with your password.");
				return;
			}
			if(fullName.length() <= 0) {
				success.setText("No name");
				return;
			}
			// confirm phone number
			if (phoneNum.length() != 10) {
				success.setText("invalid phone number length");
				return;
			}
			
			for(int i = 0;i<10;i++) {
				if(phoneNum.charAt(i) < '0' || phoneNum.charAt(i) > '9') {
					success.setText("invalid phone number");
					return;
				}
			}

			// confirm e-mail address
			String emailPattern = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
			Pattern pattern = Pattern.compile(emailPattern);
			
			boolean isValid = pattern.matcher(email).matches();
			if(!isValid) {
				success.setText("invalid email format");
				return;
			} 
			
			String query = "SELECT username, password FROM user WHERE username=?"; 
			String insertQuery = "INSERT INTO user values (?,?,?,?,?)";
			
			
			Connection con = SQLConnect.connect(); // connect to master.db database
			
			try {
				PreparedStatement stmt = con.prepareStatement(query); // create a statement variable to store the query
				stmt.setString(1, username); // replace parameters
				ResultSet rs = stmt.executeQuery(); //  values into into database
				// check if there are duplicate accounts
				int count = 0;
				while(rs.next()) { // retrieve all rows in rs
					count++;
				}
				
				if(count != 0) {
					success.setText("Duplicate user, cannot register");
					return;
				} 
				
				// insert account details into sql database
				PreparedStatement insertStmt = con.prepareStatement(insertQuery);
				insertStmt.setString(1,username);
				insertStmt.setString(2, password);
				insertStmt.setString(3, fullName);
				insertStmt.setString(4, email);
				insertStmt.setString(5, phoneNum);
				int rowCount = insertStmt.executeUpdate(); // execute the insert statement; return either the number of rows inserted successfuly or return zero if nothing inserted
				if(rowCount == 0) { // internal error
					success.setText("execute update error"); 
					return;
				}
				
			} catch(Exception e2) {
				e2.printStackTrace();
			}
			
	
			
			success.setText("Successful sign-up!");
			frame.dispose();
			WelcomePage welcomePage = new WelcomePage(username);
			
			

		}
		
	}
}
