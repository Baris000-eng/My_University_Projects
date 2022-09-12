package gui;


import domain.game.CollisionListener;
import domain.game.NeedForSpearGame;
import domain.game.ResetListener;
import domain.game.SphereMovementListener;
import domain.obstacles.MagicalHexListener;
import domain.obstacles.ObstacleMovementListener;
import domain.player.MagicalAbilityCountListener;
import domain.player.MagicalAbilityListener;
import domain.player.PhantasmMovementLengthListener;
import domain.player.RemainingGiftBoxListener;
import domain.player.ScoreLivesListener;
import domain.ymir.FrozenListener;
import domain.ymir.HollowPurpleListener;

import java.awt.Color;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class RunningGamePanel extends JPanel implements ScoreLivesListener, CollisionListener, SphereMovementListener,
MagicalAbilityListener, PhantasmMovementLengthListener, RemainingGiftBoxListener, MagicalHexListener,
ResetListener, MagicalAbilityCountListener, ObstacleMovementListener, HollowPurpleListener, FrozenListener {


	static boolean isPaused = true;

	public boolean magicalHexActivated = false;
	private JButton pauseButton;
	private JButton quitButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton resumeButton;
	private JLabel scoreLabel;
	private JLabel livesLabel;
	protected JLabel noblePhantasm;
	protected JLabel sphere;
	public static int second = 1;
	private JLabel chanceGivingAbilityNum;
	private JLabel noblePhantasmExpansionNum;
	private JLabel magicalHexNum;
	private JLabel unstoppableEnchantedSphereNum;


	private JButton customizeButton;

	public static NeedForSpearGame controller = new NeedForSpearGame("txt");

	protected JCheckBox activateYmir;
	public boolean ymirActive = false;

	private static ArrayList<JLabel> remainings = new ArrayList<JLabel>();
	private ArrayList<JLabel> giftBoxes = new ArrayList<JLabel>();
	private ArrayList<JLabel> magicalHexes = new ArrayList<JLabel>();

	public int a = 0;
	protected boolean leftOrRight = true;

	public boolean isLeftOrRight() {
		return leftOrRight;
	}

	public void setLeftOrRight(boolean leftOrRight) {
		this.leftOrRight = leftOrRight;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	protected int time = 0;

	public RunningGamePanel() {

		super();

		//To Subscribe Listeners
		controller.getScoreLivesListeners().add(this);
		controller.getCollisionListeners().add(this);
		controller.getMovementListeners().add(this);
		controller.getMagicalAbilityListeners().add(this);
		controller.getPhantasmListeners().add(this);
		controller.getRemGiftListeners().add(this);
		controller.getMagicalHexListeners().add(this);
		controller.getResetListeners().add(this);
		controller.getMagicalAbilityCountListeners().add(this);
		controller.getObstacleMovementListeners().add(this);
		controller.getHollowPurpleListeners().add(this);
		controller.getFrozenListeners().add(this);
		

		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		pauseButton = new JButton();
		quitButton = new JButton();
		saveButton = new JButton();
		loadButton = new JButton();
		customizeButton = new JButton();
		resumeButton = new JButton();

		resumeButton.setText("Resume");
		pauseButton.setText("Pause");
		quitButton.setText("Quit");
		saveButton.setText("Save");
		loadButton.setText("Load");
		customizeButton.setText("Customize");

		scoreLabel = new JLabel();

		scoreLabel.setBounds(UIConstants.width - 130, 10, 105, 75);
		scoreLabel.setVisible(true);

		livesLabel = new JLabel();

		chanceGivingAbilityNum = new JLabel();
		noblePhantasmExpansionNum = new JLabel();
		magicalHexNum = new JLabel();
		unstoppableEnchantedSphereNum = new JLabel();

		GameImages.chanceGivingAbilityIcon.setBounds(UIConstants.width - 170, 110, 105, 75);
		GameImages.noblePhantasmExpansionIcon.setBounds(UIConstants.width - 170, 160, 105, 75);
		GameImages.magicalHexIcon.setBounds(UIConstants.width - 170, 210, 105, 75);
		GameImages.unstoppableEnchantedSphereIcon.setBounds(UIConstants.width - 170, 260, 105, 75);

		chanceGivingAbilityNum.setBounds(UIConstants.width - 70, 110, 105, 75);
		noblePhantasmExpansionNum.setBounds(UIConstants.width - 70, 160, 105, 75);
		magicalHexNum.setBounds(UIConstants.width - 70, 210, 105, 75);
		unstoppableEnchantedSphereNum.setBounds(UIConstants.width - 70, 260, 105, 75);

		this.add(chanceGivingAbilityNum);
		this.add(noblePhantasmExpansionNum);
		this.add(magicalHexNum);
		this.add(unstoppableEnchantedSphereNum);

		this.add(GameImages.chanceGivingAbilityIcon);
		this.add(GameImages.noblePhantasmExpansionIcon);
		this.add(GameImages.magicalHexIcon);
		this.add(GameImages.unstoppableEnchantedSphereIcon);

		GameImages.chanceGivingAbilityIcon.setVisible(true);
		GameImages.noblePhantasmExpansionIcon.setVisible(true);
		GameImages.magicalHexIcon.setVisible(true);
		GameImages.unstoppableEnchantedSphereIcon.setVisible(true);

		GameImages.chanceGivingAbilityIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.useChanceGivingAbility();
			}
		});

		GameImages.noblePhantasmExpansionIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.useNoblePhantasmExpansionAbility();
			}
		});

		GameImages.magicalHexIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.useMagicalHexAbility();
			}
		});

		GameImages.unstoppableEnchantedSphereIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.useUnstoppableEnchantedSphereAbility();
			}
		});

		chanceGivingAbilityNum.setVisible(true);
		noblePhantasmExpansionNum.setVisible(true);
		magicalHexNum.setVisible(true);
		unstoppableEnchantedSphereNum.setVisible(true);

		livesLabel.setBounds(UIConstants.width - 130, 60, 105, 75);
		livesLabel.setVisible(true);

		activateYmir = new JCheckBox("Ymir");

		pauseButton.setBounds(0, 0, 2 * UIConstants.widthButton / 3, UIConstants.heightButton);
		quitButton.setBounds(2 * UIConstants.widthButton / 3, 0, 2 * UIConstants.widthButton / 3,
				UIConstants.heightButton);
		saveButton.setBounds(2 * 2 * UIConstants.widthButton / 3, 0, 2 * UIConstants.widthButton / 3,
				UIConstants.heightButton);
		loadButton.setBounds(3 * 2 * UIConstants.widthButton / 3, 0, 2 * UIConstants.widthButton / 3,
				UIConstants.heightButton);
		customizeButton.setBounds(4 * 2 * UIConstants.widthButton / 3, 0, 2 * UIConstants.widthButton / 3,
				UIConstants.heightButton);
		resumeButton.setBounds(5 * 2 * UIConstants.widthButton / 3, 0, 2 * UIConstants.widthButton / 3,
				UIConstants.heightButton);
		activateYmir.setBounds(6 * 2 * UIConstants.widthButton / 3, 0, 2 * UIConstants.widthButton / 3,
				UIConstants.heightButton);

		sphere = new JLabel(GameImages.sphereIcon);
		sphere.setBounds(UIConstants.sphereInitialX, UIConstants.sphereInitialY, 16, 16);

		noblePhantasm = new JLabel(GameImages.noblePhantasmIcon);
		noblePhantasm.setBounds(UIConstants.phantasmInitialX, UIConstants.phantasmInitialY, UIConstants.phantasmLength, 20);

		noblePhantasm.setFocusable(true);
		noblePhantasm.setFocusTraversalKeysEnabled(false);

		this.add(pauseButton);
		this.add(quitButton);
		this.add(saveButton);
		this.add(loadButton);
		this.add(resumeButton);
		this.add(scoreLabel);
		this.add(livesLabel);
		this.add(activateYmir);

		this.add(noblePhantasm);

		GameImages.leftCanonLabel.setVisible(false);
		GameImages.rightCanonLabel.setVisible(false);
		GameImages.firstBallLabel.setVisible(false);
		GameImages.secondBallLabel.setVisible(false);

		GameImages.firstBallLabel.setBounds(noblePhantasm.getX() + 6, noblePhantasm.getY() - 40 - 25, 25, 25);
		GameImages.secondBallLabel.setBounds(noblePhantasm.getX() + noblePhantasm.getWidth() - 40 + 9,
				noblePhantasm.getY() - 40 - 25, 25, 25);

		this.add(GameImages.firstBallLabel);
		this.add(GameImages.secondBallLabel);

		Runnable countSecond = new Runnable() {
			public void run() {
				if (RunningGamePanel.isPaused == false) {
					second++;
				}
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(countSecond, 0, 1, TimeUnit.SECONDS);



		activateYmir.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {// checkbox has been selected
					controller.activateYmir();
					requestFocus();
				} else {// checkbox has been deselected
					controller.deactivateYmir();
					requestFocus();
				}
				;
			}
		});



		customizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (RunningGamePanel.isPaused) {
					MainFrame.runningGamePanel.setVisible(false);
					MainFrame.customizeGamePanel.setVisible(true);
				} else {
					RunningGamePanel.isPaused = true;
					
					controller.stopCollisionChecking();
					JOptionPane.showMessageDialog(MainFrame.runningGamePanel,
							"Before customizing the game, please pause the game");
					RunningGamePanel.isPaused = false;
					
					controller.startCollisionChecking();
					requestFocus();
					MainFrame.runningGamePanel.setFocusable(true);
					noblePhantasm.setFocusable(true);

				}
			}
		});

		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CreateWorldPanel.isCreateButtonPressed = false;

				if (isPaused) {
					int reply = JOptionPane.showConfirmDialog(MainFrame.runningGamePanel,
							"Do you want to save the game before quiting?", "Confirm", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.NO_OPTION) {

						controller.resetGame();

						noblePhantasm.setBounds(UIConstants.phantasmInitialX, UIConstants.phantasmInitialY, UIConstants.phantasmLength, 20);

						sphere.setBounds(UIConstants.sphereInitialX, UIConstants.sphereInitialY, 16, 16);

						for (int i = 0; i < MainFrame.numHitsArrayList.size(); i++) {
							MainFrame.numHitsArrayList.get(i).setVisible(false);
						}

						for (JLabel j : MainFrame.obstacles) {
							j.setVisible(false);
						}
						MainFrame.obstacles.clear();
						
						for (JLabel j : magicalHexes) {
							j.setVisible(false);
						}
						magicalHexes.clear();
						
						for (JLabel j : remainings) {
							j.setVisible(false);
						}
						remainings.clear();
						
						for (JLabel j : giftBoxes) {
							j.setVisible(false);
						}
						giftBoxes.clear();
						
						MainFrame.runningGamePanel.setVisible(false);
						MainFrame.openingPanel.setVisible(true);
						second = 1;
					} else if (reply == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(MainFrame.runningGamePanel,
								"Please pause game and press save button to save the game.");

					}
				} else {
					JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "Please pause the game first");
				}

				requestFocus();
				setFocusable(true);
				setFocusTraversalKeysEnabled(false);
				noblePhantasm.setFocusable(true);
				noblePhantasm.setFocusTraversalKeysEnabled(false);

				noblePhantasm.setVisible(true);
				sphere.setVisible(true);
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPaused) {

					String gameName = JOptionPane.showInputDialog("Please give a name to saved game.");
					if (gameName == null) {
						JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "Cancelled.");
					} else if (gameName.equals("")) {
						JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "You cannot leave name empty!");
					} else {
						controller.saveGame(gameName);
						JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "Game is saved successfully!");
						MainFrame.runningGamePanel.setVisible(false);
						MainFrame.openingPanel.setVisible(true);
					}

				} else {
					JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "You must pause first.");
				}
				setFocusable(true);
				setFocusTraversalKeysEnabled(false);
				noblePhantasm.setFocusable(true);
				noblePhantasm.setFocusTraversalKeysEnabled(false);
			}
		});

		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPaused) {
					try {
						ArrayList<ArrayList<String>> gamesList = controller.loadGame();
						int gameCount = gamesList.size();
						if (gamesList.get(0).size() == 0) {
							JOptionPane.showMessageDialog(MainFrame.createOrLoadOrCustomizePanel,
									"You do not have any saved game.");
						} else {
							String gameNames = "Your saved games: Please enter the number of game you want to load:\n";
							for (int i = 0; i < gameCount; i++) {
								gameNames += (i + 1);
								gameNames += ": " + gamesList.get(i).get(2);
								gameNames += "\n";
							}

							String choosedGame = JOptionPane.showInputDialog(gameNames);
							if (choosedGame == null) {
								JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "Cancelled.");
							} else if (choosedGame.equals("")) {
								JOptionPane.showMessageDialog(MainFrame.runningGamePanel,
										"You leaved the game number to be loaded empty");
							} else {
								CreateOrLoadOrCustomizePanel.loadSavedGame(controller.getCurrentPlayerUsername(),
										gamesList.get(Integer.parseInt(choosedGame) - 1));
								MainFrame.createOrLoadOrCustomizePanel.setVisible(false);
								setVisible(true);
								sphere.setVisible(true);
								noblePhantasm.setVisible(true);
							}
						}

					} catch (IOException e1) {

						e1.printStackTrace();
					}

				} else {
					//MainFrame.runningGamePanel.timer.stop();
					JOptionPane.showMessageDialog(MainFrame.createWorldPanel,
							"You are trying to load an another game you saved while you are playing this game. "
									+ "Please pause this game to load an another game.");
					//MainFrame.runningGamePanel.timer.start();
				}
				MainFrame.runningGamePanel.setFocusable(true);
				MainFrame.runningGamePanel.setFocusTraversalKeysEnabled(false);
				noblePhantasm.setFocusable(true);
				noblePhantasm.setFocusTraversalKeysEnabled(false);

			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//timer.start();
				controller.startCollisionChecking();
				isPaused = false;
				requestFocus();
			}
		});

		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noblePhantasm.setFocusable(false);
				noblePhantasm.setFocusTraversalKeysEnabled(true);
				MainFrame.runningGamePanel.setFocusable(false);
				MainFrame.runningGamePanel.setFocusTraversalKeysEnabled(true);
				isPaused = true;
				controller.stopCollisionChecking();
				//timer.stop();
				requestFocus();

			}
		});

		resumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				noblePhantasm.setFocusable(true);
				noblePhantasm.setFocusTraversalKeysEnabled(false);
				MainFrame.runningGamePanel.setFocusable(true);
				MainFrame.runningGamePanel.setFocusTraversalKeysEnabled(false);
				//timer.restart();
				controller.startCollisionChecking();
				isPaused = false;
				requestFocus();

			}
		});

		this.addKeyListener(this.gameListener);

		this.add(sphere);
		this.add(customizeButton);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
	}

	public JButton getCustomizeButton() {
		return customizeButton;
	}

	public void setCustomizeButton(JButton customizeButton) {
		this.customizeButton = customizeButton;
	}

	public JButton getPauseButton() {
		return pauseButton;
	}

	public void setPauseButton(JButton pauseButton) {
		this.pauseButton = pauseButton;
	}

	public JButton getQuitButton() {
		return quitButton;
	}

	public void setQuitButton(JButton quitButton) {
		this.quitButton = quitButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JButton getLoadButton() {
		return loadButton;
	}

	public void setLoadButton(JButton loadButton) {
		this.loadButton = loadButton;
	}

	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	public void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

	public JLabel getLivesLabel() {
		return livesLabel;
	}

	public void setLivesLabel(JLabel livesLabel) {
		this.livesLabel = livesLabel;
	}



	/*public static BufferedImage rotate2(BufferedImage img) {

		// Getting Dimensions of image
		int w = img.getWidth();
		int h = img.getHeight();

		// Creating a new buffered image
		BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		// creating Graphics in buffered image
		Graphics2D g2 = newImage.createGraphics();

		// Rotating image by degrees using toradians()
		// method
		// and setting new dimension t it
		g2.rotate(Math.toRadians(angle), w / 2, h / 2);
		g2.drawImage(img, null, 0, 0);

		// Return rotated buffer image
		return newImage;
	}*/

	private KeyListener gameListener = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {

			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case 37:
				controller.movePhantasmLeft();
				break;
			case 39:
				controller.movePhantasmRight();
				break;
			case 87:
				if (RunningGamePanel.isPaused == false) {
					controller.startCollisionChecking();
				}
				break;
			case 40:
				controller.doubleSpeed();
				break;

			case 65: //A

				//ROTATION DOES NOT WORK WELL.

				/*angle = 0;
				isRotating = true;
				try {
					bf = ImageIO.read(new File("phantasm-rotated.png"));
				} catch (IOException e1) {

					e1.printStackTrace();
				}

				if (isRotating == true) {

					while (angle != 120) {
						Runnable countSecond = new Runnable() {
							public void run() {
								angle = angle + 20;
								bf = rotate2(bf);
								GameImages.noblePhantasmIcon1 = new ImageIcon(bf);
								GameImages.noblePhantasmIm = GameImages.noblePhantasmIcon1.getImage()
										.getScaledInstance(UIConstants.width / 10, 20, Image.SCALE_SMOOTH);
								GameImages.noblePhantasmIcon = new ImageIcon(GameImages.noblePhantasmIm);
								noblePhantasm.setIcon(GameImages.noblePhantasmIcon);
								noblePhantasm.setSize(UIConstants.width / 10, UIConstants.width / 10);
								noblePhantasm.repaint();
							}
						};
						ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
						executor.scheduleAtFixedRate(countSecond, 0, 1, TimeUnit.SECONDS);
						if (angle > 135) {
							angle = 135;
							isRotating = false;
							break;
						}
					}

				}*/

				break;

			case 68: /// D
				/*try {

					//ROTATION DOES NOT WORK WELL.

					BufferedImage bf1 = ImageIO.read(new File("phantasm-rotated.png"));
					angle = 45;
					bf1 = rotate2(bf1);
					GameImages.noblePhantasmIcon1 = new ImageIcon(bf1);
					GameImages.noblePhantasmIm = GameImages.noblePhantasmIcon1.getImage()
							.getScaledInstance(UIConstants.width / 10, 20, Image.SCALE_SMOOTH);
					GameImages.noblePhantasmIcon = new ImageIcon(GameImages.noblePhantasmIm);
					noblePhantasm.setIcon(GameImages.noblePhantasmIcon);
					noblePhantasm.setSize(UIConstants.width / 10, UIConstants.width / 10);
					// noblePhantasm.setBounds(19*MainFrame.width/40-MainFrame.width/100,8*MainFrame.height/10,MainFrame.width/10,20);
					noblePhantasm.repaint();
				} catch (IOException e1) {

					e1.printStackTrace();
				}*/

				break;
			case 67: // C
				controller.useChanceGivingAbility();
				break;

			case 84, 78: // T, N
				controller.useNoblePhantasmExpansionAbility();
			break;

			case 72: // H
				controller.useMagicalHexAbility();
				break;
			case 32: // SPACE
				controller.fireMagicalHex();
				break;
			case 85: // U
				controller.useUnstoppableEnchantedSphereAbility();
				break;
			}

		}

		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {

			case 40:
				controller.halveSpeed();
				break;

			case 65:
				/* ROTATION DOES NOT WORK WELL.
				isRotating = true;
				try {
					bf = ImageIO.read(new File("phantasm-rotated.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (RunningGamePanel.isPaused == false && isRotating == true) {
					while (angle != 0) {
						Runnable countSecond = new Runnable() {
							public void run() {
								angle = angle + 45;
								bf = rotate2(bf);
								noblePhantasm.setIcon(GameImages.noblePhantasmIcon);
								noblePhantasm.repaint();
							}
						};
						ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
						executor.scheduleAtFixedRate(countSecond, 0, 1, TimeUnit.SECONDS);
						if (angle < 0) {
							angle = 0;
							isRotating = false;
							break;
						}
					}

				}

				isRotating = true;
				 */
				break;

			case 68:

				break;

			default:

			}
		}

	};

	@Override
	public void onScoreChange(double score) {
		scoreLabel.setText("Score:      " + String.format("%.2f", score));
	}

	@Override
	public void onLivesChange(int lives) {
		livesLabel.setText("Lives:      " + lives);
	}

	@Override
	public void collided(int index, String currentCollidedObstacle, String currentCollisionSide) {

		if (currentCollidedObstacle.equals("SimpleObstacle")) {

			MainFrame.disappear(MainFrame.obstacles.get(index));

		} else if (currentCollidedObstacle.equals("FirmObstacle")) {

			int locX = MainFrame.obstacles.get(index).getX();
			int locY = MainFrame.obstacles.get(index).getY();
			for (JLabel numHits : MainFrame.numHitsArrayList) {
				if (locX + 10 == numHits.getX() && locY - 2 == numHits.getY()) {
					if (NeedForSpearGame.returnUnstoppableActivity() == true) {
						MainFrame.disappear(MainFrame.obstacles.get(index));
						numHits.setVisible(false);
						MainFrame.numHitsArrayList.remove(numHits);
					}

					else {

						int numRemainingHits = Integer.parseInt(numHits.getText());
						if (numRemainingHits != 1) {
							numHits.setText(Integer.toString(numRemainingHits - 1));

						} else {
							MainFrame.disappear(MainFrame.obstacles.get(index));
							numHits.setVisible(false);
							MainFrame.numHitsArrayList.remove(numHits);

						}

					}
					break;
				}
			}

		} else if (currentCollidedObstacle.equals("GiftObstacle")) {

			MainFrame.disappear(MainFrame.obstacles.get(index));

		} else if (currentCollidedObstacle.equals("ExplosiveObstacle")) {

			MainFrame.disappear(MainFrame.obstacles.get(index));

		} else if (currentCollidedObstacle.equals("HollowObstacle")) {

			MainFrame.disappear(MainFrame.obstacles.get(index));

		}
	}


	@Override
	public void onSphereMovement(int x, int y) {
		sphere.setLocation(x, y);
	}

	@Override
	public void inChanceGivingAbility() {

	}

	@Override
	public void inPhantasmExpansionAbility() {

	}

	@Override
	public void inMagicalHexAbility() {
		GameImages.leftCanonLabel.setBounds(noblePhantasm.getX(), noblePhantasm.getY() - 40, 35, 45);
		GameImages.rightCanonLabel.setBounds(noblePhantasm.getX() + noblePhantasm.getWidth() - 35,
				noblePhantasm.getY() - 40, 35, 45);
		add(GameImages.leftCanonLabel);
		add(GameImages.rightCanonLabel);
		GameImages.leftCanonLabel.setVisible(true);
		GameImages.rightCanonLabel.setVisible(true);
	}

	@Override
	public void inUnstoppableSphereAbility() {
		sphere.setIcon(GameImages.unstoppableIcon2);
	}

	@Override
	public void inEndUnstoppableSphereAbility() {
		sphere.setIcon(GameImages.sphereIcon);
	}

	@Override
	public void inXLocationChangeOfPhantasm(int x) {
		noblePhantasm.setLocation(x, noblePhantasm.getY());
		GameImages.leftCanonLabel.setLocation(noblePhantasm.getX(), noblePhantasm.getY() - 40);
		GameImages.rightCanonLabel.setLocation(noblePhantasm.getX() + noblePhantasm.getWidth() - 35,
				noblePhantasm.getY() - 40);
	}

	@Override
	public void inLengthChange(int length) {
		noblePhantasm.setBounds(noblePhantasm.getX(), noblePhantasm.getY(), length, 20);
		Image noblePhantasmIm = GameImages.noblePhantasmIcon.getImage().getScaledInstance(length, 20,
				Image.SCALE_SMOOTH);
		ImageIcon noblePhantasmIconDoubled = new ImageIcon(noblePhantasmIm);
		noblePhantasm.setIcon(noblePhantasmIconDoubled);
	}

	@Override
	public void onLiveLose(int lives) {
		JOptionPane.showMessageDialog(MainFrame.runningGamePanel,
				"You have lost 1 life." + "\nYou have " + lives + " lives left.");
	}

	@Override
	public void onRemainingMovement(int index, int x, int y) {
		remainings.get(index).setLocation(x, y);
	}

	@Override
	public void onGiftBoxMovement(int index, int x, int y) {
		giftBoxes.get(index).setLocation(x, y);
	}

	@Override
	public void onGiftBoxTouch(int index) {
		giftBoxes.get(index).setVisible(false);
		giftBoxes.remove(index);
	}

	@Override
	public void onRemainingTouch(int index) {
		remainings.get(index).setVisible(false);
		remainings.remove(index);
	}

	@Override
	public void onRemainingCreation(int x, int y) {

		JLabel remaining = new JLabel();
		remaining.setIcon(GameImages.remainingIcon);
		remaining.setBounds(x, y, 15, 15);
		remaining.setVisible(true);
		add(remaining);
		remainings.add(remaining);

	}

	@Override
	public void onGiftBoxCreation(int x, int y) {

		JLabel giftBox = new JLabel();
		giftBox.setIcon(GameImages.giftBoxIcon);
		giftBox.setBounds(x, y, 25, 25);
		giftBox.setVisible(true);
		add(giftBox);
		giftBoxes.add(giftBox);
	}

	@Override
	public void onMagicalHexCreation(int x, int y) {
		JLabel magicalHex = new JLabel();
		magicalHex.setIcon(GameImages.magicalBallIcon2);
		magicalHex.setBounds(x, y, 16, 16);
		magicalHex.setVisible(true);
		add(magicalHex);
		magicalHexes.add(magicalHex);
	}

	@Override
	public void onMagicalHexMovement(int index, int x, int y) {
		magicalHexes.get(index).setLocation(x, y);
	}

	@Override
	public void onMagicalHexCollision(int index) {
		magicalHexes.get(index).setVisible(false);
		magicalHexes.remove(index);
	}

	@Override
	public void inEndMagicalHexAbility() {
		GameImages.leftCanonLabel.setVisible(false);
		GameImages.rightCanonLabel.setVisible(false);
	}

	@Override
	public void onWinTheGame(double score) {
		JOptionPane.showMessageDialog(MainFrame.runningGamePanel,
				"Congratulations! You have broken all obstacles! \n Your Score is " + score);
		//timer.stop();
		controller.stopCollisionChecking();
		noblePhantasm.setBounds(UIConstants.phantasmInitialX, UIConstants.phantasmInitialY, UIConstants.phantasmLength,
				20);
		sphere.setBounds(UIConstants.sphereInitialX, UIConstants.sphereInitialY, 16, 16);
		RunningGamePanel.isPaused = true;
		CreateWorldPanel.isCreateButtonPressed = false;

		setFocusable(false);
		setFocusTraversalKeysEnabled(true);
		noblePhantasm.setFocusable(false);
		noblePhantasm.setFocusTraversalKeysEnabled(true);

		for (int i = 0; i < giftBoxes.size(); i++) {
			giftBoxes.get(i).setVisible(false);
			giftBoxes.get(i).setIcon(null);
		}


		for (int i = 0; i < remainings.size(); i++) {
			remainings.get(i).setVisible(false);
			remainings.get(i).setIcon(null);
		}

		for (int i = 0; i < magicalHexes.size(); i++) {
			magicalHexes.get(i).setVisible(false);
			magicalHexes.get(i).setIcon(null);
		}

		remainings.clear();
		giftBoxes.clear();
		magicalHexes.clear();

		for (int i = 0; i < MainFrame.numHitsArrayList.size(); i++) {
			MainFrame.numHitsArrayList.get(i).setVisible(false);
			MainFrame.numHitsArrayList.get(i).setIcon(null);
		}
		MainFrame.numHitsArrayList.clear();

		GameImages.leftCanonLabel.setVisible(false);
		GameImages.rightCanonLabel.setVisible(false);
		GameImages.firstBallLabel.setVisible(false);
		GameImages.secondBallLabel.setVisible(false);

		sphere.setVisible(false);
		noblePhantasm.setVisible(false);
	}

	@Override
	public void onLoseTheGame(double score) {

		setFocusable(false);
		setFocusTraversalKeysEnabled(true);
		noblePhantasm.setFocusable(false);
		noblePhantasm.setFocusTraversalKeysEnabled(true);

		for (int i = 0; i < MainFrame.numHitsArrayList.size(); i++) {
			MainFrame.numHitsArrayList.get(i).setVisible(false);
		}
		MainFrame.numHitsArrayList.clear();

		for (int i = 0; i < MainFrame.obstacles.size(); i++) {
			MainFrame.obstacles.get(i).setVisible(false);
			MainFrame.obstacles.get(i).setIcon(null);
		}
		MainFrame.obstacles.clear();

		noblePhantasm.setBounds(UIConstants.phantasmInitialX, UIConstants.phantasmInitialY, UIConstants.phantasmLength,
				20);
		sphere.setBounds(UIConstants.sphereInitialX, UIConstants.sphereInitialY, 16, 16);
		sphere.setVisible(false);

		noblePhantasm.setVisible(false);

		for (int i = 0; i < giftBoxes.size(); i++) {
			giftBoxes.get(i).setVisible(false);
			giftBoxes.get(i).setIcon(null);
		}


		for (int i = 0; i < remainings.size(); i++) {
			remainings.get(i).setVisible(false);
			remainings.get(i).setIcon(null);
		}

		for (int i = 0; i < magicalHexes.size(); i++) {
			magicalHexes.get(i).setVisible(false);
			magicalHexes.get(i).setIcon(null);
		}


		remainings.clear();
		giftBoxes.clear();
		magicalHexes.clear();

		GameImages.leftCanonLabel.setVisible(false);
		GameImages.rightCanonLabel.setVisible(false);
		GameImages.firstBallLabel.setVisible(false);
		GameImages.secondBallLabel.setVisible(false);

		CreateWorldPanel.isCreateButtonPressed = false;

		JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "Game Over! \nYour Score is " + score);

		second = 1;

		RunningGamePanel.isPaused = true;

	}

	@Override
	public void inChanceGivingCountChange(int count) {
		chanceGivingAbilityNum.setText(" " + count);
	}

	@Override
	public void inPhantasmExpansionCountChange(int count) {
		noblePhantasmExpansionNum.setText(" " + count);
	}

	@Override
	public void inMagicalHexCountChange(int count) {
		magicalHexNum.setText(" " + count);
	}

	@Override
	public void inUnstoppableCountChange(int count) {
		unstoppableEnchantedSphereNum.setText(" " + count);
	}

	@Override
	public void inMagicalAbilityInitializition(int chanceGiving, int phantasmExpansion, int magicalHex,
			int unstoppable) {
		chanceGivingAbilityNum.setText(" " + chanceGiving);
		noblePhantasmExpansionNum.setText(" " + phantasmExpansion);
		magicalHexNum.setText(" " + magicalHex);
		unstoppableEnchantedSphereNum.setText(" " + unstoppable);
	}

	@Override
	public void onObstacleMovement(int index, int x, int y) {

		for(JLabel j:MainFrame.numHitsArrayList) {
			if(j.getX()==MainFrame.obstacles.get(index).getX()+10 && j.getY()==MainFrame.obstacles.get(index).getY()-2) {
				j.setLocation(x+10,y-2);
			}
		}

		MainFrame.obstacles.get(index).setLocation(x,y);
	}

	@Override
	public void onHollowObstacleCreation(int x, int y) {

		JLabel hollowObstacle=new JLabel();
		MainFrame.obstacles.add(hollowObstacle);   
		add(hollowObstacle);
		hollowObstacle.setIcon(GameImages.hollowIcon);
		hollowObstacle.setBounds(x,y,UIConstants.rectangleObstacleWidth,20);
	}

	@Override
	public void onUseHollowPurpleAbility() {
		JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "Ymir used Hollow Purple Ability!");
	}


	@Override
	public void onUseInfiniteVoidAbility() {
		JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "Ymir used Infinite Void Ability!");
	}

	@Override
	public void onUseDoubleAccelAbility() {
		JOptionPane.showMessageDialog(MainFrame.runningGamePanel, "Ymir used Double Accel Ability!");
	}

	@Override
	public void onFrozen(String obstacleName, int index) {
		// TODO Auto-generated method stub
		if(obstacleName.equals("SimpleObstacle") || obstacleName.equals("GiftObstacle") || obstacleName.equals("HollowObstacle")) {
			MainFrame.obstacles.get(index).setIcon(GameImages.frozenSimpleIcon);
		} else if(obstacleName.equals("FirmObstacle")){
			MainFrame.obstacles.get(index).setIcon(GameImages.frozenFirmIcon);
		} else if(obstacleName.equals("ExplosiveObstacle")){
			MainFrame.obstacles.get(index).setIcon(GameImages.frozenExplosiveIcon);
		}
	}

	@Override
	public void onFrozenEnded(String obstacleName, int index) {
		// TODO Auto-generated method stub
		if(obstacleName.equals("SimpleObstacle") || obstacleName.equals("GiftObstacle") || obstacleName.equals("HollowObstacle")) {
			MainFrame.obstacles.get(index).setIcon(GameImages.simpleIcon);
		} else if(obstacleName.equals("FirmObstacle")){
			MainFrame.obstacles.get(index).setIcon(GameImages.firmIcon);
		} else if(obstacleName.equals("ExplosiveObstacle")){
			MainFrame.obstacles.get(index).setIcon(GameImages.explosiveIcon);
		}
	}


}
