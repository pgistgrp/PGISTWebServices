package org.pgist.datamodel;

import com.esri.sde.sdk.geom.Geometry;

public class TProjectSpatial
    extends TransportationProject {
  private String geometry;

  public void setGeometry(String geometry) {
    this.geometry = geometry;
  }

  public String getGeometry() {
    return geometry;
  }
}
