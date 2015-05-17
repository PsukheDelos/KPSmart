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
// $Source: /cvs/distapps/openmap/src/openmap/com/bbn/openmap/tools/drawing/OMCircleLoader.java,v $
// $RCSfile: OMCircleLoader.java,v $
// $Revision: 1.4 $
// $Date: 2004/10/14 18:06:26 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.tools.drawing;

import kps.frontend.gui.map.omGraphics.EditableOMCircle;
import kps.frontend.gui.map.omGraphics.EditableOMGraphic;
import kps.frontend.gui.map.omGraphics.EditableOMRangeRings;
import kps.frontend.gui.map.omGraphics.GraphicAttributes;
import kps.frontend.gui.map.omGraphics.OMCircle;
import kps.frontend.gui.map.omGraphics.OMGraphic;
import kps.frontend.gui.map.omGraphics.OMRangeRings;

/**
 * Loader that knows how to create/edit OMCircle and OMRangeRings
 * objects.
 */
public class OMCircleLoader extends AbstractToolLoader implements
        EditToolLoader {

    protected String circleClassName = "kps.frontend.gui.map.omGraphics.OMCircle";
    protected String rangeRingsClassName = "kps.frontend.gui.map.omGraphics.OMRangeRings";

    public OMCircleLoader() {
        init();
    }

    public void init() {
        EditClassWrapper ecw = new EditClassWrapper(circleClassName, "kps.frontend.gui.map.omGraphics.EditableOMCircle", "editablecircle.gif", i18n.get(OMCircleLoader.class,
                "omcircle.circle",
                "Circle"));
        addEditClassWrapper(ecw);

        ecw = new EditClassWrapper(rangeRingsClassName, "kps.frontend.gui.map.omGraphics.EditableOMRangeRings", "editablerangering.gif", i18n.get(OMCircleLoader.class,
                "omcircle.rings",
                "Range Rings"));
        addEditClassWrapper(ecw);
    }

    /**
     * Give the classname of a graphic to create, returning an
     * EditableOMGraphic for that graphic. The GraphicAttributes
     * object lets you set some of the initial parameters of the
     * circle, like circle type and rendertype.
     */
    public EditableOMGraphic getEditableGraphic(String classname,
                                                GraphicAttributes ga) {
        String name = classname.intern();
        if (name == circleClassName) {
            return new EditableOMCircle(ga);
        }
        if (name == rangeRingsClassName) {
            return new EditableOMRangeRings(ga);
        }
        return null;
    }

    /**
     * Give an OMGraphic to the EditToolLoader, which will create an
     * EditableOMGraphic for it.
     */
    public EditableOMGraphic getEditableGraphic(OMGraphic graphic) {
        // Range rings have to go first, they subclass Circles.
        if (graphic instanceof OMRangeRings) {
            return new EditableOMRangeRings((OMRangeRings) graphic);
        }
        if (graphic instanceof OMCircle) {
            return new EditableOMCircle((OMCircle) graphic);
        }
        return null;
    }
}