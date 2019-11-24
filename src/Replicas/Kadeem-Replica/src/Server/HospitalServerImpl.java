package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.List;
import org.omg.CORBA.ORB;


import HospitalApp.HospitalServerInterPOA;
import UDP.HospitalUDP;
import UDP.HospitalUDPInterface;
import UDP.UDPClient;
import UDP.UDPServer;



/*
 * 
 * This class implements the HospitalServerInterface class and creates a server depending on three different cities (Montreal, Sherbrooke, Quebec)
 *
 */

public class HospitalServerImpl extends HospitalServerInterPOA  {

		//CORBA Variables
		private ORB orb = null;
		private static int UDPPort;
		private String UDPHost;
		private UDPServer UDPserver;
		private String ServerName;
		private Logger logger = null;
		
		//Holds other servers' addresses : ["ServerName", "hostName:portNumber"]
		HashMap<String, String> serversList = new HashMap();
	
		
		
		//Hashmap that uses the appointment types as keys, and uses a subhash as a value
		private HashMap<String, HashMap<String, Integer>> Database = new HashMap<String,  HashMap<String, Integer>>();
		
		//A hashmap that will be used used as a subhashmap. will store the appointmentIDs as keys and the availability slots as values
		private HashMap <String,Integer> subHash= new HashMap <String,Integer>();  
		
		// create our map
		Map<String, List<String>> patientMap = new HashMap<>();    

		// populate it
		List<String> appointments= new ArrayList<>();
	
	
		
		
	
		
		//logger method in order to register each time an operation is performed on a server to a .txt file 
		private Logger initiateLogger() 
		{
			Logger logger = Logger.getLogger("Server Logs/" + this.ServerName + " - Server Log.txt");
			FileHandler fh;
			
			try
			{
				//FileHandler Configuration and Format Configuration
				fh = new FileHandler("Server Logs/" + this.ServerName + " - Server Log.txt");
				
				//Disable console handling
				logger.setUseParentHandlers(false);
				logger.addHandler(fh);
				
				//Formatting configuration
				SimpleFormatter formatter = new SimpleFormatter();
				fh.setFormatter(formatter);
			}
	
			catch (IOException e)
			{
				System.err.println("Server Log: Error: IO Exception " + e);
				e.printStackTrace();
			}
			
	
			
			return logger;
		}
		
		
		
		
		public void shutdown() {
			this.orb.shutdown(false);
			this.UDPserver.stop();
			
		}
		
	
		
