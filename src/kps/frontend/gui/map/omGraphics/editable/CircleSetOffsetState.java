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
// /cvs/distapps/openmap/src/openmap/com/bbn/openmap/omGraphics/editable/CircleSetOffsetState.java,v
// $
// $RCSfile: CircleSetOffsetState.java,v $
// $Revision: 1.4 $
// $Date: 2005/08/10 22:27:17 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.omGraphics.editable;

import java.awt.event.MouseEvent;

import kps.frontend.gui.map.omGraphics.EditableOMCircle;
import kps.frontend.gui.map.omGraphics.OffsetGrabPoint;
import kps.frontend.gui.map.omGraphics.event.EOMGEvent;

public class CircleSetOffsetState extends GraphicSetOffsetState {

    public CircleSetOffsetState(EditableOMCircle eomc) {
        super(eomc);
    }

    protected void setGrabPoint(MouseEvent e) {
        OffsetGrabPoint ogb = (OffsetGrabPoint) graphic.getGrabPoint(EditableOMCircle.OFFSET_POINT_INDEX);
        ogb.set(e.getX(), e.getY());
        ogb.updateOffsets();

        graphic.setMovingPoint(graphic.getGrabPoint(EditableOMCircle.OFFSET_POINT_INDEX));
        graphic.redraw(e);
        graphic.fireEvent(EOMGCursors.PUTNODE,
                i18n.get(CircleSetOffsetState.class,
                        "Click_to_place_offset_point_for_circle.",
                        "Click to place offset point for circle."), EOMGEvent.EOMG_UNCHANGED);
    }
}
