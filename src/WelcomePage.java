
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePage implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JLabel title = new JLabel("You are successfully logged in, welcome! Let's make a reservation.");

	public JButton logoutBtn = new JButton("Logout");
	JButton makeBkgBtn = new JButton("Click here to Make a Reservation");
	
	String username = "";
	
	Connection conn;
	WelcomePage(String us, Connection con) {
		conn = con;
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253));
		
		username = us;

		title.setBounds(200, 475, 500, 20);
		panel.add(title);

		logoutBtn.setBounds(540, 25, 100, 25);
		logoutBtn.addActionListener((ActionListener) this);
		panel.add(logoutBtn);

		makeBkgBtn.setBounds(235, 500, 250, 25);
		makeBkgBtn.addActionListener((ActionListener) this);
		panel.add(makeBkgBtn);

		try {
			// tick icon 
			BufferedImage tickImg = ImageIO.read(new File("src//tick_icon.png"));
			JLabel tickLabel = new JLabel(new ImageIcon(tickImg));
			tickLabel.setBounds(244, 200, 225, 225);
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
		if(e.getSource() == logoutBtn) { // user click 'logout' button
			frame.dispose();
			LoginGUI loginPage = new LoginGUI(conn); 
			// close welcome page and open login page
			
		} else if (e.getSource() == makeBkgBtn) { 
			// close welcome page and open reservation page
			frame.dispose();
			ReservationGUI reservationPage = new ReservationGUI(username, conn);
			
		}

	}

}
