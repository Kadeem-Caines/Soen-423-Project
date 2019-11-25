package Sequencer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;

public class Sequencer {
	
	public static int UDPPortSequencer = 2020;
	
	public static void main(String args[]){
		
		
	
		
		DatagramSocket dataSocket = null;
		
	
		try
		{
			
	dataSocket = new DatagramSocket(UDPPortSequencer);
			
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
		System.out.println("UDP Server on port " + UDPPortSequencer + " is now listening.");
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


}
