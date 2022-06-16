import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoRecieptEndpage {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	NoRecieptEndpage(){
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
	}

}
