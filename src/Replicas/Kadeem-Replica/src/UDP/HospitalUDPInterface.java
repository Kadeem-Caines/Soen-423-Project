package UDP;

import java.io.Serializable;

import HospitalApp.HospitalServerInterPOA;


public interface HospitalUDPInterface extends Serializable
{

	public void execute(HospitalServerInterPOA server);
}