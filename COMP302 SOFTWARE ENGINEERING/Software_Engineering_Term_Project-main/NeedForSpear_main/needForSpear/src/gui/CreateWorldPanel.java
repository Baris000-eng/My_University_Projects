package gui;

import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.game.CreateWorldHandler;
import domain.game.GameInfo;
import domain.obstacles.ObstacleCreationListener;


@SuppressWarnings("serial")
public class CreateWorldPanel extends JPanel implements ObstacleCreationListener{
	private JTextField numOfSimpleObstacle;
	private JTextField numOfFirmObstacle;
	private JTextField numOfGiftObstacle;
	private JTextField numOfExplosiveObstacle;
	//private JButton submitButton;
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
	static boolean isCreateButtonPressed=false;
	private String selectedObs="null";
	private static JLabel numHits;
	public static boolean isFirmObstacleClicked=false;
	private static int numOfHits;
	public static boolean collisionOccurs;
	private static CreateWorldHandler controller= new CreateWorldHandler();
	public static int simpleObstacleNumber;
	public static int firmObstacleNumber;
	public static int giftObstacleNumber;
	public static int explosiveObstacleNumber;
	public static int locX;
	public static int locY;
	public static int locXa;
	public static int locYa;
	public static Point location;
	public static MouseEvent pressed;



