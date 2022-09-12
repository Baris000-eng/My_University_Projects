package gui;




import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.game.NeedForSpearGame;
import domain.obstacles.FirmObstacle;


@SuppressWarnings("serial")
public class CreateWorldPanel extends JPanel{
	private JTextField numOfSimpleObstacle;
	private JTextField numOfFirmObstacle;
	private JTextField numOfGiftObstacle;
	private JTextField numOfExplosiveObstacle;
	private JButton submitButton;
	private JLabel simpleObsLabel,giftObsLabel,firmObsLabel,explObsLabel;
	private JLabel simpleObsImg;
	private JLabel giftObsImg;
	private JLabel firmObsImg;
	private JLabel explObsImg;
	//private JLabel noblePhantasm;
	private JLabel editingArea;
	private JButton backButton;
	private JButton createButton;
	private JLabel imagePhantasm;
	private JButton goToGameButton;
	private String selectedObs="null";
	private static JLabel numHits;
	public static boolean isFirmObstacleClicked=false;
	private static int numOfHits;
	public static boolean collisionOccurs;
	private NeedForSpearGame controller= new NeedForSpearGame("txt");
	public static int simpleObstacleNumber;
	public static int firmObstacleNumber;
	public static int giftObstacleNumber;
	public static int explosiveObstacleNumber;
	public static int locX;
	public static int locY;
	public static int locXa;
	public static int locYa;
	
	
	public CreateWorldPanel() {
		super();

		
		simpleObsImg= new JLabel(MainFrame.simpleIcon);
		giftObsImg= new JLabel(MainFrame.giftIcon);

		firmObsImg= new JLabel(MainFrame.firmIcon);
		explObsImg= new JLabel(MainFrame.explosiveIcon);

		ImageIcon my_icon= new ImageIcon("phantasm-rotated.png");
		Image image = my_icon.getImage().getScaledInstance(MainFrame.width/10,20,Image.SCALE_SMOOTH);
		my_icon = new ImageIcon(image);
		imagePhantasm= new JLabel(my_icon);

		backButton= new JButton();
		backButton.setText("Back");

		
		initializeTextFields();
		
		createButton= new JButton();
		createButton.setText("Create");

		goToGameButton= new JButton();
		goToGameButton.setText("Go to Game");

		editingArea= new JLabel();
		editingArea.setText("Editing Area For Player");
		editingArea.setFont(new Font("Verdana", Font.BOLD, 14));

		giftObsLabel= new JLabel();
		giftObsLabel.setText("Number of Gift Obstacles: ");

		simpleObsLabel= new JLabel();
		simpleObsLabel.setText("Number of Simple Obstacles:");

		firmObsLabel= new JLabel();
		firmObsLabel.setText("Number of Firm Obstacles: ");

		explObsLabel= new JLabel();
		explObsLabel.setText("Number of Explosive Obstacles: ");


		simpleObsImg.setBounds(MainFrame.width-MainFrame.widthButton/2-210-MainFrame.width/50,MainFrame.heightButton/2+15,MainFrame.width/50,20);
		giftObsImg.setBounds(MainFrame.width-MainFrame.widthButton/2-210-MainFrame.width/50,3*MainFrame.heightButton/2+15,MainFrame.width/50,20);
		firmObsImg.setBounds(MainFrame.width-MainFrame.widthButton/2-210-MainFrame.width/50,5*MainFrame.heightButton/2+15,MainFrame.width/50,20);
		//numRequiredHits.setBounds(MainFrame.width-MainFrame.widthButton/2-198-MainFrame.width/50,5*MainFrame.heightButton/2+13,MainFrame.width/50,20);
		explObsImg.setBounds(MainFrame.width-MainFrame.widthButton/2-210-MainFrame.width/50,7*MainFrame.heightButton/2+15,MainFrame.width/50,20);

		simpleObsLabel.setBounds(MainFrame.width-MainFrame.widthButton/2-200,  MainFrame.heightButton/2+MainFrame.heightButton/2-15/2,200,15);
		giftObsLabel.setBounds  (MainFrame.width-MainFrame.widthButton/2-200,3*MainFrame.heightButton/2+MainFrame.heightButton/2-15/2,200,15);
		firmObsLabel.setBounds  (MainFrame.width-MainFrame.widthButton/2-200,5*MainFrame.heightButton/2+MainFrame.heightButton/2-15/2,200,15);
		explObsLabel.setBounds  (MainFrame.width-MainFrame.widthButton/2-200,7*MainFrame.heightButton/2+MainFrame.heightButton/2-15/2,200,15);

		numOfSimpleObstacle.setBounds(MainFrame.width-MainFrame.widthButton/2,MainFrame.heightButton/2,MainFrame.widthButton/3,MainFrame.heightButton);
		numOfGiftObstacle.setBounds(MainFrame.width-MainFrame.widthButton/2,3*MainFrame.heightButton/2,MainFrame.widthButton/3,MainFrame.heightButton);
		numOfFirmObstacle.setBounds(MainFrame.width-MainFrame.widthButton/2,5*MainFrame.heightButton/2,MainFrame.widthButton/3,MainFrame.heightButton);
		numOfExplosiveObstacle.setBounds(MainFrame.width-MainFrame.widthButton/2,7*MainFrame.heightButton/2,MainFrame.widthButton/3,MainFrame.heightButton);

		imagePhantasm.setBounds(19*MainFrame.width/40-MainFrame.width/100,8*MainFrame.height/10,MainFrame.width/10,20);

		editingArea.setBounds(500,500,300,300);

		backButton.setBounds(MainFrame.width-3*MainFrame.widthButton/3*2-10,11*MainFrame.heightButton/2,MainFrame.widthButton/3*2,MainFrame.heightButton);
		createButton.setBounds(MainFrame.width-2*MainFrame.widthButton/3*2-10,11*MainFrame.heightButton/2,MainFrame.widthButton/3*2,MainFrame.heightButton);
		goToGameButton.setBounds(MainFrame.width-MainFrame.widthButton/3*2-10,11*MainFrame.heightButton/2,MainFrame.widthButton/3*2,MainFrame.heightButton);
	
		
		this.add(simpleObsImg);
		this.add(giftObsImg);
		this.add(firmObsImg);
		this.add(explObsImg);
       
		
		simpleObsImg.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				selectedObs="simpleObstacle";
			}
		});

		firmObsImg.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				selectedObs="firmObstacle";
			}
		});

		explObsImg.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				selectedObs="explosiveObstacle";
			}
		});

		giftObsImg.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				selectedObs="giftObstacle";
			}
		});

		
		this.add(imagePhantasm);

		this.add(simpleObsLabel);
		this.add(giftObsLabel);
		this.add(firmObsLabel);
		this.add(explObsLabel);

		this.add(numOfSimpleObstacle);
		this.add(numOfFirmObstacle);
		this.add(numOfGiftObstacle);
		this.add(numOfExplosiveObstacle);

		this.add(backButton);
		this.add(createButton);
		this.add(goToGameButton);

		this.add(editingArea);
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MainFrame.isCreateButtonPressed==false) {
					simpleObstacleNumber= Integer.parseInt(MainFrame.createWorldPanel.getNumOfSimpleObstacle().getText());
					firmObstacleNumber= Integer.parseInt(MainFrame.createWorldPanel.getNumOfFirmObstacle().getText());
					explosiveObstacleNumber= Integer.parseInt(MainFrame.createWorldPanel.getNumOfExplosiveObstacle().getText());
					giftObstacleNumber= Integer.parseInt(MainFrame.createWorldPanel.getNumOfGiftObstacle().getText());
					        if(simpleObstacleNumber<controller.getSimpleObstacleNumber() 
							|| firmObstacleNumber<controller.getFirmObstacleNumber() 
							|| explosiveObstacleNumber<controller.getExplosiveObstacleNumber() 
							|| giftObstacleNumber<controller.getGiftObstacleNumber()) {
						JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "To create a world,"
								+ "There must be at least:\n"
								+ "75 simple obstacles\n"
								+ "5 explosive obstacles\n"
								+ "10 gift obstacles\n"
								+ "10 firm obstacles.");
					}  else if(simpleObstacleNumber+firmObstacleNumber+explosiveObstacleNumber+giftObstacleNumber>585) {
						JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You can't put more than 585 obstacles.");
					}
					else if(simpleObstacleNumber>=controller.getSimpleObstacleNumber() && 
							firmObstacleNumber>=controller.getFirmObstacleNumber() && 
							explosiveObstacleNumber>=controller.getExplosiveObstacleNumber() && 
							giftObstacleNumber>=controller.getGiftObstacleNumber()) {
						createWorld(simpleObstacleNumber,firmObstacleNumber,explosiveObstacleNumber,giftObstacleNumber);
						//ymir.HollowPurple();
						MainFrame.isCreateButtonPressed=true;
					} 
				} else {
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You already created random world. To add more obstacles, "
							+ "click on image and press wherever you want to add.");
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.createWorldPanel.setVisible(false);
				MainFrame.createOrLoadOrCustomizePanel.setVisible(true);
			}
		});

		goToGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


				if(!MainFrame.obstacles.isEmpty()) {
					for(JLabel a: MainFrame.numHitsArrayList) {
						MainFrame.runningGamePanel.add(a);
					}

					for(JLabel j:MainFrame.obstacles) {
						MainFrame.runningGamePanel.add(j);
					}
					RunningGamePanel.controller.getCurrentPlayer().setScore(0);//for resetting the score to its' initial situation
					RunningGamePanel.controller.createEnchantedSphere(19*MainFrame.width/40,15*MainFrame.height/20);
					MainFrame.createWorldPanel.setVisible(false);
					MainFrame.runningGamePanel.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Please put obstacles before starting the game.");
				}
			}

		});
		this.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				if(e.getX()<MainFrame.width-MainFrame.widthButton*2-MainFrame.width/50 && MainFrame.heightButton<e.getY() && e.getY()<MainFrame.height/2) {
		
					if(MainFrame.createWorldPanel.getSelectedObs().equals("simpleObstacle")) {
						
						collisionOccurs = false;
						JLabel simpleObstacle=new JLabel(MainFrame.simpleIcon);
						simpleObstacle.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);	
						
						for (JLabel obs : MainFrame.obstacles){

							if(obsCollision(obs, simpleObstacle)){
								collisionOccurs = true;
							}	

						}
					
						if(collisionOccurs==false) {
							
							JLabel simpleObstacleToAdd=new JLabel(MainFrame.simpleIcon);
							RunningGamePanel.controller.createObstacle("SimpleObstacle", e.getX(),e.getY());
							MainFrame.obstacles.add(simpleObstacleToAdd);
							MainFrame.createWorldPanel.add(simpleObstacleToAdd);
							simpleObstacleToAdd.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);


							simpleObstacleToAdd.addMouseListener(new MouseAdapter() { @Override 

								public void mouseClicked(MouseEvent e) {
								if(MainFrame.createWorldPanel.isVisible()) {
									//setMouseClicked(true);
									simpleObstacleToAdd.setIcon(null);
									MainFrame.obstacles.remove(simpleObstacleToAdd);
									RunningGamePanel.controller.removeObstacle(simpleObstacleToAdd.getX(), simpleObstacleToAdd.getY());
								}
							}

							public void mousePressed(MouseEvent me)
							{
								MainFrame.pressed = me;
							}

							});




							simpleObstacleToAdd.addMouseMotionListener(new MouseMotionListener() {

								@Override
								public void mouseDragged(MouseEvent e) {
									if(collisionOccurs==false) {
										MainFrame.location = simpleObstacleToAdd.getLocation();
										int x = MainFrame.location.x - MainFrame.pressed.getX() + e.getX();
										int y = MainFrame.location.y - MainFrame.pressed.getY() + e.getY();
										simpleObstacleToAdd.setLocation(x, y);
										RunningGamePanel.controller.updateObstacleLocation(MainFrame.location.x, MainFrame.location.y, x, y);
									}
									for(JLabel l:MainFrame.obstacles) {
										if(obsCollision(simpleObstacleToAdd,l) && l!=simpleObstacleToAdd) {
											collisionOccurs=true;
										}
									}
									if(collisionOccurs==true) {
										JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops,you cannot move obstacle here");
										collisionOccurs=false;
										simpleObstacleToAdd.setLocation(MainFrame.location);
									}
								}

								@Override
								public void mouseMoved(MouseEvent e) {
									// TODO Auto-generated method stub

								}

							});


						}
						else {
							JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You can't put an obstacle here, there is an obstacle");	
						}
					}
					else if(MainFrame.createWorldPanel.getSelectedObs().equals("giftObstacle")) {
						collisionOccurs = false;
						JLabel giftObstacle=new JLabel(MainFrame.giftIcon);
						giftObstacle.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);	

						for (JLabel obs : MainFrame.obstacles){

							if(obsCollision(obs, giftObstacle)){
								collisionOccurs = true;
							}	

						}
						//boolean checkCollision = false;
						if(collisionOccurs == false) {
							JLabel giftObstacleToAdd=new JLabel(MainFrame.giftIcon);
							RunningGamePanel.controller.createObstacle("GiftObstacle", e.getX(),e.getY());
							MainFrame.obstacles.add(giftObstacleToAdd);
							MainFrame.createWorldPanel.add(giftObstacleToAdd);
							giftObstacleToAdd.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);	

							giftObstacleToAdd.addMouseListener(new MouseAdapter() { @Override 

								public void mouseClicked(MouseEvent e) {
								if(MainFrame.createWorldPanel.isVisible()) {
									//setMouseClicked(true);
									giftObstacleToAdd.setIcon(null);
									MainFrame.obstacles.remove(giftObstacleToAdd);
									RunningGamePanel.controller.removeObstacle(giftObstacleToAdd.getX(), giftObstacleToAdd.getY());
								}
							}

							public void mousePressed(MouseEvent me)
							{
								MainFrame.pressed = me;
							}

							});


							giftObstacleToAdd.addMouseMotionListener(new MouseMotionListener() {

								@Override
								public void mouseDragged(MouseEvent e) {
									if(collisionOccurs==false) {
										MainFrame.location = giftObstacleToAdd.getLocation();
										int x = MainFrame.location.x - MainFrame.pressed.getX() + e.getX();
										int y = MainFrame.location.y - MainFrame.pressed.getY() + e.getY();
										giftObstacleToAdd.setLocation(x, y);
										RunningGamePanel.controller.updateObstacleLocation(MainFrame.location.x, MainFrame.location.y, x, y);
									}
									for(JLabel jl: MainFrame.obstacles) {
										if(obsCollision(giftObstacleToAdd,jl) && jl!=giftObstacleToAdd) {
											collisionOccurs=true;
										}
									}

									if(collisionOccurs==true) {
										JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops,you cannot move obstacle here");
										collisionOccurs=false;
										giftObstacleToAdd.setLocation(MainFrame.location);
									}
								}

								@Override
								public void mouseMoved(MouseEvent e) {
									// TODO Auto-generated method stub

								}

							});


						}
						else {
							JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You can't put an obstacle here, there is an obstacle");	
						}
					}
					else if(MainFrame.createWorldPanel.getSelectedObs().equals("explosiveObstacle")) {

						collisionOccurs = false;
						JLabel explosiveObstacle=new JLabel(MainFrame.explosiveIcon);
						explosiveObstacle.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);	
						for (JLabel obs : MainFrame.obstacles){

							if(obsCollision(obs, explosiveObstacle)){
								collisionOccurs = true;
							}	

						}
						//boolean checkCollision = false;
						if(collisionOccurs == false) {
							JLabel explosiveObstacleToAdd=new JLabel(MainFrame.explosiveIcon);
							RunningGamePanel.controller.createObstacle("ExplosiveObstacle", e.getX(),e.getY());
							MainFrame.obstacles.add(explosiveObstacleToAdd);
							MainFrame.createWorldPanel.add(explosiveObstacleToAdd);
							explosiveObstacleToAdd.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);	
							explosiveObstacleToAdd.addMouseListener(new MouseAdapter() { @Override 




								public void mouseClicked(MouseEvent e) {
								if(MainFrame.createWorldPanel.isVisible()) {
									//setMouseClicked(true);
									explosiveObstacleToAdd.setIcon(null);
									MainFrame.obstacles.remove(explosiveObstacleToAdd);
									RunningGamePanel.controller.removeObstacle(explosiveObstacleToAdd.getX(), explosiveObstacleToAdd.getY());
								}
							}

							public void mousePressed(MouseEvent me)
							{
								MainFrame.pressed = me;
							}

							});
							explosiveObstacleToAdd.addMouseMotionListener(new MouseMotionListener() {

								@Override
								public void mouseDragged(MouseEvent e) {
									if(collisionOccurs==false) {
										MainFrame.location = explosiveObstacleToAdd.getLocation();
										int x = MainFrame.location.x - MainFrame.pressed.getX() + e.getX();
										int y = MainFrame.location.y - MainFrame.pressed.getY() + e.getY();
										explosiveObstacleToAdd.setLocation(x, y);
										RunningGamePanel.controller.updateObstacleLocation(MainFrame.location.x, MainFrame.location.y, x, y);
									}
									for(JLabel a: MainFrame.obstacles) {
										if(obsCollision(a, explosiveObstacleToAdd)&&a!=explosiveObstacleToAdd) {
											collisionOccurs= true;
										}
									}

									if(collisionOccurs==true) {
										JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops, you cannot move obstacle here");
										collisionOccurs=false;
										explosiveObstacleToAdd.setLocation(MainFrame.location);
									}

								}

								@Override
								public void mouseMoved(MouseEvent e) {
									// TODO Auto-generated method stub

								}

							});


						}
						else {
							JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You can't put an obstacle here, there is an obstacle");	
						}

						//id++;
					}
					else if(MainFrame.createWorldPanel.getSelectedObs().equals("firmObstacle")) {

						collisionOccurs = false;
						JLabel firmObstacle=new JLabel(MainFrame.firmIcon);
						firmObstacle.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);	
						for (JLabel obs : MainFrame.obstacles){

							if(obsCollision(obs, firmObstacle)){
								collisionOccurs = true;
							}	

						}
						//boolean checkCollision = false;
						if(collisionOccurs == false) {
							JLabel firmObstacleToAdd=new JLabel(MainFrame.firmIcon);

							MainFrame.createWorldPanel.add(firmObstacleToAdd);
							firmObstacleToAdd.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);

							int numOfHits=((FirmObstacle) RunningGamePanel.controller.createObstacle("FirmObstacle", e.getX(),e.getY())).getNumOfHitsRequired();
							JLabel numHits= new JLabel();
							numHits.setText(""+numOfHits);
							numHits.setLocation(e.getX()+10,e.getY()-2);
							numHits.setVisible(true);
							numHits.setSize(20,20);
							MainFrame.obstacles.add(firmObstacleToAdd);
							MainFrame.createWorldPanel.add(numHits);
							MainFrame.numHitsArrayList.add(numHits);
							MainFrame.createWorldPanel.add(firmObstacleToAdd);
							firmObstacleToAdd.setBounds(e.getX(),e.getY(),MainFrame.width/50,20);

							firmObstacleToAdd.addMouseListener(new MouseAdapter() { @Override 
								public void mouseClicked(MouseEvent e) {
								if(MainFrame.createWorldPanel.isVisible()) {
									//setMouseClicked(true);
									firmObstacleToAdd.setIcon(null);
									numHits.setVisible(false);
									MainFrame.obstacles.remove(firmObstacleToAdd);
									RunningGamePanel.controller.removeObstacle(firmObstacleToAdd.getX(), firmObstacleToAdd.getY());
								}
							}
							public void mousePressed(MouseEvent me) {
								MainFrame.pressed= me;
							}

							});


							firmObstacleToAdd.addMouseMotionListener(new MouseMotionListener() {

								@Override
								public void mouseDragged(MouseEvent e) {

									if(collisionOccurs==false) {
										MainFrame.location = firmObstacleToAdd.getLocation();
										int x = MainFrame.location.x - MainFrame.pressed.getX() + e.getX();
										int y = MainFrame.location.y - MainFrame.pressed.getY() + e.getY();
										firmObstacleToAdd.setLocation(x, y);
										numHits.setLocation(x+10,y);
										RunningGamePanel.controller.updateObstacleLocation(MainFrame.location.x, MainFrame.location.y, x, y);
									}

									for(JLabel jlb: MainFrame.obstacles) {
										if(obsCollision(firmObstacleToAdd,jlb) && jlb!=firmObstacleToAdd) {
											collisionOccurs=true;
										}
									}


									if(collisionOccurs==true) {
										JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops,you cannot move the obstacle here");
										collisionOccurs=false;
										firmObstacleToAdd.setLocation(MainFrame.location);
										numHits.setLocation(MainFrame.location.x+10,MainFrame.location.y);

									}

								}

								@Override
								public void mouseMoved(MouseEvent e) {
									// TODO Auto-generated method stub

								}

							});




						}
						else {
							JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You can't put an obstacle here, there is an obstacle");	
						}

						//id++;
					} else {
						JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You should click an obstacle image from right first.");
					}
				} 

				else {
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You can't put obstacle here.");
				}



			}
		});

		//this.setBackground(Color.BLUE);
		this.setLayout(null);

	}
	
	
	
	

	


	public JButton getBackButton() {
		return backButton;
	}

	public JButton getCreateButton() {
		return createButton;
	}

	public JButton getGoToGameButton() {
		return goToGameButton;
	}

	public JTextField getNumOfSimpleObstacle() {
		return numOfSimpleObstacle;
	}

	public  JTextField getNumOfFirmObstacle() {
		return numOfFirmObstacle;
	}
	public JTextField getNumOfGiftObstacle() {
		return numOfGiftObstacle;
	}

	public JTextField getNumOfExplosiveObstacle() {
		return numOfExplosiveObstacle;
	}
	public JLabel getSimpleObsLabel() {
		return simpleObsLabel;
	}

	public JLabel getGiftObsLabel() {
		return giftObsLabel;
	}

	public JLabel getFirmObsLabel() {
		return firmObsLabel;
	}

	public JLabel getExplObsLabel() {
		return explObsLabel;

	}
	
	public String getSelectedObs() {
		return selectedObs;
	}
	
	public int getNumOfHits() {
		return numOfHits;
	}
	
	public static JLabel getNumHits() {
		return numHits;
	}


