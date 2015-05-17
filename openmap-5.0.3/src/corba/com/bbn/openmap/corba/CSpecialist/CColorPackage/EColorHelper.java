package com.bbn.openmap.corba.CSpecialist.CColorPackage;


/**
* com/bbn/openmap/corba/CSpecialist/CColorPackage/EColorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/specialist/Specialist.idl
* Tuesday, November 12, 2013 11:07:45 PM EST
*/

abstract public class EColorHelper
{
  private static String  _id = "IDL:CSpecialist/CColor/EColor:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.corba.CSpecialist.CColorPackage.EColor that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.corba.CSpecialist.CColorPackage.EColor extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.CColorHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "color",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
          _members0[1] = new org.omg.CORBA.StructMember (
            "red",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
          _members0[2] = new org.omg.CORBA.StructMember (
            "green",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
          _members0[3] = new org.omg.CORBA.StructMember (
            "blue",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.bbn.openmap.corba.CSpecialist.CColorPackage.EColorHelper.id (), "EColor", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.corba.CSpecialist.CColorPackage.EColor read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.corba.CSpecialist.CColorPackage.EColor value = new com.bbn.openmap.corba.CSpecialist.CColorPackage.EColor ();
    value.color = com.bbn.openmap.corba.CSpecialist.CColorHelper.read (istream);
    value.red = istream.read_ushort ();
    value.green = istream.read_ushort ();
    value.blue = istream.read_ushort ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.corba.CSpecialist.CColorPackage.EColor value)
  {
    com.bbn.openmap.corba.CSpecialist.CColorHelper.write (ostream, value.color);
    ostream.write_ushort (value.red);
    ostream.write_ushort (value.green);
    ostream.write_ushort (value.blue);
  }

}
