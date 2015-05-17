/* 
 * <copyright>
 *  Copyright 2010 BBN Technologies
 * </copyright>
 */
package kps.frontend.gui.map.layer.vpf;

import java.util.List;

import kps.frontend.gui.map.omGraphics.OMGraphic;
import kps.frontend.gui.map.omGraphics.OMGraphicConstants;

/**
 * A feature info handler that displays attribute information as an html
 * formatted tooltip.
 * 
 * @author dietrick
 */
public class TTFeatureInfoHandler
      implements VPFFeatureInfoHandler {

   /*
    * (non-Javadoc)
    * 
    * @see
    * kps.frontend.gui.map.layer.vpf.VPFFeatureInfoHandler#updateInfoForOMGraphic
    * (kps.frontend.gui.map.omGraphics.OMGraphic,
    * kps.frontend.gui.map.layer.vpf.FeatureClassInfo, java.util.List)
    */
   public void updateInfoForOMGraphic(OMGraphic omg, FeatureClassInfo fci, List<Object> fcirow) {


      DcwColumnInfo[] colInfo = fci.getColumnInfo();
      int columnCount = colInfo.length;
      StringBuffer sBuf = new StringBuffer("<html><body>");
      for (int i = 0; i < columnCount; i++) {
         sBuf.append("<b>" + colInfo[i].getColumnDescription() + ":</b> " + fcirow.get(i).toString() + "<br>");
      }
      sBuf.append("</body></html>");

      omg.putAttribute(OMGraphicConstants.TOOLTIP, sBuf.toString());
   }

   public boolean isHighlightable(OMGraphic omg) {
      return true;
   }

   public boolean shouldPaintHighlight(OMGraphic omg) {
      return false;
   }

}
