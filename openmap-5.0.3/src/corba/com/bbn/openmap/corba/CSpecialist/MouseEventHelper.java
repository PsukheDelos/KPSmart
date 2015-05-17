package com.bbn.openmap.corba.CSpecialist;


/**
* com/bbn/openmap/corba/CSpecialist/MouseEventHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/specialist/Specialist.idl
* Tuesday, November 12, 2013 11:07:46 PM EST
*/

abstract public class MouseEventHelper
{
  private static String  _id = "IDL:CSpecialist/MouseEvent:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.corba.CSpecialist.MouseEvent that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.corba.CSpecialist.MouseEvent extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      org.omg.CORBA.TypeCode _disTypeCode0;
      _disTypeCode0 = com.bbn.openmap.corba.CSpecialist.MouseTypeHelper.type ();
      org.omg.CORBA.UnionMember[] _members0 = new org.omg.CORBA.UnionMember [4];
      org.omg.CORBA.TypeCode _tcOf_members0;
      org.omg.CORBA.Any _anyOf_members0;

      // Branch for click (case label ClickEvent)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.MouseTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.MouseType.ClickEvent);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.MouseHelper.type ();
      _members0[0] = new org.omg.CORBA.UnionMember (
        "click",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for motion (case label MotionEvent)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.MouseTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.MouseType.MotionEvent);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.MouseHelper.type ();
      _members0[1] = new org.omg.CORBA.UnionMember (
        "motion",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for keypress (case label KeyEvent)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.MouseTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.MouseType.KeyEvent);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.KeypressHelper.type ();
      _members0[2] = new org.omg.CORBA.UnionMember (
        "keypress",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for mapviewchange (case label MapViewChangeEvent)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.MouseTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.MouseType.MapViewChangeEvent);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist._MapViewHolderHelper.type ();
      _members0[3] = new org.omg.CORBA.UnionMember (
        "mapviewchange",
        _anyOf_members0,
        _tcOf_members0,
        null);
      __typeCode = org.omg.CORBA.ORB.init ().create_union_tc (com.bbn.openmap.corba.CSpecialist.MouseEventHelper.id (), "MouseEvent", _disTypeCode0, _members0);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.corba.CSpecialist.MouseEvent read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.corba.CSpecialist.MouseEvent value = new com.bbn.openmap.corba.CSpecialist.MouseEvent ();
    com.bbn.openmap.corba.CSpecialist.MouseType _dis0 = null;
    _dis0 = com.bbn.openmap.corba.CSpecialist.MouseTypeHelper.read (istream);
    switch (_dis0.value ())
    {
      case com.bbn.openmap.corba.CSpecialist.MouseType._ClickEvent:
        com.bbn.openmap.corba.CSpecialist.Mouse _click = null;
        _click = com.bbn.openmap.corba.CSpecialist.MouseHelper.read (istream);
        value.click (_click);
        break;
      case com.bbn.openmap.corba.CSpecialist.MouseType._MotionEvent:
        com.bbn.openmap.corba.CSpecialist.Mouse _motion = null;
        _motion = com.bbn.openmap.corba.CSpecialist.MouseHelper.read (istream);
        value.motion (_motion);
        break;
      case com.bbn.openmap.corba.CSpecialist.MouseType._KeyEvent:
        com.bbn.openmap.corba.CSpecialist.Keypress _keypress = null;
        _keypress = com.bbn.openmap.corba.CSpecialist.KeypressHelper.read (istream);
        value.keypress (_keypress);
        break;
      case com.bbn.openmap.corba.CSpecialist.MouseType._MapViewChangeEvent:
        com.bbn.openmap.corba.CSpecialist._MapViewHolder _mapviewchange = null;
        _mapviewchange = com.bbn.openmap.corba.CSpecialist._MapViewHolderHelper.read (istream);
        value.mapviewchange (_mapviewchange);
        break;
    }
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.corba.CSpecialist.MouseEvent value)
  {
    com.bbn.openmap.corba.CSpecialist.MouseTypeHelper.write (ostream, value.discriminator ());
    switch (value.discriminator ().value ())
    {
      case com.bbn.openmap.corba.CSpecialist.MouseType._ClickEvent:
        com.bbn.openmap.corba.CSpecialist.MouseHelper.write (ostream, value.click ());
        break;
      case com.bbn.openmap.corba.CSpecialist.MouseType._MotionEvent:
        com.bbn.openmap.corba.CSpecialist.MouseHelper.write (ostream, value.motion ());
        break;
      case com.bbn.openmap.corba.CSpecialist.MouseType._KeyEvent:
        com.bbn.openmap.corba.CSpecialist.KeypressHelper.write (ostream, value.keypress ());
        break;
      case com.bbn.openmap.corba.CSpecialist.MouseType._MapViewChangeEvent:
        com.bbn.openmap.corba.CSpecialist._MapViewHolderHelper.write (ostream, value.mapviewchange ());
        break;
    }
  }

}
