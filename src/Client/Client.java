package Client;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.CORBA.ORBPackage.InvalidName;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Properties;
import java.util.Random;
import java.io.IOException;
import java.util.Scanner;


public class Client {
	
	
	
	
	public static void main(String[] args) throws InvalidName {
	
		
		// initialize client ORB
		Properties props = new Properties();
	    props.put("org.omg.CORBA.ORBInitialPort", "900");
	    props.put("org.omg.CORBA.ORBInitialHost", "localhost");
	 
 
        // create and initialize the ORB
	     ORB orb = ORB.init(args, null);

        // get the root naming context
        org.omg.CORBA.Object objRef = 
	     orb.resolve_initial_references("NameService");
        // Use NamingContextExt instead of NamingContext, 
        // part of the Interoperable naming Service.  
        NamingContextExt ncRef =  NamingContextExtHelper.narrow(objRef);
       
		
		//Variables used for creating patient or admin ID
		Random rand = new Random();
		String patientID = null;
		String adminID=null;
		String appointmentID=null;
		String city = null;
		
		//Variables used for taking input and reasking if wrong input was entered		
		Scanner scanner = new Scanner(System.in);
		int input;
		String input1;
		boolean start=true;
		
		
				
				while(true) {  //First while loop. once everything is done, reask the user what they want to do 
					
					
					
					System.out.println("Client Started. Welcome to the Distributed Health Care Management System (DHMS)");
					System.out.println("Please choose from the following options:");
					
					
					 System.out.println("What city are you from? (Choose 1 for Montreal, 2 for Sherbrooke or 3 for Quebec (or 0 to exit))");
					 
					
					 
					 while (start){ // second while loop. keep asking user in case their input was wrong
						 
						 input = scanner.nextInt();
					 
					 switch(input)  
					 {					//switch statement used to determine which city the user chose
					 
					 case 0:
						 System.out.println("Thank you for using the Distributed Health Care Management System (DHMS) ");
						 System.exit(0);
					 
					   case 1:
					   
						city="MTL"; 
					    start=false;
					    break;
					    
					  case 2:
						  
						city="SHE";
						start=false;
					    break;
					    
					  case 3:
						  
						city="QUE";
						start=false;
						break;
						  
					  default:
						  System.out.println("Invalid Choice. Try Again");
						 
					}  //end of switch statement
					 
				 } // end of second while loop 
					 
					 start=true;
					 
					 while(start) { //second while loop . keep asking patient if they are admin or client
						 
						 
						 System.out.println("Are you an admin or patient? Choose 1 for admin, 2 for patient");
						 
						 input = scanner.nextInt();
						 
						 switch(input)
						 {				// switch statement used to determine patient or admin ID
						  case 1:
						   
							  adminID=city+String.format("%04d", rand.nextInt(10000));
								 System.out.println("Your unique ID is:" + adminID);
						    start=false;
						    break;
						    
						  case 2:
							  
							 patientID=city+String.format("%04d", rand.nextInt(10000));
							 System.out.println("Your unique ID is:" + patientID);
							start=false;
						    break;
						    
							  
						  default:
							  System.out.println("Invalid Choice. Try Again");
							 
						}
						 
					 } // end of second while loop 
						
					 start=true;
					 
					 while(start) { //third while loop. keep asking what option function user wants to perform
					 
					 	System.out.println("Set up complete ");
					 	System.out.println("Please choose one of the following options");

						System.out.println("0.Exit program");
						System.out.println("1.Book an appointment");
						System.out.println("2.Cancel an appointment!");
						System.out.println("3.View appointments schedule");
						
						input = scanner.nextInt();
						
						
						 switch(input)
						 {			
						 
						  case 0:
								 System.out.println("Thank you for using the Distributed Health Care Management System (DHMS) ");
								 System.exit(0);
							  
						  case 1:
						   
							
						    start=false;
						    break;
						    
						  case 2:
							  
							 
							start=false;
						    break;
						    
						    
						  case 3:
							  break;
							  
							  
						  case 4:
							  break;
							  
					
						  default:
							  System.out.println("Invalid Choice. Try Again");
							 
						}
						
						

					 }	
			
						start=true;
						
					
			} // end of first while loop 

	}
	
		
			

}
