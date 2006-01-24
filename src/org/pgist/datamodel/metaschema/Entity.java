package org.pgist.datamodel.metaschema;

import java.sql.*;

import java.util.List;

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
 * @hibernate.class table="_entity"
 */
public class Entity {
  private String name;
  private String description;
  private Long authorId;
  private Date createDate;
  private Integer authorType;
  private Long id;
  private List attributes;
  public Entity() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public void setAuthorType(Integer authorType) {
    this.authorType = authorType;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAttributes(List attributes) {
    this.attributes = attributes;
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
  public Integer getAuthorType() {
    return authorType;
  }

  /**
   * @hibernate.id generator-class="native"
   */
  public Long getId() {
    return id;
  }

  /**
   * @hibernate.list table="_ent_attr" cascade="none" lazy="false"
   * @hibernate.collection-key column="ent_id"
   * @hibernate.collection-index column="id"
   * @hibernate.collection-many-to-many column="attr_id" class="org.pgist.datamodel.metaschema.Attribute"
   */
  public List getAttributes() {
    return attributes;
  }
}
