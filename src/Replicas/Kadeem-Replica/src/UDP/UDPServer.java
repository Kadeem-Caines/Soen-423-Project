package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Server.HospitalServerImpl;

/*
 * 
 * This class acts as a UDP server, by creating a byte array buffer that will receive incoming requests from the UPD client class
 */
 


public class UDPServer implements Runnable
{
	private String UDPHost;
	private int UDPPort;
	private HospitalServerImpl server;
	boolean connection;

	public UDPServer(String UDPHost, int UDPPort, HospitalServerImpl server)
	{
		this.UDPHost = UDPHost;
		this.UDPPort = UDPPort;
		this.server = server;
	}
	
	
	
	public void start() throws IOException
	{
		
		
		
		DatagramSocket Socket = new DatagramSocket(UDPPort);
		
		byte[] data = new byte[2048];
		
		while (connection == true)
		{
			// Create a datagram for incoming packets
			DatagramPacket requestPacket = new DatagramPacket (data, data.length);

			
			// The server will take the incoming request
			Socket.receive(requestPacket);

			// From the packet, we take the necessary information for reply
			InetAddress ip = requestPacket.getAddress();
			int requestPort = requestPacket.getPort();
			
			// Translate the byte data from the request to invoke the method
			HospitalUDPInterface requestData = MarshallService.unmarshall(requestPacket.getData());
			requestData.execute(this.server);
			
			// Reply
			byte[] reply = MarshallService.marshall(requestData);
			DatagramPacket replyPacket = new DatagramPacket(reply, reply.length, ip, requestPort);
			Socket.send(replyPacket);
		}
	}
	
	public void stop()
	{
		connection = false;
	}

	@Override
	public void run()
	{
		try
		{
			start();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}	
	}	
}