// PROGRAMMING LANGUAGE USED: JAVA

/* Contributors: Baris & İsmail 
 * The program allows the user to create tree(s), ornament(s), 
 * present(s) with different size and color to canvas 
 * by clicking the mouse. 
 * 
 */
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.*; 
import java.util.*;
import javax.swing.*;
import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.*;
//Additional libraries
// Your code starts here

// Your code ends here

public class Comp130_Final_F18 extends GraphicsProgram{

	private final int APPLICATION_WIDTH = 900; // application width
	private final int APPLICATION_HEIGHT= 600; // application height
	// random generator
	private RandomGenerator rgen = RandomGenerator.getInstance();
	// slider sizes
	private static final int MIN_SLIDER_SIZE = 0;
	private static final int MAX_SLIDER_SIZE = 2;
	private static final int INITIAL_SLIDER_SIZE = 1;
	// pricing for the ornament as per size
	private static final int SMALL_CIRCLE_ORNAMENT_PRICE = 5;
	private static final int MEDIUM_CIRCLE_ORNAMENT_PRICE = 7;
	private static final int LARGE_CIRCLE_ORNAMENT_PRICE = 10;
	private static final int SMALL_OVAL_ORNAMENT_PRICE = 7;
	private static final int MEDIUM_OVAL_ORNAMENT_PRICE = 10;
	private static final int LARGE_OVAL_ORNAMENT_PRICE = 12;
	private static final int SMALL_STAR_ORNAMENT_PRICE = 8;
	private static final int MEDIUM_STAR_ORNAMENT_PRICE = 12;
	private static final int LARGE_STAR_ORNAMENT_PRICE = 15;
	//size of Present, ornaments
	private static final int PRESENT_WIDTH = 30;
	private static final int PRESENT_HEIGHT = 20;
	private static final int SMALL_STAR_ORNAMENT_SIZE = 10;
	private static final int MEDIUM_STAR_ORNAMENT_SIZE = 15;
	private static final int LARGE_STAR_ORNAMENT_SIZE = 20;
	private static final int SMALL_CIRCLE_ORNAMENT_SIZE = 10;
	private static final int MEDIUM_CIRCLE_ORNAMENT_SIZE = 20;
	private static final int LARGE_CIRCLE_ORNAMENT_SIZE = 25;
	private static final int SMALL_OVAL_ORNAMENT_WIDTH = 5;
	private static final int MEDIUM_OVAL_ORNAMENT_WIDTH = 10;
	private static final int LARGE_OVAL_ORNAMENT_WIDTH = 15;
	private static final int SMALL_OVAL_ORNAMENT_HEIGHT = 10;
	private static final int MEDIUM_OVAL_ORNAMENT_HEIGHT = 20;
	private static final int LARGE_OVAL_ORNAMENT_HEIGHT = 25;
	//pricing of the tree 
	private static final int TREE_PRICE = 100;
	//pricing of the present
	private static final int PRESENT_PRICE = 20;
	//Y coordinate of Tree
	private static final int TREE_Y_COORDINATE = 200;
	//Y coordinate of Present
	private static final int PRESENT_Y_COORDINATE = 400;
	
	// file names for input and output
	private static final String inputFileName = "students.txt";
	private static final String outputFileName = "output.txt";
	// names of the ornaments and colors
	String[] ornaments = {"Circle", "Oval", "Star"};
	String[] colors = {"White", "Red", "Blue", "Yellow"};
	
	// Additional instance variable
	int tree2,presentNum;
	double x;
	double y,width,height;
	boolean isThereTree=false;
	Color color;
	String studentName,s;
	GRect present=null;
	// Your code starts here
	// Your code ends here
	

	/**
	 * The method to initialize the GUI and the program
	 */
    public void init(){
    	setSize (APPLICATION_WIDTH, APPLICATION_HEIGHT);
    	createGUI();
		// Your code starts here
    	readFile();
    	for(int i=0; i<arrList.size();i++) {
    		student.addItem(arrList.get(i));
    	}
    	student.setSelectedIndex(rgen.nextInt(0,arrList.size()));
         
    	addActionListeners();
    	addMouseListeners();
		// Your code ends here		
    }

