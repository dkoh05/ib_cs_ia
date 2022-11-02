import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ResetPasswordGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	JLabel title = new JLabel("Reset Password");
	JLabel subTitle = new JLabel("Check your email, a token has been sent to your e-mail address.");
	
	JLabel tokenLabel = new JLabel("Token: ");
	JLabel newPwLabel = new JLabel("New Password: ");
	JLabel confirmPwLabel = new JLabel("Confirm Password: ");
	JTextField tokenText = new JTextField();
	JTextField newPwText = new JTextField();
	JTextField confirmPwText = new JTextField();
	JLabel success = new JLabel();

	JButton resetPwBtn = new JButton("RESET PASSWORD");
	JButton loginBtn = new JButton("<- LOGIN");

	String username = "";
	
	Connection conn;

	ResetPasswordGUI(String us, Connection con) {
		conn = con;
		frame.setSize(450, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253 ));
		
		title.setBounds(150, 10, 150, 25);
		subTitle.setBounds(10, 40, 400, 25);
		panel.add(title);
		panel.add(subTitle);
		
		tokenLabel.setBounds(10, 100, 165, 25);
		tokenText.setBounds(150, 100, 165, 25);
		panel.add(tokenText);
		panel.add(tokenLabel);
		
		newPwLabel.setBounds(10, 150, 165, 25);
		newPwText.setBounds(150, 150, 165, 25);
		panel.add(newPwLabel);
		panel.add(newPwText);
		
		confirmPwLabel.setBounds(10, 200, 165, 25);
		confirmPwText.setBounds(150, 200, 165, 25);
		panel.add(confirmPwLabel);
		panel.add(confirmPwText);
		
		resetPwBtn.setBounds(10, 250, 150, 25);
		resetPwBtn.addActionListener(this);
		loginBtn.setBounds(200, 250, 125, 25);
		loginBtn.addActionListener(this);
		panel.add(resetPwBtn);
		panel.add(loginBtn);
		
		success.setBounds(10, 285, 200, 25);
		panel.add(success);
		success.setText(null);
		
		username = us;
		
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loginBtn) {
			frame.dispose();
			LoginGUI login = new LoginGUI(conn);
			
		} else if(e.getSource() == resetPwBtn) {
			String token = tokenText.getText();
			String newPw = newPwText.getText();
			String confirmPw = confirmPwText.getText();
			
			if(newPw.length() < 8 || !confirmPw.equals(newPw)) {
				success.setText("Incorrect password input. Try again.");
				return;
			} 
			
			String query = "SELECT username, token from reset_pw_token where username=?";
			
			
			
			try {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, username);
				ResultSet rs = stmt.executeQuery();
				
				
				String tokenDb = ""; // token from the database
				
				while(rs.next()) {
					tokenDb = rs.getString("token");
					if(tokenDb.equals(token)) { // comparing the token from the database to the inputted token
						String updateQuery = "UPDATE user SET password=? WHERE username=?";
						PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
						updateStmt.setString(1, newPw);
						updateStmt.setString(2, username);
						
						int updateCount = updateStmt.executeUpdate();
						if(updateCount == 0) { // internal error
							success.setText("Nothing has been updated; error.");
							return;
						}	
					}
				}
			} catch (Exception e2){
				e2.printStackTrace();
			}
			success.setText("Successful password reset!");
			frame.dispose();
			LoginGUI login = new LoginGUI(conn);
		}
		
	}

}
