package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class SchemaUtils {
	
	String scheamFile = "schema.yaml";
	public Map<String, Object> data;
	Connection DBConnection;
	Statement DBStatement;
	String DBName;
	
	public SchemaUtils (String username, String password, String dbname, String scheamFile) {
		this.scheamFile = scheamFile;
		this.DBName = dbname;
		
		// read file
		InputStream inputStream;
		//inputStream = new FileInputStream(new File("student.yml"));
		inputStream =  this.getClass().getClassLoader().getResourceAsStream(this.scheamFile);
		Yaml yaml = new Yaml();
		this.data = yaml.load(inputStream);
		
		
		// initialize connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			String url = String.format("jdbc:mysql://localhost:3306/miniKusis?characterEncoding=utf8", dbname);
			this.DBConnection = DriverManager.getConnection(url, username, password);
			this.DBStatement = this.DBConnection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean createTable(String tableName) {
		try {
			DatabaseMetaData dbm;
			dbm = (DatabaseMetaData) this.DBConnection.getMetaData();

			
			ResultSet tables = (ResultSet) dbm.getTables(this.DBName, this.DBName, tableName, new String[] {"TABLE"});
			if (tables.next() == true) {
			  // Table exists
				System.out.println(String.format("Ok. table %s already exists", tableName));
				return false;
			}
			else {
				this.DBStatement.executeUpdate(this.parseTable(tableName));
				System.out.println(String.format("Ok. table %s created", tableName));
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	public String parseTable(String tableName) {
		// read yaml file
		Map<String, Object> tableData = (Map<String, Object>) this.data.get(tableName);
		
		
		// primary key 
		List<String> primaryKey = (List<String>) tableData.get("PK");
		tableData.remove("PK");
		
		// foreign keys
		Map<String, List<String>> foreignKey = (Map<String, List<String>>) tableData.get("FK");
		tableData.remove("FK");
		
		
		//build query
		String query = String.format("CREATE TABLE %s (", tableName);
		for (Map.Entry<String,Object> entry : tableData.entrySet()) 
			query += String.format(" %s %s,", entry.getKey(), String.join(" ", (List<String>) entry.getValue()));
		
		if (foreignKey != null) {
			for (Map.Entry<String,List<String>> entry : foreignKey.entrySet()) 
			{
				query += String.format(" FOREIGN KEY (%s) REFERENCES %s (%s),", String.join(", ", entry.getValue().subList(0, entry.getValue().size() - 1)), entry.getKey(), entry.getValue().get(entry.getValue().size() - 1));
			}
		}
		
		query += String.format(" PRIMARY KEY (%s));", String.join(", ", primaryKey));
		
		return query;
	}
	
	public boolean intializeDataBase() {
		for (Map.Entry<String,Object> entry : this.data.entrySet())
			this.createTable(entry.getKey());
				
		return true;
	}
	

}
