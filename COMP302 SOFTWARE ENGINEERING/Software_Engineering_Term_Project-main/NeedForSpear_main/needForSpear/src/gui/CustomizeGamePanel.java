package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import domain.game.NeedForSpearGame;




@SuppressWarnings("serial")
public class CustomizeGamePanel extends JPanel{
	private int r = 0;
	private int g= 0;
	private int b= 0;
	public static JCheckBox ymir;
	public JLabel ymirLabel;
	public static boolean musicPlaying=false;
	private JSlider soundManager;
	private JButton playMusic;
	private JButton stopMusic;
	private static Clip clip;
	private static FloatControl fc;
	private static FloatControl fc2;
	private static  float curVol= 0;
	private JButton backButton;
	private JComboBox<String> backgroundColors;
	private JButton goToGame;
	private JLabel sound;
	private JLabel backColor;
	private JSlider brightnessManager;
	private JLabel brightness;
	private JButton easy;
	private JButton hard;
	
	private NeedForSpearGame controller=new NeedForSpearGame("txt");
	


	public JLabel getBrightness() {
		return brightness;
	}
	public void setBrightness(JLabel brightness) {
		this.brightness = brightness;
	}
	public Clip getClip() {
		return clip;
	}
	public void setClip(Clip clip2) {
		clip = clip2;
	}
	public JButton getPlayMusic() {
		return playMusic;
	}
	public void setPlayMusic(JButton playMusic) {
		this.playMusic = playMusic;
	}
	public JButton getStopMusic() {
		return stopMusic;
	}
	public void setStopMusic(JButton stopMusic) {
		this.stopMusic = stopMusic;
	}



	public JLabel getSound() {
		return sound;
	}
	public void setSound(JLabel sound) {
		this.sound = sound;
	}


