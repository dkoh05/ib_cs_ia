import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class PendingOrderGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	
	private static JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private static JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private static JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private static JButton usersListBtn = new JButton("USERS LIST");
	private static JButton logoutBtn = new JButton("LOGOUT");
	
	
	PendingOrderGUI(){
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
//		
		pendingOrderBtn.setBounds(100,45,175,25);
		pendingOrderBtn.addActionListener(this);
		pendingOrderBtn.setBackground(Color.LIGHT_GRAY);
		panel.add(pendingOrderBtn);
		
		orderHistoryBtn.setBounds(350, 45,175,25);
		orderHistoryBtn.addActionListener(this);
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
		
		// change the data and column names to correspond with database
        String[][] data = {
                { "Kundan Kumar Jha", "4031", "CSE", "", "", "", "", ""},
                { "Anand Jha", "6014", "IT", "", "", "", "", ""}
            };
        
        
        // Column Names
        String[] columnNames = {"Name", "No. Of Guests", "Check-in Date", "Check-in Time", "Check-out Date", "Check-out Time", "Note", "Username"};
		
		JTable table = new JTable(data, columnNames);
//		table.setBounds(100, 500, 900, 400);
		
//		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
//		table.setFillsViewportHeight(true);
		
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(100, 100, 900, 400);
        panel.add(sp);
		
		frame.add(panel);
		frame.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == orderHistoryBtn) {
			frame.dispose();
			OrderHistoryGUI orderHistoryPage = new OrderHistoryGUI();
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
		
		String query = "";
		
		
	}
}
