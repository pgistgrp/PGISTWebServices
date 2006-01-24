package org.pgist.datamodel;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

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
 * @hibernate.class table="[dbo].[Scenario]"
 */
public class MICAScenario {
  private Long scen_ID;
  private String name;
  private String description;
  private String author;
  private Date date_created;
  private Double bcr_min;
  private Double safe_ben_min;
  private Double tt_ben_min;
  private Double user_ben_min;
  private Double env_ben_min;
  private Double sys_om_min;
  private Double sys_pres_min;
  private Double sp_needs_min;
  private Double cong_rel_min;
  private Double trav_opt_min;
  private Double seamless_min;
  private Double safety_min;
  private Double security_min;
  private Double commnty_min;
  private Double collab_min;
  private Double freight_min;
  private Double econ_pros_min;
  private Double tourism_min;
  private Double air_qual_min;
  private Double wtr_qual_min;
  private Double habitat_min;
  private Double resource_min;
  private Set projects = new HashSet();
  public MICAScenario() {
  }

  public void setScen_ID(Long scen_ID) {
  this.scen_ID = scen_ID;
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

  public void setDate_created(Date date_created) {
    this.date_created = date_created;
  }

  public void setBcr_min(Double bcr_min) {
    this.bcr_min = bcr_min;
  }

  public void setSafe_ben_min(Double safe_ben_min) {

    this.safe_ben_min = safe_ben_min;
  }

  public void setTt_ben_min(Double tt_ben_min) {
    this.tt_ben_min = tt_ben_min;
  }

  public void setUser_ben_min(Double user_ben_min) {
    this.user_ben_min = user_ben_min;
  }

  public void setEnv_ben_min(Double env_ben_min) {
    this.env_ben_min = env_ben_min;
  }

  public void setSys_om_min(Double sys_om_min) {
    this.sys_om_min = sys_om_min;
  }

  public void setSys_pres_min(Double sys_pres_min) {
    this.sys_pres_min = sys_pres_min;
  }

  public void setSp_needs_min(Double sp_needs_min) {
    this.sp_needs_min = sp_needs_min;
  }

  public void setCong_rel_min(Double cong_rel_min) {
    this.cong_rel_min = cong_rel_min;
  }

  public void setTrav_opt_min(Double trav_opt_min) {
    this.trav_opt_min = trav_opt_min;
  }

  public void setSeamless_min(Double seamless_min) {
    this.seamless_min = seamless_min;
  }

  public void setSafety_min(Double safety_min) {
    this.safety_min = safety_min;
  }

  public void setSecurity_min(Double security_min) {
    this.security_min = security_min;
  }

  public void setCommnty_min(Double commnty_min) {
    this.commnty_min = commnty_min;
  }

  public void setCollab_min(Double collab_min) {

    this.collab_min = collab_min;
  }

  public void setFreight_min(Double freight_min) {
    this.freight_min = freight_min;
  }

  public void setEcon_pros_min(Double econ_pros_min) {
    this.econ_pros_min = econ_pros_min;
  }

  public void setTourism_min(Double tourism_min) {
    this.tourism_min = tourism_min;
  }

  public void setAir_qual_min(Double air_qual_min) {
    this.air_qual_min = air_qual_min;
  }

  public void setWtr_qual_min(Double wtr_qual_min) {
    this.wtr_qual_min = wtr_qual_min;
  }

  public void setHabitat_min(Double habitat_min) {
    this.habitat_min = habitat_min;
  }

  public void setResource_min(Double resource_min) {
    this.resource_min = resource_min;
  }

  public void setProjects(Set projects) {
    this.projects = projects;
  }

  /**
   * @hibernate.id generator-class="native"
   */
  public Long getScen_ID() {
    return scen_ID;
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
  public Date getDate_created() {
    return date_created;
  }

  /**
   * @hibernate.property
   */
  public Double getBcr_min() {
    return bcr_min;
  }

  /**
   * @hibernate.property
   */
  public Double getSafe_ben_min() {

    return safe_ben_min;
  }

  /**
   * @hibernate.property
   */
  public Double getTt_ben_min() {
    return tt_ben_min;
  }

  /**
   * @hibernate.property
   */
  public Double getUser_ben_min() {
    return user_ben_min;
  }

  /**
   * @hibernate.property
   */
  public Double getEnv_ben_min() {
    return env_ben_min;
  }

  /**
   * @hibernate.property
   */
  public Double getSys_om_min() {
    return sys_om_min;
  }

  /**
   * @hibernate.property
   */
  public Double getSys_pres_min() {
    return sys_pres_min;
  }

  /**
   * @hibernate.property
   */
  public Double getSp_needs_min() {
    return sp_needs_min;
  }

  /**
   * @hibernate.property
   */
  public Double getCong_rel_min() {
    return cong_rel_min;
  }

  /**
   * @hibernate.property
   */
  public Double getTrav_opt_min() {
    return trav_opt_min;
  }

  /**
   * @hibernate.property
   */
  public Double getSeamless_min() {
    return seamless_min;
  }

  /**
   * @hibernate.property
   */
  public Double getSafety_min() {
    return safety_min;
  }

  /**
   * @hibernate.property
   */
  public Double getSecurity_min() {
    return security_min;
  }

  /**
   * @hibernate.property
   */
  public Double getCommnty_min() {
    return commnty_min;
  }

  /**
   * @hibernate.property
   */
  public Double getCollab_min() {

    return collab_min;
  }

  /**
   * @hibernate.property
   */
  public Double getFreight_min() {
    return freight_min;
  }

  /**
   * @hibernate.property
   */
  public Double getEcon_pros_min() {
    return econ_pros_min;
  }

  /**
   * @hibernate.property
   */
  public Double getTourism_min() {
    return tourism_min;
  }

  /**
   * @hibernate.property
   */
  public Double getAir_qual_min() {
    return air_qual_min;
  }

  /**
   * @hibernate.property
   */
  public Double getWtr_qual_min() {
    return wtr_qual_min;
  }

  /**
   * @hibernate.property
   */
  public Double getHabitat_min() {
    return habitat_min;
  }

  /**
   * @hibernate.property
   */
  public Double getResource_min() {
    return resource_min;
  }

  /**
   * @hibernate.set lazy="true" table="[dbo].[Scenario_Projects]" cascade="none" inverse="true"
   * @hibernate.collection-key  column="scen_id"
   * @hibernate.collection-many-to-many column="p_id" class="org.pgist.datamodel.MICAProject"
   */
  public Set getProjects() {
  return projects;
  }
}