	public CustomizeGamePanel() {
		super();
		soundManager= new JSlider(-80,6);
		soundManager.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
			
				curVol= soundManager.getValue();
				if(musicPlaying) {
					fc.setValue(curVol);
				}
				
			}

		});
		
		
		brightnessManager= new JSlider(0,255);
		brightnessManager.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				r= brightnessManager.getValue();
				g= brightnessManager.getValue();
				b= brightnessManager.getValue();
				if(backgroundColors.getSelectedItem().equals("Red")){
					g= 0;
					b= 0;
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.openingPanel.setBackground(new Color(r,g,b));
					MainFrame.logInPanel.setBackground(new Color(r,g,b));
					MainFrame.createWorldPanel.setBackground(new Color(r,g,b));
					MainFrame.registerPanel.setBackground(new Color(r,g,b));
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.runningGamePanel.setBackground(new Color(r,g,b));
					MainFrame.createOrLoadOrCustomizePanel.setBackground(new Color(r,g,b));
				} else if(backgroundColors.getSelectedItem().equals("Blue")) {
					r= 0;
					g= 0;
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.openingPanel.setBackground(new Color(r,g,b));
					MainFrame.logInPanel.setBackground(new Color(r,g,b));
					MainFrame.createWorldPanel.setBackground(new Color(r,g,b));
					MainFrame.registerPanel.setBackground(new Color(r,g,b));
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.runningGamePanel.setBackground(new Color(r,g,b));
					MainFrame.createOrLoadOrCustomizePanel.setBackground(new Color(r,g,b));
				} else if(backgroundColors.getSelectedItem().equals("Yellow")) {
					b= 0;
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.openingPanel.setBackground(new Color(r,g,b));
					MainFrame.logInPanel.setBackground(new Color(r,g,b));
					MainFrame.createWorldPanel.setBackground(new Color(r,g,b));
					MainFrame.registerPanel.setBackground(new Color(r,g,b));
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.runningGamePanel.setBackground(new Color(r,g,b));
					MainFrame.createOrLoadOrCustomizePanel.setBackground(new Color(r,g,b));
				} else if(backgroundColors.getSelectedItem().equals("Orange")) {
					b= 0;
					r= 255;
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.openingPanel.setBackground(new Color(r,g,b));
					MainFrame.logInPanel.setBackground(new Color(r,g,b));
					MainFrame.createWorldPanel.setBackground(new Color(r,g,b));
					MainFrame.registerPanel.setBackground(new Color(r,g,b));
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.runningGamePanel.setBackground(new Color(r,g,b));
				} else if(backgroundColors.getSelectedItem().equals("Green")) {
					r= 0;
					b= 0;
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.openingPanel.setBackground(new Color(r,g,b));
					MainFrame.logInPanel.setBackground(new Color(r,g,b));
					MainFrame.createWorldPanel.setBackground(new Color(r,g,b));
					MainFrame.registerPanel.setBackground(new Color(r,g,b));
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.runningGamePanel.setBackground(new Color(r,g,b));
				} else if(backgroundColors.getSelectedItem().equals("Pink")) {
					r= 255;
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.openingPanel.setBackground(new Color(r,g,b));
					MainFrame.logInPanel.setBackground(new Color(r,g,b));
					MainFrame.createWorldPanel.setBackground(new Color(r,g,b));
					MainFrame.registerPanel.setBackground(new Color(r,g,b));
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.runningGamePanel.setBackground(new Color(r,g,b));
				} else if(backgroundColors.getSelectedItem().equals("Cyan")) {
					g= 255;
					b= 255;
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.openingPanel.setBackground(new Color(r,g,b));
					MainFrame.logInPanel.setBackground(new Color(r,g,b));
					MainFrame.createWorldPanel.setBackground(new Color(r,g,b));
					MainFrame.registerPanel.setBackground(new Color(r,g,b));
					MainFrame.customizeGamePanel.setBackground(new Color(r,g,b));
					MainFrame.runningGamePanel.setBackground(new Color(r,g,b));
				}  
			}

		});
		ymirLabel= new JLabel();
		ymirLabel.setText("Ymir: ");
		ymirLabel.setBounds(UIConstants.middleX-UIConstants.widthButton,UIConstants.middleY-4*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		this.add(ymirLabel);
		ymir= new JCheckBox();
		ymir.setBounds(UIConstants.middleX,UIConstants.middleY-4*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		this.add(ymir);

		sound= new JLabel();
		backColor= new JLabel();
		String[] colors = {"Blue", "Dark Gray","Red","Green","Yellow","Pink","Orange","Cyan","Light Gray","Black"};
		backgroundColors = new JComboBox<String>(colors);
		brightness= new JLabel();
		
		
		backButton= new JButton();
		goToGame= new JButton();
		playMusic= new JButton();
		stopMusic= new JButton();
		easy= new JButton();
		hard= new JButton();


		stopMusic.setText("Stop Music");
		goToGame.setText("Go To Game");
		playMusic.setText("Play Music");
		easy.setText("Easy Mode");
		hard.setText("Hard Mode");

		sound.setText("Sound Level:");
		backColor.setText("Background Color:");
		backButton.setText("Back");
		brightness.setText("Brightness Level:");

		sound.setBounds(UIConstants.middleX-UIConstants.widthButton,UIConstants.middleY-2*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		soundManager.setBounds(UIConstants.middleX,UIConstants.middleY-2*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		brightness.setBounds(UIConstants.middleX-UIConstants.widthButton,UIConstants.middleY-3*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		brightnessManager.setBounds(UIConstants.middleX,UIConstants.middleY-3*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		playMusic.setBounds(UIConstants.middleX-UIConstants.widthButton,UIConstants.middleY-UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		stopMusic.setBounds(UIConstants.middleX,UIConstants.middleY-UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		backColor.setBounds(UIConstants.middleX-UIConstants.widthButton,UIConstants.middleY+UIConstants.heightButton/2,UIConstants.widthButton,UIConstants.heightButton);
		backgroundColors.setBounds(UIConstants.middleX,UIConstants.middleY+UIConstants.heightButton/2,UIConstants.widthButton,UIConstants.heightButton);
		easy.setBounds(UIConstants.middleX-UIConstants.widthButton,UIConstants.middleY+7*UIConstants.heightButton/4,UIConstants.widthButton,UIConstants.heightButton);
		hard.setBounds(UIConstants.middleX,UIConstants.middleY+7*UIConstants.heightButton/4,UIConstants.widthButton,UIConstants.heightButton);
		backButton.setBounds(UIConstants.middleX-UIConstants.widthButton,UIConstants.middleY+3*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		goToGame.setBounds(UIConstants.middleX,UIConstants.middleY+3*UIConstants.heightButton,UIConstants.widthButton,UIConstants.heightButton);
		
		soundManager.setPaintTrack(true);
		soundManager.setPaintLabels(true);
		soundManager.setPaintTicks(true);
		
		brightnessManager.setPaintTrack(true);
		brightnessManager.setPaintLabels(true);
		brightnessManager.setPaintTicks(true);


        


		this.add(backButton);
		this.add(soundManager);
		this.add(backgroundColors); 
		this.add(goToGame);
		this.add(sound);
		this.add(backColor);
		this.add(playMusic);
		this.add(stopMusic);
		this.add(brightnessManager);
		this.add(brightness);
		this.add(easy);
		this.add(hard);
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.customizeGamePanel.setVisible(false);
				MainFrame.createOrLoadOrCustomizePanel.setVisible(true);
			}
		});

		backgroundColors.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == backgroundColors) {
					String selected = (String) backgroundColors.getSelectedItem();
					MainFrame.logInPanel.getUserLabel().setForeground(Color.BLACK);
					MainFrame.logInPanel.getPasswordLabel().setForeground(Color.BLACK);
					MainFrame.createWorldPanel.getSimpleObsLabel().setForeground(Color.BLACK);
					MainFrame.createWorldPanel.getGiftObsLabel().setForeground(Color.BLACK);
					MainFrame.createWorldPanel.getExplObsLabel().setForeground(Color.BLACK);
					MainFrame.createWorldPanel.getFirmObsLabel().setForeground(Color.BLACK);
					MainFrame.registerPanel.getUserLabel().setForeground(Color.BLACK);
					MainFrame.registerPanel.getPasswordLabel().setForeground(Color.BLACK);
					MainFrame.openingPanel.getWelcome().setForeground(Color.BLACK);
					MainFrame.runningGamePanel.getScoreLabel().setForeground(Color.BLACK);
					MainFrame.runningGamePanel.getLivesLabel().setForeground(Color.BLACK);
					if(selected.equals("Blue")) {
						MainFrame.openingPanel.setBackground(Color.blue);
						MainFrame.logInPanel.setBackground(Color.blue);
						MainFrame.createWorldPanel.setBackground(Color.blue);
						MainFrame.registerPanel.setBackground(Color.blue);
						MainFrame.customizeGamePanel.setBackground(Color.blue);
						MainFrame.runningGamePanel.setBackground(Color.blue);
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.blue);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.WHITE);
						MainFrame.runningGamePanel.getLivesLabel().setForeground(Color.WHITE);
						MainFrame.runningGamePanel.getScoreLabel().setForeground(Color.WHITE);
					}
					if(selected.equals("Red")) {
						MainFrame.openingPanel.setBackground(Color.red);
						MainFrame.logInPanel.setBackground(Color.red);
						MainFrame.createWorldPanel.setBackground(Color.red);
						MainFrame.registerPanel.setBackground(Color.red);
						MainFrame.customizeGamePanel.setBackground(Color.red);
						MainFrame.runningGamePanel.setBackground(Color.red);
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.red);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.BLACK);
					}
					if(selected.equals("Dark Gray")) {
						MainFrame.openingPanel.setBackground( Color.DARK_GRAY);
						MainFrame.logInPanel.setBackground( Color.DARK_GRAY );
						MainFrame.createWorldPanel.setBackground( Color.DARK_GRAY );
						MainFrame.registerPanel.setBackground( Color.DARK_GRAY );
						MainFrame.customizeGamePanel.setBackground( Color.DARK_GRAY );
						MainFrame.runningGamePanel.setBackground( Color.DARK_GRAY  );
						MainFrame.createOrLoadOrCustomizePanel.setBackground( Color.DARK_GRAY);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.WHITE);

					}
					if(selected.equals("Green")) {
						MainFrame.openingPanel.setBackground(Color.green);
						MainFrame.logInPanel.setBackground(Color.green);
						MainFrame.createWorldPanel.setBackground(Color.green);
						MainFrame.registerPanel.setBackground(Color.green);
						MainFrame.customizeGamePanel.setBackground(Color.green);
						MainFrame.runningGamePanel.setBackground(Color.green);	
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.green);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.BLACK);
					}
					if(selected.equals("Yellow")) {
						MainFrame.openingPanel.setBackground(Color.yellow);
						MainFrame.logInPanel.setBackground(Color.yellow);
						MainFrame.createWorldPanel.setBackground(Color.yellow);
						MainFrame.registerPanel.setBackground(Color.yellow);
						MainFrame.customizeGamePanel.setBackground(Color.yellow);
						MainFrame.runningGamePanel.setBackground(Color.yellow);
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.yellow);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.BLACK);
					}
					if(selected.equals("Pink")) {
						MainFrame.openingPanel.setBackground(Color.pink);
						MainFrame.logInPanel.setBackground(Color.pink);
						MainFrame.createWorldPanel.setBackground(Color.pink);
						MainFrame.registerPanel.setBackground(Color.pink);
						MainFrame.customizeGamePanel.setBackground(Color.pink);
						MainFrame.runningGamePanel.setBackground(Color.pink);
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.pink);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.BLACK);
					} 
					if(selected.equals("Orange")) {
						MainFrame.openingPanel.setBackground(Color.ORANGE);
						MainFrame.logInPanel.setBackground(Color.ORANGE);
						MainFrame.createWorldPanel.setBackground(Color.ORANGE);
						MainFrame.registerPanel.setBackground(Color.ORANGE);
						MainFrame.customizeGamePanel.setBackground(Color.ORANGE);
						MainFrame.runningGamePanel.setBackground(Color.ORANGE);
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.ORANGE);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.BLACK);
					} 
					if(selected.equals("Cyan")) {
						MainFrame.openingPanel.setBackground(Color.CYAN);
						MainFrame.logInPanel.setBackground(Color.CYAN);
						MainFrame.createWorldPanel.setBackground(Color.CYAN);
						MainFrame.registerPanel.setBackground(Color.CYAN);
						MainFrame.customizeGamePanel.setBackground(Color.CYAN);
						MainFrame.runningGamePanel.setBackground(Color.CYAN);
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.CYAN);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.BLACK);
					}
					if(selected.equals("Light Gray")) {
						MainFrame.openingPanel.setBackground(Color.LIGHT_GRAY);
						MainFrame.logInPanel.setBackground(Color.LIGHT_GRAY);
						MainFrame.createWorldPanel.setBackground(Color.LIGHT_GRAY);
						MainFrame.registerPanel.setBackground(Color.LIGHT_GRAY);
						MainFrame.customizeGamePanel.setBackground(Color.LIGHT_GRAY);
						MainFrame.runningGamePanel.setBackground(Color.LIGHT_GRAY);
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.LIGHT_GRAY);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.BLACK);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.BLACK);
					}
					if(selected.equals("Black")) {
						MainFrame.openingPanel.setBackground(Color.BLACK);
						MainFrame.logInPanel.setBackground(Color.BLACK);
						MainFrame.createWorldPanel.setBackground(Color.BLACK);
						MainFrame.registerPanel.setBackground(Color.BLACK);
						MainFrame.customizeGamePanel.setBackground(Color.BLACK);
						MainFrame.runningGamePanel.setBackground(Color.BLACK);
						MainFrame.createOrLoadOrCustomizePanel.setBackground(Color.BLACK);
						MainFrame.logInPanel.getUserLabel().setForeground(Color.WHITE);
						MainFrame.logInPanel.getPasswordLabel().setForeground(Color.WHITE);
						MainFrame.createWorldPanel.getSimpleObsLabel().setForeground(Color.WHITE);
						MainFrame.createWorldPanel.getGiftObsLabel().setForeground(Color.WHITE);
						MainFrame.createWorldPanel.getExplObsLabel().setForeground(Color.WHITE);
						MainFrame.createWorldPanel.getFirmObsLabel().setForeground(Color.WHITE);
						MainFrame.registerPanel.getUserLabel().setForeground(Color.WHITE);
						MainFrame.registerPanel.getPasswordLabel().setForeground(Color.WHITE);
						MainFrame.openingPanel.getWelcome().setForeground(Color.WHITE);
						MainFrame.runningGamePanel.getScoreLabel().setForeground(Color.WHITE);
						MainFrame.runningGamePanel.getLivesLabel().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getSound().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getBrightness().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getBackColor().setForeground(Color.WHITE);
						MainFrame.customizeGamePanel.getYmirLabel().setForeground(Color.WHITE);
						MainFrame.runningGamePanel.getLivesLabel().setForeground(Color.WHITE);
						MainFrame.runningGamePanel.getScoreLabel().setForeground(Color.WHITE);
						
					}
				}
			}
		});

		goToGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				MainFrame.customizeGamePanel.setVisible(false);
				MainFrame.runningGamePanel.setVisible(true);

			}

		});


		playMusic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				musicPlaying=true;
				playMusic();
			}

		});

		stopMusic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				musicPlaying=false;
				CustomizeGamePanel.stop();
			}
		});

		easy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				controller.setYmirDuration(60);
				JOptionPane.showMessageDialog(MainFrame.runningGamePanel,
						"When you activate Ymir, she will use her ability in each 60 seconds!");
			}
		});

		
		hard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				controller.setYmirDuration(15);
				JOptionPane.showMessageDialog(MainFrame.runningGamePanel,
						"When you activate Ymir, she will use her ability in each 15 seconds!");
			}
		});


		ymir.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {// checkbox has been selected
					controller.activateYmir();
					MainFrame.runningGamePanel.activateYmir.setSelected(true);
				} else {// checkbox has been deselected
					controller.deactivateYmir();
					MainFrame.runningGamePanel.activateYmir.setSelected(false);
				}
				;
			}
		});
		

		this.setBackground(Color.GREEN);
		this.setLayout(null);
	}

	public JButton getEasy() {
		return easy;
	}
	public void setEasy(JButton easy) {
		this.easy = easy;
	}
	public JButton getHard() {
		return hard;
	}
	public void setHard(JButton hard) {
		this.hard = hard;
	}
	public JLabel getYmirLabel() {
		return ymirLabel;
	}
	public void setYmirLabel(JLabel ymirLabel) {
		this.ymirLabel = ymirLabel;
	}
	public JLabel getBackColor() {
		return backColor;
	}
	public void setBackColor(JLabel backColor) {
		this.backColor = backColor;
	}
	public FloatControl getFc() {
		return fc;
	}
	public void setFc(FloatControl fc2) {
		fc=fc2;
	}
	public static FloatControl getFc2() {
		return fc2;
	}
	public static void setFc2(FloatControl fc2) {
		CustomizeGamePanel.fc2 = fc2;
	}
	public float getCurrentVolume() {
		return curVol;
	}
	public void setCurrentVolume(float currentVolume) {
		curVol = currentVolume;
	}
	
	public JButton getGoToGame() {
		return goToGame;
	}

	public static void playThemeSong(String fileLocation) {
		try {
			File file= new File(fileLocation);
			if(file.exists()) {
				AudioInputStream audioInp= AudioSystem.getAudioInputStream(file);
				clip= AudioSystem.getClip();
				clip.open(audioInp);
				fc= (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				System.out.println("File not found!!!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void stop() {
		clip.stop();
	}


	
	public static void playMusic() {
		playThemeSong("theme.wav");
	}
	
	

}


