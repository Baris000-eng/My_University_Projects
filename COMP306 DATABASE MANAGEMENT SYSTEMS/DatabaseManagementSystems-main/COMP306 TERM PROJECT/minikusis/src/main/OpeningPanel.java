package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import utils.DBUtils;

@SuppressWarnings("serial")
public class OpeningPanel extends JPanel {

	private JLabel userLabel;
	private JButton logInButton;

	private JLabel welcome;

	private JLabel secondLogoImage;
	public JTextField usernameField;
	public JButton login;
	public JPasswordField passwordField;
	public JLabel passwordLabel;
	public JLabel userNameLabel;
	public JButton resetButton;
	public JComboBox userTypes;
	public DBUtils dbutils;


	public OpeningPanel(DBUtils DBconn) {
		super();
		dbutils = DBconn;


		//GUI STUFF//
		usernameField = new JTextField(50);
		login= new JButton();
		passwordField = new JPasswordField();
		passwordLabel = new JLabel();
		userNameLabel = new JLabel();
		resetButton = new JButton();

		welcome= new JLabel();
		welcome.setText("Welcome to KUSIS");
		welcome.setFont(new Font("Serif", Font.PLAIN, 24));

		String[] types= {"Student","Faculty Member","Admin"};
		userTypes=new JComboBox(types);

		ImageIcon logo = new ImageIcon("logo.png");
		Image logoImage = logo.getImage();
		Image logoImageScaled = logoImage.getScaledInstance(UIConstants.widthButton*2, UIConstants.widthButton*2,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		logo = new ImageIcon(logoImageScaled);
		secondLogoImage= new JLabel(logo);

		usernameField.setBounds(UIConstants.middleX+UIConstants.widthButton*1/2,UIConstants.middleY-2*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		passwordField.setBounds(UIConstants.middleX+UIConstants.widthButton*1/2,UIConstants.middleY-UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);

		userNameLabel.setText("Your Username: ");
		userNameLabel.setBounds(UIConstants.middleX-UIConstants.widthButton*1/2,UIConstants.middleY-2*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		passwordLabel.setText("Your Password: ");
		passwordLabel.setBounds(UIConstants.middleX-UIConstants.widthButton*1/2,UIConstants.middleY-UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);

		login.setText("Login");
		login.setBounds(UIConstants.middleX+UIConstants.widthButton*1/2,UIConstants.middleY,UIConstants.widthButton,UIConstants.heightButton);

		userTypes.setBounds(UIConstants.middleX-UIConstants.widthButton*1/2,UIConstants.middleY,UIConstants.widthButton,UIConstants.heightButton);
	
		secondLogoImage.setBounds(UIConstants.width/2-UIConstants.widthButton,UIConstants.middleY-2*UIConstants.heightButton-2*UIConstants.widthButton,2*UIConstants.widthButton,2*UIConstants.widthButton);

		add(welcome);
		add(secondLogoImage);
		add(passwordLabel);
		add(userNameLabel);
		add(usernameField);
		add(passwordField);
		add(login);
		add(userTypes);
		Color[] colorArr = {Color.GREEN,Color.RED,Color.YELLOW,Color.CYAN};
		Random rand = new Random();
		int length = colorArr.length;
		int index = rand.nextInt(length);
		setBackground(colorArr[index]);
		setLayout(null);
		//GUI STUFF END//
		
		
		
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = usernameField.getText();
				String password = passwordField.getText();
				if(userExist(username, password)) {
					Kusis.openingPanel.setVisible(false);
					
					
					if(userTypes.getSelectedItem().equals("Student")) {
						Kusis.currentUserType = "Student";
						Kusis.currentUsername = username;
						Kusis.studentPanel.setVisible(true);
						String query = "SELECT * FROM student WHERE username = '" + username +"'";
						ResultSet rs;
						try {
							rs = dbutils.executeQuery(query);
							while (rs.next())
							{
								int id = rs.getInt("studentid");
								Kusis.currentUserID = id;
								System.out.println(id);
							}
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					}
					else if(userTypes.getSelectedItem().equals("Faculty Member")) {
						Kusis.currentUserType = "Faculty Member";
						Kusis.currentUsername = username;
						Kusis.instructorPanel.setVisible(true);
						
						String query = "SELECT * FROM facultymember WHERE username = '" + username +"'";
						ResultSet rs;
						try {
							rs = dbutils.executeQuery(query);
							while (rs.next())
							{
								int id = rs.getInt("id");
								Kusis.currentUserID = id;
								System.out.println(id);
							}
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					else if(userTypes.getSelectedItem().equals("Admin")) {
						String query = "SELECT * FROM admin WHERE username = '" + username +"'";
						ResultSet rs;
						try {
							rs = dbutils.executeQuery(query);
							while (rs.next())
							{
								int id = rs.getInt("id");
								Kusis.currentUserID = id;
								System.out.println(id);
							}
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						Kusis.currentUserType = "Admin";
						Kusis.currentUsername = username;
						Kusis.adminPanel.setVisible(true);
					}
					

				} else {
					JOptionPane.showMessageDialog(login, "User does not exist");
				}
			}

		});

		
		
	}


	public Boolean userExist(String u, String p) {

		String tableName=null;
		if(userTypes.getSelectedItem().equals("Student")) tableName="student";
		else if(userTypes.getSelectedItem().equals("Faculty Member")) tableName="facultymember";
		else if(userTypes.getSelectedItem().equals("Admin")) tableName="admin";

		try {
			String query = "SELECT username, password FROM " + tableName + " WHERE username = '"+u+"' AND password = '" + p +"'";
			return dbutils.executeQuery(query).next();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


}