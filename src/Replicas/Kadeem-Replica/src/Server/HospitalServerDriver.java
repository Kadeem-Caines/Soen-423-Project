package Server;
import java.util.HashMap;
import java.util.Properties;



import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;

import HospitalApp.HospitalServerInter;
import HospitalApp.HospitalServerInterHelper;

public class HospitalServerDriver {

	/*
	 * 
	 * This class will initialize all the servers (MTL, QUE, SHE) bind their ports and localhosts ,start the ORB, create a servant and the nameservice
	 * 
	 */
	
	
	public static void main(String[] args)  {
		
		
		//Hashmap used to store the servername and their host:port number
		 HashMap<String, String> serverDetails = new HashMap();
		 
		 //Hashmap used to store each server servant object in order to access them via UDP
		 HashMap<String, HospitalServerImpl> serverDirectory = new HashMap();
		 
		 //string array used to initialize each server by name in a for loop
		String[] Servers = {"MTL", "QUE", "SHE"};
		
		try {
		
			Properties props = new Properties();
			Properties props1 = new Properties();
			Properties props2 = new Properties();
		    props.put("org.omg.CORBA.ORBInitialPort", "900");
		    props1.put("org.omg.CORBA.ORBInitialPort", "901");
		    props2.put("org.omg.CORBA.ORBInitialPort", "902");
		    props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		    
			// create and initialize the ORB
			ORB orb = ORB.init(args, props);
			ORB orb1 = ORB.init(args, props1);
			ORB orb2 = ORB.init(args, props2);
			
			// get the reference to rootpoa & activate POAManager
			POA rootpoa = (POA)orb.resolve_initial_references("RootPOA");
			rootpoa.the_POAManager().activate();
			
			// Create servers details. used for UDP
			serverDetails.put("MTL", "localhost:101");
			serverDetails.put("QUE", "localhost:102");
			serverDetails.put("SHE", "localhost:103");
	
			
			// create a servant for each city server
			HospitalServerImpl hospitalServerImpl = new HospitalServerImpl("MTL", orb,"localhost", 101, serverDetails);
			HospitalServerImpl hospitalServerImpl1 = new HospitalServerImpl("QUE", orb1,"localhost", 102, serverDetails);
			HospitalServerImpl hospitalServerImpl2 = new HospitalServerImpl("SHE", orb2,"localhost", 103, serverDetails);
			
			serverDirectory.put("MTL", hospitalServerImpl);
			serverDirectory.put("QUE", hospitalServerImpl1);
			serverDirectory.put("SHE", hospitalServerImpl2);
			
			//start each server
			for(String serverName : Servers) {
			
			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(hospitalServerImpl);
			
			// cast the reference to a CORBA reference
			HospitalServerInter href = HospitalServerInterHelper.narrow(ref);
			
			// get the root naming context
			// NameService invokes the transient name services
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			
			// use the NamingContextExt which is part of the
			// Interoperable Naming Service (INS) spec.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name(serverName);
			ncRef.rebind(path, href);
			
			System.out.println(serverName+" Hospital Server started");
			
			}
			
			// wait for invocations from clients
			orb.run();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			  System.out.println("Server error. Server Exiting ...");
		}
		
		
	}

}
