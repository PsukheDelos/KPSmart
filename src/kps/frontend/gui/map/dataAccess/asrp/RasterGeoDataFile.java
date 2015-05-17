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
// /cvs/distapps/openmap/src/openmap/com/bbn/openmap/dataAccess/asrp/RasterGeoDataFile.java,v
// $
// $RCSfile: RasterGeoDataFile.java,v $
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

public class RasterGeoDataFile extends GeneralASRPFile {

    public final static String PADDING = "PAD";
    public final static String PIXEL = "SCN";

    public RasterGeoDataFile(String fileName) throws IOException {
        DDFModule mod = load(fileName);

        if (mod != null) {
            DDFRecord record;
            while ((record = mod.readRecord()) != null) {
                loadField(record, PADDING, 0);
                loadField(record, PIXEL, 0);
            }
        }
    }

    public static void main(String[] argv) {
        Debug.init();

        if (argv.length < 1) {
            Debug.output("Usage: RasterGeoDataFile filename");
        }

        try {
            RasterGeoDataFile thf = new RasterGeoDataFile(argv[0]);
            Debug.output(thf.getField(PIXEL).toString());
        } catch (IOException ioe) {
            Debug.error(ioe.getMessage());
        }
        System.exit(0);
    }
}