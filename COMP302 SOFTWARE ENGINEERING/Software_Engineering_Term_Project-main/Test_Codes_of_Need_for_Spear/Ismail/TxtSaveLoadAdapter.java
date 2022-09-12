package domain.saveLoad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import domain.obstacles.Obstacle;
import domain.player.EnchantedSphere;
import domain.player.Player;

public class TxtSaveLoadAdapter implements ISaveLoadAdapter{

	public TxtSaveLoadAdapter() {

	}

	public void addUser(String username, String password) {
		//MODIFIES: adds the username and password to userNameAndPassword.txt file.
		try {
			Files.write(Paths.get("userNameAndPassword.txt"), (username+","+password+"\n").getBytes(), StandardOpenOption.APPEND);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public Boolean userExist(String username,String password) throws IOException { //searching user in the txt file 
		String []arr;
		BufferedReader br = new BufferedReader(new FileReader("userNameAndPassword.txt"));
		try {
			String line= br.readLine();
			while (line != null) {
				arr= line.split(",");
				if(username.equals(arr[0]) && password.equals(arr[1])) {
					return true;
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}

		return false;
	}

	public Boolean usernameExist(String username) throws IOException { //searching username in the txt file 
		// Effects : returns true if username exists in userNameAndPassword.txt file.
		String []arr;
		BufferedReader br = new BufferedReader(new FileReader("userNameAndPassword.txt"));
		try {
			String line= br.readLine();
			while (line != null) {
				arr= line.split(",");
				if(username.equals(arr[0])) {
					return true;
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return false;
	}

	public void saveGame(String gameName, Player player, EnchantedSphere sphere, List<Obstacle> obstacles) {
		try {
			Files.write(Paths.get("savedWorlds.txt"), (player+"\n").getBytes(), StandardOpenOption.APPEND);
			Files.write(Paths.get("savedWorlds.txt"), (gameName+"\n").getBytes(), StandardOpenOption.APPEND);
			Files.write(Paths.get("savedWorlds.txt"), (sphere+"\n").getBytes(), StandardOpenOption.APPEND);
			for(Obstacle obs: obstacles) {
				Files.write(Paths.get("savedWorlds.txt"), (obs+"\n").getBytes(), StandardOpenOption.APPEND);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public ArrayList<ArrayList> loadGame(String username) throws IOException {
		
		//EFFECTS:  Return an arraylist containing arraylists of strings which represents info of each saved game saved by user whose username is given username.
		
		BufferedReader br = new BufferedReader(new FileReader("savedWorlds.txt"));
		ArrayList<ArrayList> games=new ArrayList<ArrayList>();
		ArrayList<String> gameInfo=new ArrayList<String>();
		try {

			String line= br.readLine();
			//System.out.println(line);
			boolean userFound=false;
			while (line != null) {
				//System.out.println(line.split(" ")[0]);
				if(userFound==true) {
					if(line.split(" ")[0].equals("Obstacle")){
						gameInfo.add(line.split(" ")[1]);
						gameInfo.add(line.split(" ")[2]);
						gameInfo.add(line.split(" ")[3]);
						if(line.split(" ")[1].equals("FirmObstacle")) {
							gameInfo.add(line.split(" ")[4]);
						}
					} else {
						games.add(gameInfo);
						if(line.split(" ")[1].equals(username)) {
							gameInfo=new ArrayList<String>();
							gameInfo.add(line.split(" ")[2]);
							gameInfo.add(line.split(" ")[3]);
							line = br.readLine();
							gameInfo.add(line.split(" ")[0]);
							line = br.readLine();
							gameInfo.add(line.split(" ")[1]);
							gameInfo.add(line.split(" ")[2]);
						} else {
							userFound=false;
						}

					}

				} else if(line.split(" ")[0].equals("Player")&&line.split(" ")[1].equals(username)) {


					gameInfo=new ArrayList<String>();
					gameInfo.add(line.split(" ")[2]);
					gameInfo.add(line.split(" ")[3]);
					line = br.readLine();
					gameInfo.add(line);
					line = br.readLine();
					gameInfo.add(line.split(" ")[1]);
					gameInfo.add(line.split(" ")[2]);
					userFound=true;

				} 
				line = br.readLine();
				


			}
		} finally {
			br.close();
			games.add(gameInfo);
		}
		

		return games;
	}





}
