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


public class LectureHoursPanel extends JPanel{
	
	private static JButton backButton;
	private static JButton enrollButton;
	
	private static String[] lectures= {"COMP306","COMP305","COMP304","COMP302"};
	private static String[] lectureHours = {"08.30-09.40","10.00-11.10", "11.30-12.40","13.00-14.10"};
	public DBUtils dbutils;


	public LectureHoursPanel(DBUtils DBconn) {
		this.dbutils = DBconn;

		//take lectures and hours information from database.
		//(can be taken as argument)
		
		JComboBox lectures1=new JComboBox(lectures);
		JComboBox lectureHours1 = new JComboBox(lectureHours);
		
		JComboBox lectures2=new JComboBox(lectures);
		JComboBox lectureHours2 = new JComboBox(lectureHours);
		
		JComboBox lectures3=new JComboBox(lectures);
		JComboBox lectureHours3 = new JComboBox(lectureHours);
		
		JComboBox lectures4=new JComboBox(lectures);
		JComboBox lectureHours4 = new JComboBox(lectureHours);
		
		JComboBox lectures5=new JComboBox(lectures);
		JComboBox lectureHours5 = new JComboBox(lectureHours);
		
		
		enrollButton = new JButton("Enroll Courses");
		
		enrollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		backButton = new JButton();
		backButton.setText("Go Back ");
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Kusis.studentsGradesPanel.setVisible(false);
				Kusis.instructorPanel.setVisible(true);
			}
		});

		
		setLayout(new GridLayout(5,2));
		setSize(400, 400);
		
		add(lectures1);
		add(lectureHours1);
		add(lectures2);
		add(lectureHours2);
		add(lectures3);
		add(lectureHours3);
		add(lectures4);
		add(lectureHours4);
		add(lectures5);
		add(lectureHours5);
		
		add(backButton);
		add(enrollButton);
	}
}