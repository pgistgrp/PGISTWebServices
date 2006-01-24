package org.pgist.ws;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Set;

import org.pgist.util.HibernateUtil;
import org.pgist.datamodel.MICAScenario;
import org.pgist.datamodel.MICAProject;
import org.hibernate.Session;
import org.hibernate.Query;
import java.util.Iterator;
import java.util.List;
import com.esri.sde.sdk.client.SeLayer;
import com.esri.sde.sdk.client.SeRow;
import com.esri.sde.sdk.client.SeConnection;
import org.pgist.util.Properties;
import java.util.Vector;
import com.esri.sde.sdk.client.SeSqlConstruct;
import com.esri.sde.sdk.client.SeShape;
import java.util.Hashtable;
import com.esri.sde.sdk.client.SeQuery;
import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;

public class MICAScenServlet
    extends HttpServlet {

  public MICAScenServlet() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

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
    out.println("<head><title>Scenario information</title></head>");

    //do the business logic - get projects and write in HTML
    String message = null;
    Long id = Long.valueOf( request.getParameter("sid") );

    if (id != null){
      out.println("<body bgcolor=\"#ffffff\">");
      out.println("<p>The servlet has received a " + request.getMethod() +
                  ". This is the reply.</p>");

      /*/=================
      try{
        MICAScenario[] list = getScenarios();
        message = "Total scenarios: " + list.length;

        for(int i=0; i<list.length; i++){
          MICAScenario s = list[i];
          out.println( s.getDescription() + "<br>");
          Set set = s.getProjects();
          if(set != null){
            Iterator projects = set.iterator();
            while (projects.hasNext()) {
              MICAProject p = (MICAProject) projects.next();
              out.println("<li>" + p.getProject_title() + "</li>");
            }
          }
        }
      }catch (Exception e){
        message = e.getMessage();
      }

      *///=================

    }
    if (message != null) out.println( message );
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

  private void jbInit() throws Exception {
  }

  public List getScenarios(){  //MICAScenario[]
    List allScenarios = null;
    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      Query query = session.createQuery("from MICAScenario");
      //query.setFirstResult( 1 );
      //query.setMaxResults(20);
      allScenarios = query.list();

      System.out.println("///====Total number of scenarios returned: " + allScenarios.size());

      HibernateUtil.commit();
      //HibernateUtil.closeSession();

    }catch (Exception e){
      return null;
    }

    return allScenarios;
  }

  public List getProjects4Scen(Long scen_id){  //MICAProject[]
    MICAScenario scen = null;
    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //load scenario
      scen = (MICAScenario)session.load(MICAScenario.class, scen_id);
      //Query query = session.createQuery("from MICAScenario where scen_id=");
      //query.setFirstResult( 1 );
      //query.setMaxResults(20);
      //scen = query.list();

      HibernateUtil.commit();

      System.out.println("///====The scenarios returned: " + scen.getName());
      System.out.println("///====There are " + scen.getProjects().size() + " projects in this scenario");

      //make a query string for project IDs
      String whereClause = "Project_ID in (-1";
      Iterator itr = scen.getProjects().iterator();
      while(itr.hasNext()){
        whereClause += "," + ( (MICAProject)itr.next() ).getProject_id();
      }
      whereClause += ")";
      System.out.println("Use project id filter: ***" + whereClause);

      //query SDE to get coordinated for these projects
      HashMap projectCoords = getGeometry("lit_line", "Project_ID", whereClause);
      System.out.println("=====check mapped coordinates: ");
      Iterator iter = projectCoords.keySet().iterator();
      while(iter.hasNext()){
        System.out.println("\n--new line: ");
        double[][][] coords = ( double[][][])projectCoords.get(iter.next());
        for(int i=0; i<coords.length; i++)
          for(int j=0; j<coords[i].length; j++)
            for(int k=0; k<coords[i][j].length; k++){
              System.out.print(coords[i][j][k] + ", ");
            }
      }

      //assign coordinates to projects
      itr = scen.getProjects().iterator();

      while( itr.hasNext() ){
        MICAProject prj = (MICAProject)itr.next();
        System.out.println("=====scenario project:" + prj.getProject_id() );
        double[][][] coords = (double[][][])projectCoords.get("" + prj.getProject_id());
        if(coords != null){
          System.out.println("====has coord: " + coords[0][0][0]);
          prj.setAllCoordinates(coords[0][0]);
        }
      }
/**/
      //HibernateUtil.closeSession();
      List result = new ArrayList( scen.getProjects() );

      System.out.println("=====results to be returned " + result.size() );

      return result;

    }catch (Exception e){
      return null; //scen.getProjects().toArray();
    }
  }

  /**
   * getGeometry
   */
  public HashMap getGeometry(String layerName, String iDColumn, String whereClause) {
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
