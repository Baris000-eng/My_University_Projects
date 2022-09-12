package domain.saveLoad;

import java.io.IOException;
import java.util.ArrayList;


public interface ISaveLoadAdapter { 

	public void saveGame(String gameName);
	public void addUser(String username, String password);
	public Boolean userExist(String username,String password) throws IOException ;
	public Boolean usernameExist(String username) throws IOException;
	public ArrayList<ArrayList<String>> loadGame(String username) throws IOException;
	
}
