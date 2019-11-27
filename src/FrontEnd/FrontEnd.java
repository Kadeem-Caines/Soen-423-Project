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
	private static int UDPPortReplicaListener = 1001;
	private static int UDPPortSequencer=1002;


	
	public FrontEnd() {
		
	}
	
	


	public String replicaListener(DatagramSocket Socket) throws SocketException {
		
		
		DatagramSocket aSocket =null;
		String response=null;
		
		//socket times out if it doesn't receive anything
		Socket.setSoTimeout(1000);
		
		try {
			
			aSocket = new DatagramSocket(this.UDPPortReplicaListener);
			byte[] buffer = new byte[1000];
			System.out.println("Front end is now listening for Replica");
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);		
				
				response =new String(request.getData(), "UTF-8"); 		
			}
		}
			
		
		catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();		
			
		}	
		return response;
		
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

			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			
			// Send out the packet
			socket.send(reqPacket);
	
			//have the front end listen for replica responses
			response=replicaListener(socket);
			
			
			
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
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
	
			//have the front end listen for replica responses
			response=replicaListener(socket);
			
			
			
			
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
		
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
	
			//have the front end listen for replica responses
			response=replicaListener(socket);
			
			
			
			
			
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
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
	
			// Send out the packet
			socket.send(reqPacket);
	
			//have the front end listen for replica responses
			response=replicaListener(socket);
			
			
			
			
			
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
			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
	
			//have the front end listen for replica responses
			response=replicaListener(socket);
			
			
			
			
			
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

			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
	
			//have the front end listen for replica responses
			response=replicaListener(socket);
			
			
			
			
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

			
			// Prepare a socket and a packet for the request.
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, sequencerAddress.getAddress(), this.UDPPortSequencer);
			
			// Send out the packet
			socket.send(reqPacket);
	
			//have the front end listen for replica responses
			response=replicaListener(socket);
			
			
			
			
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
	
	

	
}
	
	


