package FrontEnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import corba.frontEndOperationsPOA;

/*
 * This class will implement a CORBA front end (FE) which receives a client request
and forwards it to a failure-free sequencer . the three replicas execute client requests in total order and return the
results back to the FE which in turn returns a single correct result back to the client as
soon as two identical (correct) results are received from the replicas.
 */

public class FrontEnd extends frontEndOperationsPOA {
	
	private  HashMap<String,InetSocketAddress> replicaManagerDatabase = new HashMap<String, InetSocketAddress>();
	private String sequencerAddress;


	public void initialzeORB () throws InvalidName {
		
	 try{
				
			 
			 String[] args = new String[1];
			 
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
	}
	 
	 finally {
		 
		 
	 }
	 
	}
	
	
	public void requestSequencer() {
		
	}
	
	public void replySequencer() {
		
	}
	
	public void RequestClient() {
		
	}
	
	public void replyClient() {
		
	}


	@Override
	public void bookAppointment(String patientID, String appointmentID, String appointmentType) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getAppointmentSchedule(String patientID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void cancelAppointment(String patientID, String appointmentID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addAppointment(String appointmentID, String appointmentType, int capacity, String adminID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeAppointment(String appointmentID, String appointmentType, String adminID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void listAppointmentAvailability(String appointmentType) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void swapAppointment(String patientID, String oldAppointmentID, String oldAppointmentType,
			String newAppointmentID, String newAppointmentType) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	

}
