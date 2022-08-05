import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoRecieptGUI {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	NoRecieptGUI(){
		frame.setSize(450, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
	}
}
