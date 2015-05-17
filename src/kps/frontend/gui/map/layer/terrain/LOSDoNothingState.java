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
// /cvs/distapps/openmap/src/openmap/com/bbn/openmap/layer/terrain/LOSDoNothingState.java,v
// $
// $RCSfile: LOSDoNothingState.java,v $
// $Revision: 1.3 $
// $Date: 2004/10/14 18:06:05 $
// $Author: dietrick $
// 
// **********************************************************************

package kps.frontend.gui.map.layer.terrain;

import java.awt.event.MouseEvent;

import kps.frontend.gui.map.util.stateMachine.State;

class LOSDoNothingState extends State {

    protected LOSGenerator LOSTool;

    public LOSDoNothingState(LOSGenerator tool) {
        LOSTool = tool;
    }

    public boolean mousePressed(MouseEvent e) {
        LOSTool.setCenter(e);
        LOSTool.stateMachine.setState(LOSStateMachine.TOOL_DRAW);
        return true;
    }
}

