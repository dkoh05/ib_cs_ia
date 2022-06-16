
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePage implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JLabel welcomeLabel = new JLabel("You are successfully logged in, welcome! Let's make a reservation.");

	public JButton logoutBtn = new JButton("Logout");
	JButton makeBkgBtn = new JButton("Click here to Make a Reservation");
	
	String username = "";
	

	WelcomePage(String us) {
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		username = us;

		welcomeLabel.setBounds(400, 475, 500, 20);
		panel.add(welcomeLabel);

		logoutBtn.setBounds(1080, 25, 100, 25);
		logoutBtn.addActionListener((ActionListener) this);
		panel.add(logoutBtn);

		makeBkgBtn.setBounds(475, 500, 250, 25);
		makeBkgBtn.addActionListener((ActionListener) this);
		panel.add(makeBkgBtn);

		try {
			BufferedImage tickImg = ImageIO.read(new File("src//tick_icon.png"));
			JLabel tickLabel = new JLabel(new ImageIcon(tickImg));
			tickLabel.setBounds(488, 200, 225, 225);
			panel.add(tickLabel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
			
		} else if (e.getSource() == makeBkgBtn) {
			frame.dispose();
			ReservationGUI reservationPage = new ReservationGUI(username);
		}

	}

}
