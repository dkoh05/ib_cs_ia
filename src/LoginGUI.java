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

	private static JLabel usernameLabel = new JLabel("Username:");
	private static JTextField userText = new JTextField(20);
	private static JLabel passwordLabel = new JLabel("Password:");
	private static JTextField passwordText = new JPasswordField(20);
	private static JButton loginBtn = new JButton("Login");
	private static JLabel success = new JLabel("");
	private static JButton registerBtn = new JButton("Register");
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	LoginGUI() {

		frame.setSize(450, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		usernameLabel.setBounds(10, 20, 80, 25);
		panel.add(usernameLabel);

		userText.setBounds(100, 20, 165, 25);
		Border line = BorderFactory.createLineBorder(Color.black, 1);
		userText.setBorder(line);
		panel.add(userText);

		passwordLabel.setBounds(10, 60, 80, 25);
		panel.add(passwordLabel);

		passwordText.setBounds(100, 60, 165, 25);
		passwordText.setBorder(line);
		panel.add(passwordText);

		loginBtn.setBounds(10, 90, 100, 25);
		loginBtn.addActionListener(this);
		panel.add(loginBtn);
//		
		registerBtn = new JButton("Register");
		registerBtn.setBounds(130,90,100,25);
		registerBtn.addActionListener(this);
		panel.add(registerBtn);

		success.setBounds(10, 120, 300, 25);
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
				success.setText("validation failed"); 
				return;
			}
			// password length check
			if (password.length() < 8) {
				success.setText("validation failed");
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
						System.out.println("open admin page");
					}
					success.setText("Login success!");
				} else {
					success.setText("Login failure");
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
