import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class OrderHistoryGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	private static JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private static JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private static JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private static JButton usersListBtn = new JButton("USERS LIST");
	private static JButton logoutBtn = new JButton("LOGOUT");
	
	String[][] allOrders = new String[1000][8];
	String[] columnNames = {"ID", "Username", "No. Of Guests", "Check-in Date", "Check-in Time", "Check-out Date",
			"Check-out Time", "Note" };
	JTable table = new JTable(allOrders, columnNames);

	JScrollPane sp = new JScrollPane(table);

	
	
	
	OrderHistoryGUI(){
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		pendingOrderBtn.setBounds(100,45,175,25);
		pendingOrderBtn.addActionListener(this);
		panel.add(pendingOrderBtn);
		
		orderHistoryBtn.setBounds(350, 45,175,25);
		orderHistoryBtn.addActionListener(this);
		orderHistoryBtn.setBackground(Color.LIGHT_GRAY);
		panel.add(orderHistoryBtn);
		
		finInfoBtn.setBounds(600,45,175,25);
		finInfoBtn.addActionListener(this);
		panel.add(finInfoBtn);
		
		usersListBtn.setBounds(850,45,175,25);
		usersListBtn.addActionListener(this);
		panel.add(usersListBtn);
		
		logoutBtn.setBounds(1075, 20, 100, 25);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pendingOrderBtn) {
			frame.dispose();
			PendingOrderGUI pendingOrderPage = new PendingOrderGUI();
		} else if(e.getSource() == finInfoBtn) {
			frame.dispose();
			FinInfoGUI finInfoPage = new FinInfoGUI();
		} else if(e.getSource() == usersListBtn) {
			frame.dispose();
			UsersListGUI usersListPage = new UsersListGUI();
		} else if(e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
		}
	}
}