	public CreateWorldPanel() {
		super();
		GameInfo.obstacleCreationListeners.add(this);

		simpleObsImg= new JLabel(GameImages.simpleIcon);
		giftObsImg= new JLabel(GameImages.giftIcon);

		firmObsImg= new JLabel(GameImages.firmIcon);
		explObsImg= new JLabel(GameImages.explosiveIcon);

		ImageIcon my_icon= new ImageIcon("phantasm-rotated.png");
		Image image = my_icon.getImage().getScaledInstance(UIConstants.width/10,20,Image.SCALE_SMOOTH);
		my_icon = new ImageIcon(image);
		imagePhantasm= new JLabel(my_icon);

		backButton= new JButton();
		backButton.setText("Back");

		numOfSimpleObstacle= new JTextField("75");
		numOfFirmObstacle= new JTextField("10");
		numOfGiftObstacle= new JTextField("10");
		numOfExplosiveObstacle= new JTextField("5");

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


		simpleObsImg.setBounds(UIConstants.width-UIConstants.widthButton/2-210-UIConstants.width/50,UIConstants.heightButton/2+15,UIConstants.rectangleObstacleWidth,20);
		giftObsImg.setBounds(UIConstants.width-UIConstants.widthButton/2-210-UIConstants.width/50,3*UIConstants.heightButton/2+15,UIConstants.rectangleObstacleWidth,20);
		firmObsImg.setBounds(UIConstants.width-UIConstants.widthButton/2-210-UIConstants.width/50,5*UIConstants.heightButton/2+15,UIConstants.rectangleObstacleWidth,20);
		explObsImg.setBounds(UIConstants.width-UIConstants.widthButton/2-210-UIConstants.width/50,7*UIConstants.heightButton/2+15,2*UIConstants.circularObstacleRadius,2*UIConstants.circularObstacleRadius);

		simpleObsLabel.setBounds(UIConstants.width-UIConstants.widthButton/2-200,  UIConstants.heightButton/2+UIConstants.heightButton/2-15/2,200,15);
		giftObsLabel.setBounds  (UIConstants.width-UIConstants.widthButton/2-200,3*UIConstants.heightButton/2+UIConstants.heightButton/2-15/2,200,15);
		firmObsLabel.setBounds  (UIConstants.width-UIConstants.widthButton/2-200,5*UIConstants.heightButton/2+UIConstants.heightButton/2-15/2,200,15);
		explObsLabel.setBounds  (UIConstants.width-UIConstants.widthButton/2-200,7*UIConstants.heightButton/2+UIConstants.heightButton/2-15/2,200,15);

		numOfSimpleObstacle.setBounds(UIConstants.width-UIConstants.widthButton/2,UIConstants.heightButton/2,UIConstants.widthButton/3,UIConstants.heightButton);
		numOfGiftObstacle.setBounds(UIConstants.width-UIConstants.widthButton/2,3*UIConstants.heightButton/2,UIConstants.widthButton/3,UIConstants.heightButton);
		numOfFirmObstacle.setBounds(UIConstants.width-UIConstants.widthButton/2,5*UIConstants.heightButton/2,UIConstants.widthButton/3,UIConstants.heightButton);
		numOfExplosiveObstacle.setBounds(UIConstants.width-UIConstants.widthButton/2,7*UIConstants.heightButton/2,UIConstants.widthButton/3,UIConstants.heightButton);

		imagePhantasm.setBounds(UIConstants.phantasmInitialX,UIConstants.phantasmInitialY,UIConstants.phantasmLength,20);

		editingArea.setBounds(500,500,300,300);

		backButton.setBounds(UIConstants.width-3*UIConstants.widthButton/3*2-10,11*UIConstants.heightButton/2,UIConstants.widthButton/3*2,UIConstants.heightButton);
		createButton.setBounds(UIConstants.width-2*UIConstants.widthButton/3*2-10,11*UIConstants.heightButton/2,UIConstants.widthButton/3*2,UIConstants.heightButton);
		goToGameButton.setBounds(UIConstants.width-UIConstants.widthButton/3*2-10,11*UIConstants.heightButton/2,UIConstants.widthButton/3*2,UIConstants.heightButton);


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
				if(CreateWorldPanel.isCreateButtonPressed==false) {
					simpleObstacleNumber= Integer.parseInt(MainFrame.createWorldPanel.getNumOfSimpleObstacle().getText());
					firmObstacleNumber= Integer.parseInt(MainFrame.createWorldPanel.getNumOfFirmObstacle().getText());
					explosiveObstacleNumber= Integer.parseInt(MainFrame.createWorldPanel.getNumOfExplosiveObstacle().getText());
					giftObstacleNumber= Integer.parseInt(MainFrame.createWorldPanel.getNumOfGiftObstacle().getText());

					controller.createWorld(simpleObstacleNumber, firmObstacleNumber, explosiveObstacleNumber, giftObstacleNumber);


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

				controller.startGame();
				if(!MainFrame.obstacles.isEmpty()) {
					for(JLabel a: MainFrame.numHitsArrayList) {
						MainFrame.runningGamePanel.add(a);
					}

					for(JLabel j:MainFrame.obstacles) {
						MainFrame.runningGamePanel.add(j);
					}
					//controller.getCurrentPlayer().setScore(0);//for resetting the score to its' initial situation
					//controller.createEnchantedSphere(19*UIConstants.width/40,15*UIConstants.height/20);
					MainFrame.createWorldPanel.setVisible(false);
					MainFrame.runningGamePanel.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Please put obstacles before starting the game.");
				}
			}

		});

		this.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				CreateWorldHandler.createObstacleByClick(getSelectedObs(), e.getX(), e.getY());
			}
		});

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




	//EFFECTS: If o or obs2 is null, it throws a NullPointerException. Checks the collision of two obstacles from the left up corner. 
	// Returns true if 2 obstacles collide with each other 
	//from the left up corner. Otherwise, it returns false.
	//REQUIRES: o!=null, obs2!=null (JLabel's which are not null)
	public static Boolean obsLeftUpCornerCollision(JLabel o, JLabel obs2) {


		if( (o.getX()>=obs2.getX()&&o.getX()<=obs2.getX()+UIConstants.width/50) &&
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
		if( (o.getX()+ UIConstants.width/50  >=obs2.getX()&&o.getX()+UIConstants.width/50<=obs2.getX()+UIConstants.width/50) &&
				(o.getY()>= obs2.getY() && o.getY()<=obs2.getY()+20)) {
			return true;
		}	
		return false;
	}

	//EFFECTS: Checks the collision of two obstacles from the left down corner. Returns true if 2 obstacles collide with each other 
	//from the left down corner. Otherwise, it returns false.

	//REQUIRES: o!=null, obs2!=null (JLabel's which are not null)
	//Note: obs is the new created obstacle, obs2 is already created before.

	public static boolean obsLeftDownCornerCollision(JLabel o, JLabel obs2) {
		if( (o.getX()>=obs2.getX()&&o.getX()<=obs2.getX()+UIConstants.width/50) &&
				(o.getY()+20>= obs2.getY() && o.getY()+20<=obs2.getY()+20)) {
			return true;
		}	
		return false;
	}

	//EFFECTS: If o or obs2 is null, it throws a NullPointerException. Checks the collision of two obstacles from the right down corner. 
	// Returns true if 2 obstacles collide with each other from the right down corner. Otherwise, it returns false.

	//REQUIRES: o!=null, obs2!=null (JLabel's which are not null)
	//Note: obs is the new created obstacle, obs2 is already created before.
	public static boolean obsRightDownCornerCollision(JLabel o, JLabel obs2) {
		if( (o.getX()+ UIConstants.width/50  >=obs2.getX()&&o.getX()+UIConstants.width/50<=obs2.getX()+UIConstants.width/50) &&
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

	public static boolean obsCollision(JLabel o, JLabel obs2) {
		return obsRightDownCornerCollision( o,  obs2) 
				|| obsLeftDownCornerCollision( o,  obs2)
				|| obsRightUpCornerCollision( o,  obs2) 
				|| obsLeftUpCornerCollision( o,  obs2);
	}




	//This method enables system to create the obstacles. Moreover, it also enables the user to add & remove & drag obstacles via using 
	//mouse. 
	//REQUIRES: simpleObstacleNumber>=75 && firmObstacleNumber>=10 && explosiveObstacleNumber>=5 && giftObstacleNumber>=10



	@Override
	public void onSimpleObstacleCreation(int x, int y) {

		JLabel simpleObstacle=new JLabel();

		MainFrame.obstacles.add(simpleObstacle);   
		MainFrame.createWorldPanel.add(simpleObstacle);
		simpleObstacle.setIcon(GameImages.simpleIcon);
		simpleObstacle.setBounds(x,y,UIConstants.rectangleObstacleWidth,20);

		simpleObstacle.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(collisionOccurs==false && MainFrame.createWorldPanel.isVisible()) {
					location = simpleObstacle.getLocation();
					int x = location.x - pressed.getX() + e.getX();
					int y = location.y - pressed.getY() + e.getY();
					if(UIConstants.upperBoundaryForMap<=y && y<=UIConstants.lowerBoundaryForMap && 0<=x && x<=UIConstants.rightBoundaryForMap-UIConstants.phantasmLength/5) {
						simpleObstacle.setLocation(x, y);
						CreateWorldHandler.updateObstacleLocation(location.x, location.y, x, y);
					}
				}

				for(JLabel lbl: MainFrame.obstacles) {
					if(obsCollision(lbl,simpleObstacle)&&lbl!=simpleObstacle) {
						collisionOccurs= true;
					}
				}


				if(collisionOccurs==true) {
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops, you cannot move the obstacle there!");
					collisionOccurs=false;
					simpleObstacle.setLocation(location);


				}

			}

			@Override
			public void mouseMoved(MouseEvent e) {


			}

		});
		simpleObstacle.addMouseListener(new MouseAdapter() { @Override 


			public void mouseClicked(MouseEvent e) {
			if(MainFrame.createWorldPanel.isVisible()) {
				//setMouseClicked(true);
				simpleObstacle.setIcon(null);
				MainFrame.obstacles.remove(simpleObstacle);
				controller.removeObstacle(simpleObstacle.getX(), simpleObstacle.getY());
			}
		}

		public void mousePressed(MouseEvent me)
		{
			pressed = me;
		}

		});



	}




	@Override
	public void onFirmObstacleCreation(int x, int y, int numOfHits) {

		JLabel firmObstacle=new JLabel(GameImages.firmIcon);
		JLabel numHits= new JLabel();
		numHits.setText(""+numOfHits);
		numHits.setLocation(x+10,y-2);
		numHits.setVisible(true);
		numHits.setSize(20,20);
		MainFrame.obstacles.add(firmObstacle);
		MainFrame.createWorldPanel.add(numHits);
		MainFrame.numHitsArrayList.add(numHits);
		MainFrame.createWorldPanel.add(firmObstacle);
		firmObstacle.setBounds(x,y,UIConstants.rectangleObstacleWidth,20);

		firmObstacle.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(collisionOccurs==false && MainFrame.createWorldPanel.isVisible()) {
					location = firmObstacle.getLocation();
					int x = location.x - pressed.getX() + e.getX();
					int y = location.y -pressed.getY() + e.getY();
					if(UIConstants.upperBoundaryForMap<=y && y<=UIConstants.lowerBoundaryForMap && 0<=x && x<=UIConstants.rightBoundaryForMap-UIConstants.phantasmLength/5) {
						firmObstacle.setLocation(x, y);
						numHits.setLocation(x+10,y);
						CreateWorldHandler.updateObstacleLocation(location.x, location.y, x, y);
					}
				}
				for(JLabel j: MainFrame.obstacles) {
					if(obsCollision(firmObstacle,j)&&j!=firmObstacle) {
						collisionOccurs= true;
					}
				}

				if(collisionOccurs==true) {
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops, you cannot move obstacle here");
					collisionOccurs=false;
					firmObstacle.setLocation(location);
					numHits.setLocation(location.x+10,location.y);

				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});

		firmObstacle.addMouseListener(new MouseAdapter() { @Override 



			public void mouseClicked(MouseEvent e) {
			if(MainFrame.createWorldPanel.isVisible()) {
				//setMouseClicked(true);
				firmObstacle.setIcon(null);
				numHits.setVisible(false);
				MainFrame.obstacles.remove(firmObstacle);
				controller.removeObstacle(firmObstacle.getX(), firmObstacle.getY());
			}


		}

		public void mousePressed(MouseEvent e)
		{
			pressed = e;
		}



		});


	}








	@Override
	public void onExplosiveObstacleCreation(int x, int y) {
		JLabel explosiveObstacle=new JLabel();

		MainFrame.obstacles.add(explosiveObstacle);   
		MainFrame.createWorldPanel.add(explosiveObstacle);
		explosiveObstacle.setIcon(GameImages.explosiveIcon);
		explosiveObstacle.setBounds(x,y, 2*UIConstants.circularObstacleRadius, 2*UIConstants.circularObstacleRadius);

		explosiveObstacle.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(collisionOccurs==false && MainFrame.createWorldPanel.isVisible()) {
					location = explosiveObstacle.getLocation();
					int x = location.x - pressed.getX() + e.getX();
					int y = location.y - pressed.getY() + e.getY();
					if(UIConstants.upperBoundaryForMap<=y && y<=UIConstants.lowerBoundaryForMap && 0<=x && x<=UIConstants.rightBoundaryForMap-UIConstants.phantasmLength/5) {
						explosiveObstacle.setLocation(x, y);
						CreateWorldHandler.updateObstacleLocation(location.x, location.y, x, y);
					}
				}

				for(JLabel lbl: MainFrame.obstacles) {
					if(obsCollision(lbl,explosiveObstacle)&&lbl!=explosiveObstacle) {
						collisionOccurs= true;
					}
				}


				if(collisionOccurs==true) {
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops, you cannot move the obstacle there!");
					collisionOccurs=false;
					explosiveObstacle.setLocation(location);


				}

			}

			@Override
			public void mouseMoved(MouseEvent e) {


			}

		});
		explosiveObstacle.addMouseListener(new MouseAdapter() { @Override 


			public void mouseClicked(MouseEvent e) {
			if(MainFrame.createWorldPanel.isVisible()) {
				//setMouseClicked(true);
				explosiveObstacle.setIcon(null);
				MainFrame.obstacles.remove(explosiveObstacle);
				controller.removeObstacle(explosiveObstacle.getX(), explosiveObstacle.getY());
			}
		}

		public void mousePressed(MouseEvent me)
		{
			pressed = me;
		}

		});


	}








	@Override
	public void onGiftObstacleCreation(int x, int y) {
		JLabel giftObstacle=new JLabel();

		MainFrame.obstacles.add(giftObstacle);   
		MainFrame.createWorldPanel.add(giftObstacle);
		giftObstacle.setIcon(GameImages.giftIcon);
		giftObstacle.setBounds(x,y,UIConstants.rectangleObstacleWidth,20);

		giftObstacle.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(collisionOccurs==false && MainFrame.createWorldPanel.isVisible()) {
					location = giftObstacle.getLocation();
					int x = location.x - pressed.getX() + e.getX();
					int y = location.y - pressed.getY() + e.getY();
					if(UIConstants.upperBoundaryForMap<=y && y<=UIConstants.lowerBoundaryForMap && 0<=x && x<=UIConstants.rightBoundaryForMap-UIConstants.phantasmLength/5) {
						giftObstacle.setLocation(x, y);
						CreateWorldHandler.updateObstacleLocation(location.x, location.y, x, y);
					}
				}

				for(JLabel lbl: MainFrame.obstacles) {
					if(obsCollision(lbl,giftObstacle)&&lbl!=giftObstacle) {
						collisionOccurs= true;
					}
				}


				if(collisionOccurs==true) {
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "Oops, you cannot move the obstacle there!");
					collisionOccurs=false;
					giftObstacle.setLocation(location);


				}

			}

			@Override
			public void mouseMoved(MouseEvent e) {


			}

		});
		giftObstacle.addMouseListener(new MouseAdapter() { @Override 


			public void mouseClicked(MouseEvent e) {
			if(MainFrame.createWorldPanel.isVisible()) {
				giftObstacle.setIcon(null);
				MainFrame.obstacles.remove(giftObstacle);
				controller.removeObstacle(giftObstacle.getX(), giftObstacle.getY());
			}
		}

		public void mousePressed(MouseEvent me)
		{
			pressed = me;
		}

		});


	}



	@Override
	public void onMissingNumberEntered() {
		JOptionPane.showMessageDialog(MainFrame.createWorldPanel,
				"To create a world," + "There must be at least:\n" + "75 simple obstacles\n"
						+ "5 explosive obstacles\n" + "10 gift obstacles\n" + "10 firm obstacles.");

	}





	@Override
	public void onExceedingNumberEntered(int totalNum) {
		JOptionPane.showMessageDialog(MainFrame.createWorldPanel,
				"You are trying to add " + totalNum + " obstacles.\n"
						+ "You can add maximum " + UIConstants.maxNumberOfObstacles + " obstacles in total.");

		
	}


	@Override
	public void onPutOnAnotherObstacle() {
		JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You can't put an obstacle here, there is an obstacle");
	}


	@Override
	public void onClickOutsideMap() {
		JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You can't put obstacle here.");
	}

	@Override
	public void onNoObstacleChosen() {
		JOptionPane.showMessageDialog(MainFrame.createWorldPanel, "You should click on an obstacle image first.");
	}




}




