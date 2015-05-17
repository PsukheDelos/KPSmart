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
// /cvs/distapps/openmap/src/openmap/com/bbn/openmap/omGraphics/grid/SinkGenerator.java,v
// $
// $RCSfile: SinkGenerator.java,v $
// $Revision: 1.2 $
// $Date: 2004/10/14 18:06:18 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.omGraphics.grid;

import kps.frontend.gui.map.omGraphics.OMGraphic;
import kps.frontend.gui.map.omGraphics.OMGrid;
import kps.frontend.gui.map.omGraphics.SinkGraphic;
import kps.frontend.gui.map.proj.Projection;

public class SinkGenerator implements OMGridGenerator {

    public OMGraphic generate(OMGrid grid, Projection proj) {
        return SinkGraphic.getSharedInstance();
    }

    public boolean needGenerateToRender() {
        return false;
    }
}

