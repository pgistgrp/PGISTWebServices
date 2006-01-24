package org.pgist.datamodel.metaschema;

import java.sql.*;

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
 * @hibernate.class table="_measurement"
 */

public class Measurement {
  private Long id;
  private Date time0;
  private Date time_s;
  private Date time_e;
  private Long time_c;
  private Long aid;
  private Long rid;
  private Integer measure_i;
  private Double measure_d;
  private Date measure_t;
  private Long eid;
  private Long measure_l;
  private char measure_y;
  private String measure_x;
  public Measurement() {
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTime0(Date time0) {
    this.time0 = time0;
  }

  public void setTime_s(Date time_s) {

    this.time_s = time_s;
  }

  public void setTime_e(Date time_e) {
    this.time_e = time_e;
  }

  public void setTime_c(Long time_c) {
    this.time_c = time_c;
  }

  public void setAid(Long aid) {
    this.aid = aid;
  }

  public void setRid(Long rid) {
    this.rid = rid;
  }

  public void setMeasure_i(Integer measure_i) {
    this.measure_i = measure_i;
  }

  public void setMeasure_d(Double measure_d) {
    this.measure_d = measure_d;
  }

  public void setMeasure_t(Date measure_t) {
    this.measure_t = measure_t;
  }

  public void setEid(Long eid) {
    this.eid = eid;
  }

  public void setMeasure_l(Long measure_l) {
    this.measure_l = measure_l;
  }

  public void setMeasure_y(char measure_y) {
    this.measure_y = measure_y;
  }

  public void setMeasure_x(String measure_x) {
    this.measure_x = measure_x;
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
  public Date getTime0() {
    return time0;
  }

  /**
   * @hibernate.property
   */
  public Date getTime_s() {

    return time_s;
  }

  /**
   * @hibernate.property
   */
  public Date getTime_e() {
    return time_e;
  }

  /**
   * @hibernate.property
   */
  public Long getTime_c() {
    return time_c;
  }

  /**
   * @hibernate.property
   */
  public Long getAid() {
    return aid;
  }

  /**
   * @hibernate.property
   */
  public Long getRid() {
    return rid;
  }

  /**
   * @hibernate.property
   */
  public Integer getMeasure_i() {
    return measure_i;
  }

  /**
   * @hibernate.property
   */
  public Double getMeasure_d() {
    return measure_d;
  }

  /**
   * @hibernate.property
   */
  public Date getMeasure_t() {
    return measure_t;
  }

  /**
   * @hibernate.property
   */
  public Long getEid() {
    return eid;
  }

  /**
   * @hibernate.property
   */
  public Long getMeasure_l() {
    return measure_l;
  }

  /**
   * @hibernate.property
   */
  public char getMeasure_y() {
    return measure_y;
  }

  /**
   * @hibernate.property
   */
  public String getMeasure_x() {
    return measure_x;
  }
}
