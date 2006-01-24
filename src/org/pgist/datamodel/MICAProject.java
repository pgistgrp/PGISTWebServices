package org.pgist.datamodel;

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
 * @hibernate.class table="[dbo].[Project_List]"
 */
public class MICAProject {
  private Long project_id;
  private String pin;
  private Long a_id;
  private int input_status;
  private String project_title;
  private double[] allCoordinates = null;

  /**
   *
   * @return Long
   * @hibernate.id generator-class="native"
   */
  public Long getProject_id() {

    return project_id;
  }

  /**
   * @hibernate.property
   */
  public String getPin() {
    return pin;
  }

  /**
   * @hibernate.property
   */
  public Long getA_id() {
    return a_id;
  }

  /**
   * @hibernate.property
   */
  public int getInput_status() {

    return input_status;
  }

  /**
   * @hibernate.property
   */
  public String getProject_title() {
    return project_title;
  }

  public double[] getAllCoordinates() {
    return allCoordinates;
  }

  public void setProject_id(Long project_id) {

    this.project_id = project_id;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public void setA_id(Long a_id) {
    this.a_id = a_id;
  }

  public void setInput_status(int input_status) {

    this.input_status = input_status;
  }

  public void setProject_title(String project_title) {
    this.project_title = project_title;
  }

  public void setAllCoordinates(double[] allCoordinates) {
    this.allCoordinates = allCoordinates;
  }

}
