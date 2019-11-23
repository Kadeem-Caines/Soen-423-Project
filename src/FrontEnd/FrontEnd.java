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
	private InetSocketAddress sequencerAddress;
	public static int UDPPortSequencer = 1000;
	public static int UDPPortReplicaListener = 1001;



	
	public FrontEnd() {
		
	}
	
	
	public void requestReplySequencer() {
		
	}
	
	public void sendRequestSequencer() {
		

		
	}



	@Override
	public void bookAppointment(String patientID, String appointmentID, String appointmentType) throws IOException  {
		
		DatagramSocket socket = null ;
		String  sequencerData=patientID+appointmentID+appointmentType;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}


	@Override
	public void getAppointmentSchedule(String patientID) throws IOException {
		
		DatagramSocket socket = null ;
		String sequencerData=patientID;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}


	@Override
	public void cancelAppointment(String patientID, String appointmentID) throws IOException {
		DatagramSocket socket = null ;
		
		String sequencerData=patientID+appointmentID;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public void addAppointment(String appointmentID, String appointmentType, int capacity, String adminID) throws IOException {
		
		DatagramSocket socket = null ;
		
		String sequencerData= appointmentID+appointmentType+capacity+adminID;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}


	@Override
	public void removeAppointment(String appointmentID, String appointmentType, String adminID) throws IOException {

		DatagramSocket socket = null ;
		String sequencerData=appointmentID+appointmentType+adminID;
		
		
			try {
				socket = new DatagramSocket() ;
				
				// Prepare containers for the outgoing request and incoming reply.
				byte[] requestByte = sequencerData.getBytes();
				
				// Prepare a socket and a packet for the request.
				DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
				
				// Send out the packet
				socket.send(reqPacket);
				
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
	}


	@Override
	public void listAppointmentAvailability(String appointmentType) throws IOException {
		
		DatagramSocket socket = null ;
		
		try {
			try {
				socket = new DatagramSocket() ;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		finally {
			
			
		}
		
	}


	@Override
	public void swapAppointment(String patientID, String oldAppointmentID, String oldAppointmentType,
			String newAppointmentID, String newAppointmentType) {
		
		String sequencerData = oldAppointmentID+oldAppointmentType+newAppointmentID+newAppointmentType;
				
		DatagramSocket socket = null ;
		
		try {
			try {
				socket = new DatagramSocket() ;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		finally {
			
			
		}
		
	}


	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args) throws InvalidName {
		
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
	

}
	
	


