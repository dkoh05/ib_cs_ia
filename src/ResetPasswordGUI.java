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

public class ResetPasswordGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	JLabel title = new JLabel("Reset Password");
	JLabel subTitle = new JLabel("Check your email, a token has been sent to your e-mail address.");
	
	JLabel usernameLabel = new JLabel("Username: ");
	JLabel newPwLabel = new JLabel("New Password: ");
	JLabel confirmPwLabel = new JLabel("Confirm Password: ");
	JTextField usernameText = new JTextField();
	JTextField newPwText = new JTextField();
	JTextField confirmPwText = new JTextField();
	JButton resetPwBtn = new JButton("RESET PASSWORD");
	
	
	
	
	ResetPasswordGUI(){
		frame.setSize(450, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		title.setBounds(150, 10, 150, 25);
		subTitle.setBounds(10, 40, 400, 25);
		panel.add(title);
		panel.add(subTitle);
		
		usernameLabel.setBounds(10, 100, 165, 25);
		usernameText.setBounds(150, 100, 165, 25);
		panel.add(usernameText);
		panel.add(usernameLabel);
		
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
		panel.add(resetPwBtn);
		
		frame.setVisible(true);
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
