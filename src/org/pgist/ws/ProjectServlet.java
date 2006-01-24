package org.pgist.ws;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import org.pgist.datamodel.TransportationProject;
import org.pgist.util.Properties;
import org.hibernate.Session;
import org.pgist.util.HibernateUtil;
import org.hibernate.Query;
import java.util.Vector;

import com.esri.sde.sdk.client.SeLayer;
import com.esri.sde.sdk.client.SeRow;
import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeSqlConstruct;
import com.esri.sde.sdk.client.SeShape;
import com.esri.sde.sdk.client.SeQuery;
import com.esri.sde.sdk.client.SeObjectId;

public class ProjectServlet
    extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";

  //Initialize global variables
  public void init() throws ServletException {
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>ProjectServlet</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");
    out.println("<p>The servlet has received a " + request.getMethod() +
                ". This is the reply.</p>");
    out.println("</body>");
    out.println("</html>");
    out.close();
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    doGet(request, response);
  }

  //Clean up resources
  public void destroy() {
  }

  public int getProjectsTotal(String filter){
    List list = null;
    int total = 0;

      String hql = "from TransportationProject prj";
      
      if(filter.compareToIgnoreCase("YBI-MEGA") == 0){
          hql += " where prj.lit_category='YBI-MEGA'";
      }else if(filter.compareToIgnoreCase("YBI-ROAD") == 0){
          hql += " where prj.lit_category='YBI-ROAD'";
      }else if(filter.compareToIgnoreCase("YBI-TRANSIT") == 0){
          hql += " where prj.lit_category='YBI-TRANSIT'";
      }else if(filter.compareToIgnoreCase("YBI") == 0){
          hql += " where prj.lit_category like 'YBI-%'";
      }

    try {
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      Query query = session.createQuery("select count(id) " + hql);
      list = query.list();
      if(list.size() > 0)total = ((Integer)list.get(0)).intValue();

      HibernateUtil.commit();
    }
    catch (Exception e) {
      try {
        HibernateUtil.rollback();
      }
      catch (Exception ex) {}
    }
    return total;
  }

  public List getProjects(String sort, String filter, int numrows, int start){
    //use Hibernate to get a list of projects
    List result;
    try{
      System.out.println("///====sort=" + sort + "; numrows=" + numrows + "; start=" + start);
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //construct HQL
      String hql = "from TransportationProject prj";
      
      if(filter.compareToIgnoreCase("YBI-MEGA") == 0){
          hql += " where prj.lit_category='YBI-MEGA'";
      }else if(filter.compareToIgnoreCase("YBI-ROAD") == 0){
          hql += " where prj.lit_category='YBI-ROAD'";
      }else if(filter.compareToIgnoreCase("YBI-TRANSIT") == 0){
          hql += " where prj.lit_category='YBI-TRANSIT'";
      }else if(filter.compareToIgnoreCase("YBI") == 0){
          hql += " where prj.lit_category like 'YBI-%'";
      }
      
      if(sort.compareToIgnoreCase( "fund" ) == 0){
        hql += " order by prj.curFundTotal desc";
      }else if(sort.compareToIgnoreCase( "" ) == 0){
        hql += " order by prj.compDate asc";
      }else if(sort.compareToIgnoreCase( "status" ) == 0){
        hql += " order by prj.approvalStatus asc";
      }else if(sort.compareToIgnoreCase( "lead" ) == 0){
        hql += " order by prj.sponsorLead asc";
      }else{
        hql += " order by prj.projectTitle asc";
      }

      Query query = session.createQuery(hql);
      query.setFirstResult( start );
      query.setMaxResults( numrows );
      result = query.list();

      HibernateUtil.commit();
      
      System.out.println("=====results to be returned " + result.size() );

      return result;

    }catch (Exception e){
      return null; //scen.getProjects().toArray();
    }
  }

  public double[] getGeometry(int spatial_type, long oid) {
    SeConnection sdeconn = null;
    String layerName = null;
    double[][][] coords = null;
    //String whereClause = "oid=13"; //" oid=" + oid;
    
    if(spatial_type == 1)
        layerName = "lit_point";
    else if(spatial_type == 2)
        layerName = "lit_line";
    else if(spatial_type == 3)
        layerName = "lit_polygon";
    
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

      System.out.println("Use layer: " + theLayer.getName() + ", oid=" + oid);

      if (theLayer != null) { //get feature from a layer
        //SeSqlConstruct sqlConstruct = new SeSqlConstruct(theLayer.getName(), whereClause);
        String[] cols = new String[2];
        cols[0] = theLayer.getSpatialColumn();   // "PROJTYPE";
        cols[1] = "Project_ID";

        SeQuery query = new SeQuery(sdeconn); //, cols, sqlConstruct);
        //query.prepareQuery();
        //query.execute();

        //write all projects infromation in HTML format
        System.out.println("//========SDE query finished. spatial column=" + cols[0]);
        SeRow row = query.fetchRow(layerName, new SeObjectId(oid), cols); //query.fetch();
        SeShape shp = null;
        
        if(row == null)
            System.out.println("===========empty rows...");
        else{
            shp = row.getShape(0);
            if(shp == null)System.out.println("===========empty shape");
            coords = shp.getAllCoords();
            System.out.println("---shape array length" + shp.getAllCoords().length);
        }
        
        //System.out.println("--process project " + row.getInteger(1));
        query.close();
      }

      sdeconn.close();
      return coords[0][0];
    }
    catch (Exception e) {return null;}

  }

  public void saveInterested(Long userId, Long[]prjIds){

  }
}
