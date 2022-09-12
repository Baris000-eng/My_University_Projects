package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utils.DBUtils;


public class InstructorPanel extends JPanel{

	private JButton enterEditGrades;
	private JButton updatePassword;
	private JButton showSchedule;
	private JButton logOut;
	final public DBUtils dbutils;


	public InstructorPanel(DBUtils DBconn){
		super();
		this.dbutils = DBconn;
		Color[]colors = {Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN};

		Random rn = new Random();
		int colLen = colors.length;
		int randInd = rn.nextInt(colLen);


		enterEditGrades = new JButton();
		enterEditGrades.setText("Enter or Edit grades");

		logOut = new JButton("Log Out");

		
		
		enterEditGrades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String query = "select count(*) as cnt "
						+ "from student, facultymember, enrollin, course "
						+ "where enrollin.studentid=student.studentid and course.code=enrollin.coursecode "
						+ "and facultymember.id=course.teacherid and facultymember.id="+Kusis.currentUserID+" ORDER BY course.code asc";

				System.out.println(query);
				ResultSet rs;
				int count=0;
				try {
					rs = dbutils.executeQuery(query);
					while (rs.next())
						count = rs.getInt("cnt");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				query = "select course.code, student.studentid, student.name, enrollin.grade "
						+ "from student, facultymember, enrollin, course "
						+ "where enrollin.studentid=student.studentid and course.code=enrollin.coursecode "
						+ "and facultymember.id=course.teacherid and facultymember.id="+Kusis.currentUserID+" ORDER BY course.code asc";

				System.out.println(query);
				Object[][] rows = new Object[count][4];
				int i=0;
				try {
					rs = dbutils.executeQuery(query);
					String courses = "";


					while (rs.next())
					{
						Object[] ob = {rs.getString("course.code"),rs.getString("student.studentid"),rs.getString("student.name"),rs.getString("enrollin.grade")};
						rows[i]=ob;
						i++;
					}

					Object[] cols = {
							"Code", "studentid", "name", "grade"
					};

					JTable table = new JTable(rows, cols);
					JScrollPane sc = new JScrollPane(table);
					sc.setPreferredSize(new Dimension(UIConstants.width*2/3, UIConstants.height*2/3));
					JOptionPane.showMessageDialog(null, sc);
					System.out.println("count: "+ count);

					for(int row=1; row<count; row++) {
						if(table.getModel().getValueAt(row, 3)!=null) {
							query = "update enrollin "
									+ "set grade="+"'"+table.getModel().getValueAt(row, 3)+"'"
									+ " where enrollin.coursecode= "+"'"+table.getModel().getValueAt(row, 0)+"'"
									+ " and enrollin.studentid= "+"'"+table.getModel().getValueAt(row, 1)+"'";
							DBUtils.DBStatement.executeUpdate(query);
						}
					}

					System.out.println(table.getModel().getValueAt(1, 1));
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		updatePassword = new JButton();
		updatePassword.setText("Change Password");
		updatePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String newPass;
				newPass = JOptionPane.showInputDialog ("Enter new password");
				try {
					Kusis.st.executeUpdate("UPDATE students "  //replace students with instructors
							+ "SET password = '" + newPass +"'"
							+ " WHERE id = " + Kusis.currentUserID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		});

		showSchedule = new JButton("Show Schedule");
		showSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String query = "select COUNT(*) as count "
						+ "from course, starttime "
						+ "where teacherID=" +Kusis.currentUserID +" and course.code=starttime.coursecode";

				ResultSet rs;
				int count=0;
				try {
					rs = dbutils.executeQuery(query);
					while (rs.next())
						count = rs.getInt("count");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				query = "select code, classroomid, semester, year, starttime, duration, credit "
						+ "from course, starttime "
						+ "where teacherID=" +Kusis.currentUserID +" and course.code=starttime.coursecode";


				Object[][] rows = new Object[count][8];
				int i=0;
				try {
					rs = dbutils.executeQuery(query);
					String courses = "";


					while (rs.next())
					{
						Object[] ob = {rs.getString("code"),rs.getString("classroomid"),rs.getString("semester"),rs.getString("year"),rs.getString("starttime"), rs.getString("duration"),rs.getString("credit")};
						rows[i]=ob;
						i++;
					}

					Object[] cols = {
							"Code","classroomid","Semester", "Year", "Start Time", "Duration", "Credit"
					};

					JTable table = new JTable(rows, cols);
					JScrollPane sc = new JScrollPane(table);
					sc.setPreferredSize(new Dimension(UIConstants.width*2/3, UIConstants.height*2/3));
					JOptionPane.showMessageDialog(null, sc);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}

		});

		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Kusis.instructorPanel.setVisible(false);
				Kusis.openingPanel.setVisible(true);
				Kusis.currentUserType=null;
				Kusis.currentUsername=null;
				Kusis.currentUserID=-1;
			}
		});

		setLayout(new GridLayout(2,5));
		setSize(400, 400);

		add(enterEditGrades);
		add(updatePassword);
		add(showSchedule);
		add(logOut);

		setBackground(colors[randInd]);
	}

}
