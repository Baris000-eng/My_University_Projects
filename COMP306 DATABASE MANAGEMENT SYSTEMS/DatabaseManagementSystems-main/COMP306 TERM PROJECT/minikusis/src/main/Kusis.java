package main;

import java.awt.Color;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.Driver; 
import utils.DBUtils;

public class Kusis{
	
	public static JFrame frame= new JFrame();
	public static OpeningPanel openingPanel;
	public static InstructorPanel instructorPanel;
	public static StudentsGradesPanel studentsGradesPanel;
	public static AdminPanel adminPanel;
	public static LectureHoursPanel lectureHoursPanel;
    public static StudentPanel studentPanel;
    public static DBUtils dbUtils = new DBUtils("root", "123456", "schema.yaml");
    
    public static java.sql.Connection con;
    public static java.sql.Statement st;
    
    public static String currentUserType = null;
    public static String currentUsername = null;
    public static int currentUserID = -1;
    
	public static void main(String[] args) throws IOException {
		
		dbUtils.intializeDataBase();
		
		frame = new JFrame();
		frame.setTitle("KUSIS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(UIConstants.width, UIConstants.height);      

		frame.setLayout(null);

		
		openingPanel = new OpeningPanel(dbUtils);
		openingPanel.setVisible(true);
		openingPanel.setSize(UIConstants.width, UIConstants.height);      
		frame.add(openingPanel);
		
		instructorPanel = new InstructorPanel(dbUtils);
		instructorPanel.setVisible(false);
		instructorPanel.setSize(UIConstants.width,UIConstants.height);
		frame.add(instructorPanel);
		
		
		studentsGradesPanel = new StudentsGradesPanel(dbUtils);
		studentsGradesPanel.setVisible(false);
		studentsGradesPanel.setSize(UIConstants.width,UIConstants.height);
		frame.add(studentsGradesPanel);
		
		
		adminPanel = new AdminPanel(dbUtils);
		adminPanel.setVisible(false);
		adminPanel.setSize(UIConstants.width,UIConstants.height);
		frame.add(adminPanel);
		
		
		lectureHoursPanel = new LectureHoursPanel(dbUtils);
		lectureHoursPanel.setVisible(false);
		lectureHoursPanel.setSize(UIConstants.width,UIConstants.height);
		frame.add(lectureHoursPanel);
		
		
		studentPanel = new StudentPanel(dbUtils);
		studentPanel.setVisible(false);
		studentPanel.setSize(UIConstants.width,UIConstants.height);
		frame.add(studentPanel);
			
		frame.setVisible(true);
		

	}


}

