import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GetPwTokenGUI implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	JLabel forgotPwLabel = new JLabel("Forgotten your password? Enter your username and new password!");
	JLabel usernameLabel = new JLabel("Username: ");
	JTextField usernameText = new JTextField();

	JButton getTokenBtn = new JButton("RESET PASSWORD");
	JButton loginBtn = new JButton("LOG-IN");
	GetPwTokenGUI(){
		frame.setSize(450, 200);
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

		
		getTokenBtn.setBounds(10, 125, 150, 25);
		getTokenBtn.addActionListener(this);
		panel.add(getTokenBtn);
		
		loginBtn.setBounds(200, 125, 100, 25);
		loginBtn.addActionListener(this);
		panel.add(loginBtn);
		
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loginBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
		} else if(e.getSource() == getTokenBtn) {
			frame.dispose();
			ResetPasswordGUI resetPwPage = new ResetPasswordGUI();
			
			
			
		}
		
	}

}
