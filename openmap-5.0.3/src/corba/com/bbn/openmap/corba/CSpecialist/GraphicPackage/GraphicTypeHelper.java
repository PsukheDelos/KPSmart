package com.bbn.openmap.corba.CSpecialist.GraphicPackage;


/**
* com/bbn/openmap/corba/CSpecialist/GraphicPackage/GraphicTypeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/specialist/Specialist.idl
* Tuesday, November 12, 2013 11:07:45 PM EST
*/

abstract public class GraphicTypeHelper
{
  private static String  _id = "IDL:CSpecialist/Graphic/GraphicType:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.id (), "GraphicType", new String[] { "GT_Graphic", "GT_Bitmap", "GT_Text", "GT_Poly", "GT_Line", "GT_UnitSymbol", "GT_2525Symbol", "GT_Rectangle", "GT_Circle", "GT_Raster", "GT_ForceArrow", "GT_NewGraphic", "GT_ReorderGraphic"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType read (org.omg.CORBA.portable.InputStream istream)
  {
    return com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType value)
  {
    ostream.write_long (value.value ());
  }

}
