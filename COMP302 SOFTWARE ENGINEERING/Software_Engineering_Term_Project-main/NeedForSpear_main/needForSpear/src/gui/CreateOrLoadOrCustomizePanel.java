package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import domain.game.CreateWorldHandler;
import domain.game.GameInfo;
import domain.game.NeedForSpearGame;


@SuppressWarnings("serial")
public class CreateOrLoadOrCustomizePanel extends JPanel{
	
	private JButton createButton;
	private JButton loadButton;
	private JButton customizeButton;
	private JButton logOutButton;
    
	private static NeedForSpearGame controller = new NeedForSpearGame("txt");
	
	public  CreateOrLoadOrCustomizePanel() {
		super();
		createButton= new JButton();
		loadButton= new JButton();
		customizeButton= new JButton();
		logOutButton= new JButton();

		createButton.setText("Create Game");
		loadButton.setText("Load Game");
		customizeButton.setText("Customize Game");
		logOutButton.setText("Log Out");

		createButton.setBounds(UIConstants.middleX,UIConstants.middleY-2*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		loadButton.setBounds(UIConstants.middleX,UIConstants.middleY-UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		customizeButton.setBounds(UIConstants.middleX,UIConstants.middleY,UIConstants.widthButton,UIConstants.heightButton);
		logOutButton.setBounds(UIConstants.middleX,UIConstants.middleY+UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);

		this.add(createButton);
		this.add(loadButton);
		this.add(customizeButton);
		this.add(logOutButton);
		
		this.setBackground(Color.BLUE);

		customizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.createOrLoadOrCustomizePanel.setVisible(false);
				MainFrame.customizeGamePanel.setVisible(true);
			}
		});

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.createOrLoadOrCustomizePanel.setVisible(false);
				MainFrame.createWorldPanel.setVisible(true);
			}
		});

		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.createOrLoadOrCustomizePanel.setVisible(false);
				MainFrame.openingPanel.setVisible(true);
				GameInfo.currentPlayer=null;

			}
		});

		loadButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<ArrayList<String>> gamesList=RunningGamePanel.controller.loadGame();

					int gameCount=gamesList.size();
					if(gamesList.get(0).size()==0) {
						JOptionPane.showMessageDialog(MainFrame.createOrLoadOrCustomizePanel, "You do not have any saved game.");
					} else {
						String gameNames="Your saved games: Please enter the number of game you want to load:\n";
						for(int i=0; i<gameCount; i++) {
							gameNames+=(i+1);
							gameNames+=": " +gamesList.get(i).get(2);
							gameNames+="\n";
						}

						String choosedGame=JOptionPane.showInputDialog(gameNames);
						loadSavedGame(RunningGamePanel.controller.getCurrentPlayer().getUsername(), gamesList.get(Integer.parseInt(choosedGame)-1));
						MainFrame.createOrLoadOrCustomizePanel.setVisible(false);
						MainFrame.runningGamePanel.setVisible(true);
						MainFrame.runningGamePanel.noblePhantasm.setVisible(true);
						RunningGamePanel.second=0;
					}


				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});
		this.setLayout(null);
	}
	
	public static void loadSavedGame(String username, ArrayList<String> gameInfo) {


		controller.loadGame(username, gameInfo);
		
		for (JLabel j: MainFrame.obstacles) {
			j.setVisible(false);
		}


		for(JLabel numHits:MainFrame.numHitsArrayList) {
			numHits.setText("");
		}
		MainFrame.obstacles.removeAll(MainFrame.obstacles);
		MainFrame.numHitsArrayList.removeAll(MainFrame.numHitsArrayList);
		


		int i=9;
		while(i<gameInfo.size()-3) {
			if(gameInfo.get(i).equals("SimpleObstacle")) {
				JLabel simpleObstacle=new JLabel(GameImages.simpleIcon);
				CreateWorldHandler.createObstacle("SimpleObstacle", Integer.parseInt(gameInfo.get(i+1)),Integer.parseInt(gameInfo.get(i+2)));
				MainFrame.obstacles.add(simpleObstacle);
				simpleObstacle.setIcon(GameImages.simpleIcon);
				simpleObstacle.setBounds(Integer.parseInt(gameInfo.get(i+1)),Integer.parseInt(gameInfo.get(i+2)),UIConstants.rectangleObstacleWidth,20);
				MainFrame.runningGamePanel.add(simpleObstacle);
				i+=3;
			}else if(gameInfo.get(i).equals("FirmObstacle")) {
				JLabel firmObstacle=new JLabel (GameImages.firmIcon); 
				RunningGamePanel.controller.createFirmObstacleWithNumHits(Integer.parseInt(gameInfo.get(i+1)),Integer.parseInt(gameInfo.get(i+2)),Integer.parseInt(gameInfo.get(i+3)));
				MainFrame.	obstacles.add(firmObstacle);
				firmObstacle.setIcon(GameImages.firmIcon);
				firmObstacle.setBounds(Integer.parseInt(gameInfo.get(i+1)),Integer.parseInt(gameInfo.get(i+2)),UIConstants.rectangleObstacleWidth,20);
				//runningGamePanel.add(firmObstacle);
				JLabel numHits= new JLabel();
				numHits.setText(""+Integer.parseInt(gameInfo.get(i+3)));
				numHits.setLocation(Integer.parseInt(gameInfo.get(i+1))+10,Integer.parseInt(gameInfo.get(i+2))-2);
				numHits.setVisible(true);
				numHits.setSize(20,20);
				MainFrame.runningGamePanel.add(numHits);
				MainFrame.runningGamePanel.add(firmObstacle);
				MainFrame.numHitsArrayList.add(numHits);
				i+=4;
			}else if(gameInfo.get(i).equals("GiftObstacle")) {
				JLabel giftObstacle=new JLabel(GameImages.giftIcon);
				CreateWorldHandler.createObstacle("GiftObstacle", Integer.parseInt(gameInfo.get(i+1)),Integer.parseInt(gameInfo.get(i+2)));
				MainFrame.obstacles.add(giftObstacle);
				giftObstacle.setIcon(GameImages.giftIcon);
				giftObstacle.setBounds(Integer.parseInt(gameInfo.get(i+1)),Integer.parseInt(gameInfo.get(i+2)),UIConstants.rectangleObstacleWidth,20);
				MainFrame.runningGamePanel.add(giftObstacle);
				i+=3;
			}else if(gameInfo.get(i).equals("ExplosiveObstacle")) {
				JLabel explosiveObstacle=new JLabel(GameImages.explosiveIcon);
				CreateWorldHandler.createObstacle("ExplosiveObstacle", Integer.parseInt(gameInfo.get(i+1)),Integer.parseInt(gameInfo.get(i+2)));
				MainFrame.obstacles.add(explosiveObstacle);
				explosiveObstacle.setIcon(GameImages.explosiveIcon);
				explosiveObstacle.setBounds(Integer.parseInt(gameInfo.get(i+1)),Integer.parseInt(gameInfo.get(i+2)),UIConstants.circularObstacleRadius*2,UIConstants.circularObstacleRadius*2);
				MainFrame.runningGamePanel.add(explosiveObstacle);
				i+=3;
			}

		}

	}


}