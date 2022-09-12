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
public class LogInPanel extends JPanel{
	private JButton logInSubmitButton;
	private JLabel userLabel;
	private JLabel passwordLabel;
	private JTextField userNameTextField;
	private JTextField passwordTextField;
	private JButton backButton;

	public LogInPanel() {
		super();
		logInSubmitButton= new JButton();
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

		logInSubmitButton.setBounds(UIConstants.middleX+UIConstants.widthButton*1/2,UIConstants.middleY,UIConstants.widthButton,UIConstants.heightButton);
		logInSubmitButton.setText("Login");


		this.add(logInSubmitButton);
		this.add(userLabel);
		this.add(passwordLabel);
		this.add(userNameTextField);
		this.add(passwordTextField);
		this.add(backButton);

		logInSubmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String username= MainFrame.logInPanel.getUserNameTextField().getText();
				String password= MainFrame.logInPanel.getPasswordTextField().getText();
				if(username.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(MainFrame.logInPanel ,"Please provide username and password to login to the game.");
				} else if(RunningGamePanel.controller.userExist(username, password)==true) {
					RunningGamePanel.controller.setCurrentPlayer(RunningGamePanel.controller.createPlayer(username));
					JOptionPane.showMessageDialog(MainFrame.logInPanel ,"You have successfully logged in."); 
					MainFrame.logInPanel.setVisible(false);
					MainFrame.createOrLoadOrCustomizePanel.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(MainFrame.logInPanel ,"Wrong username or password.\nIf you have not registered yet, please register first.");
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                MainFrame.logInPanel.setVisible(false);
				MainFrame.openingPanel.setVisible(true);
			}
		});


		//this.setSize(500,400);
		//this.setBackground(Color.CYAN);
		this.setLayout(null);
	}


	public JButton getLogInSubmitButton() {
		return logInSubmitButton;
	}

	public void setLogInSubmitButton(JButton logInSubmitButton) {
		this.logInSubmitButton = logInSubmitButton;
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

	public JTextField getPasswordTextField() {
		return passwordTextField;
	}

	public void setPasswordTextField(JTextField passwordTextField) {
		this.passwordTextField = passwordTextField;
	}

}