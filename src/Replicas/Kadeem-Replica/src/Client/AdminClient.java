package Client;

/*
 * 
 * This class implements the Admin client. When created, it creates a unique admin ID and connects the client to the appropriate
 * city server. Both the client and server connect via hostname "localhost" and a hardcoded port number 1001
 */


import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import HospitalApp.HospitalServerInter;
import HospitalApp.HospitalServerInterHelper;


public class AdminClient {
	
	// CORBA servant used for RPC
	static HospitalServerInter hospitalServerImpl;
	
	//Unique adminID
	private String adminID;
	
	//admin will be linked to their own server
	private String ServerName=null;
	
	// Logger object used to record log messages to a .txt file
	private Logger logger = null;
	

	public AdminClient(String adminID, String ServerName) throws Exception
	{
		
		//checks if the ID entered is a admin ID. if not, throw exception and don't create patient
		if(adminID.contains("MTLP") || adminID.contains("SHEP") || adminID.contains("QUEP") )
		{
			throw new Exception ("Login Error: This client is for admins");
		}
		

		
		this.adminID = adminID;
		this.ServerName = ServerName;
		this.logger = this.initiateLogger();
		
		
		//initialize server object and bind ord properties
		 try{
				
			 
			 String[] args = new String[1];
			 
			 if(this.ServerName.contains("MTL")) {
				 
				 
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
			        NamingContextExt ncRef = 
			          NamingContextExtHelper.narrow(objRef);
			       
			        hospitalServerImpl =  HospitalServerInterHelper.narrow(ncRef.resolve_str(this.ServerName));
			        
				 
			 }
			 
			 if(this.ServerName.contains("QUE")) {
				 
					Properties props = new Properties();
				    props.put("org.omg.CORBA.ORBInitialPort", "901");
				    props.put("org.omg.CORBA.ORBInitialHost", "localhost");
				 
			 
			        // create and initialize the ORB
				     ORB orb = ORB.init(args, null);

			        // get the root naming context
			        org.omg.CORBA.Object objRef = 
				     orb.resolve_initial_references("NameService");
			        // Use NamingContextExt instead of NamingContext, 
			        // part of the Interoperable naming Service.  
			        NamingContextExt ncRef = 
			          NamingContextExtHelper.narrow(objRef);
			       
			        hospitalServerImpl =  HospitalServerInterHelper.narrow(ncRef.resolve_str(this.ServerName));
				 
				 
			 }
			 
			 
			 if(this.ServerName.contains("SHE")) {
				 
					Properties props = new Properties();
				    props.put("org.omg.CORBA.ORBInitialPort", "902");
				    props.put("org.omg.CORBA.ORBInitialHost", "localhost");
				 
			 
			        // create and initialize the ORB
				     ORB orb = ORB.init(args, null);

			        // get the root naming context
			        org.omg.CORBA.Object objRef = 
				     orb.resolve_initial_references("NameService");
			        // Use NamingContextExt instead of NamingContext, 
			        // part of the Interoperable naming Service.  
			        NamingContextExt ncRef = 
			          NamingContextExtHelper.narrow(objRef);
			       
			        hospitalServerImpl =  HospitalServerInterHelper.narrow(ncRef.resolve_str(this.ServerName));
				 
				 
			 }
			 
	
		   
		        
		
		 }
		        catch (Exception e) {
		            System.out.println("ERROR : " + e) ;
		    	     e.printStackTrace(System.out);
		    	  } 


		
		System.out.println("Client Started. Welcome to the Distributed Health Care Management System (DHMS) Admin:" 
		+ this.adminID + " Your  Hospital Server is : " + this.ServerName);	
		
		this.logger.info("Login successful. | AdminID: " + this.adminID + " | Hospital Server: " + this.ServerName);
	}
	
	
	