    /**
     * The method to handle the mouse clicked events.
     * @param MouseEvent - the event created when a mouse is clicked
     */
    public void mouseClicked(MouseEvent e) {	
	    // Your code starts here
	

    	if(orColor.getSelectedItem().equals("White")){
     		 color=color.WHITE;
     	 }else     	 if(orColor.getSelectedItem().equals("Red")){
     		 color=color.RED;
     	 }else     	 if(orColor.getSelectedItem().equals("Blue")){
     		 color=color.BLUE;
     	 }else     	 if(orColor.getSelectedItem().equals("Yellow")){
     		 color=color.YELLOW;
     	 }
    	
    	 x=e.getX();
    	 y=e.getY();
 
         if(isThereTree) {
    	if(orType.getSelectedItem().equals("Circle")) {
            if(orSize.getValue()<=2.0/3) {
            	smallcircle+=SMALL_CIRCLE_ORNAMENT_SIZE;
            	width=SMALL_CIRCLE_ORNAMENT_SIZE;
            } else if(orSize.getValue()>2.0/3 &&orSize.getValue()<4.0/3) {
            	width=MEDIUM_CIRCLE_ORNAMENT_SIZE;
            	mediumcircle+=MEDIUM_CIRCLE_ORNAMENT_SIZE;
            }else {
            	width=LARGE_CIRCLE_ORNAMENT_SIZE;
            	largecircle+=LARGE_CIRCLE_ORNAMENT_SIZE;
            }
    		GOval circle= new GOval(x,y,width,width);
    		if(filled.isSelected()) {
    			circle.setFilled(true);
    			circle.setFillColor(color);
    		}
    		add(circle);
    	}
    	
    	if(orType.getSelectedItem().equals("Oval")) {
            if(orSize.getValue()<=2.0/3) {
            	smalloval+=SMALL_OVAL_ORNAMENT_PRICE;
            	width=SMALL_OVAL_ORNAMENT_WIDTH;
            	height=SMALL_OVAL_ORNAMENT_HEIGHT;
            } else if(orSize.getValue()>2.0/3 &&orSize.getValue()<4.0/3) {
            	mediumoval+=MEDIUM_OVAL_ORNAMENT_PRICE;

            	width=MEDIUM_OVAL_ORNAMENT_WIDTH;
            	height=MEDIUM_OVAL_ORNAMENT_HEIGHT;
            }else {
            	largeoval+=LARGE_OVAL_ORNAMENT_PRICE;

            	width=LARGE_OVAL_ORNAMENT_WIDTH;
            	height=LARGE_OVAL_ORNAMENT_HEIGHT;
            }
    		GOval oval= new GOval(x,y,width,height);
    		if(filled.isSelected()) {
    			oval.setFilled(true);
    			oval.setFillColor(color);
    		}
    		add(oval);
    	}
    	
    	if(orType.getSelectedItem().equals("Star")) {
    		
            if(orSize.getValue()<=2.0/3) {
            	width=SMALL_STAR_ORNAMENT_SIZE;
  
            	smallstar+=SMALL_STAR_ORNAMENT_PRICE;
            	
            } else if(orSize.getValue()>2.0/3 &&orSize.getValue()<4.0/3) {
            	width=MEDIUM_STAR_ORNAMENT_SIZE;
            	mediumstar+=MEDIUM_STAR_ORNAMENT_PRICE;

            }else {
            	width=LARGE_STAR_ORNAMENT_SIZE;
            	largestar+=LARGE_STAR_ORNAMENT_PRICE;

            }
    		Star star= new Star((int)width);
    		if(filled.isSelected()) {
    			star.setFilled(true);
    			star.setFillColor(color);
    		}
    		add(star,x,y);
    		
    		
    	}
   }
	    // Your code ends here  		
	}
    /**
     * The method to handle the action events.
     * @param ActionEvent - the event created when an action happens
     */
    public void actionPerformed(ActionEvent e) {
		// Your code starts here
    		String cmd=e.getActionCommand();
    		switch(cmd) {
    		case "Clear": 
    		removeAll(); 
    		isThereTree=false;
    		break;
    		case "Create Tree":  
    		Tree tree= new Tree();
    		tree2++;
    		add(tree,rgen.nextDouble(tree.getWidth()/2,APPLICATION_WIDTH-tree.getWidth()/2),TREE_Y_COORDINATE);
    		isThereTree=true;
    		break;
    		case "Calculate Spending":
    			try {
    	    		PrintWriter writer=new PrintWriter(new FileWriter(outputFileName));
    	    		writer.println("Your spending details are as below : ");
    	    		writer.println("Ornament spending= "+(largestar+mediumstar+smallstar+largeoval+mediumoval+smalloval+smallcircle+largecircle+mediumcircle));
    	    	    writer.println("Tree spending= "+(tree2*TREE_PRICE));
    	    	    writer.println("Present spending= "+presentNum*PRESENT_PRICE);
    	    	    writer.println("Total spending= "+(largestar+mediumstar+smallstar+largeoval+mediumoval+smalloval+smallcircle+largecircle+mediumcircle+tree2*TREE_PRICE+presentNum*PRESENT_PRICE));
    	    	    writer.close();
    			}catch(IOException ex) {
    	    		println("errorr!");
    	    	}
    			break;
    		case "Create Present":
    	        presentNum++;
    			s= "";
    	    	StringTokenizer tokenizer=new StringTokenizer((String)student.getSelectedItem());
    	    	while(tokenizer.hasMoreTokens()) {
    	    		String token= tokenizer.nextToken();
    	    		s+=token.charAt(0);
    	    	}
    			
    			GCompound compound= new GCompound();
    		     present= new GRect(PRESENT_WIDTH,PRESENT_HEIGHT);
    		     present.setFilled(true);
    		     present.setFillColor(rgen.nextColor());
    		     compound.add(present);
    		     GLabel label= new GLabel(s);
    		     compound.add(label);
    		     label.setLocation(present.getWidth()/2-label.getWidth()/2,present.getHeight()/2+label.getAscent()/2);
    		     add(compound,rgen.nextDouble(0,APPLICATION_WIDTH-compound.getWidth()),PRESENT_Y_COORDINATE);
    		     break;
    		}
    		
		// Your code ends here
    }
   
