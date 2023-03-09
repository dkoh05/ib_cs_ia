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
	JLabel title = new JLabel("Welcome to Duyen's Home Booking! Please log-in to your account!");
	JLabel usernameLabel = new JLabel("Username:");
	JTextField userText = new JTextField(20);
	JLabel passwordLabel = new JLabel("Password:");
	JTextField passwordText = new JPasswordField(20);
	JButton loginBtn = new JButton("Login");
	JLabel success = new JLabel("");
	JButton registerBtn = new JButton("Register");
	JButton forgotPwBtn = new JButton("Forgotten Password?");
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	Connection conn;

	LoginGUI(Connection con) {
		conn = con;
		frame.setSize(450, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253 ));
		
		title.setBounds(10, 5, 450, 25);
		panel.add(title);

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
		
		forgotPwBtn.setBounds(250, 140, 175, 25);
		forgotPwBtn.addActionListener(this);
		panel.add(forgotPwBtn);
		

		success.setBounds(10, 175, 450, 25);
		panel.add(success);
		success.setText(null);
		
		frame.getRootPane().setDefaultButton(loginBtn);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loginBtn) { // user click 'login' button
			// obtain username and password from input and store into a variable
			String username = userText.getText().toLowerCase();
			String password = passwordText.getText();

			if (username.length() < 3) { // username length check
				success.setText("Username is not long enough!");
				return;
			}

			if (password.length() < 8) { // password length check
				success.setText("Password is not long enough!");
				return;
			}
			

			String query = "SELECT username, password, role FROM user WHERE username=?"; // query to match inputted username with username in database
			try {
				PreparedStatement stmt = conn.prepareStatement(query); // create a statement that contains query
				stmt.setString(1, username); // parameter for username
				ResultSet rs = stmt.executeQuery(); // execute the query and return the result 
				
				String pass = "";
				String role = "";
				int count = 0; // counter for number for records in the rs
				while (rs.next()) {
					pass = rs.getString("password"); // gets password in query
					role = rs.getString("role"); // gets user role (guest/admin) in query
					count++;
				}
				if (count != 0 && pass.equals(password)) { // presense checking
					if(role.equals("guest")) { // designate guest to welcomePage
						frame.dispose();
						WelcomePage welcomePage = new WelcomePage(username, conn);
					} else if(role.equals("admin")) { // designate admin to pendingOrders page
						frame.dispose();	
						PendingOrderGUI pendingOrderPage = new PendingOrderGUI(conn);
					}
					success.setText("Successful login!");
				} else {
					success.setText("Wrong login! Please try again.");
					return;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == registerBtn) {
			frame.dispose(); // close the login page
			RegisterGUI signupPage = new RegisterGUI(conn); // create a register page
			
		} else if (e.getSource() == forgotPwBtn) { // create a page to reset account password
			frame.dispose();
			GetPwTokenGUI getToken = new GetPwTokenGUI(conn);
		}
		
	}

}
