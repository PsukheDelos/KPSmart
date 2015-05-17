package kps.frontend.gui.map.image.wms;

import kps.frontend.gui.map.image.ImageFormatter;

/**
 * A wms request parameter object that contain FORMAT element
 */
interface FormatRequestParameter {

    public void setFormatter(ImageFormatter formatter);

    public ImageFormatter getFormatter();

}
