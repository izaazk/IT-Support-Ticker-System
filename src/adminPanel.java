import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class adminPanel extends JFrame {

	static String save_path;
	
	private static final long serialVersionUID = 9142368841647008209L;
	private JPanel contentPane;
	JScrollPane sp = null;
	//Launch the application.
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminPanel frame = new adminPanel();
					frame.setDefaultCloseOperation(0);
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Error warning: " + e.getMessage());
				}
			}
		});
	}

	//Create the frame
	// method to get the date and time
	
	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
	
	
	// creating the GUI buttons
	public adminPanel() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(100, 100, 665, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		
		
		JLabel lblPleaseChooseAn = new JLabel("ADMINISTRATION PANEL");
		lblPleaseChooseAn.setForeground(new Color(255, 255, 255));
		lblPleaseChooseAn.setFont(new Font("Arial", Font.PLAIN, 25));
		lblPleaseChooseAn.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseChooseAn.setBounds(157, 24, 425, 50);
		contentPane.add(lblPleaseChooseAn);
		
		
		
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","IT Ticket - Administration System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		        	try {
		        		backupData();
		        	
		 	    	  
		        } catch (SQLException e) {
		        	 JOptionPane.showMessageDialog(null, "ERROR WARNING: " + e.getMessage());
		        	 System.out.println("Error warning: " + e.getMessage());
		        	 
		        	 //on some file systems, we are having issues with file permissions... temp fix
		        	 System.exit(0);
					}
		        	
		        	System.exit(0);
		        }
		    }
		});
		
		JButton btnViewTickets = new JButton("VIEW ALL TICKETS");
		btnViewTickets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//try catch statements for exceptions
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					
					Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
		                                                               + "&user=fp411&password=411");
		            
		            Statement statement = dbConn.createStatement();
		            
		            ResultSet results = statement.executeQuery("SELECT * FROM mufid_izaazTicket5");
		            
		            System.out.println("loading table...");
		            
		            // Use JTable built in functionality to build a table model and display the table model off a resultset!!!
		 
		            
			        JFrame newwindow=new JFrame("Displaying All Tickets: ");
			        newwindow.setSize(630,400);
			        newwindow.setVisible(true);
			        JTable jt = new JTable(ticketsJTable.buildTableModel(results));
			        JScrollPane scrollpane = new JScrollPane(jt);
			        jt.setEnabled(false);
			        newwindow.getContentPane().add(scrollpane, BorderLayout.CENTER);
			        
			       
			        
			        statement.close();
			        dbConn.close();   //close connections!!!
			        
		   // catch statements to go with the try statements above
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
		
		
// more buttons creation and listeners
		btnViewTickets.setBounds(10, 117, 147, 61);
		contentPane.add(btnViewTickets);
		
		JButton btnEditTicket = new JButton("EDIT TICKET");
		btnEditTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditTicket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				  JFrame frame = new JFrame("Single Ticket Lookup");
				    // prompt the user to enter their name
				    String name = JOptionPane.showInputDialog(frame, "Please Enter the Ticket Number You wish to View: ");
				    
				    
				    int ticID = Integer.parseInt(name);
				
				// try catch to find the tickets from DB
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
			           //results.next();
			          
			           int ticket_number = results.getInt("ticket_id");
			           String ticket_origin = results.getString("ticket_name");
			           String descriptor = results.getString("ticket_description");
			           String ticket_status = results.getString("status");
			           String ticket_priority = results.getString("priority");
			           String timestamp_intial = results.getString("create_date");
			            
			           statement.close();
				        dbConn.close();   //close connections!!!
			           
			         //creates base window with fields:
						 	
			           		//more GUI...
			           
			           		String[] items = {"Low", "Medium", "High", "URGENT"};
					        JComboBox combo = new JComboBox(items);
					        JTextField field2 = new JTextField();
					        JPanel panel = new JPanel(new GridLayout(0, 1));
					        panel.add(new JLabel("Editing Ticket #: " + ticket_number));
					        
					        panel.add(new JLabel("\nOrigin User ID: " + ticket_origin + " | Created on " + timestamp_intial));
					        panel.add(new JLabel("Current Ticket Status: " + ticket_status));
					        panel.add(new JLabel("\nUpdate the fields below as necessary: "));
					        panel.add(new JLabel(""));
					        panel.add(new JLabel("\nPriority: "));
					        panel.add(combo);
					        panel.add(new JLabel("Description:"));
					        panel.add(field2);
					        
					   
					        combo.setSelectedItem(ticket_priority);
					        field2.setText(descriptor);
					        
					        // setting up the edit ticket option
					        if (ticket_status.equalsIgnoreCase("OPEN")) {
					        
					        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Ticket #: " + ticket_number,
					            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					        
					        
				        //checking the users response
					        if (result == JOptionPane.OK_OPTION) {
					        	 try {
					 				Class.forName("com.mysql.jdbc.Driver").newInstance();
									
					 				Connection dbConn1 = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
					 	                                                               + "&user=fp411&password=411");
					 	            
					 	           
					 	            
					 	            
					 	            //int result1 = statement1.executeUpdate("Insert into mufid_izaazTicket5(ticket_name, ticket_description, priority, status, create_date) values('"+current_user+"','"+field2.getText()+"','"+combo.getSelectedItem()+"', '"+status+"', '"+new java.sql.Timestamp(new java.util.Date().getTime())+"')",;
					 	           
					 	           
					 	           PreparedStatement ps = dbConn1.prepareStatement(
					 	        	      "UPDATE mufid_izaazTicket5 SET ticket_description = ?, priority = ? WHERE ticket_id = ?");
					 	           
					 	        
					 	         ps.setString(1,field2.getText());
					 	         ps.setString(2,combo.getSelectedItem().toString());
					 	         ps.setInt(3, ticket_number);
					 	         

					 	         // call executeUpdate to execute our sql update statement
					 	         ps.executeUpdate();
					 	         ps.close();
					 	          
					 	        JOptionPane.showMessageDialog(null, "Ticket# " + ticket_number + " updates successfully.");
					 	         
					 	            
					        	 }
					        //catch statement for non existent ticket numbers
					        	 catch (SQLException ex) {
					        		 System.out.println("Error warning: " + ex.getMessage());
					        		
					        		 JOptionPane.showMessageDialog(null,"Error! Ticket Number Not Found");
					        		 
					 		       } catch (InstantiationException e1) {
					 		    	  System.out.println("Error warning: " + e1.getMessage());
					 			} catch (IllegalAccessException e1) {
					 				System.out.println("Error warning: " + e1.getMessage());
					 			} catch (ClassNotFoundException e1) {
					 				System.out.println("Error warning: " + e1.getMessage());
					 			}
					           
					            
					        } // if the cancell button was selected
					        else {
					        	System.out.println("cancelled");
					        }
					        
					        
					        }// If the ticket status is closed it is not editable
					        else {
					        	JOptionPane.showMessageDialog(null,"Error! Ticket Number Is in CLOSED status and cannot be edited!");
					        }
					        
					       
					        
			           }// if the ticket number was not found
			           else {
			        	   String message = "Enter correct correct ticket ID";
		              		JOptionPane.showMessageDialog(null, message);
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
		});
		//edit ticket button
		btnEditTicket.setBounds(184, 300, 147, 61);
		contentPane.add(btnEditTicket);
		
		//purge data button
		JButton btnDeleteAllTickets = new JButton("PURGE DATA");
		btnDeleteAllTickets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dropTable();
				} catch (Exception e1) {
					System.out.println("Error warning: " + e1.getMessage());
				}
			}
		});
		btnDeleteAllTickets.setForeground(Color.RED);
		btnDeleteAllTickets.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		btnDeleteAllTickets.setBounds(476, 222, 147, 61);
		contentPane.add(btnDeleteAllTickets);
		
		JLabel lblWarningCannotBe = new JLabel("WARNING! Cannot be undone!");
		lblWarningCannotBe.setForeground(new Color(255, 51, 0));
		lblWarningCannotBe.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblWarningCannotBe.setBounds(476, 294, 147, 14);
		contentPane.add(lblWarningCannotBe);
		
		// View requests for close button
		JButton btnInsertTicket = new JButton("VIEW REQUESTS");
		btnInsertTicket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					
					Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
		                                                               + "&user=fp411&password=411");
		            
		            Statement statement = dbConn.createStatement();
		            
		            ResultSet results = statement.executeQuery("SELECT * FROM mufid_izaaz_requests3");
		            
		            System.out.println("loading table...");
		            
		            // Use JTable built in functionality to build a table model and display the table model
		            // off a resultset!!!
		          
		            
		           
			        
			        JFrame newwindow=new JFrame("Displaying All Tickets: ");
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
		btnInsertTicket.setBounds(89, 189, 147, 61);
		contentPane.add(btnInsertTicket);
		
		// close ticket button
		JButton btnCloseTicket = new JButton("CLOSE TICKET");
		btnCloseTicket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
							
				
				// closing a ticket
				 int  ticID = 0;
				  JFrame frame = new JFrame("Close A Ticket - Enter ID #");
				    // prompt the user to enter their name
				    String name = JOptionPane.showInputDialog(frame, "Please Enter the Ticket Number You wish to Close: ");
				    //i was going to try frame.option for "cancel" ... but this is the part that errors ..
				
			
				  if (name != null) {
					  ticID = Integer.parseInt(name); 
					  System.out.println("parsed");
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
			           
			           
			           if (blnFound ) {
			           //results.next();
			          
			           int ticket_number = results.getInt("ticket_id");
			           String ticket_origin = results.getString("ticket_name");
			           String descriptor = results.getString("ticket_description");
			           String ticket_status = results.getString("status");
			           String ticket_priority = results.getString("priority");
			           String timestamp_intial = results.getString("create_date");
			           
			           Timestamp timestamp1 = results.getTimestamp("create_date");
			          
			           
			           statement.close();
				        dbConn.close();   //close connections!!!
			           
			         //creates base window with fields:
						 	
			           	String default1 = "OPEN";	
				        
			           if (ticket_status.equals(default1) ) {
			     
					      // creating an output table for viewing tickets
					        JPanel panel = new JPanel(new GridLayout(0, 1));
					        panel.add(new JLabel("Are you sure you wish to close Ticket #: " + ticket_number));
					        panel.add(new JLabel(""));
					        panel.add(new JLabel("\nOrigin User ID: " + ticket_origin + " | Created on " + timestamp_intial));
					        panel.add(new JLabel("\n Current Ticket Status: " + ticket_status));
					        panel.add(new JLabel(""));
					        panel.add(new JLabel("\nTicket Details: "));
					        panel.add(new JLabel("---------------------------------"));
					        panel.add(new JLabel("\nPriority: " + ticket_priority));
					        panel.add(new JLabel(""));
					        panel.add(new JLabel("\nDescription:"));
					        panel.add(new JLabel (descriptor));
					        
					        
					        
					        //taking user input for close ticket option
					        int result = JOptionPane.showConfirmDialog(null, panel, "Close Ticket #: " + ticket_number,
					            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					        
					      
				        
					        if (result == JOptionPane.OK_OPTION) {
					        	 try {
					 				Class.forName("com.mysql.jdbc.Driver").newInstance();
									
					 				Connection dbConn1 = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
					 	                                                               + "&user=fp411&password=411");
					 	            
					 	          
					 	            
					 	           					 	           
					 	           
					 	        PreparedStatement ps = dbConn1.prepareStatement(
					 	        	      "UPDATE mufid_izaazTicket5 SET status = ?, Close_Date = ? WHERE ticket_id = ?");
					 	           
					 	        
					 	         ps.setString(1,"CLOSED");
					 	         ps.setTimestamp(2,getCurrentTimeStamp());
					 	         ps.setInt(3, ticket_number);
					 	        
					 	         
					 	         
					 	         
					 	        PreparedStatement ps1 = dbConn1.prepareStatement(
					 	        	      "UPDATE mufid_izaaz_requests3 SET completed = ? WHERE ticket_id = ?");
					 	           
					 	        
					 	         ps1.setString(1,"YES");
					 	         ps1.setInt(2, ticket_number);
					 	         

					 	         // call executeUpdate to execute our sql update statement
					 	         ps.executeUpdate();
					 	         ps1.executeUpdate();
					 	         ps.close();
					 	         
       
					 	         
					 		    // create a  second time stamp
					 			Timestamp timestamp2 = new java.sql.Timestamp(new java.util.Date().getTime());
					 		 
					 		    // get time difference in seconds
					 		    long milliseconds = timestamp2.getTime() - timestamp1.getTime();
					 		    int seconds = (int) milliseconds / 1000;
					 		 
					 		    // calculate hours minutes and seconds
					 		    int hours = seconds / 3600;
					 		    int minutes = (seconds % 3600) / 60;
					 		    seconds = (seconds % 3600) % 60;
					 		 
					 		    //format caclulations into a string for easy output into joptionpane
					 		    String dateDiffCalc = hours + " Hours: " +  minutes + " Minutes: " + seconds + " Seconds: ";
					 	         
					 	         
					 	         
					 	        
					 	         //prints datediff info and confirm of ticekt closer
					 	        JOptionPane.showMessageDialog(null, "Ticket# " + ticket_number + " CLOSED successfully. "
					 	        		+ "\n Ticket Was Open for " + dateDiffCalc);
					        	 }
					        	 
					        	 
					        	 
					        	 
					        // try catch statements
					        	 catch (SQLException ex) {
					        		 System.out.println("Error warning: " + ex.getMessage());
				
					        		
					        		 JOptionPane.showMessageDialog(null,"Error! Ticket Number Not Found");
					        		 
					 		    } catch (InstantiationException e1) {
					 		    	  System.out.println("Error warning: " + e1.getMessage());
					 				
					 			} catch (IllegalAccessException e1) {
					 				System.out.println("Error warning: " + e1.getMessage());
					 			} catch (ClassNotFoundException e1) {
					 				System.out.println("Error warning: " + e1.getMessage());
					 			}
					        	 
					        	 
					        	 
					        }
					        
			           }
					        // if the ticket has been closed
					        else {
					        	JOptionPane.showMessageDialog(null,"Error! Ticket Number already closed");
					            //System.out.println("Cancelled");
					            
					        }
					        	
					      
					        
					        
			           }// if the ticket # is not found
			           else {
			        	   String message = "Ticket Does Not Exist / Field was Empty";
		              		JOptionPane.showMessageDialog(null, message);
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
		});
		btnCloseTicket.setBounds(10, 300, 147, 61);
		contentPane.add(btnCloseTicket);
		
		// export data button
		JButton btnExportData = new JButton("EXPORT DATA");
		btnExportData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				// where to export the data to
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				fileChooser.setSelectedFile(new File("exported_data.csv"));
				int userSelection = fileChooser.showSaveDialog(contentPane);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				    
				    save_path = fileToSave.getAbsolutePath();
				    
				}	
				
				 try {
					retrieveRecords();
				} catch (SQLException e) {
					System.out.println("Error warning: " + e.getMessage());
				}
				
				
				
				
				
			}
		});
		btnExportData.setForeground(Color.BLUE);
		btnExportData.setBounds(476, 150, 147, 61);
		contentPane.add(btnExportData);
		
		JLabel lblDataManagement = new JLabel("DATA MANAGEMENT");
		lblDataManagement.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataManagement.setForeground(new Color(255, 255, 255));
		lblDataManagement.setBounds(469, 125, 154, 14);
		contentPane.add(lblDataManagement);
		
		JLabel lblTicketActions = new JLabel("VIEW TICKET DATA");
		lblTicketActions.setForeground(new Color(255, 255, 255));
		lblTicketActions.setHorizontalAlignment(SwingConstants.CENTER);
		lblTicketActions.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTicketActions.setBounds(102, 92, 128, 14);
		contentPane.add(lblTicketActions);
		
		// Exit button...
		JButton btnExit = new JButton("EXIT");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				//backsup user data to local directory (for test purposes) so that even though something gets deleted it can be recovered!!
				try {
					backupData();
				} catch (SQLException e) {
					System.out.println("Error warning: " + e.getMessage());
				}
				System.exit(0);
				
			}
		});
		btnExit.setBounds(62, 399, 245, 43);
		contentPane.add(btnExit);
		
		
		// single ticket view option...
		JButton btnViewTicket = new JButton("VIEW (1) TICKET");
		btnViewTicket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
					int ticID = 0;
					
					 // a jframe here isn't strictly necessary, but it makes the example a little more real
				    JFrame frame = new JFrame("Single Ticket Lookup");

				    // prompt the user to enter their name
				    String name = JOptionPane.showInputDialog(frame, "Please Enter the Ticket Number You wish to View: ");
				 
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
				       
			           // if the ticket was found show the data
			           int ticket_number = results.getInt("ticket_id");
			           String submitter = results.getString("ticket_name");
			           String descriptor = results.getString("ticket_description");
			           String ticket_status = results.getString("status");
			           String ticket_priority = results.getString("priority");
			           String timestamp_intial = results.getString("create_date");
			           String close_date = results.getString("Close_Date");
			           
			         
			           if (ticket_status.equalsIgnoreCase("Closed")) {
			        	      
				           JOptionPane.showMessageDialog(null,
				        		   "Priority: " + ticket_priority + "\n\nTicket Number:" + ticket_number + "\nSubmitted By: " +submitter+  "\n\nDescription: " + descriptor + "\n\nStatus: " + ticket_status + "\n\nInitiated on: " + timestamp_intial + "\nClosed On: " + close_date);

			           }
			           
			           else {
			           JOptionPane.showMessageDialog(null,
			        		   "Priority: " + ticket_priority + "\n\nTicket Number:" + ticket_number +  "\n\nDescription: " + descriptor + "\n\nStatus: " + ticket_status + "\n\nInitiated on: " + timestamp_intial);

			           }
				        statement.close();
				        dbConn.close();   //close connections!!!
			           
			         
			           
				       }//entry not valid
				       else {
				    	   JOptionPane.showMessageDialog(null,"Invalid Entry! Try Again");
				       }
			           
			           
			           
			            
					}
				    
					  	catch (InstantiationException e1) {
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
		btnViewTicket.setBounds(167, 117, 147, 61);
		contentPane.add(btnViewTicket);
		
		JLabel label = new JLabel("TICKET ACTIONS");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(51, 204, 51));
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(108, 275, 128, 14);
		contentPane.add(label);
		
		//data analytics report button
		JButton btnDataAnalyticsReport = new JButton("DATA ANALYTICS REPORT");
		btnDataAnalyticsReport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				reporting_openTickets();
				reporting_HighTickets();
				

			}
		});
		btnDataAnalyticsReport.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDataAnalyticsReport.setForeground(new Color(204, 0, 153));
		btnDataAnalyticsReport.setBounds(358, 404, 224, 31);
		contentPane.add(btnDataAnalyticsReport);
		
	}
	
	
	   public void dropTable() throws Exception {
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
			      
			      final String createTable = "DROP TABLE mufid_izaazTicket";
			      
			      statement.executeUpdate(createTable);
			      System.out.println("Dropped table successfully...");

				//end create table
			    //close connection/statement object  
			     statement.close();
			     connect.close();
			    } catch (Exception e) {
			    	System.out.println(e.getMessage());  
			    }  

			
		}
	   
	   
	   
	   
	   public static void retrieveRecords() throws SQLException {
		  		      
		   
	       String sql = "select * from mufid_izaazTicket5";
	         
	       try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e1) {
			System.out.println("Error warning: " + e1.getMessage());

		} catch (IllegalAccessException e1) {
			System.out.println("Error warning: " + e1.getMessage());

		} catch (ClassNotFoundException e1) {
			System.out.println("Error warning: " + e1.getMessage());

		}
			
			Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
                                                              + "&user=fp411&password=411");
           
           Statement statement = dbConn.createStatement();
           
	       ResultSet result = statement.executeQuery(sql);
	         
	       int count = 0;
	       
	       //headers for the table
	        
	       //actual data for the table in a 2d array
	       PrintStream out = null;

	       try {
	    	   
	    	   
	           out = new PrintStream(save_path);

	       } catch (FileNotFoundException e) {

	    	   System.out.println("Error warning: " + e.getMessage());

	       }
	       
	       //table headings
	       out.println("-- IT Trouble Tickets Report --");
	       out.println("Ticket ID:,Submitted By:,Description:,Priority:,Status:,Create Date:,Close Date:");	       
	       //loop to print out all the ids
	       
	       while (result.next()){
	       int id = result.getInt("ticket_id");
	       String user_id = result.getString("ticket_name");
	       String description = result.getString("ticket_description");
	       String priorty = result.getString("priority");
	       String status = result.getString("status");
	       String create_date = result.getString("create_date");
	       String close_date = result.getString("Close_Date");



	       String output = "%-9d %-12s  %-4s";
	       
	       
	       //just a counter to print how many records were inputted
	       ++count;
	       //prints all the db entry to file for import into jtable
	       out.println(id + ","  + user_id + "," + description + "," + priorty + "," + status + "," + create_date + "," + close_date);
	       
	       
	   }
	       
	     //close connection
	       
	       
	       //confirms that all records from file were inserted
	       System.out.println("\nSuccessfully added / printed " + count + " records.");
	       
	       //print name etc
	       String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()); //format time and date
	       System.out.println("\nCur dt=" + timeStamp + "\nProgrammed by Izaaz Kothawala\n"); //display name, date, time
	       out.println("\nCur dt=" + timeStamp + "\nProgrammed by Izaaz Kothawala\n"); //print

	       
	   }
	   
	   
	   
	   
	   
	   public static void reporting_openTickets() {
		  
		
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
	                                                               + "&user=fp411&password=411");
	            
	            Statement statement = dbConn.createStatement();
	            
	            ResultSet results = statement.executeQuery("SELECT * FROM mufid_izaazTicket5");
	            
	          
	            
	            // Use JTable built in functionality to build a table model and display the table model
	            // off a resultset!!!
	           
	            
	            int ticket_number;
	            String ticket_origin;
	            String descriptor;
	            String status;
	          
	            Timestamp timestamp_intial;
	            Timestamp timestamp_final;
	           
	            boolean printed1 = false;
	            
	            int openTics = 0;
		        int closedTics = 0;
		        
		        
	            
	            
	            
	            while (results.next()) {
	            	
	              ticket_number = results.getInt("ticket_id");
		          ticket_origin = results.getString("ticket_name");
		          descriptor = results.getString("ticket_description");
		          status = results.getString("status");
		          timestamp_intial = results.getTimestamp("create_date");
		          timestamp_final = results.getTimestamp("Close_Date");
		           
		          
			         
		           if (timestamp_final == null) {
		        	 //not all tickets are closed... this checks current time because DB will be null for close column
		        	   timestamp_final = getCurrentTimeStamp();
		           }
		          
		           if (status.equalsIgnoreCase("OPEN")){ 
		        	   openTics++;
		           }
		           else {
		        	   closedTics++;
		        	   
		           }
		           
		         
		          if (status.equalsIgnoreCase("OPEN")) {
		        	  if (!printed1) {
        				   System.out.println("      -------- [OPEN] TICKETS REPORT --------------        ");
        				   System.out.printf("%-15s %-20s %-50s %-20s\n", "Ticket ID:", "Submitted by:", "Description:" , " Duration of Ticket");
        				   	printed1 = true;
        			   }

		        	  
			 		    // get time difference in seconds
			 		    long milliseconds = timestamp_final.getTime() - timestamp_intial.getTime();
			 		    int seconds = (int) milliseconds / 1000;
			 		 
			 		    // calculate hours minutes and seconds
			 		    int hours = seconds / 3600;
			 		    int minutes = (seconds % 3600) / 60;
			 		    seconds = (seconds % 3600) % 60;
			 		 
			 		    //format caclulations into a string for easy output into joptionpane
			 		    String dateDiffCalc = hours + " Hours: " +  minutes + " Minutes: " + seconds + " Seconds: ";
		        	  
			 		   System.out.printf("%-15s %-23s %-50s %-20s\n", ticket_number, ticket_origin, descriptor, dateDiffCalc);

		        	  
		        	  
		          }
	            
	            }
	            
	          
	            	
	            int totalTics = openTics + closedTics;
		        
			       // report pritner
		        System.out.println("\n ------------ Summary Report ---------------");
		        System.out.println("Total # of Tickets: " + totalTics );
		        System.out.println("# of OPEN tickets: | # Of Closed Tickets: ");
		        System.out.println("   " + openTics + "                          " + closedTics);
		         
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
	   
	   // high priority tickets report
	   public static void reporting_HighTickets() {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
	                                                               + "&user=fp411&password=411");
	            
	            Statement statement = dbConn.createStatement();
	            
	            ResultSet results = statement.executeQuery("SELECT * FROM mufid_izaazTicket5");
	            
	          
	            
	            // Use JTable built in functionality to build a table model and display the table model
	            // off a resultset!!!
	          
	            
	            int ticket_number;
	            String ticket_origin;
	            String descriptor;
	            String status;
	            String ticket_priority;
	          
	        
	            boolean printed = false;
	            
	            
	            
	            while (results.next()) {
	            	
	            	 ticket_number = results.getInt("ticket_id");
			          ticket_origin = results.getString("ticket_name");
			          descriptor = results.getString("ticket_description");
			          ticket_priority = results.getString("priority");
			          status = results.getString("status");
			         
		          
			         
			           if ((ticket_priority.equalsIgnoreCase("high") || ticket_priority.equalsIgnoreCase("urgent"))) {
			        		   	if(status.equalsIgnoreCase("open")) {
		        		   
	        			   if (!printed) {
	        				   System.out.println("\n -------- [OPEN] URGENT / HIGH PRIORITY TICKETS --------------");
	        				   System.out.printf("%-15s %-23s %-50s\n","Ticket ID", "Submitted by:" , "Description:");
	        				   	printed = true;
	        			   }
	        	 
	        	   System.out.printf("%-15s %-23s %-50s\n",ticket_number,ticket_origin,descriptor );
	        	   
	        	     
	           
	           }
			           }

		          		         
		          
	            
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
	   
	   public static void backupData() throws SQLException {
		  		      
		   
	       String sql = "select * from ikothawaTicket4";
	         
	       try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e1) {
			System.out.println("Error warning: " + e1.getMessage());

		} catch (IllegalAccessException e1) {
			System.out.println("Error warning: " + e1.getMessage());

		} catch (ClassNotFoundException e1) {
			System.out.println("Error warning: " + e1.getMessage());

		}
			
			Connection dbConn = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false"
                                                              + "&user=fp411&password=411");
           
           Statement statement = dbConn.createStatement();
           
	       ResultSet result = statement.executeQuery(sql);
	         
	       int count = 0;
	       
	       //headers for the table
	        
	       //actual data for the table in a 2d array
	       PrintStream out = null;

	       try {
	    	   //string that gets the default home based on OS
	    	   String userhome = System.getProperty("user.home");
	    	   //sets the working directory path for directory exist chcker
	    	   String save_path = userhome + "\\Documents\\IT-DB-Backup\\";
	    	   
	    	   String output = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss'.csv'").format(new Date());
	    	   File theDir = new File(save_path);

	    	// if the directory does not exist, create it (we want to make sure we dont fill up someones documents with these files... put them in a folder)
	    	if (!theDir.exists()) {
	    	    System.out.println("creating directory: " + theDir.getName());
	    	    boolean result1 = false;

	    	    try{
	    	    	//make the directory if it doesnt exist
	    	        theDir.mkdir();
	    	        result1 = true;
	    	    } 
	    	    catch(SecurityException se){
	    	        //handle it
	    	    }        
	    	    if(result1) {    
	    	        System.out.println("DIR created");  
	    	    }
	    	}
	    	// the actual file writing process in the directory with timestamp in the filename for easy tracking)
	           out = new PrintStream(userhome +"\\Documents\\IT-DB-Backup\\" + "backuo_" + output);

	       } catch (FileNotFoundException e) {
	    	   JOptionPane.showMessageDialog(null, "ERROR WARNING: " + e.getMessage());
	    	   System.out.println("Error warning: " + e.getMessage());

	       }
	       
	  	       
	       //loop to print out all the ids
	       
	       while (result.next()){
	       int id = result.getInt("ticket_id");
	       String user_id = result.getString("ticket_name");
	       String description = result.getString("ticket_description");
	       String priorty = result.getString("priority");
	       String status = result.getString("status");
	       String create_date = result.getString("create_date");
	       String close_date = result.getString("Close_Date");



	       //String output = "%-9d %-12s  %-4s";
	       
	       
	       //just a counter to print how many records were inputted
	       ++count;
	       //prints all the db entry to file for import into jtable
	       out.println(id + ","  + user_id + "," + description + "," + priorty + "," + status + "," + create_date + "," + close_date);
	       
	       
	   }
	       //close connection
	       dbConn.close();
	       statement.close();
	       
	   
	       
	       
	       //confirms that all records from file were inserted
	       
	       
	       //print name etc
	       String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()); //format time and date
	       System.out.println("\nCur dt=" + timeStamp); //display name, date, time
	       System.out.println("Data from DB backed up successfully to working directory");
	       //incase file name changes.. the timestamp is in the file tooo for easy tracking
	       out.println("\nCur dt=" + timeStamp ); //print

	       
	   }
		   
	      
	   
}