		//constructor to initlize the server
		public HospitalServerImpl(String ServerName, ORB orb, String udpHost, int udpPort, HashMap<String, String> serversList)
		{
			super();
			
			
			this.ServerName = ServerName;
			this.orb = orb;
			this.UDPHost = udpHost;
			this.UDPPort = udpPort;
			
			this.UDPserver= new UDPServer(UDPHost, UDPPort, this);
			this.serversList = serversList;
			
			// Logging Initiation
			
			this.logger = this.initiateLogger();
			this.logger.info("Initializing Server" +this.ServerName);
			
			
			System.out.println("Server: " + ServerName + " initialization success.");
			
			this.logger.info("Server: " + ServerName + " initialization success.");
			
			System.out.println("Server: " + ServerName + " port is : " + UDPPort);
			
			this.logger.info("Server: " + ServerName + " port is : " + UDPPort);
		}

		
		//Method that a patient client can invoke depending on their server
				public synchronized void bookAppointment(String patientID, String appointmentID, String appointmentType)
						 {
					
					this.logger.info("Server log:" +" Request type: Book an appoitment." + "Requested by:"+ patientID +" "+ " Requesting "
							+ "that appointmentID:"+appointmentID+ " " +"appointment type:"+" "+ appointmentType+ " "+" be added");
					
					
					
			String subtr=patientID.substring(0,4);
					
					//Checks if the unique patient ID  is related to the correct server name
					if (patientID.startsWith((this.ServerName))) {
						
						//check if appointment type requested exists
						if(Database.containsKey(appointmentType) ) {
							
							
							//check if appointmentID is in the database
							
						if(subHash.containsKey(appointmentID) ){
						
								
								//check if appointment slot is full
								if(subHash.get(appointmentID)==0) {
									
									
									System.out.println("Appointment is already booked. Please choose another date ");
									
									this.logger.info("Server log: Operation failed" +" "+ patientID +" "+"Could not book appointment"+ appointmentID);
								
							}
								
								//check if appointment is empty
								else if (subHash.get(appointmentID)>=1) {
									
									//check if this specific patient already booked this appointment.
									if(patientMap.containsKey(patientID) && appointments.stream().anyMatch(str -> str.trim().equals(appointmentID))){
										
										System.out.println("You already booked this. Please choose another date ");
										
										this.logger.info("Server log: Operation failed" +" "+ patientID +" "+"Could not book appointment"+ appointmentID);
										
									}
									
									
									//book appointment 
									else {
									
									System.out.println("Appointment has been booked. ");
									
									appointments.add(appointmentID);
									patientMap.put(patientID,appointments);
									
									this.logger.info("Server log: Operation completed" +" "+ patientID +" "+"has booked appointment"+ appointmentID);
									
									int x= subHash.get(appointmentID);
									
									x--;
									
									subHash.replace(appointmentID,x);
									
									}
									
								}
								
						
					
					
					else {
						
						
						System.out.println("Operation failed. AppointmentID doesn't exist. Please choose another");
						
						this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
						+appointmentID +" "+ "was not added to database");
				
						
					}
								
						}
						
						else {
							
							System.out.println("Operation failed. AppointmentID doesn't exist. Please choose another");
							
							this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
							+appointmentID +" "+ "was not added to database");
						}
						
						
						}
						
