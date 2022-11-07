import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class UsersListGUI implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	private JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private JButton usersListBtn = new JButton("USERS LIST");
	private JButton logoutBtn = new JButton("LOGOUT");
	
	JLabel fullNameLabel = new JLabel("Full Name: ");
	JLabel emailLabel = new JLabel("Email Address: ");
	JLabel phoneNumLabel = new JLabel("Phone Number: ");
	
	JTextField fullNameText = new JTextField();
	JTextField emailText = new JTextField();
	JTextField phoneNumText = new JTextField();
	
	
	String[][] usersList = new String[1000][4];
	String[] columnNames = {"Username", "Full Name", "Email Address", "Phone Number"};
	JTable table = new JTable(usersList, columnNames);

	JScrollPane sp = new JScrollPane(table);
	
	TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
	JLabel searchTitle = new JLabel("Search for a specific value: ");
	JTextField searchBar = new JTextField();

	JButton saveBtn = new JButton("SAVE");
	JLabel success = new JLabel();
	
	int index;
	Connection conn;
	
	UsersListGUI(Connection con) {
		conn = con;
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253 ));

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
		
		searchTitle.setBounds(100, 90, 175, 25);
		panel.add(searchTitle);
		
		searchBar.setBounds(300, 90, 700, 25);
		panel.add(searchBar);
		
		fullNameLabel.setBounds(100, 600, 150, 25);
		fullNameText.setBounds(200, 600, 150, 25);
		panel.add(fullNameLabel);
		panel.add(fullNameText);
		
		emailLabel.setBounds(400, 600, 150, 25);
		emailText.setBounds(500, 600, 150, 25);
		panel.add(emailLabel);
		panel.add(emailText);
		
		phoneNumLabel.setBounds(700, 600, 150, 25);
		phoneNumText.setBounds(800, 600, 150, 25);
		panel.add(phoneNumLabel);
		panel.add(phoneNumText);
		
		saveBtn.setBounds(100, 650, 150, 25);
		saveBtn.addActionListener(this);
		panel.add(saveBtn);
		
		success.setBounds(100, 700, 250, 25);
		panel.add(success);
		
		

		String query = "SELECT * from user;";

		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			int count = 0;

			while (rs.next()) {
				String username = rs.getString("username");
				String fullName = rs.getString("full_name");
				String email = rs.getString("email_address");
				String phoneNum = rs.getString("phone_num");
				
				String[] row = {username, fullName, email, phoneNum};
				usersList[count] = row;
				count++;
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}

		// searching for a specific value functionality
		table.setRowSorter(rowSorter);

		searchBar.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = searchBar.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = searchBar.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
																				// choose Tools | Templates.
			}

		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				index = table.getSelectedRow();
				if (usersList[index][0] != null) {
					fullNameText.setText(usersList[index][1]);
					emailText.setText(usersList[index][2]);
					phoneNumText.setText(usersList[index][3]);
				} else {
					fullNameText.setText(null);
					emailText.setText(null);
					phoneNumText.setText(null);

				}
			}
		});
		sp.setBounds(100, 150, 900, 400);
		panel.add(sp);
		
		
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pendingOrderBtn) {
			frame.dispose();
			PendingOrderGUI pendingOrderPage = new PendingOrderGUI(conn);
		} else if (e.getSource() == orderHistoryBtn) {
			frame.dispose();
			OrderHistoryGUI orderHistoryPage = new OrderHistoryGUI(conn);
		} else if (e.getSource() == finInfoBtn) {
			frame.dispose();
			FinInfoGUI finInfoPage = new FinInfoGUI(conn);
		} else if (e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI(conn);
		} else if(e.getSource() == saveBtn && usersList[index][0]!= null) {
			
			String username = usersList[index][0];
			String fullName = fullNameText.getText();
			String email = emailText.getText();
			String phoneNum = phoneNumText.getText();
			
			try {
				
				String updateQuery = "UPDATE user set full_name = ?, email_address = ?, phone_num = ? where username=?;";	

				PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
				updateStmt.setString(1, fullName);
				updateStmt.setString(2, email);
				updateStmt.setString(3, phoneNum);
				updateStmt.setString(4, username);
				
				int updateCount = updateStmt.executeUpdate();
				if (updateCount == 0) {
					success.setText("Nothing has been updated!");
					return;
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}

			success.setText("Your reservation data has been changed.");
			// update pendingOrder so that updated data displays on table for admin

			usersList[index][1] = fullName;
			usersList[index][2] = email;
			usersList[index][3] = phoneNum;
			table.repaint(); // update data on the UI
		}
	}
}