	private Logger initiateLogger() 
	{
		Logger logger = Logger.getLogger("AdminClient Logs/" + this.ServerName + this.adminID+ " - Admin Log.txt");
		FileHandler fh;
		
		try
		{
		
			fh = new FileHandler("AdminClient Logs/" + this.ServerName + this.adminID+ " - Client Log.txt");
			
			
			logger.setUseParentHandlers(false);
			logger.addHandler(fh);
			
			
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		}
		catch (SecurityException e)
		{
			System.err.println("Server Log: Error: Security Exception " + e);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.err.println("Server Log: Error: IO Exception " + e);
			e.printStackTrace();
		}
		
	
		
		return logger;
	}
	
	
	//invokes the addAppointment method remotely using a HospitalServerInterface object
	public void addAppointment (String appointmentID, String appointmentType, int capacity, String adminID) {
		
		this.logger.info("Admin" +" "+this.adminID+" "+" is attempting to add an appointment");
		  
		  this.logger.info("Admin" +this.adminID + "is contacting their appropriate server in order to book the appointment");
		  
   		        hospitalServerImpl.addAppointment(appointmentID,appointmentType,capacity,adminID);

	}
	
	//invokes the removeAppointment method remotely using a HospitalServerInterface object
	public synchronized void removeAppointment (String appointmentID, String appointmentType, String adminID) {
		
		this.logger.info("Admin" +" "+this.adminID+" "+" is attempting to remove an appointment");
		  
		  this.logger.info("Admin" +this.adminID + "is contacting their appropriate server in order to book the appointment");
		  
		hospitalServerImpl.removeAppointment(appointmentID,  appointmentType,  adminID);

		
	}
	
	
	
	
	//invokes the listAppointmentAvailability method remotely using a HospitalServerInterface object
	public synchronized void listAppointmentAvailability(String appointmentType)  {
		
		this.logger.info("Admin" +" "+this.adminID+" "+" is attempting to list all available appointments");
		  
		  this.logger.info("Admin" +this.adminID + "is contacting their appropriate server in order to book the appointment");
		
		  hospitalServerImpl.listAppointmentAvailability(appointmentType);
			
		
	}
	
	
	/*
	 * 
	 * Patient client methods that the admin can also perform
	 * 
	 */
	
	
	
	//invokes the bookAppointment method remotely using a HospitalServerInterface object
	public synchronized void bookAppointment (String patientID, String appointmentID, String appointmentType) {
		
		  this.logger.info("Admin" +" "+this.adminID+" "+" is attempting to book an appointment");
		  
		  this.logger.info("Patient" +this.adminID + "is contacting their appropriate server in order to book the appointment");
		  
	
		
		hospitalServerImpl.bookAppointment(patientID, appointmentID, appointmentType);
	}
	
	//invokes the getAppointment method remotely using a HospitalServerInterface object
	public synchronized void getAppointmentSchedule (String patientID)  {
		
		 this.logger.info("Patient" +this.adminID + "is attempting to get the appointment schedules from all servers");
		 
		 this.logger.info("Patient" +this.adminID + "is contacting their appropriate server in order to book the appointment");
		
		
		 hospitalServerImpl.getAppointmentSchedule (patientID);
		
	}
	
	//invokes the cancelAppointment method remotely using a HospitalServerInterface object
	public synchronized void cancelAppointment(String patientID, String appointmentID){
		
		
		 this.logger.info("Patient" +this.adminID+ "is  attempting to cancel an appointment");
		 
		 this.logger.info("Patient" +this.adminID + "is contacting their appropriate server in order to book the appointment");
		 
		
		hospitalServerImpl.cancelAppointment(patientID, appointmentID);
	}
	
	
	
	
	public static void main (String[] args) throws Exception  {
		

		AdminClient test= new AdminClient("MTLA0001","MTL");
		//AdminClient test2= new AdminClient("MTLP0001","MTL");
		AdminClient test3= new AdminClient("SHEA0001","SHE");
		
		test.addAppointment("MTLA100919","Dental",1,"MTLA0001");
		test.addAppointment("MTLE100654","Surgeon",1,"MTLA0001");
		test.addAppointment("MTLA10574","Surgeon",1,"MTLA0001");
		test.addAppointment("QUEA100919","Dental",1,"MTLA0001");

		//test2.removeAppointment("MTLA100919","Dental","QUEA0001");
	
	


		
	}
	
}