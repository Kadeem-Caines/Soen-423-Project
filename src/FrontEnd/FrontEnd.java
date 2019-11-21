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

/*
 * This class will implement a CORBA front end (FE) which receives a client request
and forwards it to a failure-free sequencer . the three replicas execute client requests in total order and return the
results back to the FE which in turn returns a single correct result back to the client as
soon as two identical (correct) results are received from the replicas.
 */

public class FrontEnd {
	
	
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

}
