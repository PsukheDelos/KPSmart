// **********************************************************************
// 
// <copyright>
// 
//  BBN Technologies
//  10 Moulton Street
//  Cambridge, MA 02138
//  (617) 873-8000
// 
//  Copyright (C) BBNT Solutions LLC. All rights reserved.
// 
// </copyright>
// **********************************************************************
// 
// $Source:
// /cvs/distapps/openmap/src/openmap/com/bbn/openmap/dataAccess/asrp/GeoReferenceFile.java,v
// $
// $RCSfile: GeoReferenceFile.java,v $
// $Revision: 1.2 $
// $Date: 2004/10/14 18:05:40 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.dataAccess.asrp;

import java.io.IOException;

import kps.frontend.gui.map.dataAccess.iso8211.DDFModule;
import kps.frontend.gui.map.dataAccess.iso8211.DDFRecord;
import kps.frontend.gui.map.util.Debug;

public class GeoReferenceFile extends GeneralASRPFile {

    public final static String GEO_PARAMETERS = "GEP";

    public GeoReferenceFile(String fileName) throws IOException {
        DDFModule mod = load(fileName);

        if (mod != null) {
            DDFRecord record;
            while ((record = mod.readRecord()) != null) {
                loadField(record, GEO_PARAMETERS, 0);
            }
        }
    }

    public static void main(String[] argv) {
        Debug.init();

        if (argv.length < 1) {
            Debug.output("Usage: GeoReferenceFile filename");
        }

        try {
            GeoReferenceFile thf = new GeoReferenceFile(argv[0]);
            thf.dumpFields();
        } catch (IOException ioe) {
            Debug.error(ioe.getMessage());
        }
        System.exit(0);
    }
}