package com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider;


/**
* com/bbn/openmap/layer/rpf/corba/CRpfFrameProvider/LLPoint.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/rpf/corba/CorbaRpfFrameProvider.idl
* Tuesday, November 12, 2013 11:07:44 PM EST
*/

public final class LLPoint implements org.omg.CORBA.portable.IDLEntity
{
  public float lat = (float)0;
  public float lon = (float)0;

  public LLPoint ()
  {
  } // ctor

  public LLPoint (float _lat, float _lon)
  {
    lat = _lat;
    lon = _lon;
  } // ctor

} // class LLPoint
