  
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
 * This class acts as a UDP client, by creating a datagram packet and preparing the data from the hospital servers to be sent as a request to the UDPServer
 */

public class UDPClient
{
	private String UDPHost;
	private int UDPPort;	
	private HospitalUDPInterface request;
	private HospitalUDPInterface response;
	
	private String serverName;
	
	
	
	public UDPClient(String hostName, int portNum, String serverName)
	{
		this.UDPHost = hostName;
		this.UDPPort = portNum;
		this.serverName = serverName;
		
	
	}
	

	
	public void send(HospitalUDPInterface requestCall)
	{
		// Make sure the old request is cleared.
	
		
		try
		{

			
			// Prepare packet
			// Get the destination IP
			InetAddress ip = InetAddress.getByName(UDPHost);
			
			// Prepare containers for the outgoing request and incoming reply.
			byte[] requestByte = MarshallService.marshall(requestCall);
			byte[] replyByte = new byte[requestByte.length];
			
			// Prepare a socket and a packet for the request.
			DatagramSocket reqSocket = new DatagramSocket();
			DatagramPacket reqPacket = new DatagramPacket(requestByte, requestByte.length, ip, UDPPort);
			
			// Send out the packet
			reqSocket.send(reqPacket);
			
		
			
			//4. Wait for a response
			DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length);
			
			
			// Unmarshall the response
			response = MarshallService.unmarshall(replyPacket.getData());
			

			
			// Close connection.
			reqSocket.close();
			

			
		}
		catch (IOException e)
		{
	
			e.printStackTrace();
		}
		
	}

	
	public HospitalUDPInterface getResponse()
	{
		return response;
	}
	
	

}