package com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider;


/**
* com/bbn/openmap/layer/rpf/corba/CRpfFrameProvider/RawImageHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/rpf/corba/CorbaRpfFrameProvider.idl
* Tuesday, November 12, 2013 11:07:44 PM EST
*/

abstract public class RawImageHelper
{
  private static String  _id = "IDL:CRpfFrameProvider/RawImage:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.RawImage that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.RawImage extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.rawdataHelper.id (), "rawdata", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "colortable",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_octet);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.dataHelper.id (), "data", _tcOf_members0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "imagedata",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.RawImageHelper.id (), "RawImage", _members0);
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

  public static com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.RawImage read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.RawImage value = new com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.RawImage ();
    value.colortable = com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.rawdataHelper.read (istream);
    value.imagedata = com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.dataHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.RawImage value)
  {
    com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.rawdataHelper.write (ostream, value.colortable);
    com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.dataHelper.write (ostream, value.imagedata);
  }

}
