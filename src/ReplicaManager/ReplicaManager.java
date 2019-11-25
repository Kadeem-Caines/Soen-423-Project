package ReplicaManager;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.HashMap;

import org.omg.PortableServer.POA;

import com.sun.corba.se.internal.iiop.ORB;

public class ReplicaManager {
	
	//replica is allowed to fail up to 3 times
	private static final int FAILURE_COUNT = 3;
	
	
	private boolean connectionStatus;
	
	
	//Key will be hostname, and value will be IP address
	private HashMap<String,String> replicaInfoList;
	
	//Key will be portname, and value will be port number
	private HashMap<String,Integer> UDPPortList;


	
	
		public ReplicaManager() {
			
			replicaInfoList=new HashMap<String,String>();
			UDPPortList=new HashMap<String,Integer>();
			
			replicaInfoList.put("replica1", "7777-132.205.95.190");
			replicaInfoList.put("replica2", "7778-132.205.95.189");
			replicaInfoList.put("replica3", "7779-132.205.95.191");
			replicaInfoList.put("replica3", "7779-132.205.95.191");
			replicaInfoList.put("replica4", "7779-132.205.95.192");
			
			UDPPortList.put("frontEndPort",1001);
			UDPPortList.put("multicastPort",1002);
			UDPPortList.put("sequencerPort",1003);
			UDPPortList.put("replicaManagerPort",1004);
	
			
		}
		
		public void listenFE() {
			
			
		}
		
		
		public boolean replicaStatus() {
			
			return connectionStatus;
			
			
		}
		
		public int getUDPPort(String port) {
			
			return UDPPortList.get(port);
		}
		
		
	public String geReplicaIP(String IP) {
			
			return replicaInfoList.get(IP);
		}
		
		

}
