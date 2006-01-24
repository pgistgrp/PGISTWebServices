package org.pgist.datamodel.metaschema;

import java.sql.Date;
import java.util.*;

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
 * @hibernate.class table="_objective"
 */

public class Objective {
  private String name;
  private String description;
  private Date createdate;
  private String authortype;
  private Long authorid;
  private String samplequestion;
  private Integer positive;
  private List attributes = new ArrayList();
  private Long id;
  private List values = new ArrayList();

  public Objective() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
  }

  public void setAuthortype(String authortype) {
    this.authortype = authortype;
  }

  public void setAuthorid(Long authorid) {
    this.authorid = authorid;
  }

  public void setSamplequestion(String samplequestion) {
    this.samplequestion = samplequestion;
  }

  public void setPositive(Integer positive) {
    this.positive = positive;
  }

  public void setAttributes(List attributes) {

    this.attributes = attributes;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setValues(List values) {
    this.values = values;
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
  public Date getCreatedate() {
    return createdate;
  }

  /**
   * @hibernate.property
   */
  public String getAuthortype() {
    return authortype;
  }

  /**
   * @hibernate.property
   */
  public Long getAuthorid() {
    return authorid;
  }

  /**
   * @hibernate.property
   */
  public String getSamplequestion() {
    return samplequestion;
  }

  /**
   * @hibernate.property
   */
  public Integer getPositive() {
    return positive;
  }

  /**
   * @hibernate.list lazy="false" table="_obj_attr" cascade="none"
   * @hibernate.collection-key column="obj_id"
   * @hibernate.collection-index column="id"
   * @hibernate.collection-many-to-many column="attr_id" class="org.pgist.datamodel.metaschema.Attribute"
   */
  public List getAttributes() {

    return attributes;
  }
  public void removeAttribute(Attribute a){
    if(a != null){
      this.attributes.remove(a);
      a.getObjectives().remove(this);
    }
  }
  public void addAttribute(Attribute a){
    if(a != null){
      this.attributes.add(a);
      a.getObjectives().add(this);
    }
  }

  /**
   * @hibernate.id generator-class="native"
   */
  public Long getId() {
    return id;
  }

  /**
   * @hibernate.list lazy="true" table="_val_obj" cascade="none" inverse="true"
   * @hibernate.collection-key column="obj_id"
   * @hibernate.collection-index column="id"
   * @hibernate.collection-many-to-many column="val_id" class="org.pgist.datamodel.metaschema.SocialValue"
   */
  public List getValues() {
    return values;
  }
}
