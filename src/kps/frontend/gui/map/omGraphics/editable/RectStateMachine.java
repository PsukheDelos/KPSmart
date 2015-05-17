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
// /cvs/distapps/openmap/src/openmap/com/bbn/openmap/omGraphics/editable/RectStateMachine.java,v
// $
// $RCSfile: RectStateMachine.java,v $
// $Revision: 1.3 $
// $Date: 2004/10/14 18:06:16 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.omGraphics.editable;

import kps.frontend.gui.map.omGraphics.EditableOMRect;
import kps.frontend.gui.map.util.Debug;
import kps.frontend.gui.map.util.stateMachine.State;
public class RectStateMachine
        extends EOMGStateMachine {

    public RectStateMachine(EditableOMRect rect) {
        super(rect);
    }

    protected State[] init() {
        State[] states = super.init();
        Debug.message("eomc", "RectStateMachine.init()");

        // These are the only two states that need something special
        // to happen.
        states[GRAPHIC_UNDEFINED] = new RectUndefinedState((EditableOMRect) graphic);
        states[GRAPHIC_SELECTED] = new RectSelectedState((EditableOMRect) graphic);
        states[GRAPHIC_SETOFFSET] = new RectSetOffsetState((EditableOMRect) graphic);
        states[GRAPHIC_EDIT] = new GraphicUndefinedEditState(graphic);
        return states;
    }

    protected void setInitialEdit() {
        setEdit();
        if (getState(GRAPHIC_EDIT) instanceof GraphicUndefinedEditState) {
            ((GraphicUndefinedEditState) getState(GRAPHIC_EDIT)).needAnotherPoint = true;
        }
    }
}