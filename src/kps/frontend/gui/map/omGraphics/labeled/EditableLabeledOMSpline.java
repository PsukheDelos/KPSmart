package kps.frontend.gui.map.omGraphics.labeled;

import kps.frontend.gui.map.omGraphics.EditableOMSpline;
import kps.frontend.gui.map.omGraphics.GraphicAttributes;
import kps.frontend.gui.map.omGraphics.OMGraphic;
import kps.frontend.gui.map.omGraphics.OMSpline;

/**
 * The EditableLabeledOMSpline encompasses an LabeledOMSpline,
 * providing methods for modifying or creating it. Nothing created,
 * code adapted from EditableLabeledOMPoly We just need to edit the
 * generating points as an OMSpline
 * 
 * @author Eric LEPICIER
 * @version 22 juil. 2002
 */
public class EditableLabeledOMSpline extends EditableOMSpline {

    /**
     * Create the EditableLabeledOMSpline, setting the state machine
     * to create the poly off of the gestures.
     * 
     * @see kps.frontend.gui.map.omGraphics.EditableOMPoly#EditableOMPoly()
     */
    public EditableLabeledOMSpline() {
        super();
    }

    /**
     * Create an EditableLabeledOMSpline with the polyType and
     * renderType parameters in the GraphicAttributes object.
     * 
     * @param ga
     * @see kps.frontend.gui.map.omGraphics.EditableOMPoly#EditableOMPoly(GraphicAttributes)
     */
    public EditableLabeledOMSpline(GraphicAttributes ga) {
        super(ga);
    }

    /**
     * Create the EditableLabeledOMSpline with a LabeledOMSpline
     * already defined, ready for editing.
     * 
     * @param omls LabeledOMSpline that should be edited.
     * @see kps.frontend.gui.map.omGraphics.EditableOMPoly#EditableOMPoly(GraphicAttributes)
     */
    public EditableLabeledOMSpline(LabeledOMSpline omls) {
        super(omls);
    }

    /**
     * Extendable method to create specific subclasses of OMSplines.
     * 
     * @see kps.frontend.gui.map.omGraphics.EditableOMPoly#createGraphic(int,
     *      int)
     */
    public OMGraphic createGraphic(int renderType, int lineType) {
        OMGraphic g = null;
        switch (renderType) {
        case (OMGraphic.RENDERTYPE_LATLON):
            g = new LabeledOMSpline(new double[0], OMGraphic.RADIANS, lineType);
            break;
        case (OMGraphic.RENDERTYPE_OFFSET):
            g = new LabeledOMSpline(90f, -180f, new int[0], OMSpline.COORDMODE_ORIGIN);
            break;
        default:
            g = new LabeledOMSpline(new int[0]);
        }
        ((LabeledOMSpline) g).setDoShapes(true);
        return g;
    }

    public java.net.URL getImageURL(String imageName) {
        try {
            return Class.forName("kps.frontend.gui.map.omGraphics.EditableOMPoly")
                    .getResource(imageName);
        } catch (ClassNotFoundException cnfe) {
        }
        return null;
    }
}