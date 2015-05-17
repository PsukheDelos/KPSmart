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
// $Source: /cvs/distapps/openmap/src/openmap/com/bbn/openmap/tools/drawing/OMDecoratedSplineLoader.java,v $
// $RCSfile: OMDecoratedSplineLoader.java,v $
// $Revision: 1.4 $
// $Date: 2004/10/14 18:06:26 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.tools.drawing;

import kps.frontend.gui.map.omGraphics.EditableOMDecoratedSpline;
import kps.frontend.gui.map.omGraphics.EditableOMGraphic;
import kps.frontend.gui.map.omGraphics.GraphicAttributes;
import kps.frontend.gui.map.omGraphics.OMDecoratedSpline;
import kps.frontend.gui.map.omGraphics.OMGraphic;
import kps.frontend.gui.map.omGraphics.OMSpline;

/**
 * OMDecoratedSplineLoader
 * 
 * @author Eric LEPICIER
 * @version 22 juil. 2002
 */
public class OMDecoratedSplineLoader extends AbstractToolLoader implements
        EditToolLoader {

    protected String graphicClassName = "kps.frontend.gui.map.omGraphics.OMDecoratedSpline";

    public OMDecoratedSplineLoader() {
        init();
    }

    public void init() {
        EditClassWrapper ecw = new EditClassWrapper(graphicClassName, "kps.frontend.gui.map.omGraphics.EditableOMDecoratedSpline", "editablespline.gif", i18n.get(OMDecoratedSplineLoader.class,
                "omdecoratedspline",
                "Decorated Splines"));

        addEditClassWrapper(ecw);
    }

    /**
     * Give the classname of a graphic to create, returning an
     * EditableOMGraphic for that graphic. The GraphicAttributes
     * object lets you set some of the initial parameters of the
     * spline, like spline type and rendertype.
     */
    public EditableOMGraphic getEditableGraphic(String classname,
                                                GraphicAttributes ga) {
        if (classname.intern() == graphicClassName) {
            return new EditableOMDecoratedSpline(ga);
        }

        return null;
    }

    /**
     * Give an OMGraphic to the EditToolLoader, which will create an
     * EditableOMGraphic for it.
     */
    public EditableOMGraphic getEditableGraphic(OMGraphic graphic) {
        if (graphic instanceof OMDecoratedSpline) {
            return new EditableOMDecoratedSpline((OMSpline) graphic);
        }
        return null;
    }

}

