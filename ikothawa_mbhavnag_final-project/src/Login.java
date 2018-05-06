import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class Login {
    
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    
    static String username;
    
    public Login(){
    	    createTable();
    	    populateUsers();
        prepareGUI();
    }
    
    public void createTable(){
    	
		// vars. for SQL Query create
		  final String createTable = "CREATE TABLE IF NOT EXISTS " +
				 " mufid_izaazUsers1(user_id INT AUTO_INCREMENT PRIMARY KEY, user_name VARCHAR(30), " +
				  "user_password VARCHAR(256), user_type INT)";
		  
		  final String create_requests = "CREATE TABLE IF NOT EXISTS " +
					 " mufid_izaaz_requests3(req_id INT AUTO_INCREMENT PRIMARY KEY, user_name VARCHAR(30), " +
					  "ticket_id INT NOT NULL UNIQUE, description VARCHAR(256), completed VARCHAR(3))";
		  
		  
		  Connection connect = null;
		  Statement statement = null;
				  
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
		              + "&user=fp411&password=411");
	 	      
		      //create table
		    
		      statement = connect.createStatement();
		      
		      statement.executeUpdate(createTable);
		      statement.executeUpdate(create_requests);
		      System.out.println("Table created in given database...");

			//end create table
		    //close connection/statement object  
		     statement.close();
		     connect.close();
		    } catch (Exception e) {
		    	System.out.println(e.getMessage());  
		    }  

    }
    
    public void populateUsers(){
    	
    	      // vars. for SQL Query inserts
		  String sql;
		  Connection connect = null;
		  Statement statement = null;
	      BufferedReader br;
	    	  List<List<String>> array = new ArrayList<>();  //arraylist to hold spreadsheet rows & columns	
	    	  
	    	  //read data from file
	    	  try {
				   br = new BufferedReader(new FileReader(new File("./userslist.csv")));

			       String line;
						while ((line = br.readLine()) != null) {
							array.add(Arrays.asList(line.split(",")));
						}
					} catch (Exception e) {
						System.out.println("There was a problem loading the file");
		   }
	    	  
		   try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
		              + "&user=fp411&password=411");
		      statement = connect.createStatement();
	
			  //create loop to grab each array index containing a list of values
			  //and PASS that data into your record objects' setters 
			    for (List<String> rowData: array){
				  //perform inserts into users table
			    	  //grab values one column at a time from array
				   sql="insert into mufid_izaazUsers1(user_name,user_password,user_type) " +
					     "values('"+rowData.get(0)+"','"+rowData.get(1)+"', '"+rowData.get(2)+"');";
	               statement.executeUpdate(sql);
			    }
		  	    System.out.println("Inserts completed in the given database...");

		    //close connection/statement object  
		     statement.close();
		     connect.close();
		    } catch (Exception e) {
		    	System.out.println(e.getMessage());  
		    }  
    }
    

    private void prepareGUI(){
        mainFrame = new JFrame("Login");
        mainFrame.getContentPane().setForeground(Color.BLACK);
        mainFrame.setBackground(SystemColor.activeCaption);
        mainFrame.getContentPane().setBackground(SystemColor.textHighlight);
        mainFrame.setSize(504,153);
        mainFrame.setResizable(false);
        
        
        mainFrame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent wE){   //define a close operation
                System.exit(0);
        }
        });
        
        
        headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
        headerLabel.setBounds(0, 0, 498, 64);
        
        controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setBackground(new Color(0,0,0,125));
        controlPanel.setBounds(10, 72, 478, 45);
        mainFrame.getContentPane().setLayout(null);
        
        
        mainFrame.getContentPane().add(headerLabel);
        mainFrame.getContentPane().add(controlPanel);
       

    	
        mainFrame.setVisible(true);
    }
    
    private void showTextFields(){
    	
         
        headerLabel.setText("IT Help Ticket - Login");
        
        JLabel  namelabel= new JLabel("User ID: ", JLabel.RIGHT);
        namelabel.setForeground(Color.WHITE);
        namelabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        namelabel.setBounds(132, 35, 80, 25);
        JLabel  passwordLabel = new JLabel("Password: ", SwingConstants.RIGHT);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        passwordLabel.setBounds(129, 89, 97, 25);
        final JTextField userText = new JTextField(6);
        userText.setFont(new Font("Tahoma", Font.PLAIN, 14));
        userText.setBounds(236, 31, 255, 37);
        final JPasswordField passwordText = new JPasswordField(6);
        passwordText.setFont(new Font("Tahoma", Font.PLAIN, 14));
        passwordText.setBounds(236, 85, 255, 37);
        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        loginButton.setBounds(266, 153, 120, 44);
        loginButton.addActionListener(new ActionListener() {
        	
            @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
                
               /*verify user with a matching password/username from users table
                 *give popup message if either username or password is incorrect
               */
            	  Connection connect = null;
       		  Statement statement = null;
       		  
       		  try {
       		      // This will load the MySQL driver, each DB has its own driver
       		      Class.forName("com.mysql.jdbc.Driver");
       		      // Setup the connection with the DB
       		      connect = DriverManager
       		          .getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
       		              + "&user=fp411&password=411");
       	 	      
       		      //verify user from users table

       		      statement = connect.createStatement();
       		      
       		     username = userText.getText();
       		      String password = passwordText.getText();
    			      boolean blnFound=false; //flag if user's credentials are valid
    			      
       		      String sql = "SELECT user_name, user_password, user_type FROM mufid_izaazUsers1 WHERE user_name='" + username + "' " +
       		    		           "AND user_password='" + password + "';";
       	    
       			  statement.executeQuery(sql);
       	          ResultSet rs = statement.getResultSet();
       	          blnFound = rs.first();  //grabs first record match!
                     
       	          String isadmin = "";
       	          
                  if(blnFound) {
              	  //close of Login window
                	  
                	  int rights = rs.getInt("user_type");
                	  
                	  if (rights == 1 ) {
                		  mainFrame.dispose();
                		 isadmin = "User";
                		 JOptionPane.showMessageDialog(null, "Welcome, " + username +  "!" + "\nYou are a(n)"  + isadmin);
                		 Options_user.runner();
                		 
                	  }
                	  
                	  if (rights == 2) {
                		  mainFrame.dispose();
                		 isadmin = "Administrator";
                		 JOptionPane.showMessageDialog(null, "Welcome, " + username +  "!" + "\nYou are a(n)"  + isadmin);
                		 adminPanel.run();
                		 
                		  
                	  }
                	 
                	  
              	
              	  //open up ticketsGUI file upon successful login
                 // new ticketsGUI();
              	  }
              	  else {
              		String message = "Enter correct username\n and/or password";
              		JOptionPane.showMessageDialog(null, message);
              	  }
  
        		     //close connection/statement object  
       		     statement.close();
       		     connect.close();
       		    } catch (Exception e1) {
       		    	System.out.println(e1.getMessage());  
       		    }  
               }
        });
       // controlPanel.setLayout(null);
        
        controlPanel.add(namelabel);
        controlPanel.add(userText);
        controlPanel.add(passwordLabel);       
        controlPanel.add(passwordText);
        controlPanel.add(loginButton);
    
        mainFrame.setVisible(true);  
    }
    public static void main(String[] args){
        Login login = new Login();
        login.showTextFields();
    }
    
}