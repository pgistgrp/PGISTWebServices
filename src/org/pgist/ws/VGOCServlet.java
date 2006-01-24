package org.pgist.ws;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import org.pgist.datamodel.metaschema.Attribute;
import org.pgist.datamodel.metaschema.Objective;
import org.pgist.datamodel.metaschema.SocialValue;
import org.pgist.datamodel.metaschema.Entity;
import org.hibernate.Session;
import org.hibernate.Query;
import org.pgist.util.HibernateUtil;

public class VGOCServlet
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
    out.println("<head><title>VGOCServlet</title></head>");
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

  public void createValue(String name, String source, String description){
    System.out.println("///====create new value:" + name);

    if(name==null)return;
    if(name.compareTo("") == 0)return;

    SocialValue v = new SocialValue();
    v.setName(name);
    v.setSource(source);
    v.setDescription(description);

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //save this value into the database
      session.save(v);
      HibernateUtil.commit();
      System.out.println("///====value saved");
    }catch(Exception e){System.out.println("///====error:" + e.getMessage() );}
  }

  public void createObjective(String name, String description, String sampleq){
    if(name==null)return;
    if(name.compareTo("") == 0)return;

    Objective obj = new Objective();
    obj.setName(name);
    obj.setDescription(description);
    obj.setSamplequestion(sampleq);

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //save this value into the database
      session.save(obj);
      HibernateUtil.commit();
      System.out.println("///====objective saved");
    }catch(Exception e){System.out.println("///====error:" + e.getMessage() );}
  }

  public void createObjective4Value(String name, String description, String sampleq, Long vid){
    if (name == null)return;
    if (name.compareTo("") == 0)return;

    Objective obj = new Objective();
    obj.setName(name);
    obj.setDescription(description);
    obj.setSamplequestion(sampleq);

    try {
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //save this objective into the database
      session.save(obj);

      SocialValue val = null;

      //load value according to its id
      val = (SocialValue) session.load(SocialValue.class, vid);

      if(val != null){
        val.addObjective(obj);
        session.saveOrUpdate(val);
      }

      HibernateUtil.commit();
      System.out.println("///====new objective created.");

    }
    catch (Exception e) {
      System.out.println("///====error:" + e.getMessage());
      return;
    }
  }

  public void createEntity(String name, String source, String description){

    if(name==null)return;
    if(name.compareTo("") == 0)return;

    Entity ent = new Entity();
    ent.setName(name);
    ent.setDescription(description);

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //save this value into the database
      session.save(ent);
      HibernateUtil.commit();
      System.out.println("///====entity saved");
    }catch(Exception e){System.out.println("///====error:" + e.getMessage() );}
  }

  /**
   * create a new attribute
   */
  public void createAttribute(String name, Integer datatype, String descp){
    if(name==null)return;
    if(name.compareTo("") == 0)return;

    Attribute attr = new Attribute();
    attr.setName(name);
    attr.setDescription(descp);
    attr.setDataType(datatype);

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //save this value into the database
      session.save(attr);
      HibernateUtil.commit();
    }catch(Exception e){return;}

  }

  /**
   * create a new attribute and attach it to an objective with given id
   */
  public void createAttribute4Objective(String name, Integer datatype, String descp, Long oid){
    if(name==null)return;
    if(name.compareTo("") == 0)return;

    Attribute attr = new Attribute();
    attr.setName(name);
    attr.setDescription(descp);
    attr.setDataType(datatype);

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //save this attribute into the database
      session.save(attr);

      //load objective according to its id
      Objective obj = null;
      obj = (Objective)session.load(Objective.class, oid);

      if( obj != null ){
        obj.addAttribute(attr);
        session.saveOrUpdate(obj);
      }

      HibernateUtil.commit();
    }catch (Exception e){
      System.out.println("---error adding new attr" + e.getMessage());
      return;
    }

  }

  /**
   * create a new attribute and attach it to a entity with given id
   */
  public void createAttribute4Entity(String name, Integer datatype, String descp, Long eid){
    if(name==null)return;
    if(name.compareTo("") == 0)return;

    Attribute attr = new Attribute();
    attr.setName(name);
    attr.setDescription(descp);
    attr.setDataType(datatype);

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //save this value into the database
      session.save(attr);
      HibernateUtil.commit();
    }catch(Exception e){return;}

    Entity ent = null;
    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //load value according to its id
      ent = (Entity)session.load(Entity.class, eid);

      if (ent != null)ent.getAttributes().add(attr);
      session.update(ent);
      HibernateUtil.commit();
      session.flush();
    }catch (Exception e){
      return;
    }

  }

  /**
   * Get all social values stored in the database
   * @return List
   */
  public List getAllValues(){
    List vals = null;
    try{
      Session session = HibernateUtil.getSession();
      session.clear();
      HibernateUtil.begin();

      Query query = session.createQuery("from SocialValue");
      vals = query.list();

      System.out.println("///====Total number of values returned: " + vals.size());
      HibernateUtil.commit();
      //HibernateUtil.closeSession();
    }catch (Exception e){
      return null;
    }

    return vals;
  }

  /**
   * Get all the attributes related to an objective by its ID
   * @return List
   */
  public List getAttributes4Objective(Long oid){
    List attrs = null;
    Objective obj = null;

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //load value according to its id
      obj = (Objective)session.load(Objective.class, oid);
      HibernateUtil.commit();

      System.out.println("///====The objective returned: " + obj.getName() );

      if (obj != null){
        attrs = obj.getAttributes();
      }
    }catch (Exception e){

    }

    return attrs;
  }

  public void deleteItem(String type, Long id){
    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      Object o = null;
      System.out.println("///==to delete a " + type);
      if(type.compareToIgnoreCase("SocialValue") == 0){
        o = session.load(SocialValue.class, id);
        if(o != null){
          ( (SocialValue) o).removeAllObjectives();
        }
        session.delete(o);
      }else if(type.compareToIgnoreCase("Objective") == 0){
        o = session.load(Objective.class, id);
        if(o != null){
          System.out.println("//====values associated to obj '" + ((Objective) o).getName() + "': " + ((Objective) o).getValues().size());
          for(int i=0;i<((Objective) o).getValues().size(); i++){
            if((SocialValue) ( (Objective) o).getValues().get(i) != null){
              ((SocialValue) ( (Objective) o).getValues().get(i)).removeObjective((Objective) o);
            }
          }

          for(int i=0;i<((Objective) o).getAttributes().size(); i++){
            if( ((Objective) o).getAttributes().get(i) != null)
            ((Objective) o).removeAttribute((Attribute) ( (Objective) o).getAttributes().get(i));
          }

          session.delete(o);
        }

      }else if(type.compareToIgnoreCase("Attribute") == 0){
        o = session.load(Attribute.class, id);
      }

      session.flush();
      HibernateUtil.commit();
    }catch (Exception e){System.out.println("===delete error: " + e.getMessage());
      try {
        HibernateUtil.rollback();
      }
      catch (Exception ex) {
      }
    }
  }

  /**
   * Get all the attributes related to an objective by its ID
   * @return List
   */
  public List getObjectives4Value(Long vid){
    List objs = null;
    SocialValue val = null;

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //load value according to its id
      val = (SocialValue)session.load(SocialValue.class, vid);
      HibernateUtil.commit();

      System.out.println("///====The value returned: " + val.getName() );

      if (val != null){
        objs = val.getObjectives();
      }
    }catch (Exception e){

    }

    return objs;
  }

  public List getAttributes4Entity(Long eid){
    List attrs = null;
    Entity ent = null;

    try{
      Session session = HibernateUtil.getSession();
      HibernateUtil.begin();

      //load entity according to its id
      ent = (Entity)session.load(Entity.class, eid);
      HibernateUtil.commit();

      System.out.println("///====The entity returned: " + ent.getName() );

      if (ent != null){
        attrs = ent.getAttributes();
      }
    }catch (Exception e){

    }

    return attrs;
  }



}
