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
// /cvs/distapps/openmap/src/openmap/com/bbn/openmap/omGraphics/grid/OMGridGenerator.java,v
// $
// $RCSfile: OMGridGenerator.java,v $
// $Revision: 1.3 $
// $Date: 2005/12/22 18:46:21 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.omGraphics.grid;

import kps.frontend.gui.map.omGraphics.OMGraphic;
import kps.frontend.gui.map.omGraphics.OMGrid;
import kps.frontend.gui.map.proj.Projection;

public interface OMGridGenerator {

    public OMGraphic generate(OMGrid grid, Projection proj);

    public boolean needGenerateToRender();
}

