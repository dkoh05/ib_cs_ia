import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;


public class FinInfoGUI implements ActionListener{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	private JButton pendingOrderBtn = new JButton("PENDING ORDERS");
	private JButton orderHistoryBtn = new JButton("ORDER HISTORY");
	private JButton finInfoBtn = new JButton("FINANCIAL INFO.");
	private JButton usersListBtn = new JButton("USERS LIST");
	private JButton logoutBtn = new JButton("LOGOUT");
	
	JLabel currentPriceLabel = new JLabel("Current price of one reservation: ");
	JLabel rentLabel = new JLabel("Rent: ");
	JLabel utilitiesLabel = new JLabel("Utilities (water/electricity): ");
	JLabel gardeningLabel = new JLabel("Gardening costs: ");
	JLabel cleaningLabel = new JLabel("Cleaning costs: ");
	
	JButton saveBtn = new JButton("SAVE EDITS");
	
	JLabel totalRevLabel = new JLabel("Total Revenue: ");
	JLabel totalCostLabel = new JLabel("Total Costs: ");
	JLabel totalProfitLabel = new JLabel("Total Profit: ");
	
	JTextField currentPriceText = new JTextField();
	JTextField rentText = new JTextField();
	JTextField utilitiesText = new JTextField();
	JTextField gardeningText = new JTextField();
	JTextField cleaningText = new JTextField();
	
	JTextField totalRevText = new JTextField();
	JTextField totalCostText = new JTextField();
	JTextField totalProfitText = new JTextField();
	
	JLabel success = new JLabel("");
	
	Connection conn;
	
