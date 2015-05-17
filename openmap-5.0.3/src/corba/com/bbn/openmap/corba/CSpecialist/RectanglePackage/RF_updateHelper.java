package com.bbn.openmap.corba.CSpecialist.RectanglePackage;


/**
* com/bbn/openmap/corba/CSpecialist/RectanglePackage/RF_updateHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/specialist/Specialist.idl
* Tuesday, November 12, 2013 11:07:46 PM EST
*/

abstract public class RF_updateHelper
{
  private static String  _id = "IDL:CSpecialist/Rectangle/RF_update:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_update that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_update extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      org.omg.CORBA.TypeCode _disTypeCode0;
      _disTypeCode0 = com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFieldsHelper.type ();
      org.omg.CORBA.UnionMember[] _members0 = new org.omg.CORBA.UnionMember [4];
      org.omg.CORBA.TypeCode _tcOf_members0;
      org.omg.CORBA.Any _anyOf_members0;

      // Branch for p1 (case label RF_p1)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields.RF_p1);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.XYPointHelper.type ();
      _members0[0] = new org.omg.CORBA.UnionMember (
        "p1",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for p2 (case label RF_p2)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields.RF_p2);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.XYPointHelper.type ();
      _members0[1] = new org.omg.CORBA.UnionMember (
        "p2",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for ll1 (case label RF_ll1)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields.RF_ll1);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.LLPointHelper.type ();
      _members0[2] = new org.omg.CORBA.UnionMember (
        "ll1",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for ll2 (case label RF_ll2)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields.RF_ll2);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.LLPointHelper.type ();
      _members0[3] = new org.omg.CORBA.UnionMember (
        "ll2",
        _anyOf_members0,
        _tcOf_members0,
        null);
      __typeCode = org.omg.CORBA.ORB.init ().create_union_tc (com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_updateHelper.id (), "RF_update", _disTypeCode0, _members0);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_update read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_update value = new com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_update ();
    com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields _dis0 = null;
    _dis0 = com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFieldsHelper.read (istream);
    switch (_dis0.value ())
    {
      case com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields._RF_p1:
        com.bbn.openmap.corba.CSpecialist.XYPoint _p1 = null;
        _p1 = com.bbn.openmap.corba.CSpecialist.XYPointHelper.read (istream);
        value.p1 (_p1);
        break;
      case com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields._RF_p2:
        com.bbn.openmap.corba.CSpecialist.XYPoint _p2 = null;
        _p2 = com.bbn.openmap.corba.CSpecialist.XYPointHelper.read (istream);
        value.p2 (_p2);
        break;
      case com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields._RF_ll1:
        com.bbn.openmap.corba.CSpecialist.LLPoint _ll1 = null;
        _ll1 = com.bbn.openmap.corba.CSpecialist.LLPointHelper.read (istream);
        value.ll1 (_ll1);
        break;
      case com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields._RF_ll2:
        com.bbn.openmap.corba.CSpecialist.LLPoint _ll2 = null;
        _ll2 = com.bbn.openmap.corba.CSpecialist.LLPointHelper.read (istream);
        value.ll2 (_ll2);
        break;
    }
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_update value)
  {
    com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFieldsHelper.write (ostream, value.discriminator ());
    switch (value.discriminator ().value ())
    {
      case com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields._RF_p1:
        com.bbn.openmap.corba.CSpecialist.XYPointHelper.write (ostream, value.p1 ());
        break;
      case com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields._RF_p2:
        com.bbn.openmap.corba.CSpecialist.XYPointHelper.write (ostream, value.p2 ());
        break;
      case com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields._RF_ll1:
        com.bbn.openmap.corba.CSpecialist.LLPointHelper.write (ostream, value.ll1 ());
        break;
      case com.bbn.openmap.corba.CSpecialist.RectanglePackage.settableFields._RF_ll2:
        com.bbn.openmap.corba.CSpecialist.LLPointHelper.write (ostream, value.ll2 ());
        break;
    }
  }

}
