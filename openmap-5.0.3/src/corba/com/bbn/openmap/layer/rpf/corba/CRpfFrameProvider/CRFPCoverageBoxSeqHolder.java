package com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider;


/**
* com/bbn/openmap/layer/rpf/corba/CRpfFrameProvider/CRFPCoverageBoxSeqHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./src/corba/com/bbn/openmap/layer/rpf/corba/CorbaRpfFrameProvider.idl
* Tuesday, November 12, 2013 11:07:44 PM EST
*/

public final class CRFPCoverageBoxSeqHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox value[] = null;

  public CRFPCoverageBoxSeqHolder ()
  {
  }

  public CRFPCoverageBoxSeqHolder (com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxSeqHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxSeqHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxSeqHelper.type ();
  }

}
