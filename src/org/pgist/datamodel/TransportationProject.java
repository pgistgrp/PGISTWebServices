package org.pgist.datamodel;

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
 * @hibernate.class table="lit_project"
 */
public class TransportationProject implements java.io.Serializable {
    private java.lang.String approvalStatus;
    private java.lang.String compDate;
    private java.lang.Double curFundTotal;
    private java.lang.String projectDesc;
    private java.lang.String spatial;
    private java.lang.String fromLoc;
    private java.lang.Long id;
    private java.lang.String projectId;
    private TIPScenario[] scenarios;
    private java.lang.String sponsorLead;
    private java.lang.String sponsorProjectId;
    private java.lang.String projectTitle;
    private java.lang.String toLoc;
  private String lit_category;
  private Integer spatial_type;
  private Long spatial_oid;
  /**
     * @hibernate.property
     */
    public java.lang.String getApprovalStatus() {
        return approvalStatus;
    }


    /**
     * Sets the approvalStatus value for this TransportationProject.
     *
     * @param approvalStatus
     */
    public void setApprovalStatus(java.lang.String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }


    /**
     * @hibernate.property
     */
    public java.lang.String getCompDate() {
        return compDate;
    }


    /**
     * Sets the compDate value for this TransportationProject.
     *
     * @param compDate
     */
    public void setCompDate(java.lang.String compDate) {
        this.compDate = compDate;
    }


    /**
     * @hibernate.property
     */
    public java.lang.Double getCurFundTotal() {
    return curFundTotal;
    }


    /**
     * Sets the currentFundTotal value for this TransportationProject.
     *
     * @param currentFundTotal
     */
    public void setCurFundTotal(java.lang.Double curFundTotal) {
      this.curFundTotal = curFundTotal;
    }


    /**
     * @hibernate.property
     */
    public java.lang.String getSpatial() {
      return spatial;
    }


    /**
     * Sets the footprint value for this TransportationProject.
     *
     * @param footprint
     */
    public void setSpatial(java.lang.String spatial) {
      this.spatial = spatial;
    }


    /**
     * @hibernate.property
     */
    public java.lang.String getFromLoc() {
        return fromLoc;
    }


    /**
     * Sets the fromLoc value for this TransportationProject.
     *
     * @param fromLoc
     */
    public void setFromLoc(java.lang.String fromLoc) {
        this.fromLoc = fromLoc;
    }


    /**
     * @hibernate.id generator-class="native"
     */
    public java.lang.Long getId() {
        return id;
    }


    /**
     * Sets the id value for this TransportationProject.
     *
     * @param id
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }

    /**
     * @hibernate.property
     */
    public java.lang.String getProjectId() {
      return projectId;
    }


    /**
     * Sets the pid value for this TransportationProject.
     *
     * @param pid
     */
    public void setProjectId(java.lang.String projectId) {
      this.projectId = projectId;
    }


  public java.util.Set getScenarios() {
       Set s = new HashSet();
       if(scenarios.length >0){
         for(int i=0; i<scenarios.length; i++)s.add(scenarios[i]);
       }
       return s;
    }


    /**
     * Sets the scenarios value for this TransportationProject.
     *
     * @param scenarios
     */
    public void setScenarios(java.util.Set scenarios) {
        this.scenarios = (TIPScenario[]) scenarios.toArray() ;
    }


    /**
     * @hibernate.property
     */
    public java.lang.String getSponsorLead() {
      return sponsorLead;
    }


    /**
     * Sets the sponsor value for this TransportationProject.
     *
     * @param sponsor
     */
    public void setSponsorLead(java.lang.String sponsorLead) {
      this.sponsorLead = sponsorLead;
    }


    /**
     * @hibernate.property
     */
    public java.lang.String getSponsorProjectId() {
      return sponsorProjectId;
    }


    /**
     * Sets the sponsorProjId value for this TransportationProject.
     *
     * @param sponsorProjId
     */
    public void setSponsorProjectId(java.lang.String sponsorProjectId) {
      this.sponsorProjectId = sponsorProjectId;
    }


    /**
     * @hibernate.property
     */
    public java.lang.String getProjectTitle() {

    return projectTitle;
  }


    /**
     * Sets the title value for this TransportationProject.
     *
     * @param title
     */
    public void setProjectTitle(java.lang.String projectTitle) {

    this.projectTitle = projectTitle;
  }


  /**
   * @hibernate.property
   */
    public java.lang.String getToLoc() {
        return toLoc;
    }

   /**
    * @hibernate.property
    */
  public String getProjectDesc() {
    return projectDesc;
  }

  /**
   * @hibernate.property
   */
  public String getLit_category() {
    return lit_category;
  }

  /**
   * @hibernate.property
   */
  public Integer getSpatial_type() {
    return spatial_type;
  }

  /**
   * @hibernate.property
   */
  public Long getSpatial_oid() {
    return spatial_oid;
  }

  /**
     * Sets the toLoc value for this TransportationProject.
     *
     * @param toLoc
     */
    public void setToLoc(java.lang.String toLoc) {
        this.toLoc = toLoc;
    }

  public void setProjectDesc(String projectDesc) {
    this.projectDesc = projectDesc;
  }

  public void setLit_category(String lit_category) {
    this.lit_category = lit_category;
  }

  public void setSpatial_type(Integer spatial_type) {
    this.spatial_type = spatial_type;
  }

  public void setSpatial_oid(Long spatial_oid) {
    this.spatial_oid = spatial_oid;
  }
}
