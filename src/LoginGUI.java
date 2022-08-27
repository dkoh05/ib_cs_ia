import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class LoginGUI implements ActionListener {
	JLabel welcomeLabel = new JLabel("Welcome to Duyen's Home Booking! Please log-in to your account!");
	private static JLabel usernameLabel = new JLabel("Username:");
	JTextField userText = new JTextField(20);
	JLabel passwordLabel = new JLabel("Password:");
	JTextField passwordText = new JPasswordField(20);
	JButton loginBtn = new JButton("Login");
	JLabel success = new JLabel("");
	JButton registerBtn = new JButton("Register");
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	LoginGUI() {

		frame.setSize(450, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);
		
		welcomeLabel.setBounds(10, 5, 450, 25);
		panel.add(welcomeLabel);

		usernameLabel.setBounds(10, 50, 80, 25);
		panel.add(usernameLabel);

		userText.setBounds(100, 50, 165, 25);
		Border line = BorderFactory.createLineBorder(Color.black, 1);
		userText.setBorder(line);
		panel.add(userText);

		passwordLabel.setBounds(10, 90, 80, 25);
		panel.add(passwordLabel);

		passwordText.setBounds(100, 90, 165, 25);
		passwordText.setBorder(line);
		panel.add(passwordText);

		loginBtn.setBounds(10, 140, 100, 25);
		loginBtn.addActionListener(this);
		panel.add(loginBtn);
//		
		registerBtn = new JButton("Register");
		registerBtn.setBounds(130,140,100,25);
		registerBtn.addActionListener(this);
		panel.add(registerBtn);

		success.setBounds(10, 175, 450, 25);
		panel.add(success);
		success.setText(null);
		
		frame.getRootPane().setDefaultButton(loginBtn);

		frame.setVisible(true);
	}

	@Override
	// validation
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == loginBtn) { // user click 'login' button
			// get the username and password from the textbox
			String username = userText.getText().toLowerCase();
			String password = passwordText.getText();

			// username length check
			if (username.length() < 3) {
				success.setText("Username is not long enough!"); 
				return;
			}
			// password length check
			if (password.length() < 8) {
				success.setText("Password is not long enough!");
				return;
			}
			

			String query = "SELECT username, password, role FROM user WHERE username=?"; // query to match inputted username with username in database

			Connection con = SQLConnect.connect(); // connect to database
			try {
				PreparedStatement stmt = con.prepareStatement(query); // create a statement that contains query
				stmt.setString(1, username); // parameter for username
				ResultSet rs = stmt.executeQuery(); // execute the query and return the result 

				int count = 0; // counter for number for records in the rs

				String pass = "";
				String role = "";
				while (rs.next()) {
					pass = rs.getString("password"); // gets the password in the query
					role = rs.getString("role");
					count++;
				}
				if (count != 0 && pass.equals(password)) { // presense checking
					if(role.equals("guest")) {
						frame.dispose();
						WelcomePage welcomePage = new WelcomePage(username);
					} else if(role.equals("admin")) {
						frame.dispose();
						PendingOrderGUI pendingOrderPage = new PendingOrderGUI();
					}
					success.setText("Successful login!");
				} else {
					success.setText("Failed login! Please try again.");
					return;
				}
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == registerBtn) {
			frame.dispose(); // close the login page
			RegisterGUI signupPage = new RegisterGUI(); // create a register page
			
		} 
		
	}

}
