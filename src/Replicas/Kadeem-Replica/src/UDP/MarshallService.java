package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/*
 * 
 * 	This class will Take a data object and convert it to a byte array for sending over the udp service
 */

public class MarshallService implements Serializable
{
	private static final long serialVersionUID = 1L;

	//We will translate the data object to byte array for UDP requests 
	public static final byte[] marshall(HospitalUDPInterface data)
	{	
		try
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutput output = new ObjectOutputStream(outputStream);
			
			output.writeObject(data);
			output.close();
			
			return outputStream.toByteArray();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	//We will unpack the data object into readable form
	public static final HospitalUDPInterface unmarshall(byte[] data)
	{
		try
		{
			ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
			ObjectInputStream input = new ObjectInputStream(inputStream);
			
			HospitalUDPInterface dataUnmarshall = (HospitalUDPInterface)input.readObject();
			input.close();
			
			return dataUnmarshall;
		}
		catch (IOException | ClassNotFoundException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}