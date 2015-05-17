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
// $Source: /cvs/distapps/openmap/src/openmap/com/bbn/openmap/tools/drawing/OMSplineLoader.java,v $
// $RCSfile: OMSplineLoader.java,v $
// $Revision: 1.4 $
// $Date: 2004/10/14 18:06:27 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.tools.drawing;

import kps.frontend.gui.map.omGraphics.EditableOMGraphic;
import kps.frontend.gui.map.omGraphics.EditableOMSpline;
import kps.frontend.gui.map.omGraphics.GraphicAttributes;
import kps.frontend.gui.map.omGraphics.OMGraphic;
import kps.frontend.gui.map.omGraphics.OMSpline;
import kps.frontend.gui.map.omGraphics.labeled.EditableLabeledOMSpline;

/**
 * OMSplineLoader
 * 
 * @author Eric LEPICIER
 * @version 22 juil. 2002
 */
public class OMSplineLoader extends AbstractToolLoader implements
        EditToolLoader {

    protected String graphicClassName = "kps.frontend.gui.map.omGraphics.OMSpline";
    protected String labeledClassName = "kps.frontend.gui.map.omGraphics.labeled.LabeledOMSpline";

    public OMSplineLoader() {
        init();
    }

    public void init() {
        EditClassWrapper ecw = new EditClassWrapper(graphicClassName, "kps.frontend.gui.map.omGraphics.EditableOMSpline", "editablespline.gif", i18n.get(OMSplineLoader.class,
                "omspline",
                "Splines"));
        addEditClassWrapper(ecw);

        // A class wrapper isn't added here for the LabeledOMSpline
        // because currently they are only created programmatically.
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
            return new EditableOMSpline(ga);
        }
        if (classname.intern() == labeledClassName) {
            return new EditableLabeledOMSpline(ga);
        }
        return null;
    }

    /**
     * Give an OMGraphic to the EditToolLoader, which will create an
     * EditableOMGraphic for it.
     */
    public EditableOMGraphic getEditableGraphic(OMGraphic graphic) {
        if (graphic instanceof OMSpline) {
            return new EditableOMSpline((OMSpline) graphic);
        }
        return null;
    }
}