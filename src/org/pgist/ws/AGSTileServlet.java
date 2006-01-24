/*
 * AGSTileServlet.java
 *
 * Created on January 12, 2006, 12:53 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.pgist.ws;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.esri.arcgis.server.IServerConnection;
import com.esri.arcgis.server.ServerConnection;
import com.esri.arcgis.server.IServerObjectManager;
import com.esri.arcgis.server.IServerContext;

import com.esri.arcgis.carto.IMapServer;
import com.esri.arcgis.carto.IMapServerProxy;
import com.esri.arcgis.carto.IMapDescription;
import com.esri.arcgis.carto.IMapServerObjects;
import com.esri.arcgis.carto.IMapServerObjectsProxy;
import com.esri.arcgis.carto.IMap;
import com.esri.arcgis.carto.IMapProxy;
import com.esri.arcgis.carto.IMapDescriptionProxy;
import com.esri.arcgis.carto.MapDescription;
import com.esri.arcgis.carto.IImageDescription;
import com.esri.arcgis.carto.IImageDescriptionProxy;
import com.esri.arcgis.carto.ImageDescription;
import com.esri.arcgis.carto.IImageType;
import com.esri.arcgis.carto.IImageTypeProxy;
import com.esri.arcgis.carto.ImageType;
import com.esri.arcgis.carto.IImageDisplay;
import com.esri.arcgis.carto.IImageDisplayProxy;
import com.esri.arcgis.carto.ImageDisplay;
import com.esri.arcgis.carto.IMapImage;
import com.esri.arcgis.carto.IMapImageProxy;
import com.esri.arcgis.carto.MapImage;
import com.esri.arcgis.carto.IMapExtent;
import com.esri.arcgis.carto.IMapExtentProxy;
import com.esri.arcgis.carto.MapExtent;
import com.esri.arcgis.carto.IMapArea;
import com.esri.arcgis.carto.IMapAreaProxy;
import com.esri.arcgis.carto.IMapServerIdentifyResults;
import com.esri.arcgis.carto.esriIdentifyOption;
import com.esri.arcgis.carto.MapServerIdentifyResults;
import com.esri.arcgis.carto.esriImageFormat;
import com.esri.arcgis.carto.esriImageReturnType;
import com.esri.arcgis.carto.ILegend;
import com.esri.arcgis.carto.ILegendProxy;
import com.esri.arcgis.carto.Legend;
import com.esri.arcgis.carto.IMapServerLayout;
import com.esri.arcgis.carto.IMapServerLayoutProxy;
import com.esri.arcgis.carto.IImageResult;
import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.carto.IFeatureLayer;
import com.esri.arcgis.carto.IFeatureLayerProxy;
import com.esri.arcgis.carto.FeatureLayer;

import com.esri.arcgis.system.IXMLSerializeProxy;
import com.esri.arcgis.system.LongArray;
import com.esri.arcgis.system.ILongArray;
import com.esri.arcgis.system.ILongArrayProxy;

import com.esri.arcgis.geometry.Point;
import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.geometry.IPointProxy;
import com.esri.arcgis.geometry.IPointCollection;
import com.esri.arcgis.geometry.IEnvelope;
import com.esri.arcgis.geometry.IEnvelopeProxy;
import com.esri.arcgis.geometry.Envelope;
import com.esri.arcgis.geometry.ISpatialReference;
import com.esri.arcgis.geometry.SpatialReferenceEnvironment;
import com.esri.arcgis.geometry.ISpatialReferenceFactory;
import com.esri.arcgis.geometry.ISpatialReferenceFactoryProxy;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.esriSRGeoCSType;
     
import org.pgist.util.Properties;
import com.esri.arcgis.geodatabase.IFIDSetProxy;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.IQueryFilterProxy;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFIDSet;
import com.esri.arcgis.geodatabase.IClassProxy;
import com.esri.arcgis.geodatabase.FIDSet;
import com.esri.arcgis.geodatabase.IClass;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.display.IColor;
import org.hibernate.Session;

import org.pgist.datamodel.MICAScenario;
import org.pgist.util.HibernateUtil;
import org.hibernate.Query;
import com.esri.arcgis.carto.IGeoFeatureLayer;
import com.esri.arcgis.carto.IGeoFeatureLayerProxy;
import com.esri.arcgis.display.IHsvColor;
import com.esri.arcgis.display.HsvColor;
import com.esri.arcgis.carto.ISimpleRenderer;
import com.esri.arcgis.carto.SimpleRenderer;
import com.esri.arcgis.display.ISimpleLineSymbol;
import com.esri.arcgis.display.ISimpleLineSymbolProxy;
import com.esri.arcgis.display.SimpleLineSymbol;
import com.esri.arcgis.system.EngineInitializer;
import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.esriLicenseProductCode;
import com.esri.arcgis.system.esriLicenseExtensionCode;
import com.esri.arcgis.system.esriLicenseStatus;
import com.esri.arcgis.carto.ILayerDescription;
import com.esri.arcgis.carto.ILayerDescriptionProxy;
import com.esri.arcgis.display.IHsvColorProxy;
import com.esri.arcgis.display.HsvColor;
import com.esri.arcgis.display.IRgbColor;
import com.esri.arcgis.display.IRgbColorProxy;
import com.esri.arcgis.display.RgbColor;
import com.esri.arcgis.geodatabase.Workspace;
import com.esri.arcgis.geodatabase.IWorkspaceProxy;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.datasourcesGDB.SdeWorkspaceFactory;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.IWorkspaceFactoryProxy;
import com.esri.arcgis.geodatabase.IFeatureClassProxy;
import com.esri.arcgis.datasourcesfile.ShapefileWorkspaceFactory;
import com.esri.arcgis.geodatabase.IFeatureWorkspaceProxy;
import com.esri.arcgis.carto.LayerDescription;
import com.esri.arcgis.carto.ILayerProxy;
import com.esri.arcgis.display.ISymbol;
import com.esri.arcgis.display.ISymbolProxy;
import com.esri.arcgis.carto.IFeatureRendererProxy;
import com.esri.arcgis.carto.IFeatureRenderer;
import com.esri.arcgis.carto.ISimpleRendererProxy;
import org.pgist.datamodel.MICAProject;
import com.esri.arcgis.carto.IFeatureLayerDefinition;
import com.esri.arcgis.carto.IFeatureLayerDefinitionProxy;
/**
 * <p>Title: PGIST</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: University of Washington Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Guirong Zhou
 * @version 1.0
 */


