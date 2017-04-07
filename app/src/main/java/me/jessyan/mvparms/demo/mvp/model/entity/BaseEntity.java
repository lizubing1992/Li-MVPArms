/*
 * All rights Reserved, Designed By 农金圈  2017年04月01日17:47
 */
package me.jessyan.mvparms.demo.mvp.model.entity;

import java.util.ArrayList;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class BaseEntity<T> {

  private boolean status;
  private int total;
  private ArrayList<Object>  tngou;

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public ArrayList<Object> getTngou() {
    return tngou;
  }

  public void setTngou(ArrayList<Object> tngou) {
    this.tngou = tngou;
  }
}
