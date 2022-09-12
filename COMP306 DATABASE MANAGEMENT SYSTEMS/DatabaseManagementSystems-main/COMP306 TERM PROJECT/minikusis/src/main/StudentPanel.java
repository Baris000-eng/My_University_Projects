
package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import utils.DBUtils;

public class StudentPanel extends JPanel{
	
	public JButton seeMyInfo;
	public JButton enrollCourse;
	public JButton viewCourses;
	public JButton updatePassword;
	public JButton classSearch;
	public JButton logOut;
	final public DBUtils dbutils;

	public JButton dropCourse;
	public JButton finishEnrolling;
	public JComboBox courseCodes;
	public JButton finishDropping;
	
	public StudentPanel(DBUtils DBconn) {
		
		dbutils = DBconn;
		seeMyInfo = new JButton("See My Info");
		enrollCourse = new JButton("Enroll courses");
		viewCourses = new JButton("View courses");
		dropCourse = new JButton("Drop course");
		updatePassword = new JButton("Update password");
		logOut = new JButton("Log Out");



		seeMyInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object[][] rows = new Object[1][5];
				int i=0;
				try {
					ResultSet rs = dbutils.executeQuery("SELECT s.name, s.surname, s.country, s.city, s.gpa FROM student s WHERE studentid=" + Kusis.currentUserID);

					while (rs.next())
					{
						Object[] ob = {rs.getString("s.name"),rs.getString("s.surname"),rs.getString("s.country"),rs.getString("s.city"),rs.getString("s.gpa")};
						rows[i]=ob;
						i++;
					}

					Object[] cols = {
							"Name","Surname","Country","City", "GPA"
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
		
		
		
		updatePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String newPass;
				newPass = JOptionPane.showInputDialog ("Enter new password");
				try {
					Kusis.st.executeUpdate("UPDATE students "
							+ "SET password = '" + newPass +"'"
							+ " WHERE id = " + Kusis.currentUserID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		});
		
		enrollCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				JLabel chooseCourseCode = new JLabel("Choose Course Code");
				courseCodes = new JComboBox();
				// TODO Auto-generated method stub
				
				try {
					ResultSet rs2 = dbutils.executeQuery("SELECT Code FROM course");
					while (rs2.next()) {
						courseCodes.addItem(rs2.getString("Code"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				
				courseCodes.setEditable(true);

				Object[] options = new Object[] {};
				JOptionPane jop = new JOptionPane("Enter course to enroll",
						JOptionPane.QUESTION_MESSAGE,
						JOptionPane.DEFAULT_OPTION,
						null,options, null);

				finishEnrolling = new JButton("Finish Enrolling");

				finishEnrolling.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						
						String selectedCourseCode = (String) courseCodes.getSelectedItem();
						String insertQuery="INSERT INTO enrollin(studentID, coursecode) VALUES ("+Kusis.currentUserID+", '"+selectedCourseCode+"')";
						System.out.println(insertQuery);

						try {
							DBUtils.DBStatement.executeUpdate(insertQuery);

						} catch (SQLException e1) {
							e1.printStackTrace();
						} 

					} 
				});

				jop.add(courseCodes);
				jop.add(finishEnrolling);

				JDialog diag = new JDialog();
				diag.getContentPane().add(jop);
				diag.pack();
				diag.setVisible(true);
			} 
		});

		
		dropCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

		

				JLabel chooseCourseCode = new JLabel("Choose Course Code");
				courseCodes = new JComboBox();
				// TODO Auto-generated method stub
				
				try {
					ResultSet rs2 = dbutils.executeQuery("SELECT coursecode FROM enrollin WHERE studentid= " + Kusis.currentUserID);
					while (rs2.next()) {
						courseCodes.addItem(rs2.getString("coursecode"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				courseCodes.setEditable(true);

				Object[] options = new Object[] {};
				JOptionPane jop = new JOptionPane("Enter course to drop",
						JOptionPane.QUESTION_MESSAGE,
						JOptionPane.DEFAULT_OPTION,
						null,options, null);

				finishDropping = new JButton("Finish Dropping");

				finishDropping.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

					
						String selectedCourseCode = (String) courseCodes.getSelectedItem();
						String deleteQuery="DELETE FROM enrollin WHERE enrollin.studentid="+Kusis.currentUserID+" and enrollin.coursecode='"+selectedCourseCode+"'";
						System.out.println(deleteQuery);

						try {
							DBUtils.DBStatement.executeUpdate(deleteQuery);

						} catch (SQLException e1) {
							e1.printStackTrace();
						} 

					} 
				});

				jop.add(courseCodes);
				jop.add(finishDropping);

				JDialog diag = new JDialog();
				diag.getContentPane().add(jop);
				diag.pack();
				diag.setVisible(true);
			} 
		});			
		
		viewCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String query = "select COUNT(*) as count "
						+ "from course, enrollin, starttime "
						+ "where enrollin.studentid="+Kusis.currentUserID+" and enrollin.coursecode=course.code and starttime.coursecode=course.code";
				System.out.println(query);
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
				
				query = "select code, classroomid, semester, year, starttime, duration, credit, grade "
						+ "from course, enrollin, starttime "
						+ "where enrollin.studentid="+Kusis.currentUserID+" and enrollin.coursecode=course.code and starttime.coursecode=course.code";

				
				Object[][] rows = new Object[count][8];
				int i=0;
				try {
					System.out.println(query);
					rs = dbutils.executeQuery(query);
					String courses = "";

	
					while (rs.next())
					{
						Object[] ob = {rs.getString("code"),rs.getString("classroomid"),rs.getString("semester"),rs.getString("year"),rs.getString("starttime"), rs.getString("duration"),rs.getString("credit"),rs.getString("grade")};
						rows[i]=ob;
						i++;
					}
					
					Object[] cols = {
							"Code","ClassroomID","Semester", "Year", "Start Time", "Duration", "Credit", "Grade"
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
					Kusis.studentPanel.setVisible(false);
					Kusis.openingPanel.setVisible(true);
					Kusis.currentUserType=null;
					Kusis.currentUsername=null;
					Kusis.currentUserID=-1;
			}
		});
		
		setLayout(new GridLayout(2,5));
		setSize(400, 400);
		
		add(seeMyInfo);
		add(enrollCourse);
		add(dropCourse);
		add(viewCourses);
		add(updatePassword);
		add(logOut);
		
	}

}
