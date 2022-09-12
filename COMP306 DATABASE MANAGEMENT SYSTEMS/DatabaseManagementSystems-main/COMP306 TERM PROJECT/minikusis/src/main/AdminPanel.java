package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import utils.DBUtils;

public class AdminPanel extends JPanel{

	private JButton addCourses;
	private JCheckBox isEnrollingAllowed;
	private JCheckBox isDroppingAllowed;
	private JButton updatePassword;
	private JButton logOut;
	public DBUtils dbutils;
	public JComboBox instructorIDs;
	public JComboBox hours;
	public JComboBox classroomID;
	public JComboBox majorCodes;
	public JTextField enterCourseName;
	public JTextField enterDuration;
	public JTextField enterCredit;
	public JTextField enterCode;
	public JTextField enterSemester;
	public JTextField enterYear;
	public JButton saveCourse;

	private JButton seeLeastWorkFacultyMembers;
	private JButton seeBestStudentByYear;
	private JButton findMostInternationalCourse;
	private JButton seeBestAdvisors;
	private JButton seeMostVisitedClassrooms;
	
	private JButton findPotentialAdvisorsForAStudent;
	
	public JTextField studentID = new JTextField();
	
	public AdminPanel(DBUtils DBconn) {
		super();
		this.dbutils = DBconn;

		addCourses = new JButton("Add Courses");
		updatePassword = new JButton("Update password");


		logOut = new JButton("Log Out");


		seeLeastWorkFacultyMembers=new JButton("See Faculty Members With Least Work");
		seeBestStudentByYear=new JButton("See Best Students By Year");
		findMostInternationalCourse = new JButton("Find Most International Course");
		seeBestAdvisors = new JButton("See Best Advisors");
		seeMostVisitedClassrooms = new JButton("See Most Visited Classrooms");
		findPotentialAdvisorsForAStudent=new JButton("Find Potential Advisors For A Student");
		
		seeLeastWorkFacultyMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				String query = "select  f.id, f.name, count(*) as advisees "
						+"FROM facultymember f, student s "
						+"WHERE f.id=s.advisorid AND id in (SELECT id "
						+"FROM facultymember f2, course c where f2.id=c.teacherid "
						+" GROUP BY  (f2.id) HAVING count(*)<3) "
						+" GROUP BY (id)";


				Object[][] rows = new Object[30][3];
				int i=0;
				try {
					System.out.println(query);
					ResultSet rs = dbutils.executeQuery(query);
					//String courses = "";


					while (rs.next())
					{
						Object[] ob = {rs.getString("f.id"),rs.getString("f.name"),rs.getString("advisees")};
						rows[i]=ob;
						i++;
					}

					Object[] cols = {
							"Faculty ID","Faculty Name","Advisee Count"
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


		seeBestStudentByYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				String query = "SELECT s.start_year, s.name, s.surname, s.gpa "+ 
							   "FROM Student s " + 
							   "WHERE s.gpa in (SELECT MAX(s1.gpa) " +
							   					"FROM student s1 "+
							   					"GROUP BY (s1.start_year)) " + 
							   	"ORDER BY (s.start_year) ASC";


				Object[][] rows = new Object[30][4];
				int i=0;
				try {
					System.out.println(query);
					ResultSet rs = dbutils.executeQuery(query);


					while (rs.next())
					{
						Object[] ob = {rs.getString("s.start_year"),rs.getString("s.name"),rs.getString("s.surname"),rs.getString("s.gpa")};
						rows[i]=ob;
						i++;
					}

					Object[] cols = {
							"Year","Student Name", "Student Surname" ,"GPA"
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



		findMostInternationalCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				String query = "SELECT count(distinct(s1.country)), C.code "+
						"FROM Student s1, Course C, Enrollin E "+
						"WHERE E.CourseCode = C.Code AND E.StudentID = s1.StudentID "+
						"GROUP BY C.Code "+
						"ORDER BY count(distinct(s1.country)) desc "+
						"LIMIT 1";


				Object[][] rows = new Object[1][2];
				int i=0;
				try {
					System.out.println(query);
					ResultSet rs = dbutils.executeQuery(query);


					while (rs.next())
					{
						Object[] ob = {rs.getString("C.code"),rs.getString("count(distinct(s1.country))")};
						rows[i]=ob;
						i++;
					}

					Object[] cols = {
							"Course","Number of Different Nations"
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

		seeBestAdvisors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				String query = "SELECT f.name, f.surname, avg(gpa) "+
						"FROM student s, facultymember f "+
						"WHERE s.advisorid = f.id "+
						"GROUP BY (advisorID) "+
						"HAVING count(*) > 3 "+
						"ORDER BY(avg(gpa)) desc";


				Object[][] rows = new Object[30][3];
				int i=0;
				try {
					System.out.println(query);
					ResultSet rs = dbutils.executeQuery(query);


					while (rs.next())
					{
						Object[] ob = {rs.getString("f.name"),rs.getString("f.surname"),rs.getString("avg(gpa)")};
						rows[i]=ob;
						i++;
					}

					Object[] cols = {
							"Advisor Name","Advisor Surname", "Average GPA"
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

		seeMostVisitedClassrooms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				String query = "SELECT building, classroom.classroomid, count(*) as total_visit_to_classroom "+ 
						"FROM classroom, course, enrollin, student "+
						"WHERE course.classroomid=classroom.classroomid AND enrollin.studentid=student.studentID AND enrollin.coursecode = course.code "+
						"GROUP BY (classroom.classroomid) "+
						"ORDER BY count(*) desc ";



				Object[][] rows = new Object[30][3];
				int i=0;
				try {
					System.out.println(query);
					ResultSet rs = dbutils.executeQuery(query);


					while (rs.next())
					{
						Object[] ob = {rs.getString("building"),rs.getString("classroom.classroomid"),rs.getString("total_visit_to_classroom")};
						rows[i]=ob;
						i++;
					}

					Object[] cols = {
							"Building","Classroomid", "Total Visits"
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
		
		
		addCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				enterCode = new JTextField("Enter Course Code");
				enterDuration = new JTextField("Enter Course Duration");
				enterCredit = new JTextField("Enter Credit");
				enterSemester = new JTextField("Enter Semester");
				enterYear = new JTextField("Enter Year");

				JLabel chooseClassroom = new JLabel("Choose Classroom");
				JLabel chooseInstructorID = new JLabel("Choose InstructorID");
				JLabel chooseMajor = new JLabel("Choose Major");
				JLabel chooseHour = new JLabel("Choose Hour");

				instructorIDs = new JComboBox();

				try {
					ResultSet rs = dbutils.executeQuery("SELECT ID FROM facultymember");
					while (rs.next()) {
						instructorIDs.addItem(rs.getString("ID"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				hours = new JComboBox();

				try {
					ResultSet rs2 = dbutils.executeQuery("SELECT distinct StartTime FROM StartTime");
					while (rs2.next()) {
						hours.addItem(rs2.getString("StartTime"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				classroomID = new JComboBox();

				try {
					ResultSet rs2 = dbutils.executeQuery("SELECT distinct classroomID FROM classroom");
					while (rs2.next()) {
						classroomID.addItem(rs2.getString("classroomID"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				majorCodes = new JComboBox();

				try {
					ResultSet rs2 = dbutils.executeQuery("SELECT Code FROM major");
					while (rs2.next()) {
						majorCodes.addItem(rs2.getString("Code"));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				enterCode.setEditable(true);
				enterDuration.setEditable(true);
				enterCredit.setEditable(true);
				enterSemester.setEditable(true);
				enterYear.setEditable(true);
				instructorIDs.setEditable(true);
				hours.setEditable(true);
				classroomID.setEditable(true);
				majorCodes.setEditable(true);

				//create a JOptionPane
				Object[] options = new Object[] {};
				JOptionPane jop = new JOptionPane("Enter course information",
						JOptionPane.QUESTION_MESSAGE,
						JOptionPane.DEFAULT_OPTION,
						null,options, null);


				saveCourse = new JButton("Save Course");


				saveCourse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String selectedInstructor;
						String selectedHour;
						String enteredDuration;
						String enteredSemester;
						String enteredYear;
						String enteredCredit;
						String selectedClassroomID;
						String enteredCode;
						String selectedMajorCode;

						enteredDuration = enterDuration.getText();
						enteredSemester = enterSemester.getText();
						enteredYear = enterYear.getText();
						enteredCredit = enterCredit.getText();
						enteredCode = enterCode.getText();
						selectedClassroomID = (String) classroomID.getSelectedItem();
						selectedInstructor = (String) instructorIDs.getSelectedItem();
						selectedHour = (String) hours.getSelectedItem();
						selectedMajorCode = (String) majorCodes.getSelectedItem();
						String insertQuery="INSERT INTO Course VALUES ("+enteredDuration+", "+enteredCredit+", '"+enteredCode+
								"', "+selectedClassroomID+", '"+enteredSemester+"', "+enteredYear+", '"+selectedMajorCode+
								"', "+selectedInstructor+")";
						System.out.println(insertQuery);
						try {
							DBUtils.DBStatement.executeUpdate(insertQuery);
						} catch (SQLException e1) {
							e1.printStackTrace();
						} 	
					} 
				});
				jop.add(enterCode);
				jop.add(enterDuration);
				jop.add(enterCredit);
				jop.add(enterSemester);
				jop.add(enterYear);
				jop.add(chooseInstructorID);
				jop.add(instructorIDs);
				jop.add(chooseHour);
				jop.add(hours);
				jop.add(chooseClassroom);
				jop.add(classroomID);
				jop.add(chooseMajor);
				jop.add(majorCodes);
				jop.add(saveCourse);


				JDialog diag = new JDialog();
				diag.getContentPane().add(jop);
				diag.pack();
				diag.setVisible(true);
			}
		});

		updatePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String newPass;
				newPass = JOptionPane.showInputDialog ("Enter new password");
				try {
					Kusis.st.executeUpdate("UPDATE students "  //replace students with admins
							+ "SET password = '" + newPass +"'"
							+ " WHERE id = " + Kusis.currentUserID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		});


		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Kusis.adminPanel.setVisible(false);
				Kusis.openingPanel.setVisible(true);
				Kusis.currentUserType=null;
				Kusis.currentUsername=null;
				Kusis.currentUserID=-1;
			}
		});

		
		findPotentialAdvisorsForAStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				JLabel chooseCourseCode = new JLabel("Enter Student ID");
				studentID = new JTextField();
				// TODO Auto-generated method stub
				
				

				Object[] options = new Object[] {};
				JOptionPane jop = new JOptionPane("Enter Student ID",
						JOptionPane.QUESTION_MESSAGE,
						JOptionPane.DEFAULT_OPTION,
						null,options, null);

				JButton search = new JButton("Search");

				search.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						
						String enteredstudentID = (String) studentID.getText();
						String query=
								"Select F.id, F.name, count(AD.studentID) as advisee_cnt from Facultymember as F "+
						        "JOIN Department as D on F.DepID = D.Id "+
						       " JOIN Student as AD on AD.AdvisorID = F.ID "+
						"WHERE D.id IN (Select M.depID "+
						                "from Major as M JOIN Has as H on M.code = H.MajorCode "+
						                                "JOIN Student as S on H.StudentID = S.studentID "+
						                               "WHERE S.studentID ="+enteredstudentID+ ")"+
						"GROUP BY (F.id) "+
						"ORDER BY advisee_cnt ASC";
;
						System.out.println(query);

						Object[][] rows = new Object[30][3];
						int i=0;
						ResultSet rs;
						try {
							rs = dbutils.executeQuery(query);
							while (rs.next())
							{
								Object[] ob = {rs.getString("f.id"),rs.getString("f.name"),rs.getString("advisee_cnt")};
								rows[i]=ob;
								i++;
							}
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}


		

						Object[] cols = {
								"Faculty Member ID","Facult Member Name", "Total Advisee Count"
						};

						JTable table = new JTable(rows, cols);
						JScrollPane sc = new JScrollPane(table);
						sc.setPreferredSize(new Dimension(UIConstants.width*2/3, UIConstants.height*2/3));
						JOptionPane.showMessageDialog(null, sc);
						
						

					} 
				});

				jop.add(studentID);
				jop.add(search);

				JDialog diag = new JDialog();
				diag.getContentPane().add(jop);
				diag.pack();
				diag.setVisible(true);
			} 
		});
		
		
		
		setLayout(new GridLayout(2,5));
		setSize(400, 400);

		add(addCourses);

		add(updatePassword);
		add(logOut);
		
		add(seeLeastWorkFacultyMembers);
		add(seeBestStudentByYear);
		add(findMostInternationalCourse);
		add(seeBestAdvisors);
		add(seeMostVisitedClassrooms);
		add(findPotentialAdvisorsForAStudent);
		
		add(updatePassword);
		add(logOut);
	}
}