	FinInfoGUI(Connection con) { 
		conn = con;
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253 ));
		
		pendingOrderBtn.setBounds(100,45,175,25);
		pendingOrderBtn.addActionListener(this);
		panel.add(pendingOrderBtn);
		
		orderHistoryBtn.setBounds(350, 45,175,25);
		orderHistoryBtn.addActionListener(this);
		panel.add(orderHistoryBtn);
		
		finInfoBtn.setBounds(600,45,175,25);
		finInfoBtn.addActionListener(this);
		finInfoBtn.setBackground(Color.LIGHT_GRAY);
		panel.add(finInfoBtn);
		
		usersListBtn.setBounds(850,45,175,25);
		usersListBtn.addActionListener(this);
		panel.add(usersListBtn);
		
		logoutBtn.setBounds(1075, 20, 100, 25);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		currentPriceLabel.setBounds(400, 95, 350, 25);
		currentPriceText.setBounds(600, 95, 175, 25 );
		panel.add(currentPriceLabel);
		panel.add(currentPriceText);
		
		rentLabel.setBounds(200, 150, 175, 25);
		rentText.setBounds(350, 150, 175, 25);
		panel.add(rentLabel);
		panel.add(rentText);
		
		
		utilitiesLabel.setBounds(575, 150, 175, 25);
		utilitiesText.setBounds(750, 150, 175, 25);
		panel.add(utilitiesLabel);
		panel.add(utilitiesText);
		
		gardeningLabel.setBounds(200, 200, 175, 25);
		gardeningText.setBounds(350, 200, 175, 25);
		panel.add(gardeningLabel);
		panel.add(gardeningText);
		
		cleaningLabel.setBounds(575, 200, 175, 25);
		cleaningText.setBounds(750, 200, 175, 25);
		panel.add(cleaningLabel);
		panel.add(cleaningText);
		
		totalRevLabel.setBounds(400, 400, 175, 25);
		totalRevText.setBounds(600, 400, 175, 25);
		totalRevText.setEditable(false);
		panel.add(totalRevLabel);
		panel.add(totalRevText);
		
		totalCostLabel.setBounds(400, 450, 175, 25);
		totalCostText.setBounds(600, 450, 175, 25);
		totalCostText.setEditable(false);
		panel.add(totalCostLabel);
		panel.add(totalCostText);
		
		totalProfitLabel.setBounds(400, 500, 175, 25);
		totalProfitText.setBounds(600, 500, 175, 25);
		totalProfitText.setEditable(false);
		panel.add(totalProfitLabel);
		panel.add(totalProfitText);
		
		
		saveBtn.setBounds(500, 300, 175, 25);
		saveBtn.addActionListener(this);
		panel.add(saveBtn);
		
		success.setBounds(400, 350, 400, 25);
		panel.add(success);
		
	    
	    // display current prices on the page
	    String displayQuery = "SELECT * from financial;";
	    try {
	    	PreparedStatement displayStmt = conn.prepareStatement(displayQuery);
			ResultSet rs = displayStmt.executeQuery();
			currentPriceText.setText(String.valueOf(rs.getInt("price")));
			rentText.setText(String.valueOf(rs.getInt("rent_cost")));
			utilitiesText.setText(String.valueOf(rs.getInt("utilities_cost")));
			gardeningText.setText(String.valueOf(rs.getInt("gardening_cost")));
			cleaningText.setText(String.valueOf(rs.getInt("cleaning_cost")));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    currentPriceText.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		success.setText("");
	    		
	    	}
	    });
	    rentText.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		success.setText("");
	    		
	    	}
	    });
	    utilitiesText.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		success.setText("");
	    		
	    	}
	    });
	    gardeningText.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		success.setText("");
	    		
	    	}
	    });
	    cleaningText.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		success.setText("");
	    		
	    	}
	    });
		frame.setVisible(true);
		
		// calculate revenue. costs and profit
		int totalRev = 0, totalCost = 0, totalProfit = 0;
		
		String revSumQuery = "select sum(total_price) from reservation where is_completed = 1;";
		try {
			PreparedStatement sumStmt = conn.prepareStatement(revSumQuery);
			ResultSet rs = sumStmt.executeQuery();
			rs.next();
			totalRev = rs.getInt(1);
			totalRevText.setText(String.valueOf(totalRev));
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		
		String costSumQuery = "select sum(total_cost) from reservation where is_completed = 1;";
		try {
			PreparedStatement sumStmt = conn.prepareStatement(costSumQuery);
			ResultSet rs = sumStmt.executeQuery();
			rs.next();
			totalCost = rs.getInt(1);
			totalCostText.setText(String.valueOf(totalCost));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		

		totalProfit = totalRev - totalCost;
		totalProfitText.setText(String.valueOf(totalProfit));
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pendingOrderBtn) {
			frame.dispose();
			PendingOrderGUI pendingOrderPage = new PendingOrderGUI(conn);
		} else if(e.getSource() == orderHistoryBtn) {
			frame.dispose();
			OrderHistoryGUI orderHistoryPage = new OrderHistoryGUI(conn);
		} else if(e.getSource() == usersListBtn) {
			frame.dispose();
			UsersListGUI usersListPage = new UsersListGUI(conn);
		} else if(e.getSource() == logoutBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI(conn);
		} else if (e.getSource() == saveBtn) {
			int currentPrice = 0, rent=0, utilities=0, gardening=0, cleaning=0;
			try {
				currentPrice = Integer.parseInt(currentPriceText.getText());
				rent = Integer.parseInt(rentText.getText());
				utilities = Integer.parseInt(utilitiesText.getText());
				gardening = Integer.parseInt(gardeningText.getText());
				cleaning = Integer.parseInt(cleaningText.getText());
			} catch (Exception e4){
				success.setText("Invalid number! ");
				return;
			}

			try {
				String updateQuery = "UPDATE financial set price = ?, rent_cost=?, utilities_cost=?, gardening_cost=?, "
						+ "cleaning_cost=? where id = 1;";

				PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
				updateStmt.setInt(1, currentPrice);
				updateStmt.setInt(2, rent);
				updateStmt.setInt(3, utilities);
				updateStmt.setInt(4, gardening);
				updateStmt.setInt(5, cleaning);

				int updateCount = updateStmt.executeUpdate();
				if (updateCount == 0) {
					success.setText("Nothing has been updated!");
					return;
				}

			} catch (Exception e5) {
				e5.printStackTrace();
			}
			success.setText("You have successfully changed prices!");

		}
	}
}

// pick all total_price values where it's is_completed = 1
// 