						else {
							
							System.out.println("Operation failed. Appointment type doesn't exist. Please choose another");
							
							this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
							+appointmentID +" "+ "was not added to database");
						
						}
						
				}
				
						else {
						
						System.out.println("Operation failed. You are in the wrong hospital server");
						this.logger.severe("Server Log: | Hospital Mismatch | Patient ID: " + patientID);
							
					}
						
					
				}
						
						

					
					
				

				//method invoked by a Patient client depending on their server. returns all appointments in the entire database
				public synchronized void getAppointmentSchedule(String patientID)  {
					
					
					this.logger.info("Server log:" +" Request type: Get appointment schedule." + "Requested by:"+ patientID +" "
					+ " Requesting appointments in all hospitals");
					
					String subtr=patientID.substring(0,4);
					
					
					
					if (patientID.startsWith((this.ServerName))) {
						

						System.out.println("Operation successful.");
						
						
					System.out.println("Here are all the appointments for this server:");
						
						Database.forEach((key, value) -> System.out.println(key + ":" + value));
						
						System.out.println("Here are all of patient's"+ " "+patientID +" "+"appointments ");
						
				
							
							System.out.println(patientMap.get(patientID));
							
						
						
						this.logger.info("Server Log:"+ " "+ "Operation successful.");
						
					}
					
					else {
						
						this.logger.severe("Server Log: | Hospital Mismatch | Patient ID: " + patientID);
							
						
					}
					
				}
					
				
				//method invoked by a Patient client depending on their server. removes an appointment from the subhash map depending on server
				public synchronized void cancelAppointment(String patientID, String appointmentID)  {
					
					this.logger.info("Server log:" +" Request type: Cancel an appoitment." + "Requested by:"+ patientID +" "+ " Requesting "
							+ "that appointmentID:"+appointmentID+ " "+" to be cancelled");
					
					
					if (patientID.startsWith((this.ServerName))) {
						
						if(subHash.containsKey(appointmentID)) {
							
							if(patientMap.containsKey(patientID) && !appointments.stream().anyMatch(str -> str.trim().equals(appointmentID))) {
								
								System.out.println("Operation failed.You never booked this appointment. Try again");
								
								this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
										+appointmentID +" "+ "was not cancelled from the database");
								
							}
							
							else if (subHash.get(appointmentID)>=1 && subHash.get(appointmentID)<=5 ) {
								
								 for (int counter = 0; counter < appointments.size(); counter++) { 		      
							         
									 if(patientMap.containsKey(patientID)) {
										 
											subHash.replace(appointmentID,1);
											
											appointments.remove(appointmentID);
											
											System.out.println("Operation successful . Appointment cancelled");
											
											this.logger.info("Server Log:"+ " "+ "Operation successful."+" "+"Appointment:"+" "
													+appointmentID +" "+ "was cancelled from the database");
										 break;
									 }
									 
								
							      }   		
							}
							
							else {
								
								 
								 System.out.println("Operation failed . Operation failed. Appointment was never booked at all. Try again");
									
									this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
											+appointmentID +" "+ "was not cancelled from the database");
							}
							
							
					
							
						}
						
				
						else {
							
							System.out.println("Appointment doesn't exist ");
							
							this.logger.info("Server log: Operation failed" + "AppointmentID:"+ appointmentID+ " "+"was not cancelled" );
							
							
						}
						
						
						
					
				}
					
					else {
						
						System.out.println("Operation failed. You are in the wrong hospital server");
						this.logger.severe("Server Log: | Hospital Mismatch | Patient ID: " + patientID);
					
					}
				
					
				}
				
				

				public void swapAppointment(String patientID, String oldappointmentID, String oldappointmentType,
						String newappointmentID, String newappointmentType)  {
				
					String originalHospital= oldappointmentID.substring(0, 2);
					String destinationHospital = newappointmentID.substring(0, 2);
					
					boolean isSourceLocal = originalHospital.equals(this.ServerName);
					boolean isDestLocal = destinationHospital.equals(this.ServerName);
				
					
					this.logger.info("Server log:" +" Request type: Swap an appointment." + "Requested by:"+ patientID +" "+ " Requesting "
							+ "that appointmentID:" +oldappointmentID+ " " +"appointment type:"+" "+ oldappointmentType+ " "+ " " + "be swapped with" 
							+ newappointmentID + " "+ "appointment type" + newappointmentType);
					
					String subtr=patientID.substring(0,4);
					
					//Checks if the unique patient ID  is related to the correct server name
					if (patientID.startsWith((this.ServerName))) {	
						
						try {
							
							// If this is a local transfer (same hospital)
							if(isSourceLocal == isDestLocal)
							{
								
								//check if appointment type requested exists
								if(Database.containsKey(oldappointmentType) ) {
									
									
									//check if appointmentID is in the database
									
								if(subHash.containsKey(oldappointmentID) ){
								
										
										//check if appointment slot is full
										if(subHash.get(oldappointmentID)==0) {
											
											if(patientMap.containsKey(patientID) && appointments.stream().anyMatch(str -> str.trim().equals(oldappointmentID))){
												
												//check if appointment type requested exists
												if(Database.containsKey(newappointmentType) ) {
													
													
													//check if appointmentID is in the database
													
												if(subHash.containsKey(newappointmentID) ){
												
														
														//check if appointment slot is full
														if(subHash.get(newappointmentID)==1) {
															
											
															System.out.println("New Appointment has been booked. ");
															
															appointments.add(newappointmentID);
															patientMap.put(patientID,appointments);
															
															this.logger.info("Server log: Operation completed" +" "+ patientID +" "+"has booked appointment"+ newappointmentID);
															
															
															subHash.replace(oldappointmentID,1);
												
											}
														
														else {
															
															System.out.println("Operation failed. New appointment is already booked. Please choose another");
															
															this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
															+newappointmentID +" "+ "was not added to database");
														}
										
									}
												
												else {
													
													System.out.println("Operation failed. AppointmentID doesn't exist. Please choose another");
													
													this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
													+newappointmentID +" "+ "was not added to database");
												}
										
							
						}
												
												
												else {
													
													System.out.println("Operation failed. Appointment type doesn't exist. Please choose another");
													
													this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
													+newappointmentID +" "+ "was not added to database");
												
												}
												
											}
											
											else {
												
												System.out.println("You never booked this appointment. Please choose another appointment. ");
										
												
												this.logger.info("Server log: Operation failed" +" "+ patientID +" "+"has not booked appointment"+ newappointmentID);
												
												
										
												}
											
										}
										
								}
								
								else {
									
									System.out.println("Operation failed. AppointmentID doesn't exist. Please choose another");
									
									this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
									+oldappointmentID +" "+ "was not added to database");
								}
								
								}
								
								else {
									
									System.out.println("Operation failed. Appointment type doesn't exist. Please choose another");
									
									this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
									+oldappointmentID +" "+ "was not added to database");
								
								}
								
							}
							
							else if(isSourceLocal == true && isDestLocal == false) {
								
								//check if appointment type requested exists
								if(Database.containsKey(oldappointmentType) ) {
									
									
									//check if appointmentID is in the database
									
								if(subHash.containsKey(oldappointmentID) ){
								
										
										//check if appointment slot is full
										if(subHash.get(oldappointmentID)==0) {
											
											if(patientMap.containsKey(patientID) && appointments.stream().anyMatch(str -> str.trim().equals(oldappointmentID))){
												
												for(String remoteServer : serversList.keySet()){
													
													
													this.logger.info("Server Log: | Swapping appointments | Connection Initialized.");
													
													// Extract the key that is associated with the destination 
													String connectionData = serversList.get(destinationHospital);
													
													// Extract the host and IP 
													String hostDest = connectionData.split(":")[0];
													int portDest = Integer.parseInt(connectionData.split(":")[1]);
													
													// Create an UDPClient and prepare the request.
													UDPClient requestClient = new UDPClient(hostDest, portDest, originalHospital);
													
													HospitalUDPInterface transferReq = new HospitalUDP(newappointmentType, newappointmentID, patientID);
													
													requestClient.send(transferReq);
													
													

													// Receive the response.
													HospitalUDPInterface transferResp = requestClient.getResponse();
													
													if(((HospitalUDP)transferResp).SwappedStatus() == true)
														
													{
														System.out.println("New Appointment has been booked. ");
														
														appointments.add(newappointmentID);
														patientMap.put(patientID,appointments);
														
														this.logger.info("Server log: Operation completed" +" "+ patientID +" "+"has booked appointment"+ newappointmentID);
														
														
														subHash.replace(oldappointmentID,2);
														
													
													}
												}	//end for-loop
											}
													
												}
										
										else {
											
											System.out.println("You never booked this appointment. Please choose another appointment. ");
									
											
											this.logger.info("Server log: Operation failed" +" "+ patientID +" "+"has not booked appointment"+ oldappointmentID);
											
											
									
											}
													
												}	
											}
								
								else {
									
									System.out.println("Operation failed. Appointment type doesn't exist. Please choose another");
									
									this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
									+oldappointmentID +" "+ "was not added to database");
								
								}	
										}
							
						}
								
							finally {
								
							
								
							}
						
					}
					
					else {
						
						System.out.println("Operation failed. You are in the wrong hospital server");
						this.logger.severe("Server Log: | Hospital Mismatch | Patient ID: " + patientID);
					
					}
					
							
			}
				
					
							
				


				//method invoked by admin client depending on their server. adds an appointment to database
				public void addAppointment(String appointmentID, String appointmentType, int capacity, String adminID)  {
					
					this.logger.info("Server log:" +" Request type: Book an appoitment." + "Requested by:"+ adminID +" "+ " Requesting "
							+ "that appointmentID:"+appointmentID+ " " +"appointment type:"+" "+ appointmentType+ " "+" be added");
					
					String sub=adminID.substring(0,3);
			
					
				if(adminID.contains("P")) {
					
					System.out.println("Operation failed. Only admins can perform this operation");
					this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
							+appointmentID +" "+ "was not added to database");
				}
					
				else if (sub.contains(this.ServerName)) {
						
						if(Database.containsKey(appointmentType) &&subHash.containsKey(appointmentID)){
							
							
							
							System.out.println("Appointment already exists. Please try again");
							
							this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
									+appointmentID +" "+ "was not added to database");
							
						}				
					
					
					else {
						
					if(capacity<=5 && capacity>=1) {
						
						if(appointmentID.startsWith(this.ServerName)) {
						
						subHash.put(appointmentID,capacity);
						Database.put(appointmentType,subHash);
						
						System.out.println("Operation successful. Appointment has been added");
						
						this.logger.info("Server Log:"+ " "+ "Operation successful."+" "+"Appointment:"+" "
								+appointmentID +" "+ "was added to database");
						
						}
						
						else {
							
							System.out.println("Operation failed. Can only add appointments related to your server");
							
							this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
									+appointmentID +" "+ "was not added to database");
						}
						
					}
					
					else {
						
						System.out.println("Can only add appointment 1 to 5 slots. Please try again");
						
						this.logger.info("Server Log:"+ " "+ "Operation unsucessful."+" "+"Appointment:"+" "
								+appointmentID +" "+ "was not added to database");
					}
								
				
							
							
					}
							
					}

					
						
						else {
							System.out.println("Operation failed. You are in the wrong hospital server");
						this.logger.severe("Server Log: | Hospital Mismatch | Patient ID: " + adminID);
						
					}
					
				}
					
				

				//method invoked by admin client depending on their server. removes an appointment from database
				public synchronized void removeAppointment(String appointmentID, String appointmentType, String adminID)  {
					
					System.out.println(this.ServerName);
					
					if(adminID.contains("P")) {
						
						System.out.println("Operation failed. Only admins can perform this operation");
						this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
								+appointmentID +" "+ "was not removed from database");
					}
					
					else if (adminID.startsWith((this.ServerName))) {
					
						if(Database.containsKey(appointmentType)) {
							
			if(subHash.containsKey(appointmentID)) {
				
				
								if(subHash.get(appointmentID)==0) {
									
									subHash.remove(appointmentID);
									
									subHash.replace(appointmentID,1);
									
									System.out.println("Appointment has been removed ");
									
									
									
									this.logger.info("Server log: Operation successful " +" "+ adminID +" "+"removed "+ appointmentID);
								
							}
								
								else if (subHash.get(appointmentID)==1) {
									
									
									
									System.out.println("Appointment was never booked ");
									
									this.logger.info("Server log: Operation failed" +" "+ adminID +" "+"was not removed"+ appointmentID);
									
									
								}
										
							
							}
			
			else {
				
				System.out.println("Operation failed. Appointment type doesn't exist. Please choose another");
				
				this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
				+appointmentID +" "+ "was not removed from database");
				
			}
			
									
							}
							
							
							
							
						}
					
					
						
						else {

							System.out.println("Operation failed. You are in the wrong hospital server");
							this.logger.severe("Server Log: | Hospital Mismatch | Patient ID: " + adminID);
							
						}
					
					
				}
					
			

				//method invoked by admin client depending on their server. list all appointments from each server
				
			/*
				public synchronized void listAppointmentAvailability (String appointmentType)  {
				
					DatagramSocket socket = null;
					String[] HospitalServers = {"MTL","QUE","SHE"};
					
					int test = 100;
					
					try
					{
						this.logger.info("Creating UDP socket to receive data from other servers.");
						try {
							socket = new DatagramSocket(HospitalServerImpl.UDPPort);
						} catch (SocketException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						for(String hospitalServers: HospitalServers)
					    {
					    	if(hospitalServers.equals(this.ServerName))
					    	{
					    		continue;
					    	}
					    	
					    	
					    	HospitalServerImpl otherServer = new HospitalServerImpl(hospitalServers, "localhost", test, serversList);
					    	
					    	
					    	String rData = null;
					    	
					    	
					    	
					    		otherServer.getUDPData(test,appointmentType);
					    		byte[] buffer = new byte[1024];
					    		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
					    		
					    		try
					    		{
					    			try {
										socket.receive(request);
									} catch (IOException e) {
									
										e.printStackTrace();
									}
					    			
					    			
					    			
					    			rData = new String(request.getData());
					    			System.out.println(rData);
					    			
					    		}
					    		
					    		
					    	finally {
					    		
					    	}
					    		
					    		test++;
					    		
					    	}
					    	
					    }
						
					
					    		
					    		finally {
					    			
					    		}
								
					    		
				}	
			
				*/
				
				public synchronized boolean checkAppointment (String appointmentType,String appointmentID,String patientID) {
					
					boolean test = false;
					
					//check if appointment type requested exists
					if(Database.containsKey(appointmentType) ) {
						
						
						//check if appointmentID is in the database
						
					if(subHash.containsKey(appointmentID) ){
					
							
							//check if appointment slot is full
							if(subHash.get(appointmentID)==0) {
								
								
								System.out.println("Appointment is already booked. Please choose another date ");
								
								this.logger.info("Server log: Operation failed" +" "+ patientID +" "+"Could not book appointment"+ appointmentID);
							
						}
							
							//check if appointment is empty
							else if (subHash.get(appointmentID)==1) {
								
								test=true;
						
									
								}
								
								
								
							}
							
				
				else {
					
					
					System.out.println("Operation failed. AppointmentID doesn't exist. Please choose another");
					
					this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
					+appointmentID +" "+ "was not added to database");
			
					
				}
							
					}
					
					else {
						
						System.out.println("Operation failed. Appointmentype doesn't exist. Please choose another");
						
						this.logger.info("Server Log:"+ " "+ "Operation failed."+" "+"Appointment:"+" "
						+appointmentID +" "+ "was not added to database");
					}
					
					
					return test;
					
					
					}



			
				
			/*	
				
				public synchronized void getUDPData(int portNum, String appointmentID) {
					
					DatagramSocket dataSocket;
					
					try
					{
						System.out.println("Initialiating a datagram with port " + portNum);
						dataSocket = new DatagramSocket();
						
					
						byte[] message = ByteBuffer.allocate(4).putInt(getAppointmentSlot(appointmentID)).array();
						
						InetAddress hostAddress = InetAddress.getByName("localhost");

						DatagramPacket request = new DatagramPacket(message, message.length, hostAddress, portNum);
						dataSocket.send(request);
						
						dataSocket.close();
					}
					catch (Exception e)
					{
						System.out.println("Server Log: | getUDPData Error: " + e.getMessage());
				
					}	
				}

		*/		
				public static void main(String args[]){
					
					
					DatagramSocket dataSocket = null;
					
				
					try
					{
						
				dataSocket = new DatagramSocket(UDPPort);
						
				System.out.println(" DataSocket Created.");
						
				
			byte[] buffer = new byte[1024];
						
			System.out.println(" Buffer Array Created.");
						
					
						while(true)
						{
					DatagramPacket request = new DatagramPacket(buffer, buffer.length);
					dataSocket.receive(request);
					String reply = "reply";
					buffer = reply.getBytes();
					DatagramPacket replyPackey = new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());
					dataSocket.send(replyPackey);
					System.out.println("UDP Server on port " + UDPPort + " is now listening.");
						}
					}
					catch (Exception e)
					{
						System.err.println(" Unable to start port listening");
						System.err.println(e.getMessage());
					}
					finally
					{
						dataSocket.close();	
						System.out.println(" DataSocket Closed.");
					}
				}




				@Override
				public void listAppointmentAvailability(String appointmentType) {
					// TODO Auto-generated method stub
					
				}	
					

				
		}
		
		
		
		

		
		
		
		

