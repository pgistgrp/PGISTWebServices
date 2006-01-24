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
 * @hibernate.class table="_attribute"
 */

public class Attribute {
  private String name;
  private Long authorId;
  private Date createDate;
  private Integer dataType;
  private String description;
  private Long id;

  public static int DATATYPE_YESNO = -1;
  public static int DATATYPE_INTEGER = -2;
  public static int DATATYPE_LONG = -4;
  public static int DATATYPE_DOUBLE = -8;
  public static int DATATYPE_DATE = -9;
  public static int DATATYPE_TEXT = -256;
  private String authorType;
  private List objectives = new ArrayList();

  public Attribute() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public void setDataType(Integer dataType) {
    this.dataType = dataType;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAuthorType(String authorType) {
    this.authorType = authorType;
  }

  public void setObjectives(List objectives) {
    this.objectives = objectives;
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
  public Long getAuthorId() {
    return authorId;
  }

  /**
   * @hibernate.property
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * @hibernate.property
   */
  public Integer getDataType() {
    return dataType;
  }

  /**
   * @hibernate.property
   */
  public String getDescription() {
    return description;
  }

  /**
   * @hibernate.id generator-class="native"
   */
  public Long getId() {
    return id;
  }

  public String getAuthorType() {
    return authorType;
  }

  /**
   * @hibernate.list lazy="true" table="_obj_attr" cascade="none" inverse="true"
   * @hibernate.collection-key column="attr_id"
   * @hibernate.collection-index column="id"
   * @hibernate.collection-many-to-many column="obj_id" class="org.pgist.datamodel.metaschema.Objective"
   */
  public List getObjectives() {
    return objectives;
  }
}
