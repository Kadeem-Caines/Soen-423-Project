import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
​
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
​
import Jordan.*;
​
public class RMJordan 
{
	static LogFileCreator log;
	static LogFileCreator serverLog;
	private static String id= "RMJordan";
	private static int responsePort = 1001;
	public static void main(String[] args) throws IOException, Exception
	{
		ORB orb=ORB.init(args,null);
		InetAddress a = InetAddress.getByName("localhost");
		// testing sending something to sequencer
		DatagramSocket asocket=new DatagramSocket();
		String data = "swapAppointment:MTLP1:MTLA121212:Dental:MTLA131212:Dental";
		byte[] buf = null;
		buf = data.getBytes();
		DatagramPacket messageOut = new DatagramPacket(buf,buf.length,a,9004);
	//	asocket.send(messageOut);
		//
		
		InetAddress group = InetAddress.getByName("228.5.6.7");
		MulticastSocket s = new MulticastSocket(9005);
		s.joinGroup(group);
		System.out.println("RM2 Started");
		byte[] buffer;
		
		
		while (true)
		{
			log = new LogFileCreator(id);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now(); 
			System.out.println("IN LOOP");
			buffer= new byte[1000];
			DatagramPacket messageIn = new DatagramPacket(buffer,buffer.length);
			s.receive(messageIn);//blocks until received
			System.out.println("AFTER");
			String totalRequest = new String(messageIn.getData());
			String[] params = totalRequest.split(":");
			String operation = params[0];
			System.out.println(operation);
			String arguement = params[1];
			String num = params[params.length-1].trim();
			System.out.println(num);
			int seqNum = Integer.parseInt(num);
			//System.out.println("Operation:"+operation+" AppointmentType:" + arguement + " AppointmentID:" + arguement2 + " Capacity:" + arguement3 + " Seq#:"+arguement4);
			String server = arguement.charAt(0)+""+arguement.charAt(1)+""+arguement.charAt(2);
			serverLog = new LogFileCreator(server);
			serverLog.append(id+": ");
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			AdminAppointmentHandler obj = AdminAppointmentHandlerHelper.narrow(ncRef.resolve_str("AppointmentHandler"+server));
			
			//obj.writeToLog(id+".txt","Request: Login, User:"+id);
			
			//obj.setID(id);
			//obj.setName(server);
			String logData = "";
			String serverLogData = "";
			if (operation.equals("addAppointment"))
			{
				//obj.setType('A');
				logData+="Request Made:" +  dtf.format(now) + " Type of Request:Add Appointment ";
				
				String arguement2 = params[2];
				String arguement3 =params[3];
				String arguement4 = params[4];
				System.out.println(arguement);
				System.out.println(arguement2);
				System.out.println(arguement3);
				
				if (obj.addAppointment(arguement,arguement2,Integer.parseInt(arguement3)) > 0)
				{
					data = id+":Appointment Added:"+seqNum;
					logData+="Response: The appointment was added";
					
					log.append(logData);
					serverLog.append(logData);
					System.out.println("Appointment Added");
					//System.out.println(obj.getAppointmentByType(aType));
				}
				else
				{
					logData+="Response: Something went wrong, the appointment was not added";
					data = id+":Failure creating appointment with this ID/TYPE!! Check log for more details!:"+seqNum;
					log.append(logData);
					serverLog.append(logData);
					System.out.println("Failure creating appointment with this ID/TYPE!! Check log for more details!");
				}
				
			
				buf = data.getBytes();
				messageOut = new DatagramPacket(buf,buf.length,a,responsePort);
				asocket.send(messageOut);
				
			}
			else if (operation.equals("removeAppointment"))
			{
				logData+="Request Made:" +  dtf.format(now) + " Type of Request:Remove Appointment ";
				
				String arguement2 = params[2];
				//obj.setType('A');
				System.out.println(arguement);
				System.out.println(arguement2);
				logData+="Request Parameters: " + arguement + " " + arguement2 + " ";
				if (obj.removeAppointment(arguement,arguement2) > 0) 
				{
					logData+="Response: The appointment has been removed";
					data = id+":Appointment Removed:"+seqNum;
					System.out.println("Appointment Removed");
					//obj.writeToLog(id+".txt","Request type: Remove appointment, Parameters: AppointmentID:"+arguement+",AppointmentType:"+arguement2+" Success Appointment Removed!");
				}
				else
				{
					logData+="Response: Something went wrong, the appointment either doesn't exist or it was not removed";
					data = id+":Error cannot be removed please check logs for more details:"+seqNum;
					//obj.writeToLog(id+".txt","Request type: Remove appointment, Parameters: AppointmentID:"+arguement+",AppointmentType:"+arguement2+" Failure with this id and TYPE!!");
					System.out.println("Error cannot be removed please check logs for more details");
				}
				log.append(logData);
				serverLog.append(logData);
				buf = data.getBytes();
				messageOut = new DatagramPacket(buf,buf.length,a,responsePort);
				asocket.send(messageOut);
			}
			else if(operation.equals("listAppointmentAvailability"))
			{
				logData+="Request Made:" +  dtf.format(now) + " Type of Request:Show All Available Appointments for Appointment Type ";
				String arguement2 = params[2];
				System.out.println(arguement2);
				logData+="Request Parameter:" + arguement2+" ";
				String list = obj.listAppointmentAvailability(arguement2);
				logData+="Server Response(See next line(s)):" + list; 
				if (list.trim().equals("There are no available appointments for that type"))
				{
					data = id+":There are none available of this type!:"+seqNum;
					System.out.println("There are none available of this type!");
					//obj.writeToLog(id+".txt","Request type: List Appointments Available, Parameters: AppointmentType:"+arguement2+"Failure None Availableo of this type!");
				}
				else
				{
					data = id+":List:"+obj.listAppointmentAvailability(arguement2)+":"+seqNum;
					System.out.println(obj.listAppointmentAvailability(arguement2));
					//obj.writeToLog(id+".txt","Request type: List Appointments Available, Parameters: AppointmentType:"+arguement2+" Success Appointments Shown!!");
				}
				log.append(logData);
				serverLog.append(logData);
				buf = data.getBytes();
				messageOut = new DatagramPacket(buf,buf.length,a,responsePort);
				asocket.send(messageOut);
			}
			else if (operation.equals("bookAppointment"))
			{
				logData+="Request Made:" +  dtf.format(now) + " Type of Request:Book Appointment ";
				String arguement2 = params[2];
				String arguement3 =params[3];
				//System.out.println(arguement);
				//System.out.println(arguement2);
				//System.out.println(arguement3);
				
				int reason = obj.bookAppointment(arguement,arguement2,arguement3);
				System.out.println(reason);
				logData+="Request Parameters: " + arguement + " " +  arguement2 + " " + arguement3 + " ";
				if (reason > 0)
				{
					logData+="Response: The appointment has been booked";
					data = id+":Appointment Booked!:"+seqNum;
					System.out.println("Appointment Booked!");
					//obj.writeToLog(id+".txt","Request type: Book Appointment, Parameters: AppointmentType:"+arguement3+",AppointmentID:"+arguement2+",PatientID:"+arguement+" Success");
				}
				else
				{
					data = id+":Not booked.Check logs for more details!:"+seqNum;
					System.out.println("Not booked.Check logs for more details!");
					logData+="Response:Something went wrong, the appointment was not booked";
					//obj.writeToLog(id+".txt","Request type: Book Appointment, Parameters: AppointmentType:"+arguement3+",AppointmentID:"+arguement2+",PatientID:"+arguement+" Failure "+reason);
				}
				log.append(logData);
				serverLog.append(logData);
				buf = data.getBytes();
				messageOut = new DatagramPacket(buf,buf.length,a,responsePort);
				asocket.send(messageOut);
			}
			else if (operation.equals("getAppointmentSchedule"))
			{
				logData+="Request Made:" +  dtf.format(now) + " Type of Request:View Appointments ";
				logData+="Request Parameters: " + id +" ";
				
				String bookings = obj.getAppointmentSchedule(arguement);
				
				logData+="Response(S=Surgery, D=Dental, P=Physician): " + bookings;
				if (bookings.equals("You have not booked any appointments"))
				{
					data = id+":You have no appointments currently:"+seqNum;
					System.out.println("You have no appointments currently");
					//obj.writeToLog(id+".txt","Request: Get Appointment Schedule Fail No Appointments Currently!:"+arguement);
				}
				else
				{
					data = id+":Schedule:"+bookings+":"+seqNum;
					System.out.println(bookings);
					//obj.writeToLog(id+".txt","Request: Get Appointment Schedule Success Schedule has been Shown:"+arguement);
				}
				log.append(logData);
				serverLog.append(logData);
				buf = data.getBytes();
				messageOut = new DatagramPacket(buf,buf.length,a,responsePort);
				asocket.send(messageOut);
			}
			else if(operation.equals("cancelAppointment"))
			{
				String arguement2 = params[2];
				String arguement3 =params[3];
​
				logData+="Request Made:" +  dtf.format(now) + " Type of Request:Cancel Appointment ";
				int reason = obj.cancelAppointment(arguement,arguement2);
			
				System.out.println("Reason:"+reason);
				if (reason > 0)
				{
					logData+="Response:You have successfully cancelled the appointment";
					data = id+":Appointment Canceled!:"+seqNum;
					System.out.println("Appointment Canceled!");
					//obj.writeToLog(id+".txt","Request type: Cancel Appointment, Parameters: AppointmentType:"+arguement3+",AppointmentID:"+arguement2+",PatientID:"+arguement+" Success");
				}
				else
				{
					logData+="Response:Something went wrong, the appointment was not removed or the appointment does not exist";
					
					data = id+":Not canceled.Check logs for more details!!:"+seqNum;
					System.out.println("Not canceled.Check logs for more details!");
					//obj.writeToLog(id+".txt","Request type: Cancel Appointment, Parameters: AppointmentType:"+arguement3+",AppointmentID:"+arguement2+",PatientID:"+arguement+" Failure "+reason);
				}
				log.append(logData);
				serverLog.append(logData);
				buf = data.getBytes();
				messageOut = new DatagramPacket(buf,buf.length,a,responsePort);
				asocket.send(messageOut);
			}
			else if(operation.equals("swapAppointment"))
			{
				logData+="Request Made:" +  dtf.format(now) + " Type of Request:Swap Appointment ";
				//System.out.println("HELLO");
				String arguement2 = params[2];
				String arguement3 =params[3];
				String arguement4 =params[4];
				String arguement5 =params[5];
				logData+="Request Parameters: " + arguement+ " " + arguement2 + " " + arguement3 + " " + arguement4 + " " + arguement5 + " ";
				
				if (obj.swapAppointment(arguement,arguement2,arguement3,arguement4,arguement5) > 0)
				{
					logData+="Response:You have successfully swapped " + arguement2+ " for " + arguement4;
					data = id+":Appointment Swaped!:"+seqNum;
					System.out.println("Appointment Swaped!");
					//obj.writeToLog(id+".txt","Request type: Swap Appointment, Parameters: oldAppointmentType:"+arguement3+",oldAppointmentID:"+arguement2+"newAppointmentType:"+arguement5+",newAppointmentID:"+arguement4+",PatientID:"+arguement+" Success Swap has been done!");
				}
				else
				{
					logData+="Response:Something went wrong. There may be something wrong with 1 or both of the Appointments you entered to swap.";
					data = id+":Appointment Swap Failed!:"+seqNum;
					System.out.println("Appointment Swap Failed!");
					//obj.writeToLog(id+".txt","Request type: Swap Appointment, Parameters: oldAppointmentType:"+arguement3+",oldAppointmentID:"+arguement2+"newAppointmentType:"+arguement5+",newAppointmentID:"+arguement4+",PatientID:"+arguement+" Failure swap not done!!");
				}
				log.append(logData);
				serverLog.append(logData);
				buf = data.getBytes();
				System.out.println(buf.length);
				messageOut = new DatagramPacket(buf,buf.length,a,responsePort);
				asocket.send(messageOut);
			}
			log.closeFile();
			serverLog.closeFile();
		}
		
	}
