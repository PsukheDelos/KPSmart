/* **********************************************************************
 * 
 *    Use, duplication, or disclosure by the Government is subject to
 *           restricted rights as set forth in the DFARS.
 *  
 *                         BBNT Solutions LLC
 *                             A Part of 
 *                  Verizon      
 *                          10 Moulton Street
 *                         Cambridge, MA 02138
 *                          (617) 873-3000
 *
 *    Copyright (C) 2002 by BBNT Solutions, LLC
 *                 All Rights Reserved.
 * ********************************************************************** */

package kps.frontend.gui.map.layer.beanbox;

import java.beans.PropertyEditorSupport;

/**
 * Custom property editor for the layout class property of a
 * {@link kps.frontend.gui.map.layer.beanbox.SimpleBeanContainer}.
 */
public class LayoutClassEditor extends PropertyEditorSupport {

    /**
     * returns a String array containing the class names of two layout
     * managers: viz the NullLayout and the WallFormationLayout
     */
    public String[] getTags() {
        String result[] = { "kps.frontend.gui.map.layer.beanbox.NullLayout",
                "kps.frontend.gui.map.layer.beanbox.WallFormationLayout" };
        return result;
    }
}
