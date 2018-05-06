import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
@SuppressWarnings("serial")
public class Options_user extends JFrame {
// User panel...
	private JPanel contentPane;
	
	static String current_user = Login.username;

	/**
	 * Launch the application.
	 */
	
	public static void runner() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Options_user frame = new Options_user();
					frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Error warning: " + e.getMessage());
				}
			}
		});
	}

	
	private void createTable()
	{	
		// vars. for SQL Query create
		  String createTable = "CREATE TABLE mufid_izaazTicket5(ticket_id INT AUTO_INCREMENT PRIMARY KEY, ticket_name VARCHAR(200), ticket_description VARCHAR(200), priority VARCHAR(100), status VARCHAR(20), create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, Close_Date TIMESTAMP NULL)";
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
		      System.out.println("Created table in given database...");

			//end create table
		    //close connection/statement object  
		     statement.close();
		     connect.close();
		    } catch (Exception e) {
		    	System.out.println(e.getMessage());  
		    }  

		
	}
	
	/**
	 * Create the frame.
	 */
	public Options_user() {
		
		createTable();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 700, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 102, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","IT Ticket - User System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		        	
		            System.exit(0);
		        }
		    }
		});
		
		// single ticket view option
		JButton btnViewTicket = new JButton("VIEW (1) Ticket");
		btnViewTicket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int ticID = 0;
				 // a jframe here isn't strictly necessary, but it makes the example a little more real
			    JFrame frame = new JFrame("Single Ticket Lookup");

			    // prompt the user to enter their name
			    String name = JOptionPane.showInputDialog(frame, "Please Enter the Ticket Number You wish to View: ");
			 
			    //try catch to check the user entry was valid
				  try {
					  ticID = Integer.parseInt(name); 
					  System.out.println("parsed");
				  } 
				  
				  catch (NumberFormatException x1){
					 
					  
				  }
			    
			    try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					
					Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
		                                                               + "&user=fp411&password=411");
		            
		            Statement statement = dbConn.createStatement();
		            
		            String sql = "SELECT * FROM mufid_izaazTicket5 WHERE ticket_id='" + ticID + "';";
		            
		            ResultSet results = statement.executeQuery(sql);
		            
		            System.out.println("loading table...");
		            
		            // Use JTable built in functionality to build a table model and display the table model
		            // off a resultset!!!
		            
		            
		            boolean blnFound=false; //flag if user's credentials are valid
			           blnFound = results.first();  //grabs first record match!
			           
			       if (blnFound) {
		           // results.next();
			       
		           // the result for ticket search
		           int ticket_number = results.getInt("ticket_id");
		           String descriptor = results.getString("ticket_description");
		           String ticket_status = results.getString("status");
		           String ticket_priority = results.getString("priority");
		           String timestamp_intial = results.getString("create_date");
		          
		          
		           String authenticate = results.getString("ticket_name");
		           
		           
		           if (authenticate.equals(current_user)) {
		           
		           JOptionPane.showMessageDialog(null,
		        		   "Priority: " + ticket_priority + "\n\nTicket Number:" + ticket_number +  "\n\nDescription: " + descriptor + "\n\nStatus: " + ticket_status + "\n\nInitiated on: " + timestamp_intial);

			        
			        statement.close();
			        dbConn.close();   //close connections!!!
		           }// check if the user entry is valid
		           else {
			        	  JOptionPane.showMessageDialog(null,"You are not authorized to view another user's ticket");
			           }
		           
			       }
			       else {
			    	   JOptionPane.showMessageDialog(null,"Invalid Entry / Ticket Number!! Please Try Again");
			       }
		           
		           
		           
		            
				} catch (InstantiationException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (IllegalAccessException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (ClassNotFoundException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (SQLException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				}
			    
			    
				
			}
		});//listeners...
		btnViewTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnViewTicket.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnViewTicket.setBounds(497, 66, 147, 61);
		contentPane.add(btnViewTicket);
		
		JButton btnEditTicket = new JButton("EXIT APPLICATION");
		btnEditTicket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btnEditTicket.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditTicket.setBounds(248, 251, 147, 61);
		contentPane.add(btnEditTicket);
		
		JLabel lblPleaseChooseAn = new JLabel("PLEASE CHOOSE AN OPTION");
		lblPleaseChooseAn.setForeground(new Color(255, 255, 255));
		lblPleaseChooseAn.setFont(new Font("Arial", Font.PLAIN, 25));
		lblPleaseChooseAn.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseChooseAn.setBounds(143, 11, 433, 61);
		contentPane.add(lblPleaseChooseAn);
		// new ticket creating option
		JButton btnNewRequest = new JButton("NEW TICKET");
		btnNewRequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newTicketMaker();
			}
		});
		btnNewRequest.setBounds(10, 87, 176, 60);
		contentPane.add(btnNewRequest);
		
		//view all tickets option
		JButton btnViewAllTickets = new JButton("VIEW ALL TICKETS");
		btnViewAllTickets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					
					Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
		                                                               + "&user=fp411&password=411");
		            
		            Statement statement = dbConn.createStatement();
		            
		            String by_ID = "SELECT * FROM mufid_izaazTicket5 WHERE ticket_name='" + current_user + "';";
		            
		            ResultSet results = statement.executeQuery(by_ID);
		            
		            System.out.println("loading table...");
		            
		            // Use JTable built in functionality to build a table model and display the table model
		            // off a resultset!!!
		          
		            
		           
			        //Show all the tickets created by the logged in user
			        JFrame newwindow=new JFrame("Displaying All Submitted Tickets by: " + current_user);
			        newwindow.setSize(630,400);
			        newwindow.setVisible(true);
			        JTable jt = new JTable(ticketsJTable.buildTableModel(results));
			        JScrollPane scrollpane = new JScrollPane(jt);
			        jt.setEnabled(false);

			        newwindow.getContentPane().add(scrollpane, BorderLayout.CENTER);
			        
			        
			        statement.close();
			        dbConn.close();   //close connections!!!
			        
		            //catch statements
				} catch (InstantiationException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (IllegalAccessException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (ClassNotFoundException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (SQLException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				}
				
				
			}
		});
		btnViewAllTickets.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnViewAllTickets.setBounds(497, 138, 147, 61);
		contentPane.add(btnViewAllTickets);
		
		JLabel lblSubmittedByThis = new JLabel("SUBMITTED BY THIS USER");
		lblSubmittedByThis.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubmittedByThis.setForeground(Color.RED);
		lblSubmittedByThis.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblSubmittedByThis.setBounds(497, 204, 147, 14);
		contentPane.add(lblSubmittedByThis);
		
		// Button for ticket close request
		JButton btnAdminRequest = new JButton("REQUEST TICKET CLOSE");
		btnAdminRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAdminRequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JPanel panel = new JPanel(new GridLayout(0, 1));
				JTextField field1 = new JTextField();
				JTextField field2 = new JTextField();
				
				panel.add(new JLabel( "Ticket #:"));
				panel.add(field1);
				panel.add(new JLabel("Description:"));
		        panel.add(field2);
		        
				// this creates a request ticket to close a ticket
				int result = JOptionPane.showConfirmDialog(null, panel, "Create A New Request Ticket",
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				// user input...
		        if (result == JOptionPane.OK_OPTION) {
		        	 try {
		 				Class.forName("com.mysql.jdbc.Driver").newInstance();
						
		 				Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
		 	                                                               + "&user=fp411&password=411");
		 	         
		 	            Statement statement = dbConn.createStatement();
		 	            
		 	            
		 	          //  int exist_checker = statement.executeUpdate("Insert into mufid_izaaz_requests3(user_name, ticket_id, description) values('"+current_user+"','"+field1.getText()+"','"+field2.getText()+"')", Statement.RETURN_GENERATED_KEYS);
		 	            System.out.println(field1.getText());
		 	            
		 	            String sql1 = "SELECT * FROM mufid_izaazTicket5 WHERE ticket_id='" + field1.getText() + "';";
			            
			            ResultSet results = statement.executeQuery(sql1);
			            
			           
			            boolean blnFound=false; //flag if user's credentials are valid
				       
			            	blnFound = results.first();  //grabs first record match!
				           
				           System.out.println(blnFound);
				           
				       if (blnFound) {
			            
			            	
		 	            int result1 = statement.executeUpdate("Insert into mufid_izaaz_requests3(user_name, ticket_id, description) values('"+current_user+"','"+field1.getText()+"','"+field2.getText()+"')", Statement.RETURN_GENERATED_KEYS);
		 	           
		 	            
		 	            //gets last entered ID for records purposes
		 	           ResultSet rs = statement.getGeneratedKeys();
		 	          int generatedKey = 0;
		 	          if (rs.next()) {
		 	              generatedKey = rs.getInt(1);
		 	          }
		 	            
		 	            if (result1 != 0) {
		 					System.out.println("Request Ticket Created Successfully!!!");
		 				} else {
		 					System.out.println("Ticket Ticket cannot be Created!!!");
		 				}
		 	            // this confirms request
		 		        JOptionPane.showMessageDialog(null,"Thank you for your submission!! \n        Your Ticket id is : " + generatedKey + "!! \n Please remember this for your records!"); //******fill in with id value newly created in table!******//
		 		       statement.close();
				        dbConn.close();   //close connections!!!
		        	 }
			          else {
			 		        JOptionPane.showMessageDialog(null,"ERROR!! That ticket ID does not exist!!");
			          }
		        	 }
		        	 
		        	//catch statements
		        	 catch (SQLException ex) {
		        	System.out.println("Error warning: " + ex.getMessage());
		 		       JOptionPane.showMessageDialog(null,"ERROR!! CHECK TICKET #ID AND TRY AGAIN!");

		 			
		 				
		 		       } catch (InstantiationException e1) {
		 		    	  System.out.println("Error warning: " + e1.getMessage());
		 			
		 			} catch (IllegalAccessException e1) {
		 				System.out.println("Error warning: " + e1.getMessage());
		 			} catch (ClassNotFoundException e1) {
		 				System.out.println("Error warning: " + e1.getMessage());
		 				
		 			}
		        	 
		           
		            
		        } else {
		            System.out.println("Cancelled");
		        
		        }
		        
		        
			}
		});
		btnAdminRequest.setBounds(203, 87, 176, 60);
		contentPane.add(btnAdminRequest);
		
		//close request button...
		JButton btnViewCloseRequests = new JButton("VIEW CLOSE REQUESTS");
		btnViewCloseRequests.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
				
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					
					Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
		                                                               + "&user=fp411&password=411");
		            
		            Statement statement = dbConn.createStatement();
		            
		            String by_ID = "SELECT * FROM mufid_izaaz_requests3 WHERE user_name='" + current_user + "';";
		            
		            ResultSet results = statement.executeQuery(by_ID);
		            
		            System.out.println("loading table...");
		            
		            // Use JTable built in functionality to build a table model and display the table model
		            // off a resultset!!!
		          
		            
		           
			        
			        JFrame newwindow=new JFrame("Displaying All Ticket Requests by: " + current_user);
			        newwindow.setSize(630,400);
			        newwindow.setVisible(true);
			        JTable jt = new JTable(ticketsJTable.buildTableModel(results));
			        JScrollPane scrollpane = new JScrollPane(jt);
			        jt.setEnabled(false);

			        newwindow.getContentPane().add(scrollpane, BorderLayout.CENTER);
			        
			        
			        statement.close();
			        dbConn.close();   //close connections!!!
			        
		            
				} catch (InstantiationException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (IllegalAccessException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (ClassNotFoundException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				} catch (SQLException e1) {
					System.out.println("Error warning: " + e1.getMessage());
				}
				
			}
		});
		btnViewCloseRequests.setBounds(105, 158, 176, 60);
		contentPane.add(btnViewCloseRequests);
	}
	
	//making a new ticket
    private static void newTicketMaker() {
        String[] items = {"Low", "Medium", "High", "URGENT"};
        JComboBox combo = new JComboBox(items);
        JTextField field2 = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Priority: "));
        panel.add(combo);
        panel.add(new JLabel("Description:"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Create A New Ticket",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        //user input checking
        if (result == JOptionPane.OK_OPTION) {
        	 try {
 				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
 				Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
 	                                                               + "&user=fp411&password=411");
 	            
 	            Statement statement = dbConn.createStatement();
 	            
 	            String status = "OPEN";
 	            
 	            int result1 = statement.executeUpdate("Insert into mufid_izaazTicket5(ticket_name, ticket_description, priority, status, create_date) values('"+current_user+"','"+field2.getText()+"','"+combo.getSelectedItem()+"', '"+status+"', '"+new java.sql.Timestamp(new java.util.Date().getTime())+"')", Statement.RETURN_GENERATED_KEYS);
 	           
 	            
 	            //gets last entered ID for records purposes
 	           ResultSet rs = statement.getGeneratedKeys();
 	          int generatedKey = 0;
 	          if (rs.next()) {
 	              generatedKey = rs.getInt(1);
 	          }
 	            
 	            if (result1 != 0) {
 					System.out.println("Ticket Created Successfully!!!");
 				} else {
 					System.out.println("Ticket cannot be Created!!!");
 				}
 		 
 		        JOptionPane.showMessageDialog(null,"Thank you for your submission!! \n        Your Ticket id is : " + generatedKey + "!! \n Please remember this for your records!"); //******fill in with id value newly created in table!******//
 		       statement.close();
		        dbConn.close();   //close connections!!!
        	 }
        	 //catch statement
        	 catch (SQLException ex) {
        		 System.out.println("Error warning: " + ex.getMessage());
 		       } catch (InstantiationException e1) {
 		    	  System.out.println("Error warning: " + e1.getMessage());
 			} catch (IllegalAccessException e1) {
 				System.out.println("Error warning: " + e1.getMessage());
 			} catch (ClassNotFoundException e1) {
 				System.out.println("Error warning: " + e1.getMessage());
 			}
           
            
        } else {
            System.out.println("Cancelled");
            

        }
        
        
    }
}
