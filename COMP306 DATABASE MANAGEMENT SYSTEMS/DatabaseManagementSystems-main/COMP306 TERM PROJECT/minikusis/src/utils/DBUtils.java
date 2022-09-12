package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;


public class DBUtils {

	String scheamFile = "schema.yaml";
	public Map<String, Object> data;
	public static Connection DBConnection;
	public static Statement DBStatement;
	

	public DBUtils (String username, String password, String scheamFile) {
		this.scheamFile = scheamFile;
		

		// read file
		InputStream inputStream;
		//inputStream = new FileInputStream(new File("student.yml"));
		inputStream =  this.getClass().getClassLoader().getResourceAsStream(this.scheamFile);
		Yaml yaml = new Yaml();
		this.data = yaml.load(inputStream);


		// initialize connection
		try {
			DBConnection = DriverManager.getConnection
					("jdbc:mysql://localhost/?user="+username+"&password="+password+"&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false"); 
			DBStatement=DBConnection.createStatement();

			boolean databaseExists=false;
			ResultSet resultSet = DBConnection.getMetaData().getCatalogs();

			while (resultSet.next()) {
				if(resultSet.getString(1).equals("minikusis")) databaseExists=true;
			}

			if(!databaseExists) {
				DBStatement.executeUpdate("CREATE DATABASE minikusis");
			}
			DBStatement.executeUpdate("USE minikusis");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean createTable(String tableName) {
		try {
			DatabaseMetaData dbm;
			dbm = (DatabaseMetaData) this.DBConnection.getMetaData();


			ResultSet tables = (ResultSet) dbm.getTables("minikusis", "minikusis", tableName, new String[] {"TABLE"});
			if (tables.next() == true) {
				// Table exists
				System.out.println(String.format("Table %s already exists", tableName));
				return false;
			}
			else {
				this.DBStatement.executeUpdate(this.parseTable(tableName));
				System.out.println(String.format("Table %s created", tableName));
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
		System.out.println(query);
		return query;
	}

	public boolean addTuple(String tableName, List<String> values) {
		String query = String.format("INSERT INTO %s VALUES(\"%s\")", tableName, String.join("\", \"", values));
		try {
			this.DBStatement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	public boolean addTuples(String tableName, String[][] values)
	{
		boolean success = true;
		for (int i =0;i<values.length;i++) {
			success &= addTuple(tableName, Arrays.asList(values[i]));
		}	
		return success;
	}

	public boolean exists(String tableName, String col, String value) {

		String query = String.format("SELECT * FROM %s WHERE %s = %s", tableName, col, value);
		try {
			return executeQuery(query).next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet executeQuery(String query) throws SQLException {
		return this.DBStatement.executeQuery(query);
	}

	public boolean intializeDataBase() {

		try {
			DBStatement.executeUpdate("USE minikusis");
			DatabaseMetaData dbm = (DatabaseMetaData) DBConnection.getMetaData();

			// check if "department" table is there
			ResultSet tables = (ResultSet) dbm.getTables(null, null, "department", null);
			if (tables.next()) {
				// Table exists
				System.out.println("tables already exists.");
			}
			else {
				// Table does not exist
				for (Map.Entry<String,Object> entry : this.data.entrySet())
					this.createTable(entry.getKey());

				String[][] departments = { 
						{"ENG", "Engineering"}, 
						{"SOS", "Social Sciences"}, 
						{"CASE", "Administrative Sciences and Economics"}, 
						{"CS", "Sciences"}, 
						{"CSSH", "Social Sciences and Humanities"},
						{"SOM", "Medicine"}};
				
				addTuples("department", departments);
				System.out.println("initial department tuples added.");

				try {	
					File file2 = new File("facultymember.txt");
					BufferedReader br2= new BufferedReader(new FileReader(file2));
					String st2;
					while ((st2 = br2.readLine()) != null)
						DBStatement.executeUpdate(st2);
					br2.close();
					File file = new File("student.txt");
					BufferedReader br= new BufferedReader(new FileReader(file));
					String st;
					while ((st = br.readLine()) != null)
						DBStatement.executeUpdate(st);
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				String[][] majors = { 
						{"COMP", "Computer Engineering" , "ENG"}, 
						{"ELEC", "Electric Engineering" , "ENG"}, 
						{"MECH", "Mechanical Engineering" , "ENG"},
						{"INDR", "Industrial Engineering" , "ENG"},
						{"HIST", "History", "SOS"},
						{"PSYC", "Psychology", "SOS"},
						{"ECON", "Economics" , "CASE"},
						{"MATH", "Mathematics" , "CS"},
						{"PHYS", "Physics" , "CS"},
						{"CHEM", "Chemistry" , "CS"},
						{"MEDI", "Medicine" , "SOM"}};

				addTuples("major", majors);
				System.out.println("major tuples added.");

				String[][] classrooms = { 
						{"1", "SOSB" , "20"}, 
						{"2", "SNA" , "100"}, 
						{"3", "SNA" , "30"},
						{"4", "ENGR" , "200"},
						{"5", "CASE" , "50"},
						{"6", "SOSB" , "100"}, 
						{"7", "SNA" , "100"}, 
						{"8", "CS" , "25"},
						{"9", "CS" , "200"},
						{"10", "SOSB" , "20"}};

				addTuples("classroom", classrooms);
				System.out.println("classroom tuples added.");

				String[][] courses = {  
						{"2", "3", "COMP131", "2", "Spring", "2022", "COMP", "1"}, 
						{"2", "4", "COMP132", "4", "Spring", "2022", "COMP", "2"}, 
						{"2", "3", "COMP201", "4", "Spring", "2022", "COMP", "1"}, 
						{"2", "4", "COMP202", "5", "Spring", "2022", "COMP", "1"},
						{"2", "3", "COMP301", "7", "Spring", "2022", "COMP", "8"},
						{"2", "3", "COMP302", "3", "Spring", "2022", "COMP", "8"},
						
						{"2", "3", "ELEC131", "2", "Spring", "2022", "ELEC", "3"}, 
						{"2", "3", "ELEC132", "1", "Spring", "2022", "ELEC", "5"}, 
						{"2", "4", "ELEC201", "1", "Spring", "2022", "ELEC", "2"}, 
						{"2", "3", "ELEC202", "7", "Spring", "2022", "ELEC", "6"},
						{"2", "3", "ELEC301", "10", "Spring", "2022", "ELEC", "15"},
						{"2", "3", "ELEC302", "5", "Spring", "2022", "ELEC", "18"},
						
						{"2", "3", "MECH131", "6", "Spring", "2022", "MECH", "6"}, 
						{"2", "4", "MECH132", "4", "Spring", "2022", "MECH", "13"}, 
						{"2", "3", "MECH201", "2", "Spring", "2022", "MECH", "15"}, 
						{"2", "3", "MECH202", "10", "Spring", "2022", "MECH", "13"},
						{"2", "3", "MECH301", "7", "Spring", "2022", "MECH", "4"},
						{"2", "4", "MECH302", "3", "Spring", "2022", "MECH", "4"},
						
						{"2", "3", "INDR131", "5", "Spring", "2022", "INDR", "7"}, 
						{"2", "4", "INDR132", "3", "Spring", "2022", "INDR", "11"}, 
						{"2", "3", "INDR201", "8", "Spring", "2022", "INDR", "15"}, 
						{"2", "3", "INDR202", "2", "Spring", "2022", "INDR", "4"},
						{"2", "3", "INDR301", "1", "Spring", "2022", "INDR", "6"},
						{"2", "3", "INDR302", "8", "Spring", "2022", "INDR", "16"},
						
						{"2", "3", "HIST131", "10", "Spring", "2022", "HIST", "3"}, 
						{"2", "4", "HIST132", "7", "Spring", "2022", "HIST", "18"}, 
						{"2", "3", "HIST201", "5", "Spring", "2022", "HIST", "5"}, 
						{"2", "3", "HIST202", "4", "Spring", "2022", "HIST", "18"},
						{"2", "3", "HIST301", "3", "Spring", "2022", "HIST", "7"},
						{"2", "3", "HIST302", "3", "Spring", "2022", "HIST", "5"},
						
						{"2", "3", "PSYC131", "6", "Spring", "2022", "PSYC", "18"}, 
						{"2", "3", "PSYC132", "8", "Spring", "2022", "PSYC", "14"}, 
						{"2", "4", "PSYC201", "1", "Spring", "2022", "PSYC", "7"}, 
						{"2", "3", "PSYC202", "1", "Spring", "2022", "PSYC", "14"},
						{"2", "3", "PSYC301", "2", "Spring", "2022", "PSYC", "20"},
						{"2", "3", "PSYC302", "6", "Spring", "2022", "PSYC", "20"},
						
						{"2", "3", "ECON131", "9", "Spring", "2022", "ECON", "3"}, 
						{"2", "3", "ECON132", "10", "Spring", "2022", "ECON", "3"}, 
						{"2", "3", "ECON201", "5", "Spring", "2022", "ECON", "10"}, 
						{"2", "3", "ECON202", "5", "Spring", "2022", "ECON", "8"},
						{"2", "4", "ECON301", "7", "Spring", "2022", "ECON", "3"},
						{"2", "3", "ECON302", "2", "Spring", "2022", "ECON", "10"},		
						
						{"2", "3", "MATH131", "1", "Spring", "2022", "MATH", "13"}, 
						{"2", "3", "MATH132", "5", "Spring", "2022", "MATH", "16"}, 
						{"2", "3", "MATH201", "5", "Spring", "2022", "MATH", "13"}, 
						{"2", "3", "MATH202", "9", "Spring", "2022", "MATH", "6"},
						{"2", "3", "MATH301", "9", "Spring", "2022", "MATH", "19"},
						{"2", "4", "MATH302", "1", "Spring", "2022", "MATH", "19"},
						
						{"2", "3", "PHYS131", "5", "Spring", "2022", "PHYS", "12"}, 
						{"2", "3", "PHYS132", "5", "Spring", "2022", "PHYS", "19"}, 
						{"2", "3", "PHYS201", "4", "Spring", "2022", "PHYS", "16"}, 
						{"2", "4", "PHYS202", "2", "Spring", "2022", "PHYS", "19"},
						{"2", "3", "PHYS301", "1", "Spring", "2022", "PHYS", "20"},
						{"2", "3", "PHYS302", "10", "Spring", "2022", "PHYS", "8"},
						
						{"2", "3", "CHEM131", "6", "Spring", "2022", "CHEM", "4"}, 
						{"2", "3", "CHEM132", "4", "Spring", "2022", "CHEM", "16"}, 
						{"2", "3", "CHEM201", "2", "Spring", "2022", "CHEM", "6"}, 
						{"2", "4", "CHEM202", "1", "Spring", "2022", "CHEM", "16"},
						{"2", "3", "CHEM301", "7", "Spring", "2022", "CHEM", "19"},
						{"2", "3", "CHEM302", "8", "Spring", "2022", "CHEM", "11"},

						{"2", "3", "MEDI131", "2", "Spring", "2022", "MEDI", "17"}, 
						{"2", "4", "MEDI132", "6", "Spring", "2022", "MEDI", "15"}, 
						{"2", "3", "MEDI201", "7", "Spring", "2022", "MEDI", "17"}, 
						{"2", "3", "MEDI202", "3", "Spring", "2022", "MEDI", "12"},
						{"2", "3", "MEDI301", "4", "Spring", "2022", "MEDI", "12"},
						{"2", "3", "MEDI302", "6", "Spring", "2022", "MEDI", "12"}};

				addTuples("course", courses);
				System.out.println("course tuples added.");

				String[][] enrollin = {  
						{"1", "COMP131"}, {"1", "COMP132"}, {"1", "COMP202"}, 
						{"2", "COMP202"}, {"2", "COMP201"}, {"2", "COMP302"},
						{"3", "MEDI131"}, {"3", "MEDI132"},
						{"4", "COMP131"}, {"4", "COMP201"},
						{"5", "ELEC202"},
						{"6", "COMP131"}, {"6", "COMP132"}, {"6", "COMP202"},
						{"7", "ELEC131"}, {"7", "ELEC132"}, {"7", "ELEC202"},
						{"8", "MECH131"}, {"8", "MECH132"}, {"8", "MECH202"},
						{"9", "COMP131"}, {"9", "COMP132"}, {"9", "COMP202"},
						{"10", "COMP131"}, {"10", "COMP132"}, {"10", "COMP202"},
						{"11", "ELEC131"}, {"11", "ELEC132"}, {"11", "ELEC202"},
						{"12", "PHYS131"}, {"12", "PHYS132"}, {"12", "PHYS202"},
						{"13", "COMP131"}, {"13", "COMP132"}, {"13", "COMP202"},
						{"14", "COMP131"}, {"14", "COMP132"}, {"14", "COMP202"},
						{"15", "COMP131"}, {"15", "COMP132"}, {"15", "COMP202"},
						{"16", "HIST131"}, {"16", "HIST132"}, {"16", "HIST202"},
						{"17", "ELEC131"}, {"17", "ELEC132"}, {"17", "ELEC202"},
						{"18", "ECON131"}, {"18", "ECON132"}, {"18", "ECON202"},
						{"19", "COMP131"}, {"19", "COMP132"}, {"19", "COMP202"},
						{"20", "ECON131"}, {"20", "ECON132"}, {"20", "ECON202"},
						{"21", "MECH131"}, {"21", "MECH132"}, {"21", "MECH202"},
						{"22", "INDR131"}, {"22", "INDR132"}, {"22", "INDR202"},
						{"23", "PSYC131"}, {"23", "PSYC132"}, {"23", "PSYC202"},
						{"24", "COMP131"}, {"24", "COMP132"}, {"24", "COMP202"},
						{"25", "PHYS131"}, {"25", "PHYS132"}, {"25", "PHYS202"},
						{"26", "COMP131"}, {"26", "COMP132"}, {"26", "COMP202"}, 
						{"27", "CHEM202"}, {"27", "CHEM201"}, {"27", "CHEM302"},
						{"28", "MECH202"}, {"28", "MECH132"},
						{"29", "CHEM131"}, {"29", "CHEM201"},
						{"30", "INDR202"},
						{"31", "ELEC131"}, {"31", "ELEC132"}, {"31", "ELEC202"},
						{"32", "MEDI131"}, {"32", "MEDI132"}, {"32", "MEDI202"},
						{"33", "CHEM131"}, {"33", "CHEM132"}, {"33", "CHEM202"},
						{"34", "MEDI131"}, {"34", "MEDI132"}, {"34", "MEDI202"},
						{"35", "COMP131"}, {"35", "COMP132"}, {"35", "COMP202"},
						{"36", "MECH131"}, {"36", "MECH132"}, {"36", "MECH202"},
						{"37", "INDR131"}, {"37", "INDR132"}, {"37", "INDR202"},
						{"38", "PSYC131"}, {"38", "PSYC132"}, {"38", "PSYC202"},
						{"39", "HIST131"}, {"39", "HIST132"}, {"39", "HIST202"},
						{"40", "COMP131"}, {"40", "COMP132"}, {"40", "COMP202"},
						{"41", "ELEC131"}, {"41", "ELEC132"}, {"41", "ELEC202"}, 
						{"42", "COMP131"}, {"42", "COMP132"}, {"42", "COMP202"}, 
						{"43", "HIST131"}, {"43", "HIST132"}, {"43", "HIST202"}, 
						{"44", "MECH131"}, {"44", "MECH132"}, {"44", "MECH202"}, 
						{"45", "MATH131"}, {"45", "MATH132"}, {"45", "MATH202"}, 
						{"46", "INDR131"}, {"46", "INDR132"}, {"46", "INDR202"}, 
						{"47", "ELEC131"}, {"47", "ELEC132"}, {"47", "ELEC202"}, 
						{"48", "COMP131"}, {"48", "COMP132"}, {"48", "COMP202"}, 
						{"49", "MATH131"}, {"49", "MATH132"}, {"49", "MATH202"},
						{"50", "COMP131"}, {"50", "COMP132"}, {"50", "COMP202"},
						};

				String enrollQuery="";
				for(int i=0; i<enrollin.length; i++) {
					enrollQuery="INSERT INTO enrollin(studentid, coursecode) values (" + enrollin[i][0]+ ", '"+enrollin[i][1] +"')";
					DBStatement.executeUpdate(enrollQuery);
				}

				System.out.println("enrollin tuples added.");

				String[][] admins = {  
						{"1", "admin1", "pass1"}, 
						{"2", "admin2", "pass2"}, 
						{"3", "admin3", "pass3"}, 
						{"4", "admin4", "pass4"},
						{"5", "admin5", "pass5"}
				};

				addTuples("admin", admins);
				System.out.println("admin tuples added.");

				String[][] has = {  
						{"1", "COMP"},
						{"2", "COMP"},
						{"3", "MEDI"},
						{"4", "COMP"},
						{"5", "ELEC"},
						{"6", "COMP"},
						{"7", "ELEC"},
						{"8", "MECH"},
						{"9", "COMP"},
						{"10", "COMP"},
						{"11", "ELEC"},
						{"12", "PHYS"},
						{"13", "COMP"},
						{"14", "COMP"},
						{"15", "COMP"},
						{"16", "HIST"}, 
						{"17", "ELEC"},
						{"18", "ECON"},
						{"19", "COMP"},
						{"20", "ECON"},
						{"21", "MECH"},
						{"22", "INDR"},
						{"23", "PSYC"},
						{"24", "COMP"},
						{"25", "PHYS"}, 
						{"26", "COMP"},  
						{"27", "CHEM"}, 
						{"28", "MECH"},
						{"29", "CHEM"}, 
						{"30", "INDR"},
						{"31", "ELEC"}, 
						{"32", "MEDI"}, 
						{"33", "CHEM"}, 
						{"34", "MEDI"}, 
						{"35", "COMP"}, 
						{"36", "MECH"},
						{"37", "INDR"},
						{"38", "PSYC"}, 
						{"39", "HIST"},
						{"40", "COMP"}, 
						{"41", "ELEC"}, 
						{"42", "COMP"},
						{"43", "HIST"},
						{"44", "MECH"},
						{"45", "MATH"},
						{"46", "INDR"},
						{"47", "ELEC"}, 
						{"48", "COMP"},
						{"49", "MATH"},
						{"50", "COMP"},
				};

				addTuples("has", has);
				System.out.println("has tuples added.");

				
				
				String[][] starttimes = {  
						{"Wednesday - 12:00", "COMP131"}, 
						{"Thursday - 12:00", "COMP132"}, 
						{"Thursday - 15:00", "COMP201"}, 
						{"Wednesday - 18:00", "COMP202"},
						{"Friday - 09:00", "COMP301"},
						{"Friday - 09:00", "COMP302"},
						
						{"Monday - 12:00", "ELEC131"}, 
						{"Wednesday - 12:00", "ELEC132"}, 
						{"Thursday - 15:00", "ELEC201"}, 
						{"Wednesday - 18:00", "ELEC202"},
						{"Friday - 09:00", "ELEC301"},
						{"Monday - 09:00", "ELEC302"},
						
						{"Monday - 12:00", "MECH131"}, 
						{"Wednesday - 12:00", "MECH132"}, 
						{"Thursday - 15:00", "MECH201"}, 
						{"Monday - 18:00", "MECH202"},
						{"Friday - 09:00", "MECH301"},
						{"Friday - 09:00", "MECH302"},
						
						{"Wednesday - 09:00", "INDR131"}, 
						{"Wednesday - 12:00", "INDR132"}, 
						{"Thursday - 15:00", "INDR201"}, 
						{"Thursday - 09:00", "INDR202"},
						{"Friday - 12:00", "INDR301"},
						{"Friday - 09:00", "INDR302"},
						
						{"Wednesday - 12:00", "HIST131"}, 
						{"Thursday - 12:00", "HIST132"}, 
						{"Thursday - 15:00", "HIST201"}, 
						{"Wednesday - 18:00", "HIST202"},
						{"Wednesday - 09:00", "HIST301"},
						{"Friday - 09:00", "HIST302"},
						
						{"Wednesday - 12:00", "PSYC131"}, 
						{"Monday - 12:00", "PSYC132"}, 
						{"Thursday - 15:00", "PSYC201"}, 
						{"Wednesday - 18:00", "PSYC202"},
						{"Thursday - 09:00", "PSYC301"},
						{"Friday - 09:00", "PSYC302"},
						
						{"Wednesday - 12:00", "ECON131"}, 
						{"Wednesday - 12:00", "ECON132"}, 
						{"Thursday - 15:00", "ECON201"}, 
						{"Wednesday - 18:00", "ECON202"},
						{"Friday - 15:00", "ECON301"},
						{"Friday - 09:00", "ECON302"},
						
						{"Wednesday - 12:00", "MATH131"}, 
						{"Wednesday - 12:00", "MATH132"}, 
						{"Thursday - 15:00", "MATH201"}, 
						{"Wednesday - 18:00", "MATH202"},
						{"Friday - 12:00", "MATH301"},
						{"Friday - 09:00", "MATH302"},
						
						{"Wednesday - 15:00", "PHYS131"}, 
						{"Wednesday - 17:00", "PHYS132"}, 
						{"Thursday - 15:00", "PHYS201"}, 
						{"Wednesday - 18:00", "PHYS202"},
						{"Friday - 12:00", "PHYS301"},
						{"Friday - 09:00", "PHYS302"},
						
						{"Wednesday - 12:00", "CHEM131"}, 
						{"Wednesday - 12:00", "CHEM132"}, 
						{"Thursday - 15:00", "CHEM201"}, 
						{"Wednesday - 18:00", "CHEM202"},
						{"Friday - 09:00", "CHEM301"},
						{"Friday - 09:00", "CHEM302"},
						
						{"Wednesday - 12:00", "MEDI131"}, 
						{"Wednesday - 12:00", "MEDI132"}, 
						{"Thursday - 15:00", "MEDI201"}, 
						{"Wednesday - 18:00", "MEDI202"},
						{"Friday - 09:00", "MEDI301"},
						{"Friday - 09:00", "MEDI302"},
				};

				addTuples("starttime", starttimes);
				System.out.println("starttime tuples added.");

			}	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}


}
