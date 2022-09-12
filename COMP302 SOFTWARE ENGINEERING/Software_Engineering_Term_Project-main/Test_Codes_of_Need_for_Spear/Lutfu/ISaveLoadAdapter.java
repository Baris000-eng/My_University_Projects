package domain.saveLoad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.obstacles.Obstacle;
import domain.player.EnchantedSphere;
import domain.player.Player;

public interface ISaveLoadAdapter {   //WE CREATED THIS CLASS BUT DID NOT APPLY ADAPTER PATTERN YET.

	public void saveGame(String gameName, Player player, EnchantedSphere sphere, List<Obstacle> obstacles);
	public void addUser(String username, String password);
	public Boolean userExist(String username,String password) throws IOException ;
	public Boolean usernameExist(String username) throws IOException;
	//public String listGames(String username);
	public ArrayList<ArrayList> loadGame(String username) throws IOException;
	
	//application of the adapter pattern
	
}
