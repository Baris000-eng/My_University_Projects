package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RegisterPanel extends JPanel{

	private JButton registerSubmitButton;
	private JLabel userLabel;
	private JLabel passwordLabel;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JButton backButton;

	public RegisterPanel() {
		super();
		registerSubmitButton= new JButton();
		backButton= new JButton();
		userLabel= new JLabel();
		passwordLabel= new JLabel();
		userNameTextField= new JTextField(20);
		passwordTextField= new JPasswordField();
		
		backButton.setText("Back");
		userLabel.setText("Username:");
		userLabel.setBounds(UIConstants.middleX-UIConstants.widthButton*1/2,UIConstants.middleY-2*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		userNameTextField.setBounds(UIConstants.middleX+UIConstants.widthButton*1/2,UIConstants.middleY-2*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		backButton.setBounds(UIConstants.middleX-UIConstants.widthButton*1/2,UIConstants.middleY,UIConstants.widthButton,UIConstants.heightButton);

		passwordLabel.setBounds(UIConstants.middleX-UIConstants.widthButton*1/2,UIConstants.middleY-UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		passwordLabel.setText("Password:");
		passwordTextField.setBounds(UIConstants.middleX+UIConstants.widthButton*1/2,UIConstants.middleY-UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);

		registerSubmitButton.setBounds(UIConstants.middleX+UIConstants.widthButton*1/2,UIConstants.middleY,UIConstants.widthButton,UIConstants.heightButton);
		registerSubmitButton.setText("Register");
		
		this.add(userLabel);
		this.add(userNameTextField);
		this.add(passwordLabel);
		this.add(passwordTextField);
		this.add(registerSubmitButton);
		this.add(backButton);
		registerSubmitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String username= userNameTextField.getText();
				@SuppressWarnings("deprecation")
				String password= passwordTextField.getText();
				if(username.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(MainFrame.registerPanel ,"Please provide username and password to register to the game.");
				}
				else if(RunningGamePanel.controller.usernameExist(username) == false) {
					RunningGamePanel.controller.addUser(username, password);
					RunningGamePanel.controller.setCurrentPlayer(RunningGamePanel.controller.createPlayer(username));
					JOptionPane.showMessageDialog(MainFrame.registerPanel ,"You have successfully registered"); 
					MainFrame.registerPanel.setVisible(false);
					MainFrame.createOrLoadOrCustomizePanel.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(MainFrame.registerPanel ,"Username already taken. Please enter another username.");
				}

			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.registerPanel.setVisible(false);
				MainFrame.openingPanel.setVisible(true);
			}
		});
		this.setLayout(null);
		//this.setBackground(Color.BLUE);

	}


	public JLabel getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(JLabel userLabel) {
		this.userLabel = userLabel;
	}

	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	public void setPasswordLabel(JLabel passwordLabel) {
		this.passwordLabel = passwordLabel;
	}

	public JTextField getUserNameTextField() {
		return userNameTextField;
	}

	public void setUserNameTextField(JTextField userNameTextField) {
		this.userNameTextField = userNameTextField;
	}

	public JPasswordField getPasswordTextField() {
		return passwordTextField;
	}

	public void setPasswordTextField(JPasswordField passwordTextField) {
		this.passwordTextField = passwordTextField;
	}




}