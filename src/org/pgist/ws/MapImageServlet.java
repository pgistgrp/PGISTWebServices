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

import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.geometry.IPointCollection;
import com.esri.arcgis.geometry.IEnvelope;
import com.esri.arcgis.geometry.IEnvelopeProxy;
import com.esri.arcgis.geometry.Envelope;
import com.esri.arcgis.geometry.Point;
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


public class MapImageServlet
    extends HttpServlet {
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
      Hashtable hTable = Properties.getProp("ags.properties");
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

    //1. parse request
    //mapimage?so=USA&w=500&h=400&x0=&y0=&x1=&y1=
    int imageW, imageH;
    if( request.getParameter("w") != null )
      imageW = Integer.parseInt(request.getParameter("w"));
    else
      imageW = 640;
    if( request.getParameter("h") != null )
      imageH = Integer.parseInt(request.getParameter("h"));
    else
      imageH = 480;

    //2. connect to map server
    try{
      System.out.println("==now try to connect");
      connect();
    }catch (Exception e){System.out.println(e.getMessage());
    }

    //3. process map operations and write iamge
    try{
      if(session.getAttribute("imgDescp") == null)
        getMap(imageW, imageH);
      else{
        getMap(imageW, imageH, (String) session.getAttribute("mapDescp"),
               (String) session.getAttribute("imgDescp"));
      }

      String strPrjLst = request.getParameter("pl");
      if( strPrjLst != null ){
        selectFeaturesBySql("ID IN (" + strPrjLst + ")");
      }
      
      //get colors
      String[] colors = null;
      String c = request.getParameter("colors");
      int icolor;
      IRgbColor rgbcolor = null;
      if(c != null){
          colors = c.split(",");
          for(int i=0; i<colors.length; i++){
              rgbcolor = new IRgbColorProxy(m_context.createObject(RgbColor.getClsid()));
              icolor = Integer.parseInt(colors[i].substring(0,2), 16);
              rgbcolor.setRed(icolor);
              icolor = Integer.parseInt(colors[i].substring(2,4), 16);
              rgbcolor.setGreen(icolor);
              icolor = Integer.parseInt(colors[i].substring(4,6), 16);
              rgbcolor.setBlue(icolor);
              
              m_colors.add( rgbcolor );
              System.out.println("---received color: " + colors[i] + "; as RGB: " + icolor);
          }
      }
      
      //process scenarios
      String scenids = request.getParameter("sids");
      if( scenids != null){
        System.out.println("---scen ids: " + scenids);
        if(scenids.compareTo("") != 0)addScenarios(scenids);
      }
      refreshMapDescription();

      //zoom and pans
      int xMin = (request.getParameter("x0") == null)?-1:Integer.parseInt(request.getParameter("x0"));
      int yMin = (request.getParameter("y0") == null)?-1:Integer.parseInt(request.getParameter("y0"));
      int xMax = (request.getParameter("x1") == null)?-1:Integer.parseInt(request.getParameter("x1"));
      int yMax = (request.getParameter("y1") == null)?-1:Integer.parseInt(request.getParameter("y1"));
      try{
        String nv = request.getParameter("nv");
        if (xMin != -1 && nv != null){
          if (nv.compareToIgnoreCase("zoomin") == 0) {
            System.out.println("--zoom in: " + xMin + "/" + yMin + "/" + xMax + "/" + yMax);
            if (xMax == -1 || yMax == -1) //only one point
              zoomInImgExtent(xMin, yMin, xMin, yMin);
            else
              zoomInImgExtent(xMin, yMin, xMax, yMax);
          }
          else if (nv.compareToIgnoreCase("zoomout") == 0) {
            System.out.println("--zoom out: " + xMin + "/" + yMin + "/" + xMax + "/" + yMax);
            if (xMax == -1 || yMax == -1)
              zoomOutImgExtent(xMin, yMin, xMin, yMin);
            else
              zoomOutImgExtent(xMin, yMin, xMax, yMax);
          }
          else if (nv.compareToIgnoreCase("pan") == 0) {
            System.out.println("--zoom pan: " + xMin + "/" + yMin + "/" + xMax + "/" + yMax);
            if (xMax == -1 || yMax == -1)
              recenterImg(xMin, yMin, xMin, yMin);
            else
              recenterImg(xMin, yMin, xMax, yMax);
          }
        }
      }catch (Exception e){
        System.out.println("----error in pan/zoom: " + e.getMessage());
      }

      byte[] mimeData;
      if(request.getParameter("legend") != null)
        mimeData = getLegendMimeData();
      else
        mimeData = getImageMimeData();

      String contentType = CONTENT_TYPE;
      response.setContentType(contentType);
      ServletOutputStream outServ = response.getOutputStream();
      if (mimeData == null) {
        outServ.write("mime data is null".getBytes());
        outServ.flush();
        outServ.close();
        return;
      }
      outServ.write(mimeData);
      outServ.flush();
      outServ.close();

      //recover server object state
      for(int i=0; i<addedLayers.size(); i++){
        System.out.println("---delete added layer: " + ((ILayer)addedLayers.get(i)).getName());
        m_map.deleteLayer((ILayer)addedLayers.get(i));
      }
      addedLayers.clear();
      m_colors.clear();
      refreshMapDescription();

      session.setAttribute("mapDescp",  m_context.saveObject( new IXMLSerializeProxy(this.m_mapDesc) ));
      session.setAttribute("imgDescp", m_context.saveObject( new IXMLSerializeProxy(this.m_imageDesc) ));
      destroy();
    }catch (Exception e){
      destroy();
      System.out.println(e.getMessage());
    }
  }

  private void refreshMapDescription(){
    double minx, miny, maxx, maxy;
    try{
      ////load old extent
      minx = m_mapDesc.getMapArea().getExtent().getXMin();
      miny = m_mapDesc.getMapArea().getExtent().getYMin();
      maxx = m_mapDesc.getMapArea().getExtent().getXMax();
      maxy = m_mapDesc.getMapArea().getExtent().getYMax();

      //reload map description
      m_mapServerObj.refreshServerObjects();
      m_mapDesc = (m_mapServer.getServerInfo(m_mapServer.getDefaultMapName())).
          getDefaultMapDescription();

      //recover saved map extent and do zoom and pan
      zoomToMapExtent(minx, miny, maxx, maxy);
      //m_mapDesc.getMapArea().getExtent().setXMin(minx);
      //m_mapDesc.getMapArea().getExtent().setYMin(miny);
      //m_mapDesc.getMapArea().getExtent().setXMax(maxx);
      //m_mapDesc.getMapArea().getExtent().setYMax(maxy);
    }catch (Exception e){
      System.out.println("--error refreshing map description: " + e.getMessage());
    }
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

  public void zoomInImgExtent(int x0, int y0, int x1, int y1) throws Exception{
    IEnvelope env = m_mapDesc.getMapArea().getExtent();

    ILongArray longArrayX = new ILongArrayProxy(m_context.createObject(LongArray.getClsid()));
    longArrayX.insert(0, x0);  longArrayX.insert(1, x1);
    ILongArray longArrayY = new ILongArrayProxy(m_context.createObject(LongArray.getClsid()));
    longArrayY.insert(0, y0);  longArrayY.insert(1, y1);
    IPointCollection pointColl =
        m_mapServer.toMapPoints(m_mapDesc, m_imageDesc.getDisplay(), longArrayX, longArrayY);
    IPoint point0 = null, point1 = null;
    if (pointColl.getPointCount() > 1) {
      point0 = pointColl.getPoint(0);
      point1 = pointColl.getPoint(1);

      //if the two points are close enough, then use as a one-point zoom in
      if(Math.abs(x1-x0)<=3 && Math.abs(y1-y0)<=3){
        env.centerAt(point0);
        env.expand(0.5, 0.5, true);
        System.out.println("-- zoom in by 0.5");
      }else {
        System.out.println("-- setting extent... ");
        zoomToMapExtent(point0.getX(), point0.getY(), point1.getX(),
                        point1.getY());
      }

      zoomToMapExtent(env.getXMin(), env.getYMin(), env.getXMax(), env.getYMax());
    }
  }

  public void zoomOutImgExtent(int x0, int y0, int x1, int y1) throws Exception{
    IEnvelope env = m_mapDesc.getMapArea().getExtent();

    ILongArray longArrayX = new ILongArrayProxy(m_context.createObject(LongArray.getClsid()));
    longArrayX.insert(0, x0);  longArrayX.insert(1, x1);
    ILongArray longArrayY = new ILongArrayProxy(m_context.createObject(LongArray.getClsid()));
    longArrayY.insert(0, y0);  longArrayY.insert(1, y1);
    IPointCollection pointColl =
        m_mapServer.toMapPoints(m_mapDesc, m_imageDesc.getDisplay(), longArrayX, longArrayY);
    IPoint point0 = null, point1 = null;
    if (pointColl.getPointCount() > 1) {
      point0 = pointColl.getPoint(0);
      point1 = pointColl.getPoint(1);

      //if the two points are close enough, then use as a one-point zoom out
      if(Math.abs(x1-x0)<=3 && Math.abs(y1-y0)<=3){
        env.centerAt(point0);
        env.expand(2.0, 2.0, true);
      } else {
        env.setXMin(env.getXMin() - Math.abs(point1.getX() - point0.getX()) / 2);
        env.setYMin(env.getYMin() - Math.abs(point1.getY() - point0.getY()) / 2);
        env.setXMax(env.getXMax() + Math.abs(point1.getX() - point0.getX()) / 2);
        env.setYMax(env.getYMax() + Math.abs(point1.getY() - point0.getY()) / 2);
      }
      zoomToMapExtent(env.getXMin(), env.getYMin(), env.getXMax(), env.getYMax());
    }
  }

  public void recenterImg(int x0, int y0, int x1, int y1) throws Exception{
    IEnvelope env = m_mapDesc.getMapArea().getExtent();

    ILongArray longArrayX = new ILongArrayProxy(m_context.createObject(LongArray.getClsid()));
    longArrayX.insert(0, x0);  longArrayX.insert(1, x1);
    ILongArray longArrayY = new ILongArrayProxy(m_context.createObject(LongArray.getClsid()));
    longArrayY.insert(0, y0);  longArrayY.insert(1, y1);
    IPointCollection pointColl =
        m_mapServer.toMapPoints(m_mapDesc, m_imageDesc.getDisplay(), longArrayX, longArrayY);
    IPoint point0 = null, point1 = null;
    if (pointColl.getPointCount() > 1) {
      point0 = pointColl.getPoint(0);
      point1 = pointColl.getPoint(1);

      //if the two points are close enough, then use as a one-point zoom out
      if(Math.abs(x1-x0)<=3 && Math.abs(y1-y0)<=3){
        env.centerAt(point0);
      } else {
        env.offset(point0.getX()-point1.getX(), point0.getY()-point1.getY() );
      }
    }
    zoomToMapExtent(env.getXMin(), env.getYMin(), env.getXMax(), env.getYMax());
  }


  /**
   * if FIDs are given, then just put those IDs into an FIDSet object and get the features
   * 1,3,555,87,
   * **/
public String selectFeaturesById(String ids) {
 String s = "";
 if (ids == null )return s;
 if (ids.length() == 0)return s;
 if (ids.charAt(ids.length()-1) != ',')ids += ",";

 try{
   IFIDSet fIDs = new IFIDSetProxy(m_context.createObject( (FIDSet.getClsid())));
   int start=0, end=0, id;
   while (end >= 0) {
     if (ids.charAt(start) == ',' || ids.charAt(start) == ' ') start++;
     end = ids.indexOf(',', start);
     if(end >= 0)
       if ( (id = Integer.parseInt(ids.substring(start, end))) >= 0)
         //System.out.println("\nnumber=" + id + "(" + start + "," + end + ")");
         fIDs.add(id);
     start = end;
   }

   //highlight features
   m_mapDesc.getLayerDescriptions().getElement(1).setSelectionFeatures(fIDs);
 }catch (Exception e){
   s = e.getMessage();
 }

 return s;
}

public void addScenarios(String sids){
    if(sids != null){
      //List scenlist = null;
      try {
        Session session = HibernateUtil.getSession();
        HibernateUtil.begin();
        String[] sIDs  = sids.split(","); // "(" + sids + ")";
        //Query query = session.createQuery("from MICAScenario where scen_id in " + sIDs);
        //scenlist = query.list();
        //HibernateUtil.commit();

        IFeatureLayer projectsLayer = new IFeatureLayerProxy(m_map.getLayer(0));

        int symsize = 4 *  sIDs.length - 2;  //size for the bottom scenario layer
        int colorvar = 150;                //color value (0~360) (of HSV) for the bottom layer

        IColor color = null;
        ISimpleLineSymbol symbol;
        ISimpleRenderer renderer;
        IGeoFeatureLayer lyr;
        String defClause;
        IFeatureLayerDefinition fd;

        //add the scenarios to the map, with given symbols
        MICAScenario scenario;
        for(int i=0; i<sIDs.length; i++){    // scenlist.size();
          scenario = (MICAScenario)session.load(MICAScenario.class, new Long(sIDs[i]));
          if( scenario == null) continue;
          System.out.println( "---working on scenario: " + scenario.getName() +
                              "; with projects number=" + scenario.getProjects().size());

          if( i < m_colors.size() ){
              color = (IRgbColor) m_colors.get(i);
              System.out.println("====use given color: " + color.getRGB() + " for scenario: " + sIDs[i]);
          }
          if(color == null){//make a color
              color = new IHsvColorProxy(m_context.createObject(HsvColor.getClsid()));
              ((IHsvColor)color).setHue(colorvar);  
              ((IHsvColor)color).setSaturation(30);  
              ((IHsvColor)color).setValue(90);
              
              colorvar = (colorvar + 87) % 360;
          }
          symbol = new ISimpleLineSymbolProxy(
              m_context.createObject(SimpleLineSymbol.getClsid()));
          symbol.setColor(color);
          symbol.setWidth(symsize);
          renderer = new ISimpleRendererProxy(
              m_context.createObject(SimpleRenderer.getClsid()) );
          renderer.setSymbolByRef( new ISymbolProxy(symbol));

          lyr = new IGeoFeatureLayerProxy( m_context.createObject(FeatureLayer.getClsid()) );
          lyr.setFeatureClassByRef(projectsLayer.getFeatureClass());  //new IFeatureWorkspaceProxy(ws).openFeatureClass("project_lines"));
          lyr.setName(scenario.getName());
          lyr.setRendererByRef(new IFeatureRendererProxy(renderer));

          //prepare to subset the projects for only this scenario
          defClause = "Project_ID in (-1";
          Iterator itr = scenario.getProjects().iterator();
          while(itr.hasNext()){
            defClause += "," + ( (MICAProject)itr.next() ).getProject_id();
          }
          defClause += ")";

          //definitions.add(defClause);
          fd = new IFeatureLayerDefinitionProxy(lyr);
          fd.setDefinitionExpression(defClause);

          addedLayers.add(lyr);
          m_map.addLayer(lyr);

          symsize -= 4;
          color = null;
        }

        //refreshMapDescription();  //done outside
      }
      catch (Exception e) {
        System.out.println("----error when adding scenario as layer: " + e.getMessage());
      }
    }
  }

private ILayer getLayerByName(String name) {
  if(m_map != null) return null;
  try{
    for (int i = 0; i < m_map.getLayerCount(); i++) {
      if (m_map.getLayer(i).getName().compareToIgnoreCase(name) == 0)
        return m_map.getLayer(i);
    }
  }  catch (Exception e){
    return null;
  }
  return null;
}

/**
 * if a SQL is used to select features, need to construct the queryfilter object
 * **/
public String selectFeaturesBySql(String strWhereClause) {
  String s = strWhereClause;
  try{
    IFeatureClass fClass = new IFeatureLayerProxy(m_map.getLayer(1)).getFeatureClass();
    IClass iClass = new IClassProxy(fClass);

    IQueryFilter filter = new IQueryFilterProxy(m_context.createObject(QueryFilter.getClsid()));
    filter.setSubFields(iClass.getOIDFieldName() + ",ID,Shape");
    filter.setWhereClause(strWhereClause);

    IFeatureCursor cursor = fClass.search(filter, false);
    IFIDSet fIDs = new IFIDSetProxy(m_context.createObject(FIDSet.getClsid()));

    IFeature feature;
    IEnvelope env = null;

    boolean first = true;
    while ( (feature = cursor.nextFeature()) != null) {
      fIDs.add(feature.getOID());
      if(feature.getShape() != null){
        if(first){
          env = new IEnvelopeProxy( feature.getExtent() );
          first = false;
        }else{
          env.union(feature.getExtent());
        }
      }

    }

    //highlight features
    m_mapDesc.getLayerDescriptions().getElement(1).setSelectionFeatures(fIDs);

    if(env != null){
      //env.expand(0.1,0.1, true);
      IMapExtent mapExtent = new IMapExtentProxy(m_context.createObject(MapExtent.
      getClsid()));
      mapExtent.setExtent(env);
      IMapArea mapArea = new IMapAreaProxy(mapExtent);
      m_mapDesc.setMapArea(mapArea);
    }

  }catch (Exception e){
    s = e.getMessage();
  }

  return s;
 }

  private String getTagValue(String seg, String tagname){
          int i1, i2;

          String s = "<" + tagname;
          i1 = seg.indexOf(s, 0) + tagname.length() + 1;
          s = "/" + tagname + ">";
          i2 = seg.indexOf(s, i1);
          if(i2 > i1) return( seg.substring(i1+1, i2 - 1) );	//pattern <tagname> ..... </tagname>

          s = "/>";
          i2 = seg.indexOf(s, i1);

          if(i2 < i1)return("");

          //for pattern like: <id value="3"/>
          s = seg.substring(i1, i2);	//get this tag part

          i1= s.indexOf("value=\"", 0);
          if(i1 < 0)return("");

          i2= s.indexOf("\"", i1 + 7);	//length of value=" is 7
          return( s.substring(i1 + 7, i2) );
  }

  /**
   * Description:
   *  Intialize ArcObjects for all Product Levels
   */
  public static void initializeArcObjects(){
    EngineInitializer.initializeEngine();

    try {
          AoInitialize ao = new AoInitialize();
          if( ao.isProductCodeAvailable( esriLicenseProductCode.esriLicenseProductCodeEngine ) == esriLicenseStatus.esriLicenseAvailable ){
              ao.initialize( esriLicenseProductCode.esriLicenseProductCodeEngine );
          }else if( ao.isProductCodeAvailable( esriLicenseProductCode.esriLicenseProductCodeArcInfo ) == esriLicenseStatus.esriLicenseAvailable ){
              ao.initialize( esriLicenseProductCode.esriLicenseProductCodeArcInfo );
          }else if( ao.isProductCodeAvailable( esriLicenseProductCode.esriLicenseProductCodeArcEditor ) == esriLicenseStatus.esriLicenseAvailable ){
              ao.initialize( esriLicenseProductCode.esriLicenseProductCodeArcEditor );
          }else if( ao.isProductCodeAvailable( esriLicenseProductCode.esriLicenseProductCodeArcView ) == esriLicenseStatus.esriLicenseAvailable ){
              ao.initialize( esriLicenseProductCode.esriLicenseProductCodeArcView );
          }else{
              System.out.println( "Program Exit: Unable to initialize ArcObjects");
              //System.exit( 0 );
          }
          ao.checkOutExtension(esriLicenseExtensionCode.esriLicenseExtensionCodeSpatialAnalyst);
      } catch ( Exception e ) {
          System.out.println(" Program Exit: Unable to initialize ArcObjects ");
          System.out.println( e.getMessage() );
          //System.exit(0);
      } // end try - catch
    } // end initializeArcObjects
}