public class AGSTileServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "image/png";

  private String m_host = "localhost";
  private String m_serverObjectName = null;
  private String m_userName = null;
  private String m_pwd = null;
  private String m_domain = null;
  private IServerContext m_context = null;
  private IMapServer m_mapServer = null;
  private IMapServerObjects m_mapServerObj = null;
  private IMapDescription m_mapDesc = null;
  private IImageDescription m_imageDesc = null;
  private IImageType m_imageType = null;
  private IImageDisplay m_imageDisplay = null;
  private String m_strMapDesc = null;
  private String m_strImageDesc = null;
  private IMap m_map = null;
  private IMapImage mapImage = null;
  
  private ArrayList m_colors =  new ArrayList();

  private ArrayList addedLayers = new ArrayList();

  //Initialize global variables
  public void init() throws ServletException {
    try {
      Hashtable hTable = Properties.getProp("ags2.properties");
      this.m_host = hTable.get("agsserver").toString();
      this.m_domain = hTable.get("agsdomain").toString();
      this.m_serverObjectName = hTable.get("serverobject").toString();
      this.m_userName = hTable.get("username").toString();
      this.m_pwd = hTable.get("password").toString();
      new com.esri.arcgis.system.ServerInitializer().initializeServer(m_domain,
          m_userName, m_pwd);
    }
    catch (Exception e) {}

  }


  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    HttpSession session = (HttpSession) request.getSession();

    //2. connect to map server
    try{
      connect();
    }catch (Exception e){System.out.println(e.getMessage());
    }
		
    // TODO 1. prepare parameters
    String tilepath = "D:\\GISDATA\\MapTileCache\\";

    float xmin, ymin, xmax, ymax;
    String x, y, z;
    int width, height;
    String image_name, image_type;
    byte[] return_image = new byte[1];

    String bbox = request.getParameter("BBOX");
    String[] coords = bbox.split(",");
    xmin = Float.parseFloat(coords[0]);
    ymin = Float.parseFloat(coords[1]);
    xmax = Float.parseFloat(coords[2]);
    ymax = Float.parseFloat(coords[3]);
    x = request.getParameter("X");
    y = request.getParameter("Y");
    z = request.getParameter("zoom");
    width = Integer.parseInt(request.getParameter("WIDTH"));
    height = Integer.parseInt(request.getParameter("HEIGHT"));

    if(request.getParameter("image_type") != null)
            image_type = request.getParameter("image_type");
    else
            image_type = "png";

    //2. make image name
    image_name = "lit1map__" + x + "_" + y + "_" + z + "." + image_type;
    System.out.println("--requested image name: " + image_name);

    //3. prepare image bytes
    File imagefile = new File(tilepath + image_name);
    if(imagefile.exists()){
            FileInputStream in = new FileInputStream(imagefile);
            return_image = new byte[ (int)imagefile.length() ];
            in.read( return_image );
            in.close();
    }else {
        try{
            //process map operations and write iamge
            try{
              if(session.getAttribute("imgDescp") == null)
                getMap(width, height);
              else{
                getMap(width, height, (String) session.getAttribute("mapDescp"),
                       (String) session.getAttribute("imgDescp"));
              }
              System.out.println("--tile bbox: " + xmin + "," + ymin + "," + xmax + "," + ymax);
              zoomToLatLong(xmin, ymin, xmax, ymax); //zoomToLatLong if necessary  zoomToMapExtent
              return_image = getImageMimeData();

              session.setAttribute("mapDescp",  m_context.saveObject( new IXMLSerializeProxy(this.m_mapDesc) ));
              session.setAttribute("imgDescp", m_context.saveObject( new IXMLSerializeProxy(this.m_imageDesc) ));
              destroy();
            }catch (Exception e){
              destroy();
              System.out.println(e.getMessage());
            }
            
            //cache the image:
            if(return_image.length > 1){
                    FileOutputStream fos = new FileOutputStream(tilepath + image_name);
                    fos.write(return_image, 0, return_image.length);
                    fos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //4. write back image
    response.setContentType("image/png");
    ServletOutputStream outServ = response.getOutputStream();
    outServ.write(return_image);
    outServ.flush();
    outServ.close();
  }

  //Clean up resources
  public void destroy() {
    try{
       m_context.removeAll();
       m_context.releaseContext();
       this.m_mapDesc = null;
       this.m_imageDesc = null;
       //com.linar.jintegra.Cleaner.releaseAll();
       m_context = null;
   } catch (Exception e){
   }
  }

  public void connect() throws Exception {
    IServerConnection connection = new ServerConnection();
    connection.connect(this.m_host);
    IServerObjectManager mgr = connection.getServerObjectManager();
    m_context = mgr.createServerContext(m_serverObjectName, "MapServer");
    m_mapServer = new IMapServerProxy(m_context.getServerObject());
    m_mapServerObj = new IMapServerObjectsProxy(m_context.getServerObject());
    if (m_mapServer.getDefaultMapName() != null) {
      m_map = new IMapProxy(m_mapServerObj.getMap(m_mapServer.getDefaultMapName()));
    }
    else {
      throw new Exception("No Default Map available !!! ");
    }
  }

  public void getMap(int w, int h) throws Exception {
    if (m_mapServer.getDefaultMapName() != null) {
      m_mapDesc = (m_mapServer.getServerInfo(m_mapServer.getDefaultMapName())).
          getDefaultMapDescription();

      m_imageType = new IImageTypeProxy(m_context.createObject(ImageType.
          getClsid()));
      m_imageType.setFormat(esriImageFormat.esriImagePNG);
      m_imageType.setReturnType(esriImageReturnType.esriImageReturnMimeData);

      m_imageDisplay = new IImageDisplayProxy(m_context.createObject(
          ImageDisplay.getClsid()));
      m_imageDisplay.setDeviceResolution(96D);
      m_imageDisplay.setWidth(w);
      m_imageDisplay.setHeight(h);

      m_imageDesc = new IImageDescriptionProxy(m_context.createObject(
          ImageDescription.getClsid()));
      m_imageDesc.setDisplay(m_imageDisplay);
      m_imageDesc.setType(m_imageType);
    }
    else {
      throw new Exception("No Default Map available !!! ");
    }
  }

  /**
   * Loads the map based on Map Description and Image Description passed
   * @param strMapDesc value of Map Description
   * @param strImageDesc value of Image Description
   * @throws Exception when both Map and Image Description are not able to load
   */
  public void getMap(int w, int h, String strMapDesc, String strImageDesc) throws Exception {
    m_mapDesc = new IMapDescriptionProxy(m_context.loadObject(strMapDesc));
    m_imageDesc = new IImageDescriptionProxy(m_context.loadObject(strImageDesc));

    m_imageDesc.getDisplay().setWidth(w);
    m_imageDesc.getDisplay().setHeight(h);
  }

  public byte[] getImageMimeData() throws Exception {
    if (m_imageDesc == null) {
      throw new Exception("ImageDesc null..");
    }

    m_imageDesc.getType().setReturnType(esriImageReturnType.esriImageReturnMimeData);
    mapImage = m_mapServer.exportMapImage(m_mapDesc, m_imageDesc);

    byte[] imageMimeData = mapImage.getMimeData();
    if (imageMimeData == null) {
      System.out.println("ImageMimeData is : null");
      return null;
    }
    return imageMimeData;
  }

  public byte[] getLegendMimeData() throws Exception {
    if (m_imageDesc == null) {
      throw new Exception("ImageDesc null..");
    }

    IMapServerLayout layout = new IMapServerLayoutProxy(m_mapServer);
    ILegend legend = new ILegendProxy(m_context.createObject(Legend.getClsid()));
    legend.setAutoReorder(true);
    legend.setAutoVisibility(true);
    legend.setAutoAdd(true);
    legend.setMapByRef(m_map);
    legend.setTitle("LIT Scenarios");


    IImageDisplay imgdisp = new IImageDisplayProxy(m_context.createObject(
        ImageDisplay.getClsid()));
    imgdisp.setDeviceResolution(96D);
    imgdisp.setWidth(200);
    imgdisp.setHeight(0);

    IImageDescription imgdsc = new IImageDescriptionProxy(m_context.createObject(
        ImageDescription.getClsid()));
    imgdsc.setDisplay(imgdisp);
    imgdsc.setType(m_imageType);

    System.out.println("--making ledgnd");
    IImageResult imageResult = layout.exportLegend(legend, m_mapDesc, null,null,
        imgdsc);

    byte[] legendMimeData = imageResult.getMimeData();
    System.out.println("--legend size: " + legendMimeData.length);
    if (legendMimeData == null) {
      System.out.println("LegendMimeData is : null");
      return null;
    }
    return legendMimeData;
  }

  public void zoomToMapExtent(double xMin, double yMin, double xMax, double yMax) throws Exception {
    IEnvelope env = m_mapDesc.getMapArea().getExtent();
    env.setXMin(xMin); env.setYMin(yMin);  env.setXMax(xMax); env.setYMax(yMax);

    IMapExtent mapExtent = new IMapExtentProxy(m_context.createObject(MapExtent.
        getClsid()));
    mapExtent.setExtent(env);
    IMapArea mapArea = new IMapAreaProxy(mapExtent);
    m_mapDesc.setMapArea(mapArea);
  }
  
  public void zoomToLatLong(double longmin, double latmin, double longmax, double latmax){
      try{
          IPoint p1 = new IPointProxy( m_context.createObject(Point.getClsid()) );
          IPoint p2 = new IPointProxy( m_context.createObject(Point.getClsid()) );
          p1.setX(longmin); p1.setY(latmin);
          p2.setX(longmax); p2.setY(latmax);
          
          ISpatialReference spatialRef;
          ISpatialReferenceFactory srf = 
                  new ISpatialReferenceFactoryProxy(m_context.createObject(SpatialReferenceEnvironment.getClsid()));
          
          spatialRef = srf.createGeographicCoordinateSystem(esriSRGeoCSType.esriSRGeoCS_WGS1984);
          
          System.out.println("--p1.x before projection: " + p1.getX());
          p1.setSpatialReferenceByRef(spatialRef);
          p1.project(m_map.getSpatialReference());
          p2.setSpatialReferenceByRef(spatialRef);
          p2.project(m_map.getSpatialReference());          
          System.out.println("--p1.x after projection: " + p1.getX());
          
          zoomToMapExtent(p1.getX(), p1.getY(), p2.getX(), p2.getY());
          
      }catch (Exception e){      }
      
  }

}
