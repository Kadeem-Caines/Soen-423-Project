package Client;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import corbaFrontEnd.frontEndOperations;
import corbaFrontEnd.frontEndOperationsHelper;

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
	
	

	
	public static void main(String[] args) throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, IOException {
	
		
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
        
        frontEndOperations object;
       
		
		//Variables used for creating patient or admin ID
		Random rand = new Random();
		String ID = null;
		String city = null;
	
		
		
		
		
		//Variables used for taking input and reasking if wrong input was entered		
		Scanner scanner = new Scanner(System.in);
		int input;
		String input1;
		boolean start=true;
		String appointmentID;
		String appointmentType;
		String newAppointmentID;
		String newAppointmentType;
		int capacity;
		
		
				
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
						   
							  ID=city+String.format("%04d", rand.nextInt(10000));
								 System.out.println("Your unique ID is:" + ID);
						    start=false;
						    break;
						    
						  case 2:
							  
							 ID=city+String.format("%04d", rand.nextInt(10000));
							 System.out.println("Your unique ID is:" + ID);
							start=false;
						    break;
						    
							  
						  default:
							  System.out.println("Invalid Choice. Try Again");
							 
						}
						 
					 } // end of second while loop 
						
					 
						System.out.println("Set up complete ");
						
					 start=true;
					 
					 while(start) { //third while loop. keep asking what option function user wants to perform
					 
					 
					 	System.out.println("Please choose one of the following options");

						System.out.println("0.Exit program");
						System.out.println("1.Book an appointment");
						System.out.println("2.Cancel an appointment!");
						System.out.println("3.View appointments schedule");
						System.out.println("4.Add appointments (Admins only)");
						System.out.println("5.List appointments (Admins only)");
						System.out.println("6.Swap appointments");
						System.out.println("7.Remove appointments (Admins only)");
						
						input = scanner.nextInt();
						
						
						 switch(input)
						 {			
						 
						  case 0:
								 System.out.println("Thank you for using the Distributed Health Care Management System (DHMS) ");
								 System.exit(0);
							  
						  case 1:
							  
						
							  System.out.print("Enter appointmentID");
							appointmentID = scanner.next();
							
							  System.out.print("Enter appointment type");
							appointmentType= scanner.next();
							
							object= frontEndOperationsHelper.narrow(ncRef.resolve_str(city));
							
							object.bookAppointment(ID,appointmentID,appointmentType);
								 
						
						    break;
						    
						  case 2:
							  
							  System.out.print("Enter appointmentID");
								 appointmentID = scanner.next();
							  
							 
							
						    break;
						    
						    
						  case 3:
							  System.out.print("Searching up appointments for"+ID);
							  
							  break;
							  
							  
						  case 4:
							  
							  if(ID.contains("P")) {
								  
								  System.out.print("You are not an admin. Please choose another option");
								  break;
							  }
							  
							  System.out.print("Enter appointmentID");
							  appointmentID = scanner.next();
							  
							  System.out.print("Enter appointment type");
								appointmentType= scanner.next();
								
								System.out.print("Enter appointment capacity");
								capacity= scanner.nextInt();
								
							  break;
							  
						  case 5:
							  
							  if(ID.contains("P")) {
								  
								  System.out.print("You are not an admin. Please choose another option");
								  break;
							  }
	  
							  System.out.print("Enter appointment type");
								appointmentType= scanner.next();
								
							  break;
							  
						  case 6:
							  
							  System.out.print("Enter appointment ID");
							  appointmentID = scanner.next();
							  
							  System.out.print("Enter appointment type");
								appointmentType= scanner.next();
								
								  System.out.print("Enter new appointment ID");
									newAppointmentID= scanner.next();
									

									  System.out.print("Enter new appointment type");
										newAppointmentType= scanner.next();
										start=false;
							  break;
							  
						  case 7: 
							  
							  if(ID.contains("P")) {
								  
								  System.out.print("You are not an admin. Please choose another option");
								  break;
							  }
							  
						  System.out.print("Enter appointment ID");
						  appointmentID = scanner.next();
						  
						  System.out.print("Enter appointment type");
							appointmentType= scanner.next();
					
					break;
						  default:
							  
							  System.out.println("Invalid Choice. Try Again");
							 
						}
						
						

					 }	
			
						start=true;
						
					
			} // end of first while loop 

	}
	
		

			

}
