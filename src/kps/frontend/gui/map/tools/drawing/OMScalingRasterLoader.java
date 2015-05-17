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
// $Source: /cvs/distapps/openmap/src/openmap/com/bbn/openmap/tools/drawing/OMScalingRasterLoader.java,v $
// $RCSfile: OMScalingRasterLoader.java,v $
// $Revision: 1.2 $
// $Date: 2004/10/14 18:06:27 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.tools.drawing;

import kps.frontend.gui.map.omGraphics.EditableOMGraphic;
import kps.frontend.gui.map.omGraphics.EditableOMScalingRaster;
import kps.frontend.gui.map.omGraphics.GraphicAttributes;
import kps.frontend.gui.map.omGraphics.OMGraphic;
import kps.frontend.gui.map.omGraphics.OMScalingRaster;

/**
 * Loader that knows how to create/edit OMRect objects.
 */
public class OMScalingRasterLoader extends AbstractToolLoader implements
        EditToolLoader {

    protected String graphicClassName = "kps.frontend.gui.map.omGraphics.OMScalingRaster";

    public OMScalingRasterLoader() {
        init();
    }

    public void init() {
        EditClassWrapper ecw = new EditClassWrapper(graphicClassName, "kps.frontend.gui.map.omGraphics.EditableOMScalingRaster", "editablescalingraster.gif", i18n.get(OMScalingRasterLoader.class,
                "omscalingraster",
                "Raster"));
        addEditClassWrapper(ecw);
    }

    /**
     * Give the classname of a graphic to create, returning an
     * EditableOMGraphic for that graphic. The GraphicAttributes
     * object lets you set some of the initial parameters of the rect,
     * like rect type and rendertype.
     */
    public EditableOMGraphic getEditableGraphic(String classname,
                                                GraphicAttributes ga) {
        if (classname.intern() == graphicClassName) {
            return new EditableOMScalingRaster(ga);
        }
        return null;
    }

    /**
     * Give an OMGraphic to the EditToolLoader, which will create an
     * EditableOMGraphic for it.
     */
    public EditableOMGraphic getEditableGraphic(OMGraphic graphic) {
        if (graphic instanceof OMScalingRaster) {
            return new EditableOMScalingRaster((OMScalingRaster) graphic);
        }
        return null;
    }
}