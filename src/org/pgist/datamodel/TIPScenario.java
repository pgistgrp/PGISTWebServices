package org.pgist.datamodel;

import java.util.Set;
import java.util.HashSet;

/**
 *
 * <p>Title: PGIST Web Services</p>
 *
 * <p>Description: This system provides data services through standard web service protocol.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Department of Goegraphy, University of Washington</p>
 *
 * @author Guirong Zhou
 * @version 1.0
 *
 * @hibernate.class table="lit_scenario"
 */
public class TIPScenario {
  private String sce_Id;
  private Long id;
  private String name;
  private String description;
  private String author;
  private String dataCreated;
  private Set projects = new HashSet();

  public TIPScenario() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setSce_Id(String sce_Id) {
    this.sce_Id = sce_Id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setDataCreated(String dataCreated) {
    this.dataCreated = dataCreated;
  }

  public void setProjects(Set projects) {
    this.projects = projects;
  }

  /**
   * @hibernate.property
   */
  public String getSce_Id() {
    return sce_Id;
  }

  /**
   * @hibernate.id generator-class="native"
   */
  public Long getId() {
    return id;
  }

  /**
   * @hibernate.property
   */
  public String getName() {
    return name;
  }

  /**
   * @hibernate.property
   */
  public String getDescription() {
    return description;
  }

  /**
   * @hibernate.property
   */
  public String getAuthor() {
    return author;
  }

  /**
   * @hibernate.property
   */
  public String getDataCreated() {
    return dataCreated;
  }

  /**
   * @hibernate.set lazy="true" table="lit_sce_prj" cascade="none" inverse="true"
   * @hibernate.collection-key  column="sce_id"
   * @hibernate.collection-many-to-many column="prj_id" class="org.pgist.datamodel.TransportationProject"
   */
  public Set getProjects() {
    return projects;
  }

  private void jbInit() throws Exception {
  }
}
