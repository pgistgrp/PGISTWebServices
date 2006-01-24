package org.pgist.ws;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import org.pgist.util.HibernateUtil;
import org.pgist.datamodel.MICAProject;
import org.hibernate.Session;
import org.hibernate.Query;

public class MICAProjServlet
    extends HttpServlet {
  public MICAProjServlet() {
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
    out.println("<head><title>Project information</title></head>");

    //do the business logic - get projects and write in HTML
    String message = null;
    Long id = Long.valueOf( request.getParameter("pid") );

    if (id != null){
      out.println("<body bgcolor=\"#ffffff\">");
      out.println("<p>The servlet has received a " + request.getMethod() +
                  ". This is the reply.</p>");

      //=================
      try{
        Session session = HibernateUtil.getSession();
        HibernateUtil.begin();
        //MICAProject prj = (MICAProject) session.load(MICAProject.class, id);

        Query query = session.createQuery("from MICAProject");
        query.setFirstResult( 1 );
        query.setMaxResults(20);
        List list = query.list();

        HibernateUtil.commit();

        for(int i=0; i<list.size(); i++)
          out.println( ((MICAProject)list.get(i) ).getProject_title() + "<br>");

        /*if(prj != null){
          out.println("<h1>" + prj.getProject_title() + "</h1>");
        }else{
          message = "Project not found.";
        }*/

        HibernateUtil.closeSession();

      }catch (Exception e){
        message = e.getMessage();
      }

      //=================

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
}
