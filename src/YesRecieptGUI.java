import javax.swing.JFrame;
import javax.swing.JPanel;

public class YesRecieptGUI {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	YesRecieptGUI(){
		frame.setSize(450, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
	}

}
