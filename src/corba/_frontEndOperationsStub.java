package corba;


/**
* corba/_frontEndOperationsStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* Saturday, November 23, 2019 3:54:02 PM EST
*/

public class _frontEndOperationsStub extends org.omg.CORBA.portable.ObjectImpl implements corba.frontEndOperations
{

  public void bookAppointment (String patientID, String appointmentID, String appointmentType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("bookAppointment", true);
                $out.write_string (patientID);
                $out.write_string (appointmentID);
                $out.write_string (appointmentType);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                bookAppointment (patientID, appointmentID, appointmentType        );
            } finally {
                _releaseReply ($in);
            }
  } // bookAppointment

  public void getAppointmentSchedule (String patientID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAppointmentSchedule", true);
                $out.write_string (patientID);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                getAppointmentSchedule (patientID        );
            } finally {
                _releaseReply ($in);
            }
  } // getAppointmentSchedule

  public void cancelAppointment (String patientID, String appointmentID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("cancelAppointment", true);
                $out.write_string (patientID);
                $out.write_string (appointmentID);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                cancelAppointment (patientID, appointmentID        );
            } finally {
                _releaseReply ($in);
            }
  } // cancelAppointment

  public void addAppointment (String appointmentID, String appointmentType, int capacity, String adminID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addAppointment", true);
                $out.write_string (appointmentID);
                $out.write_string (appointmentType);
                $out.write_long (capacity);
                $out.write_string (adminID);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                addAppointment (appointmentID, appointmentType, capacity, adminID        );
            } finally {
                _releaseReply ($in);
            }
  } // addAppointment

  public void removeAppointment (String appointmentID, String appointmentType, String adminID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("removeAppointment", true);
                $out.write_string (appointmentID);
                $out.write_string (appointmentType);
                $out.write_string (adminID);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                removeAppointment (appointmentID, appointmentType, adminID        );
            } finally {
                _releaseReply ($in);
            }
  } // removeAppointment

  public void listAppointmentAvailability (String appointmentType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("listAppointmentAvailability", true);
                $out.write_string (appointmentType);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                listAppointmentAvailability (appointmentType        );
            } finally {
                _releaseReply ($in);
            }
  } // listAppointmentAvailability

  public void swapAppointment (String patientID, String oldAppointmentID, String oldAppointmentType, String newAppointmentID, String newAppointmentType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("swapAppointment", true);
                $out.write_string (patientID);
                $out.write_string (oldAppointmentID);
                $out.write_string (oldAppointmentType);
                $out.write_string (newAppointmentID);
                $out.write_string (newAppointmentType);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                swapAppointment (patientID, oldAppointmentID, oldAppointmentType, newAppointmentID, newAppointmentType        );
            } finally {
                _releaseReply ($in);
            }
  } // swapAppointment

  public void shutdown ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("shutdown", false);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                shutdown (        );
            } finally {
                _releaseReply ($in);
            }
  } // shutdown

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/frontEndOperations:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _frontEndOperationsStub