////HELPER METHODS
	
	
	//EFFECTS:
	//This method initializes the text fields with the default obstacle number values. The user enters the obstacle numbers for specifying how many 
	//obstacles the system will create (for each type of obstacles). 
	
	//The default value of the simple obstacle number should be 75.
	//The default value of the firm obstacle number should be 10.
	//The default value of the gift obstacle number should be 10.
	//The default value of the explosive obstacle number should be 5.
	public void initializeTextFields() {
	numOfSimpleObstacle= new JTextField("75");
	numOfFirmObstacle= new JTextField("10");
	numOfGiftObstacle= new JTextField("10");
	numOfExplosiveObstacle= new JTextField("5");
	}
	
	
	
   //EFFECTS: If o or obs2 is null, it throws a NullPointerException. Checks the collision of two obstacles from the left up corner. 
	// Returns true if 2 obstacles collide with each other 
	//from the left up corner. Otherwise, it returns false.
	//REQUIRES: o!=null, obs2!=null (JLabel's which are not null)
	public static Boolean obsLeftUpCornerCollision(JLabel o, JLabel obs2) {
		
		
		if( (o.getX()>=obs2.getX()&&o.getX()<=obs2.getX()+MainFrame.width/50) &&
				(o.getY()>= obs2.getY() && o.getY()<=obs2.getY()+20)) {
			return true;
		}	
		return false;
	}
	 //EFFECTS: If o or obs2 is null, it throws a NullPointerException. Checks the collision of two obstacles from the right up corner. 
	// Returns true if 2 obstacles collide with each other 
		//from the right up corner. Otherwise, it returns false.
	//REQUIRES: o!=null, obs2!=null (JLabel's which are not null)
	public static Boolean obsRightUpCornerCollision(JLabel o, JLabel obs2) {
		if( (o.getX()+ MainFrame.width/50  >=obs2.getX()&&o.getX()+MainFrame.width/50<=obs2.getX()+MainFrame.width/50) &&
				(o.getY()>= obs2.getY() && o.getY()<=obs2.getY()+20)) {
			return true;
		}	
		return false;
	}

	 //EFFECTS: Checks the collision of two obstacles from the left down corner. Returns true if 2 obstacles collide with each other 
	//from the left down corner. Otherwise, it returns false.

	//REQUIRES: o!=null, obs2!=null (JLabel's which are not null)
	//Note: obs is the new created obstacle, obs2 is already created before.
	
	public static Boolean obsLeftDownCornerCollision(JLabel o, JLabel obs2) {
		if( (o.getX()>=obs2.getX()&&o.getX()<=obs2.getX()+MainFrame.width/50) &&
				(o.getY()+20>= obs2.getY() && o.getY()+20<=obs2.getY()+20)) {
			return true;
		}	
		return false;
	}
	
	 //EFFECTS: If o or obs2 is null, it throws a NullPointerException. Checks the collision of two obstacles from the right down corner. 
	 // Returns true if 2 obstacles collide with each other from the right down corner. Otherwise, it returns false.
	
	//REQUIRES: o!=null, obs2!=null (JLabel's which are not null)
	//Note: obs is the new created obstacle, obs2 is already created before.
	public static Boolean obsRightDownCornerCollision(JLabel o, JLabel obs2) {
		if( (o.getX()+ MainFrame.width/50  >=obs2.getX()&&o.getX()+MainFrame.width/50<=obs2.getX()+MainFrame.width/50) &&
				(o.getY()+20>= obs2.getY() && o.getY()+20<=obs2.getY()+20)) {
			return true;
		}	
		return false;
	}

	//EFFECTS: If o or obs2 is null, it throws a NullPointerException. Checks the collision of two obstacles which are created as JLabels , 
	// returns true if two obstacles collide with each other, and returns false if two obstacles do not collide with each other. 
	//REQUIRES: o!=null, obs2!=null (JLabel's which are not null)
	//Collision of the two obstacles with each other is checked from all of the corners in this method.
	//This method returns a boolean value by considering JLabel collisions from the right down, left down, right up, and left up corners.
	
	public static Boolean obsCollision(JLabel o, JLabel obs2) {
		return obsRightDownCornerCollision( o,  obs2) 
				|| obsLeftDownCornerCollision( o,  obs2)
				|| obsRightUpCornerCollision( o,  obs2) 
				|| obsLeftUpCornerCollision( o,  obs2);
	}

	
	
	
	//This method enables system to create the obstacles. Moreover, it also enables the user to add & remove & drag obstacles via using 
	//mouse. 
	//REQUIRES: simpleObstacleNumber>=75 && firmObstacleNumber>=10 && explosiveObstacleNumber>=5 && giftObstacleNumber>=10
	public static void createWorld(int simpleObstacleNumber,int firmObstacleNumber,int explosiveObstacleNumber,int giftObstacleNumber) {


		Random random= new Random();

		for(int i=0;i<simpleObstacleNumber;i++) {
			
			locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
			locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			while(MainFrame.getMyArray()[locXa][locYa]==1) {
				locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
				locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			}

			MainFrame.getMyArray()[locXa][locYa]=1;
			locX=2*(locXa*MainFrame.width/50);
			locY=2*locYa*20+MainFrame.heightButton;

			//MainFrame.createObstacle();
			JLabel simpleObstacle=new JLabel(MainFrame.simpleIcon);

			MainFrame.controller.createObstacle("SimpleObstacle", locX, locY);
			MainFrame.obstacles.add(simpleObstacle);
			MainFrame.createWorldPanel.add(simpleObstacle);
			simpleObstacle.setIcon(MainFrame.simpleIcon);
			simpleObstacle.setBounds(locX,locY,MainFrame.width/50,20);//

			simpleObstacle.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if(collisionOccurs==false) {
						MainFrame.location = simpleObstacle.getLocation();
						int x = MainFrame.location.x - MainFrame.pressed.getX() + e.getX();
						int y = MainFrame.location.y - MainFrame.pressed.getY() + e.getY();
						simpleObstacle.setLocation(x, y);
						RunningGamePanel.controller.updateObstacleLocation(MainFrame.location.x, MainFrame.location.y, x, y);
					}

					for(JLabel lbl: MainFrame.obstacles) {
						if(obsCollision(lbl,simpleObstacle)&&lbl!=simpleObstacle) {
							collisionOccurs= true;
						}
					}


					if(collisionOccurs==true) {
						JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops, you cannot move the obstacle there!");
						collisionOccurs=false;
						simpleObstacle.setLocation(MainFrame.location);

					}

				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});
			simpleObstacle.addMouseListener(new MouseAdapter() { @Override 


				public void mouseClicked(MouseEvent e) {
				if(MainFrame.createWorldPanel.isVisible()) {
					//setMouseClicked(true);
					simpleObstacle.setIcon(null);
					MainFrame.obstacles.remove(simpleObstacle);
					RunningGamePanel.controller.removeObstacle(simpleObstacle.getX(), simpleObstacle.getY());
				}
			}

			public void mousePressed(MouseEvent me)
			{
				MainFrame.pressed = me;
			}

			});

			

		}

		for(int j=0;j<firmObstacleNumber;j++) {


			locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
			locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			while(MainFrame.getMyArray()[locXa][locYa]==1) {
				locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
				locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			}
			MainFrame.getMyArray()[locXa][locYa]=1;
			locX=2*(locXa*MainFrame.width/50); 
			locY=2*locYa*20+MainFrame.heightButton;

			JLabel firmObstacle=new JLabel(MainFrame.firmIcon);

			numOfHits=((FirmObstacle) RunningGamePanel.controller.createObstacle("FirmObstacle", locX, locY)).getNumOfHitsRequired();
			numHits= new JLabel();
			numHits.setText(""+numOfHits);
			numHits.setLocation(locX+10,locY-2);
			numHits.setVisible(true);
			numHits.setSize(20,20);
			MainFrame.obstacles.add(firmObstacle);
			MainFrame.createWorldPanel.add(numHits);
			MainFrame.numHitsArrayList.add(numHits);
			MainFrame.createWorldPanel.add(firmObstacle);
			firmObstacle.setBounds(locX,locY,MainFrame.width/50,20);

			firmObstacle.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if(collisionOccurs==false) {
						MainFrame.location = firmObstacle.getLocation();
						int x = MainFrame.location.x - MainFrame.pressed.getX() + e.getX();
						int y = MainFrame.location.y - MainFrame.pressed.getY() + e.getY();
						firmObstacle.setLocation(x, y);
						RunningGamePanel.controller.updateObstacleLocation(MainFrame.location.x, MainFrame.location.y, x, y);
						numHits.setLocation(x+10,y);
					}
					for(JLabel j: MainFrame.obstacles) {
						if(obsCollision(firmObstacle,j)&&j!=firmObstacle) {
							collisionOccurs= true;
						}
					}

					if(collisionOccurs==true) {
						JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops, you cannot move obstacle here");
						collisionOccurs=false;
						firmObstacle.setLocation(MainFrame.location);
						numHits.setLocation(MainFrame.location.x+10,MainFrame.location.y);
					}
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});

			firmObstacle.addMouseListener(new MouseAdapter() { @Override 



				public void mouseClicked(MouseEvent e) {
				if(MainFrame.createWorldPanel.isVisible()) {
					firmObstacle.setIcon(null);
					MainFrame.obstacles.remove(firmObstacle);
					RunningGamePanel.controller.removeObstacle(firmObstacle.getX(), firmObstacle.getY());
					numHits.setVisible(false);
					isFirmObstacleClicked=true;
				}


			}

			public void mousePressed(MouseEvent e)
			{
				MainFrame.pressed = e;
			}



			});









			//id++;
			//locX+=9*(MainFrame.width/50);

		}

		for(int k=0;k<explosiveObstacleNumber;k++) {

			locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
			locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			while(MainFrame.getMyArray()[locXa][locYa]==1) {
				locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
				locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			}
			MainFrame.getMyArray()[locXa][locYa]=1;
			locX=2*(locXa*MainFrame.width/50);
			locY=2*locYa*20+MainFrame.heightButton;

			JLabel explosiveObstacle=new JLabel(MainFrame.explosiveIcon); 
			RunningGamePanel.controller.createObstacle("ExplosiveObstacle", locX, locY);
			MainFrame.obstacles.add(explosiveObstacle);
			MainFrame.createWorldPanel.add(explosiveObstacle);
			explosiveObstacle.setIcon(MainFrame.explosiveIcon);
			explosiveObstacle.setBounds(locX,locY,MainFrame.width/50,20);
			explosiveObstacle.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if(collisionOccurs==false) {
						MainFrame.location = explosiveObstacle.getLocation();
						int x = MainFrame.location.x - MainFrame.pressed.getX() + e.getX();
						int y = MainFrame.location.y - MainFrame.pressed.getY() + e.getY();
						explosiveObstacle.setLocation(x, y);
						RunningGamePanel.controller.updateObstacleLocation(MainFrame.location.x, MainFrame.location.y, x, y);
					}
					for(JLabel a: MainFrame.obstacles) {
						if(obsCollision(a, explosiveObstacle)&&a!=explosiveObstacle) {
							collisionOccurs= true;
						}
					}

					if(collisionOccurs==true) {
						JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops, you cannot move obstacle here");
					    collisionOccurs=false;
						explosiveObstacle.setLocation(MainFrame.location);
					}

				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});

			explosiveObstacle.addMouseListener(new MouseAdapter() { @Override 

				public void mouseClicked(MouseEvent e) {
				if(MainFrame.createWorldPanel.isVisible()) {
					//setMouseClicked(true);
					explosiveObstacle.setIcon(null);
					MainFrame.obstacles.remove(explosiveObstacle);
					RunningGamePanel.controller.removeObstacle(explosiveObstacle.getX(), explosiveObstacle.getY());
				}
			}
			public void mousePressed(MouseEvent e)
			{
				MainFrame.pressed = e;
			}


			});




		}

		for(int t=0;t<giftObstacleNumber;t++) {

			locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
			locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			while(MainFrame.getMyArray()[locXa][locYa]==1) {
				locXa= random.nextInt((int) (25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width)+1);
				locYa= random.nextInt((int) ((MainFrame.height/2-MainFrame.heightButton)/40)+1);
			}
			MainFrame.getMyArray()[locXa][locYa]=1;
			locX=2*(locXa*MainFrame.width/50);
			locY=2*locYa*20+MainFrame.heightButton;


			JLabel giftObstacle=new JLabel(MainFrame.giftIcon); 
			RunningGamePanel.controller.createObstacle("GiftObstacle", locX, locY);
			MainFrame.obstacles.add(giftObstacle);

			giftObstacle.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if(collisionOccurs==false) {
						MainFrame.location = giftObstacle.getLocation();
						int x = MainFrame.location.x - MainFrame.pressed.getX() + e.getX();
						int y = MainFrame.location.y - MainFrame.pressed.getY() + e.getY();
						giftObstacle.setLocation(x, y);
						RunningGamePanel.controller.updateObstacleLocation(MainFrame.location.x, MainFrame.location.y, x, y);
					}
					for(JLabel l: MainFrame.obstacles) {
						if(obsCollision(giftObstacle,l)&&l!=giftObstacle) {
							collisionOccurs=true;
						}
					}

					if(collisionOccurs==true) {
						JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops,you cannot move obstacle here");
						//collisionOccurs=false;
						giftObstacle.setLocation(MainFrame.location);
					}

				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});
			giftObstacle.addMouseListener(new MouseAdapter() { @Override 


				public void mouseClicked(MouseEvent e) {
				if(MainFrame.createWorldPanel.isVisible()) {
					//setMouseClicked(true);
					giftObstacle.setIcon(null);
					MainFrame.obstacles.remove(giftObstacle);
					RunningGamePanel.controller.removeObstacle(giftObstacle.getX(), giftObstacle.getY());
				}
			}

			public void mousePressed(MouseEvent e)
			{
				MainFrame.pressed = e;
			}


			});
			MainFrame.createWorldPanel.add(giftObstacle);
			giftObstacle.setIcon(MainFrame.giftIcon);
			giftObstacle.setBounds(locX,locY,MainFrame.width/50,20);


		}

		for(int i=0; i<25*(MainFrame.width-2*MainFrame.widthButton-MainFrame.width/50)/MainFrame.width+1; i++){
			for(int j=0; j<(MainFrame.height/2-MainFrame.heightButton)/40+1; j++) {
				MainFrame.getMyArray()[i][j]=0;
			}
		}
		

	}
	



}


