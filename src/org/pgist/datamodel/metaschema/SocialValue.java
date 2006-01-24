package org.pgist.datamodel.metaschema;

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
 * @hibernate.class table="_socialvalue"
 */

public class SocialValue {
  private String name;
  private String source;
  private String description;
  private Long id;
  private List objectives = new ArrayList();

  /**
   * @hibernate.property
   */
  public String getName() {
    return name;
  }

  /**
   * @hibernate.property
   */
  public String getSource() {
    return source;
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

  /**
   * @hibernate.list lazy="false" table="_val_obj" cascade="save-update"
   * @hibernate.collection-key column="val_id"
   * @hibernate.collection-index column="id"
   * @hibernate.collection-many-to-many column="obj_id" class="org.pgist.datamodel.metaschema.Objective"
   */
  public List getObjectives() {

    return objectives;
  }
  public void removeObjective(Objective o){
    if(o != null){
      this.objectives.remove(o);
      o.getValues().remove(this);
    }
  }
  public void addObjective(Objective o){
    if(o != null){
      this.objectives.add(o);
      o.getValues().add(this);
    }
  }
  public void removeAllObjectives(){
    for(int i=this.objectives.size()-1; i>=0; i--){
      ( (Objective)this.objectives.get(i) ).getValues().remove(this);
      this.objectives.remove(i);
    }
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setObjectives(List objectives) {

    this.objectives = objectives;
  }
}
