package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.DBUtils;


public class StudentsGradesPanel extends JPanel{
	
	String[] letterGrades = {"A+","A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
	private static JButton saveButton;
	private static JButton backButton;
	final public DBUtils dbutils;

	
	
	public StudentsGradesPanel(DBUtils DBconn) {
		
		dbutils = DBconn;
		//take students data from database.
		JLabel student1=new JLabel("Student 1");
		JComboBox letters1 = new JComboBox(letterGrades);
		JLabel student2=new JLabel("Student 2");
		JComboBox letters2 = new JComboBox(letterGrades);
		JLabel student3=new JLabel("Student 3");
		JComboBox letters3 = new JComboBox(letterGrades);
		JLabel student4=new JLabel("Student 4");
		JComboBox letters4 = new JComboBox(letterGrades);
		JLabel student5=new JLabel("Student 5");
		JComboBox letters5 = new JComboBox(letterGrades);
		
		saveButton = new JButton();
		saveButton.setText("Save Grades");
		
		backButton = new JButton();
		backButton.setText("Go Back ");
		
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//KusisFrame.studentsGradesPanel = new StudentsGradesPanel();
				Kusis.studentsGradesPanel.setVisible(false);		
				
				Kusis.instructorPanel = new InstructorPanel(dbutils); //take data from database to create this panel.
				Kusis.instructorPanel.setVisible(true);
			}
		});
		
		
		student1.setHorizontalAlignment(0);
		student2.setHorizontalAlignment(0);
		student3.setHorizontalAlignment(0);
		student4.setHorizontalAlignment(0);
		student5.setHorizontalAlignment(0);
		
		setLayout(new GridLayout(6,2));
		setSize(400, 400);
		
	    add(student1);
		add(letters1);
	    add(student2);
	    add(letters2);
		add(student3);
		add(letters3);
		add(student4);
		add(letters4);
		
		add(backButton);
		add(saveButton);
		
	}
}