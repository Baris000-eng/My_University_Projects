package domain.saveLoad;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.obstacles.Obstacle;
import domain.player.EnchantedSphere;
import domain.player.Player;


public class DatabaseSaveLoadAdapter implements ISaveLoadAdapter {

	private static String url = "jdbc:postgresql://localhost:5432/needforspear";
	private static String user = "postgres";
	private static String password = "asd123";

	public DatabaseSaveLoadAdapter() {}

	public void addUser(String u, String p){
		//MODIFIES: adds the username and password to userNameAndPassword.txt file.
		try {

			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, user, password);
			Statement st = con.createStatement();

			st.executeUpdate("CREATE TABLE IF NOT EXISTS users (username varchar(255), password varchar(255));");
			st.executeUpdate("INSERT INTO users (username, password) VALUES ('"+u+"', '"+p+"' )" );
			//st.executeQuery("CREATE TABLE IF NOT EXISTS users (username varchar(255), password varchar(255));");
			/*ResultSet rs = st.executeQuery("SELECT * FROM users");
			if(rs.next()) {
				System.out.println(rs.getString(1));
			}*/

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DatabaseSaveLoadAdapter.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Boolean userExist(String u, String p) {
		// Effects : returns true if username exists in com.mysql.cj.jdbc.Drive. else it returns false.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, user, password);
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT username, password FROM users WHERE username = '"+u+"' AND password = '" + p +"';");
			if(rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DatabaseSaveLoadAdapter.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean usernameExist(String u) {
		// Effects : returns true if username exists in  com.mysql.cj.jdbc.Driver. Else it returns false.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con = DriverManager.getConnection(url, user, password);
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("SELECT username FROM users WHERE username = '"+u+"';");
			if(rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DatabaseSaveLoadAdapter.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void saveGame(String gameName, Player player, EnchantedSphere sphere, List<Obstacle> obstacles) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public ArrayList<ArrayList> loadGame(String username) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

