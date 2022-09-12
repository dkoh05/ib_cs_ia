import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UsersListGUI implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	private JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private JButton usersListBtn = new JButton("USERS LIST");
	private JButton logoutBtn = new JButton("LOGOUT");

	UsersListGUI() {
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);

		pendingOrderBtn.setBounds(100, 45, 175, 25);
		pendingOrderBtn.addActionListener(this);
		panel.add(pendingOrderBtn);

		orderHistoryBtn.setBounds(350, 45, 175, 25);
		orderHistoryBtn.addActionListener(this);
		panel.add(orderHistoryBtn);

		finInfoBtn.setBounds(600, 45, 175, 25);
		finInfoBtn.addActionListener(this);
		panel.add(finInfoBtn);

		usersListBtn.setBounds(850, 45, 175, 25);
		usersListBtn.addActionListener(this);
		usersListBtn.setBackground(Color.LIGHT_GRAY);
		panel.add(usersListBtn);

		logoutBtn.setBounds(1075, 20, 100, 25);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pendingOrderBtn) {
			System.out.println("user1");
			frame.dispose();
			PendingOrderGUI pendingOrderPage = new PendingOrderGUI();
		} else if (e.getSource() == orderHistoryBtn) {
			System.out.println("user2");
			frame.dispose();
			OrderHistoryGUI orderHistoryPage = new OrderHistoryGUI();
		} else if (e.getSource() == finInfoBtn) {
			System.out.println("user3");
			frame.dispose();
			FinInfoGUI finInfoPage = new FinInfoGUI();
		} else if (e.getSource() == logoutBtn) {
			System.out.println("user4");
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
		}
	}
}
