package com.bbn.openmap.corba.CSpecialist;


/**
* com/bbn/openmap/corba/CSpecialist/UpdateGraphicHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/specialist/Specialist.idl
* Tuesday, November 12, 2013 11:07:46 PM EST
*/

abstract public class UpdateGraphicHelper
{
  private static String  _id = "IDL:CSpecialist/UpdateGraphic:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.corba.CSpecialist.UpdateGraphic that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.corba.CSpecialist.UpdateGraphic extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      org.omg.CORBA.TypeCode _disTypeCode0;
      _disTypeCode0 = com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.type ();
      org.omg.CORBA.UnionMember[] _members0 = new org.omg.CORBA.UnionMember [13];
      org.omg.CORBA.TypeCode _tcOf_members0;
      org.omg.CORBA.Any _anyOf_members0;

      // Branch for gf_update (case label GT_Graphic)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_Graphic);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.GraphicPackage.GF_updateHelper.type ();
      _members0[0] = new org.omg.CORBA.UnionMember (
        "gf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for bf_update (case label GT_Bitmap)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_Bitmap);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.BitmapPackage.BF_updateHelper.type ();
      _members0[1] = new org.omg.CORBA.UnionMember (
        "bf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for tf_update (case label GT_Text)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_Text);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.TextPackage.TF_updateHelper.type ();
      _members0[2] = new org.omg.CORBA.UnionMember (
        "tf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for pf_update (case label GT_Poly)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_Poly);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.PolyPackage.PF_updateHelper.type ();
      _members0[3] = new org.omg.CORBA.UnionMember (
        "pf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for lf_update (case label GT_Line)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_Line);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.LinePackage.LF_updateHelper.type ();
      _members0[4] = new org.omg.CORBA.UnionMember (
        "lf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for usf_update (case label GT_UnitSymbol)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_UnitSymbol);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.UnitSymbolPackage.USF_updateHelper.type ();
      _members0[5] = new org.omg.CORBA.UnionMember (
        "usf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for u2525f_update (case label GT_2525Symbol)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_2525Symbol);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.U2525SymbolPackage.U2525F_updateHelper.type ();
      _members0[6] = new org.omg.CORBA.UnionMember (
        "u2525f_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for rf_update (case label GT_Rectangle)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_Rectangle);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_updateHelper.type ();
      _members0[7] = new org.omg.CORBA.UnionMember (
        "rf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for cf_update (case label GT_Circle)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_Circle);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.CirclePackage.CF_updateHelper.type ();
      _members0[8] = new org.omg.CORBA.UnionMember (
        "cf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for rasf_update (case label GT_Raster)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_Raster);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.RasterPackage.RASF_updateHelper.type ();
      _members0[9] = new org.omg.CORBA.UnionMember (
        "rasf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for faf_update (case label GT_ForceArrow)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_ForceArrow);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.ForceArrowPackage.FAF_updateHelper.type ();
      _members0[10] = new org.omg.CORBA.UnionMember (
        "faf_update",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for new_graphic (case label GT_NewGraphic)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_NewGraphic);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.UGraphicHelper.type ();
      _members0[11] = new org.omg.CORBA.UnionMember (
        "new_graphic",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for reorder_kind (case label GT_ReorderGraphic)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.insert (_anyOf_members0, com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType.GT_ReorderGraphic);
      _tcOf_members0 = com.bbn.openmap.corba.CSpecialist.ReorderTypeHelper.type ();
      _members0[12] = new org.omg.CORBA.UnionMember (
        "reorder_kind",
        _anyOf_members0,
        _tcOf_members0,
        null);
      __typeCode = org.omg.CORBA.ORB.init ().create_union_tc (com.bbn.openmap.corba.CSpecialist.UpdateGraphicHelper.id (), "UpdateGraphic", _disTypeCode0, _members0);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.corba.CSpecialist.UpdateGraphic read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.corba.CSpecialist.UpdateGraphic value = new com.bbn.openmap.corba.CSpecialist.UpdateGraphic ();
    com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType _dis0 = null;
    _dis0 = com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.read (istream);
    switch (_dis0.value ())
    {
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Graphic:
        com.bbn.openmap.corba.CSpecialist.GraphicPackage.GF_update _gf_update = null;
        _gf_update = com.bbn.openmap.corba.CSpecialist.GraphicPackage.GF_updateHelper.read (istream);
        value.gf_update (_gf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Bitmap:
        com.bbn.openmap.corba.CSpecialist.BitmapPackage.BF_update _bf_update = null;
        _bf_update = com.bbn.openmap.corba.CSpecialist.BitmapPackage.BF_updateHelper.read (istream);
        value.bf_update (_bf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Text:
        com.bbn.openmap.corba.CSpecialist.TextPackage.TF_update _tf_update = null;
        _tf_update = com.bbn.openmap.corba.CSpecialist.TextPackage.TF_updateHelper.read (istream);
        value.tf_update (_tf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Poly:
        com.bbn.openmap.corba.CSpecialist.PolyPackage.PF_update _pf_update = null;
        _pf_update = com.bbn.openmap.corba.CSpecialist.PolyPackage.PF_updateHelper.read (istream);
        value.pf_update (_pf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Line:
        com.bbn.openmap.corba.CSpecialist.LinePackage.LF_update _lf_update = null;
        _lf_update = com.bbn.openmap.corba.CSpecialist.LinePackage.LF_updateHelper.read (istream);
        value.lf_update (_lf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_UnitSymbol:
        com.bbn.openmap.corba.CSpecialist.UnitSymbolPackage.USF_update _usf_update = null;
        _usf_update = com.bbn.openmap.corba.CSpecialist.UnitSymbolPackage.USF_updateHelper.read (istream);
        value.usf_update (_usf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_2525Symbol:
        com.bbn.openmap.corba.CSpecialist.U2525SymbolPackage.U2525F_update _u2525f_update = null;
        _u2525f_update = com.bbn.openmap.corba.CSpecialist.U2525SymbolPackage.U2525F_updateHelper.read (istream);
        value.u2525f_update (_u2525f_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Rectangle:
        com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_update _rf_update = null;
        _rf_update = com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_updateHelper.read (istream);
        value.rf_update (_rf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Circle:
        com.bbn.openmap.corba.CSpecialist.CirclePackage.CF_update _cf_update = null;
        _cf_update = com.bbn.openmap.corba.CSpecialist.CirclePackage.CF_updateHelper.read (istream);
        value.cf_update (_cf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Raster:
        com.bbn.openmap.corba.CSpecialist.RasterPackage.RASF_update _rasf_update = null;
        _rasf_update = com.bbn.openmap.corba.CSpecialist.RasterPackage.RASF_updateHelper.read (istream);
        value.rasf_update (_rasf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_ForceArrow:
        com.bbn.openmap.corba.CSpecialist.ForceArrowPackage.FAF_update _faf_update = null;
        _faf_update = com.bbn.openmap.corba.CSpecialist.ForceArrowPackage.FAF_updateHelper.read (istream);
        value.faf_update (_faf_update);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_NewGraphic:
        com.bbn.openmap.corba.CSpecialist.UGraphic _new_graphic = null;
        _new_graphic = com.bbn.openmap.corba.CSpecialist.UGraphicHelper.read (istream);
        value.new_graphic (_new_graphic);
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_ReorderGraphic:
        com.bbn.openmap.corba.CSpecialist.ReorderType _reorder_kind = null;
        _reorder_kind = com.bbn.openmap.corba.CSpecialist.ReorderTypeHelper.read (istream);
        value.reorder_kind (_reorder_kind);
        break;
    }
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.corba.CSpecialist.UpdateGraphic value)
  {
    com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.write (ostream, value.discriminator ());
    switch (value.discriminator ().value ())
    {
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Graphic:
        com.bbn.openmap.corba.CSpecialist.GraphicPackage.GF_updateHelper.write (ostream, value.gf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Bitmap:
        com.bbn.openmap.corba.CSpecialist.BitmapPackage.BF_updateHelper.write (ostream, value.bf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Text:
        com.bbn.openmap.corba.CSpecialist.TextPackage.TF_updateHelper.write (ostream, value.tf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Poly:
        com.bbn.openmap.corba.CSpecialist.PolyPackage.PF_updateHelper.write (ostream, value.pf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Line:
        com.bbn.openmap.corba.CSpecialist.LinePackage.LF_updateHelper.write (ostream, value.lf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_UnitSymbol:
        com.bbn.openmap.corba.CSpecialist.UnitSymbolPackage.USF_updateHelper.write (ostream, value.usf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_2525Symbol:
        com.bbn.openmap.corba.CSpecialist.U2525SymbolPackage.U2525F_updateHelper.write (ostream, value.u2525f_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Rectangle:
        com.bbn.openmap.corba.CSpecialist.RectanglePackage.RF_updateHelper.write (ostream, value.rf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Circle:
        com.bbn.openmap.corba.CSpecialist.CirclePackage.CF_updateHelper.write (ostream, value.cf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_Raster:
        com.bbn.openmap.corba.CSpecialist.RasterPackage.RASF_updateHelper.write (ostream, value.rasf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_ForceArrow:
        com.bbn.openmap.corba.CSpecialist.ForceArrowPackage.FAF_updateHelper.write (ostream, value.faf_update ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_NewGraphic:
        com.bbn.openmap.corba.CSpecialist.UGraphicHelper.write (ostream, value.new_graphic ());
        break;
      case com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType._GT_ReorderGraphic:
        com.bbn.openmap.corba.CSpecialist.ReorderTypeHelper.write (ostream, value.reorder_kind ());
        break;
    }
  }

}
