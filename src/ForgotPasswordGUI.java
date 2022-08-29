import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ForgotPasswordGUI {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	JLabel forgotPwLabel = new JLabel("Forgotten your password? Enter your username and new password!");
	JLabel usernameLabel = new JLabel("Username: ");
	JTextField usernameText = new JTextField();
	JLabel newPwLabel = new JLabel("New Password: ");
	JTextField newPwText = new JTextField();
	JLabel confirmPwLabel = new JLabel("Confirm Password: ");
	JTextField confirmPwText = new JTextField();
	
	JButton resetPwBtn = new JButton("RESET PASSWORD");
	JButton loginBtn = new JButton("LOG-IN");
	JButton logoutBtn = new JButton("LOG-OUT");
	
	
	
	
	
	ForgotPasswordGUI(){
		frame.setSize(550, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		forgotPwLabel.setBounds(10, 20, 400, 25);
		panel.add(forgotPwLabel);
		
		usernameLabel.setBounds(10, 75, 80, 25);
		panel.add(usernameLabel);

		usernameText.setBounds(150, 75, 165, 25);
		Border line = BorderFactory.createLineBorder(Color.black, 1);
		usernameText.setBorder(line);
		panel.add(usernameText);
		
		newPwLabel.setBounds(10, 125, 150, 25);
		panel.add(newPwLabel);

		newPwText.setBounds(150, 125, 165, 25);
		Border line1 = BorderFactory.createLineBorder(Color.black, 1);
		newPwText.setBorder(line1);
		panel.add(newPwText);
		
		confirmPwLabel.setBounds(10, 175, 165, 25);
		panel.add(confirmPwLabel);

		confirmPwText.setBounds(150, 175, 165, 25);
		confirmPwText.setBorder(line);
		panel.add(confirmPwText);
		
		resetPwBtn.setBounds(10, 225, 150, 25);
		panel.add(resetPwBtn);
		
		loginBtn.setBounds(200, 225, 100, 25);
		panel.add(loginBtn);
		
		logoutBtn.setBounds(425, 20, 100, 25);
		panel.add(logoutBtn);
		
		
		
		
		
		
		frame.setVisible(true);

	}

}