    // OTHER HELPER METHODS
    private void readFile() {
    	Scanner sc=null;
    	try {
    		 sc= new Scanner (new File(inputFileName));
    		while(sc.hasNextLine()) {
    			String str= sc.nextLine();
                arrList.add(str);
    		}
    		sc.close();
    	}catch(IOException e) {
    		throw new ErrorException(e);
    	}
    	Collections.sort(arrList);
    }
    // Your code starts here
   
    
     
    private void createGUI() {
    	filled=new JCheckBox();
    	add(filled,NORTH);
    	filled.setSelected(true);
    	filledText=new JLabel("Filled  ");
    	add(filledText,NORTH);
    	orText= new JLabel("Ornament Size-");
    	add(orText,NORTH);
    	zero=new JLabel("0");
    	add(zero,NORTH);
    	orSize=new JSlider(MIN_SLIDER_SIZE,MAX_SLIDER_SIZE,INITIAL_SLIDER_SIZE);
    	add(orSize,NORTH);
    	two=new JLabel("2");
    	add(two,NORTH);
    	
    	
    	orType=new JComboBox();
    	add(orType,NORTH);
    	
    	for(int i=0; i<ornaments.length; i++) {
        	orType.addItem(ornaments[i]);
    	}
    	
    	
    	orColor=new JComboBox();
    	add(orColor,NORTH);
    	for(int i=0;i<colors.length;i++) {
    		orColor.addItem(colors[i]);
    	}
    	orColor.setEditable(false);
    	
    	
    	student=new JComboBox();
    	add(student,NORTH);
        
    	createTree= new JButton("Create Tree");
    	add(createTree,SOUTH);
    	createPresent= new JButton("Create Present");
    	add(createPresent,SOUTH);
    	calculateSpending=new JButton("Calculate Spending");
    	add(calculateSpending,SOUTH);
    	clear=new JButton("Clear");
    	add(clear,SOUTH);
    }
    
    ArrayList<String> arrList=new ArrayList<String>();
    
  
    int largestar,mediumstar,smallstar,largeoval,mediumoval,smalloval,smallcircle,largecircle,mediumcircle=0;
    int price=0;
    
	JCheckBox filled;
    JLabel filledText;
	JLabel orText;
	JLabel zero;
	JSlider orSize;
	JLabel two;
	JComboBox orType;
	JComboBox orColor;
	JComboBox student;
	JButton createTree;
	JButton createPresent;
	JButton calculateSpending;
	JButton clear;
	
	// Your code ends here
}
