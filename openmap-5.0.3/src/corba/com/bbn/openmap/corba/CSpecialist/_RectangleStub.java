package com.bbn.openmap.corba.CSpecialist;


/**
* com/bbn/openmap/corba/CSpecialist/_RectangleStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/specialist/Specialist.idl
* Tuesday, November 12, 2013 11:07:46 PM EST
*/

public class _RectangleStub extends org.omg.CORBA.portable.ObjectImpl implements com.bbn.openmap.corba.CSpecialist.Rectangle
{

  public com.bbn.openmap.corba.CSpecialist.XYPoint p1 ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_p1", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.XYPoint $result = com.bbn.openmap.corba.CSpecialist.XYPointHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return p1 (        );
            } finally {
                _releaseReply ($in);
            }
  } // p1

  public void p1 (com.bbn.openmap.corba.CSpecialist.XYPoint newP1)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_p1", true);
                com.bbn.openmap.corba.CSpecialist.XYPointHelper.write ($out, newP1);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                p1 (newP1        );
            } finally {
                _releaseReply ($in);
            }
  } // p1

  public com.bbn.openmap.corba.CSpecialist.XYPoint p2 ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_p2", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.XYPoint $result = com.bbn.openmap.corba.CSpecialist.XYPointHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return p2 (        );
            } finally {
                _releaseReply ($in);
            }
  } // p2

  public void p2 (com.bbn.openmap.corba.CSpecialist.XYPoint newP2)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_p2", true);
                com.bbn.openmap.corba.CSpecialist.XYPointHelper.write ($out, newP2);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                p2 (newP2        );
            } finally {
                _releaseReply ($in);
            }
  } // p2

  public com.bbn.openmap.corba.CSpecialist.LLPoint ll1 ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_ll1", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.LLPoint $result = com.bbn.openmap.corba.CSpecialist.LLPointHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return ll1 (        );
            } finally {
                _releaseReply ($in);
            }
  } // ll1

  public void ll1 (com.bbn.openmap.corba.CSpecialist.LLPoint newLl1)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_ll1", true);
                com.bbn.openmap.corba.CSpecialist.LLPointHelper.write ($out, newLl1);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                ll1 (newLl1        );
            } finally {
                _releaseReply ($in);
            }
  } // ll1

  public com.bbn.openmap.corba.CSpecialist.LLPoint ll2 ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_ll2", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.LLPoint $result = com.bbn.openmap.corba.CSpecialist.LLPointHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return ll2 (        );
            } finally {
                _releaseReply ($in);
            }
  } // ll2

  public void ll2 (com.bbn.openmap.corba.CSpecialist.LLPoint newLl2)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_ll2", true);
                com.bbn.openmap.corba.CSpecialist.LLPointHelper.write ($out, newLl2);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                ll2 (newLl2        );
            } finally {
                _releaseReply ($in);
            }
  } // ll2

  public com.bbn.openmap.corba.CSpecialist.RectanglePackage.ERectangle fill ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("fill", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.RectanglePackage.ERectangle $result = com.bbn.openmap.corba.CSpecialist.RectanglePackage.ERectangleHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return fill (        );
            } finally {
                _releaseReply ($in);
            }
  } // fill


  //  original spot
  public String gID ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_gID", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return gID (        );
            } finally {
                _releaseReply ($in);
            }
  } // gID

  public com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType gType ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_gType", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicType $result = com.bbn.openmap.corba.CSpecialist.GraphicPackage.GraphicTypeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return gType (        );
            } finally {
                _releaseReply ($in);
            }
  } // gType

  public com.bbn.openmap.corba.CSpecialist.Comp obj ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_obj", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.Comp $result = com.bbn.openmap.corba.CSpecialist.CompHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return obj (        );
            } finally {
                _releaseReply ($in);
            }
  } // obj

  public void obj (com.bbn.openmap.corba.CSpecialist.Comp newObj)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_obj", true);
                com.bbn.openmap.corba.CSpecialist.CompHelper.write ($out, newObj);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                obj (newObj        );
            } finally {
                _releaseReply ($in);
            }
  } // obj

  public com.bbn.openmap.corba.CSpecialist.GraphicPackage.LineType lType ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_lType", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.GraphicPackage.LineType $result = com.bbn.openmap.corba.CSpecialist.GraphicPackage.LineTypeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return lType (        );
            } finally {
                _releaseReply ($in);
            }
  } // lType

  public void lType (com.bbn.openmap.corba.CSpecialist.GraphicPackage.LineType newLType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_lType", true);
                com.bbn.openmap.corba.CSpecialist.GraphicPackage.LineTypeHelper.write ($out, newLType);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                lType (newLType        );
            } finally {
                _releaseReply ($in);
            }
  } // lType

  public com.bbn.openmap.corba.CSpecialist.GraphicPackage.RenderType rType ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_rType", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.GraphicPackage.RenderType $result = com.bbn.openmap.corba.CSpecialist.GraphicPackage.RenderTypeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return rType (        );
            } finally {
                _releaseReply ($in);
            }
  } // rType

  public void rType (com.bbn.openmap.corba.CSpecialist.GraphicPackage.RenderType newRType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_rType", true);
                com.bbn.openmap.corba.CSpecialist.GraphicPackage.RenderTypeHelper.write ($out, newRType);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                rType (newRType        );
            } finally {
                _releaseReply ($in);
            }
  } // rType

  public com.bbn.openmap.corba.CSpecialist.CColor color ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_color", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.CColor $result = com.bbn.openmap.corba.CSpecialist.CColorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return color (        );
            } finally {
                _releaseReply ($in);
            }
  } // color

  public void color (com.bbn.openmap.corba.CSpecialist.CColor newColor)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_color", true);
                com.bbn.openmap.corba.CSpecialist.CColorHelper.write ($out, newColor);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                color (newColor        );
            } finally {
                _releaseReply ($in);
            }
  } // color

  public com.bbn.openmap.corba.CSpecialist.CColor fillColor ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_fillColor", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.CColor $result = com.bbn.openmap.corba.CSpecialist.CColorHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return fillColor (        );
            } finally {
                _releaseReply ($in);
            }
  } // fillColor

  public void fillColor (com.bbn.openmap.corba.CSpecialist.CColor newFillColor)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_fillColor", true);
                com.bbn.openmap.corba.CSpecialist.CColorHelper.write ($out, newFillColor);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                fillColor (newFillColor        );
            } finally {
                _releaseReply ($in);
            }
  } // fillColor

  public short lineWidth ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_lineWidth", true);
                $in = _invoke ($out);
                short $result = $in.read_ushort ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return lineWidth (        );
            } finally {
                _releaseReply ($in);
            }
  } // lineWidth

  public void lineWidth (short newLineWidth)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_lineWidth", true);
                $out.write_ushort (newLineWidth);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                lineWidth (newLineWidth        );
            } finally {
                _releaseReply ($in);
            }
  } // lineWidth

  public com.bbn.openmap.corba.CSpecialist.CStipple stipple ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_stipple", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.CStipple $result = com.bbn.openmap.corba.CSpecialist.CStippleHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return stipple (        );
            } finally {
                _releaseReply ($in);
            }
  } // stipple

  public void stipple (com.bbn.openmap.corba.CSpecialist.CStipple newStipple)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_stipple", true);
                com.bbn.openmap.corba.CSpecialist.CStippleHelper.write ($out, newStipple);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                stipple (newStipple        );
            } finally {
                _releaseReply ($in);
            }
  } // stipple

  public com.bbn.openmap.corba.CSpecialist.CStipple fillStipple ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_fillStipple", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.CStipple $result = com.bbn.openmap.corba.CSpecialist.CStippleHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return fillStipple (        );
            } finally {
                _releaseReply ($in);
            }
  } // fillStipple

  public void fillStipple (com.bbn.openmap.corba.CSpecialist.CStipple newFillStipple)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_fillStipple", true);
                com.bbn.openmap.corba.CSpecialist.CStippleHelper.write ($out, newFillStipple);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                fillStipple (newFillStipple        );
            } finally {
                _releaseReply ($in);
            }
  } // fillStipple

  public com.bbn.openmap.corba.CSpecialist.GraphicPackage.DeclutterType dcType ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_get_dcType", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.GraphicPackage.DeclutterType $result = com.bbn.openmap.corba.CSpecialist.GraphicPackage.DeclutterTypeHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return dcType (        );
            } finally {
                _releaseReply ($in);
            }
  } // dcType

  public void dcType (com.bbn.openmap.corba.CSpecialist.GraphicPackage.DeclutterType newDcType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("_set_dcType", true);
                com.bbn.openmap.corba.CSpecialist.GraphicPackage.DeclutterTypeHelper.write ($out, newDcType);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                dcType (newDcType        );
            } finally {
                _releaseReply ($in);
            }
  } // dcType

  public com.bbn.openmap.corba.CSpecialist.GraphicPackage.EGraphic gfill ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("gfill", true);
                $in = _invoke ($out);
                com.bbn.openmap.corba.CSpecialist.GraphicPackage.EGraphic $result = com.bbn.openmap.corba.CSpecialist.GraphicPackage.EGraphicHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return gfill (        );
            } finally {
                _releaseReply ($in);
            }
  } // gfill

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CSpecialist/Rectangle:1.0", 
    "IDL:CSpecialist/Graphic:1.0"};

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
} // class _RectangleStub
