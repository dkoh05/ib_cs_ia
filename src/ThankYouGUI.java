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


public class ThankYouGUI implements ActionListener {
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	public JButton logoutBtn = new JButton("Logout");
	JLabel title = new JLabel("You have successfully made a reservation. We hope you enjoy your stay!");
	JButton makeBkgBtn = new JButton("Make Another Reservation!");
	
	String username = "";
	
	ThankYouGUI(String us){
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		username = us;
		
		title.setBounds(400, 475, 500, 20);
		panel.add(title);
		

		makeBkgBtn.setBounds(475, 500, 250, 25);
		makeBkgBtn.addActionListener((ActionListener) this);
		panel.add(makeBkgBtn);
		
		logoutBtn.setBounds(1080, 25, 100, 25);
		logoutBtn.addActionListener((ActionListener) this);
		panel.add(logoutBtn);
		
		try {
			// tick icon 
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
		if(e.getSource() == logoutBtn) { // user click 'logout' button
			frame.dispose();
			LoginGUI loginPage = new LoginGUI(); 
			// close welcome page and open login page
		} else if (e.getSource() == makeBkgBtn) { 
			// close welcome page and open reservation page
			frame.dispose();
			ReservationGUI reservationPage = new ReservationGUI(username);
		}
	}
}
