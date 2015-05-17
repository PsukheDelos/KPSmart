package kps.frontend.gui.map.image.wms;

abstract class WmsRequestParameters {

    private Version version;

    public void setVersion(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

}
