import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GetPwTokenGUI implements ActionListener {
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	JLabel title = new JLabel("Forgotten your password? Enter your username and new password!");
	JLabel usernameLabel = new JLabel("Username: ");
	JTextField usernameText = new JTextField();

	JButton getTokenBtn = new JButton("RESET PASSWORD");
	JButton loginBtn = new JButton("LOG-IN");
	String tokenAdd = "";

	JLabel success = new JLabel("");

	GetPwTokenGUI() {
		frame.setSize(450, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		panel.setBackground(new Color(187, 224, 253 ));

		title.setBounds(10, 20, 400, 25);
		panel.add(title);

		usernameLabel.setBounds(10, 75, 80, 25);
		panel.add(usernameLabel);

		usernameText.setBounds(150, 75, 165, 25);
		Border line = BorderFactory.createLineBorder(Color.black, 1);
		usernameText.setBorder(line);
		panel.add(usernameText);

		getTokenBtn.setBounds(10, 125, 150, 25);
		getTokenBtn.addActionListener(this);
		panel.add(getTokenBtn);

		loginBtn.setBounds(200, 125, 100, 25);
		loginBtn.addActionListener(this);
		panel.add(loginBtn);

		success.setBounds(10, 175, 200, 25);
		panel.add(success);

		frame.setVisible(true);

	}

	public String getRandomToken() {
		int leftLimit = 97;
		int rightLimit = 122;
		int targetStringLength = 6;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomInt);
		}
		String generatedString = buffer.toString();

		return generatedString;
	}

	public void sendEmail(String emailTo, String token, String username) {
		String emailFrom = "noreplyhomebooking@gmail.com";
		String pw = System.getenv("EMAIL_PASSWORD");

		String host = "smtp.gmail.com";
		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailFrom, pw);
			}
		};
		Session session = Session.getInstance(props, auth);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setSubject("Reset Password: Token");
			message.setText("Hello, your token needed to reset your password is: " + token);

			Transport.send(message);
			System.out.println("Sent message successfully!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String email = "";
		String token = getRandomToken();

		if (e.getSource() == loginBtn) {
			frame.dispose();
			LoginGUI loginPage = new LoginGUI();
		} else if (e.getSource() == getTokenBtn) {
			String username = usernameText.getText();
			if (username.length() < 3) {
				success.setText("Username is not long enough!");
				return;
			}

			// checks if the username is in the database
			String selectQuery = "SELECT username, email_address FROM user where username=?";
			String insertQuery = "INSERT INTO reset_pw_token (username, token) values (?, ?)";

			Connection con = SQLConnect.connect();

			try {
				PreparedStatement selectStmt = con.prepareStatement(selectQuery);
				selectStmt.setString(1, username);
				ResultSet rs = selectStmt.executeQuery();

				if (rs.next()) {
					email = rs.getString("email_address");

				} else {
					success.setText("Username doesn't exist!");
					return;
				}

				PreparedStatement insertStmt = con.prepareStatement(insertQuery);
				insertStmt.setString(1, username);
				insertStmt.setString(2, token);

				int rowCount = insertStmt.executeUpdate();
				if (rowCount == 0) {
					success.setText("Execute update error");
					return;
				}
			} catch (Exception e2) {
				e2.printStackTrace();

			}

			// send the email
			sendEmail(email, token, username);
			try {
				con.close();
			} catch (Exception e3){
				e3.printStackTrace();
			}
			frame.dispose();
			ResetPasswordGUI resetPwPage = new ResetPasswordGUI(username);

		}

	}

}
