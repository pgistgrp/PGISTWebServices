package org.pgist.gis;

import com.esri.sde.sdk.client.SeLayer;
import com.esri.sde.sdk.client.SeRow;
import java.util.HashMap;
import com.esri.sde.sdk.client.SeConnection;
import org.pgist.util.Properties;
import java.util.Vector;
import com.esri.sde.sdk.client.SeSqlConstruct;
import com.esri.sde.sdk.client.SeShape;
import java.util.Hashtable;
import com.esri.sde.sdk.client.SeQuery;

public class SDEFetch {

  /**
   * getGeometry
   */
  public static HashMap getGeometry(String layerName, String iDColumn, String whereClause) {
    HashMap projects = new HashMap();

    SeConnection sdeconn = null;
    try {
      Hashtable hTable = Properties.getProp("sdeprop.properties");
      String sdeServer = hTable.get("sdeserver").toString();
      int sdeInstance = Integer.parseInt(hTable.get("sdeinstance").toString());
      String sdeDb = hTable.get("sdedb").toString();
      String userName = hTable.get("username").toString();
      String userPwd = hTable.get("password").toString();

      sdeconn = new SeConnection(sdeServer, sdeInstance, sdeDb, userName, userPwd);
    } catch (Exception e) {}

    try {
      Vector layerList = sdeconn.getLayers();
      SeLayer theLayer = null;

      for (int i = 0; i < layerList.size(); i++) {
        theLayer = (SeLayer) layerList.elementAt(i);
        if (theLayer.getName().compareToIgnoreCase( layerName ) == 0)break;
      }

      System.out.println("Use layer: " + theLayer.getName());

      if (theLayer != null) { //get feature from a layer
        SeSqlConstruct sqlConstruct = new SeSqlConstruct(theLayer.getName(), whereClause);
        String[] cols = new String[2];
        cols[0] = theLayer.getSpatialColumn();   // "PROJTYPE";
        cols[1] = iDColumn;

        SeQuery query = new SeQuery(sdeconn, cols, sqlConstruct);
        query.prepareQuery();
        query.execute();

        //write all projects infromation in HTML format
        System.out.println("//========SDE query finished.");
        SeRow row = query.fetch();
        if(row == null)System.out.println("===========empty rows...");
        SeShape shp = null;
        while (row != null) {
          shp = row.getShape(0);
          System.out.println("--process project " + row.getInteger(1));
          projects.put( ("" + row.getInteger(1)), shp.getAllCoords());

          /*double[][][] coords = shp.getAllCoords();
          for(int i=0; i<coords.length; i++)
            for(int j=0; j<coords[i].length; j++)
              for(int k=0; k<coords[i][j].length; k++){
                System.out.println(coords[i][j][k]);
              }
           */

          row = query.fetch();
        }


        query.close();
      }

      sdeconn.close();

      return projects;
    }
    catch (Exception e) {return null;}

  }
}
