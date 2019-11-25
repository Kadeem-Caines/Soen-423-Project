  package UDP;

import HospitalApp.HospitalServerInter;
import HospitalApp.HospitalServerInterHelper;
import HospitalApp.HospitalServerInterPOA;
import java.util.Properties;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;


/*
 * 
 * This class is used to invoke the checkappointment method, which will verify if a server has the bookable appointment during UDP
 */

public class HospitalUDP implements HospitalUDPInterface
{
	private static final long serialVersionUID = 1L;
	
	private String appointmentType;
	private String appointmentID;
	private String patientID;
	private boolean swapStatus = false;

	
	//Constructor for swapping appointments between hospitals
	public HospitalUDP(String appointmentType, String appointmentID, String patientID)
	{
		this.appointmentType = appointmentType;
		this.appointmentID 	= appointmentID;
		this.patientID = patientID;
	}
	
	
	public boolean SwappedStatus()
	{
		return swapStatus;
	}

		
	@Override
	public void execute(HospitalServerInterPOA server)
	{
		
		String serverName = patientID.substring(0,3);
		
		Properties sysProperties = System.getProperties();
		
		sysProperties.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
		sysProperties.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
		
		sysProperties.put("org.omg.CORBA.ORBInitialHost", "localhost");
		sysProperties.put("org.omg.CORBA.ORBInitialPort", "900");
		
		ORB orb = ORB.init(new String[1], sysProperties);
		
		try
		{
			org.omg.CORBA.Object objNS = orb.resolve_initial_references("NameService");
			NamingContextExt namingContext = NamingContextExtHelper.narrow(objNS);
			org.omg.CORBA.Object objServer = namingContext.resolve_str(serverName);
			HospitalServerInter hospitalServer = HospitalServerInterHelper.narrow(objServer);
			
			if(	hospitalServer.checkAppointment(this.appointmentType, this.appointmentID, this.patientID)) {
				
				swapStatus=true;
			}
		
		
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}		
	}


	
}