package Client;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import HospitalApp.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Properties;

/*
 * 
 * This class implements the patient client. When created, it creates a unique patient ID and connects the client to the appropriate
 * city server. Both the client and server connect via hostname "localhost" and a hardcoded port number 1001
 */



public class PatientClient {
	
	// CORBA servant used for RPC
	static HospitalServerInter hospitalServerImpl;
	
	
	//Patient will be linked to their own server
	private String ServerName;
	
	// Logger object used to record log messages to a .txt file
	private Logger logger = null;
	
	//Unique PatientID
	private String PatientID;
	
	public String getServerName() {
		return ServerName;
	}
	
	
	
	//method to create new Logger object and initialize the files where patient logs will be kept
	private Logger initiateLogger() 
	{
		Logger logger = Logger.getLogger("PatientClient Logs/" + this.ServerName + this.PatientID+ " - Client Log.txt");
		FileHandler fh;
		
		try
		{
	
			fh = new FileHandler("PatientClient Logs/" + this.ServerName + this.PatientID+ " - Client Log.txt");
			

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
	
	
	
	//patient client constructor. Create a patient with a unique ID and link them to a specific server
	public PatientClient(String patientID, String ServerName) throws Exception
	{
		
		//checks if the ID entered is a patient ID. if not, throw exception and don't create patient
		if(patientID.contains("MTLA") || patientID.contains("SHEA") || patientID.contains("QUEA") )
		{
			throw new Exception ("Login Error: This client is for patients only");
		}

		this.PatientID=patientID;
		this.ServerName = ServerName;
		
		this.logger = this.initiateLogger();
		this.logger.info("Attempting to start client for patient:" +this.PatientID);
		
		
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

		
		
		
		System.out.println("Client Started. Welcome to the Distributed Health Care Management System (DHMS) Patient:" 
		+ this.PatientID + " Your  Hospital Server is : " + this.ServerName);	
		
		this.logger.info("Login successful. | PatientID: " + this.PatientID + " | Hospital Server: " + this.ServerName);
	}
	

	
	//invokes the bookAppointment method remotely using a HospitalServerInterface object
	public synchronized void bookAppointment (String patientID, String appointmentID, String appointmentType) {
		
		  this.logger.info("Patient" +" "+this.PatientID+" "+" is attempting to book an appointment");
		  
		  this.logger.info("Patient" +this.PatientID + "is contacting their appropriate server in order to book the appointment");
		  
		  hospitalServerImpl.bookAppointment(patientID,appointmentID,appointmentType);
		
		
	}
	
	//invokes the getAppointment method remotely using a HospitalServerInterface object
	public synchronized void getAppointmentSchedule (String patientID) {
		
		 this.logger.info("Patient" +this.PatientID + "is attempting to get the appointment schedules from all servers");
		 
		 this.logger.info("Patient" +this.PatientID + "is contacting their appropriate server in order to book the appointment");
		
		
		 hospitalServerImpl.getAppointmentSchedule(patientID);
		
	}
	
	//invokes the cancelAppointment method remotely using a HospitalServerInterface object
	public synchronized void cancelAppointment(String patientID, String appointmentID) {
		
		
		 this.logger.info("Patient" +this.PatientID + "is  attempting to cancel an appointment");
		 
		 this.logger.info("Patient" +this.PatientID + "is contacting their appropriate server in order to book the appointment");
		 
		
		 hospitalServerImpl.cancelAppointment(patientID, appointmentID);
	}
	
	public synchronized void swapAppointment(String patientID, String oldappointmentID, String oldappointmentType,
			String newappointmentID, String newappointmentType) {
		
		
		 this.logger.info("Patient" +this.PatientID + "is  attempting to cancel an appointment");
		 
		 this.logger.info("Patient" +this.PatientID + "is contacting their appropriate server in order to book the appointment");
		 
		
		 hospitalServerImpl.swapAppointment(patientID,  oldappointmentID, oldappointmentType,
					newappointmentID, newappointmentType);
	}
	
	
	//invokes the addAppointment method remotely using a HospitalServerInterface object
	public synchronized void addAppointment (String appointmentID, String appointmentType, int capacity, String adminID)  {
		
		this.logger.info("Patient" +" "+this.PatientID+" "+" is attempting to add an appointment");
		  
		  this.logger.info("Admin" +this.PatientID + "is contacting their appropriate server in order to book the appointment");
		  
		
		  hospitalServerImpl.addAppointment(appointmentID,appointmentType,capacity,adminID);
		
	}
	
	//invokes the removeAppointment method remotely using a HospitalServerInterface object
	public synchronized void removeAppointment (String appointmentID, String appointmentType, String adminID) {
		
		this.logger.info("Admin" +" "+this.PatientID+" "+" is attempting to remove an appointment");
		  
		  this.logger.info("Admin" +this.PatientID + "is contacting their appropriate server in order to book the appointment");
		  
		
		
		  hospitalServerImpl.removeAppointment(appointmentID,  appointmentType,  adminID);
		
	}
	
	
	
	
	//invokes the listAppointmentAvailability method remotely using a HospitalServerInterface object
	public synchronized void listAppointmentAvailability(String appointmentType) {
		
		this.logger.info("Admin" +" "+this.PatientID+" "+" is attempting to list all available appointments");
		  
		  this.logger.info("Admin" +this.PatientID + "is contacting their appropriate server in order to book the appointment");
		  

	}
	
	
	public static void main (String[] args) throws Exception  {
		
		PatientClient patient = new PatientClient("MTLP0001","MTL");
		//PatientClient patient2 = new PatientClient("MTLA0001","MTL");
		PatientClient patient3 = new PatientClient("SHEP0001","SHE");
		PatientClient patient4 = new PatientClient("QUEP0001","QUE");
		
		patient.bookAppointment("MTLP0001","MTLA100919","Dental");
		patient.bookAppointment("MTLP0001","QUEA100919", "Dental" );
		patient.bookAppointment("MTLP0001","MTLA10574", "Surgeon" );
		patient.getAppointmentSchedule("MTLP0001");
		patient.cancelAppointment("MTLA10574", "MTLP0001");
		patient.swapAppointment("MTLP0001","MTLA100919","Dental","MTLE100654", "Surgeon");
		patient.getAppointmentSchedule("MTLP0001");
		// patient.addAppointment("MTLA100919","Dental",1,"MTLP0001");

		
	}
	
}
	
