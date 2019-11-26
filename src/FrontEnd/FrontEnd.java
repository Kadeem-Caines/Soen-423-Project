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

import corbaFrontEnd.frontEndOperationsPOA;


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
	public String bookAppointment(String patientID, String appointmentID, String appointmentType) throws IOException  {
		
		DatagramSocket socket = null ;
		String  sequencerData="bookAppointment"+":"+patientID+":"+appointmentID+":"+appointmentType;
		String response = null;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			byte[] replyByte = new byte[requestByte.length];
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length);
			
			// Send out the packet
			socket.send(reqPacket);
			
			response =new String(replyPacket.getData(), "UTF-8"); 
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}


	@Override
	public String getAppointmentSchedule(String patientID) throws IOException {
		
		DatagramSocket socket = null ;
		String sequencerData="getAppointment"+":"+patientID;
		String response = null;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			byte[] replyByte = new byte[requestByte.length];
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length);
			
			// Send out the packet
			socket.send(reqPacket);
			
			response =new String(replyPacket.getData(), "UTF-8"); 
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
		
	}


	@Override
	public String cancelAppointment(String patientID, String appointmentID) throws IOException {
		DatagramSocket socket = null ;
		
		String sequencerData="cancelAppointment"+":"+patientID+":"+appointmentID;
				
		String response = null;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			byte[] replyByte = new byte[requestByte.length];
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length);
			
			// Send out the packet
			socket.send(reqPacket);
			
			response =new String(replyPacket.getData(), "UTF-8"); 
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}


	@Override
	public String addAppointment(String appointmentID, String appointmentType, int capacity, String adminID) throws IOException {
		
		DatagramSocket socket = null ;
		
		String sequencerData= "addAppointment"+":"+appointmentID+":"+appointmentType+":"+capacity+":"+adminID;
		String response = null;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			byte[] replyByte = new byte[requestByte.length];
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length);
			
			// Send out the packet
			socket.send(reqPacket);
			
			response =new String(replyPacket.getData(), "UTF-8"); 
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
		

		
	}


	@Override
	public String removeAppointment(String appointmentID, String appointmentType, String adminID) throws IOException {

		DatagramSocket socket = null ;
		String sequencerData="removeAppointment"+":"+appointmentID+":"+appointmentType+":"+adminID;
		String response = null;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			byte[] replyByte = new byte[requestByte.length];
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length);
			
			// Send out the packet
			socket.send(reqPacket);
			
			response =new String(replyPacket.getData(), "UTF-8"); 
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	
		
	}


	@Override
	public String listAppointmentAvailability(String appointmentType) throws IOException {
		
		DatagramSocket socket = null ;
		String sequencerData="listAppointmentAvailability"+":"+appointmentType;
		String response = null;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			byte[] replyByte = new byte[requestByte.length];
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length);
			
			// Send out the packet
			socket.send(reqPacket);
			
			response =new String(replyPacket.getData(), "UTF-8"); 
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
		
	


	@Override
	public String swapAppointment(String patientID, String oldAppointmentID, String oldAppointmentType,
			String newAppointmentID, String newAppointmentType) throws IOException {
		
		String sequencerData = "swapAppointment"+":"+oldAppointmentID+":"+oldAppointmentType+":"+newAppointmentID+":"+newAppointmentType;
				
		DatagramSocket socket = null ;
		String response = null;
		
		try {
			socket = new DatagramSocket() ;
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = sequencerData.getBytes();
			byte[] replyByte = new byte[requestByte.length];
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length);
			
			// Send out the packet
			socket.send(reqPacket);
			
			response =new String(replyPacket.getData(), "UTF-8"); 
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
		
	}


	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args) throws InvalidName {
		
		FrontEnd test= new FrontEnd();
		
		

}
	
}
	
	


