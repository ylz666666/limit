package com.domain;

import java.io.Serializable;

public class Fn implements Serializable {
    private Integer fno ;
    private String fname ;
    private String fhref ;
    private Integer flag ;// 1菜单功能，2按钮功能
    private String ftarget ;
    private Integer pno ;

    private String yl1 ;
    private String yl2 ;

    private Fn pfn ;

    public Integer getFno() {
        return fno;
    }

    public void setFno(Integer fno) {
        this.fno = fno;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFhref() {
        return fhref;
    }

    public void setFhref(String fhref) {
        this.fhref = fhref;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getFtarget() {
        return ftarget;
    }

    public void setFtarget(String ftarget) {
        this.ftarget = ftarget;
    }

    public Integer getPno() {
        return pno;
    }

    public void setPno(Integer pno) {
        this.pno = pno;
    }

    public String getYl1() {
        return yl1;
    }

    public void setYl1(String yl1) {
        this.yl1 = yl1;
    }

    public String getYl2() {
        return yl2;
    }

    public void setYl2(String yl2) {
        this.yl2 = yl2;
    }

    public Fn getPfn() {
        return pfn;
    }

    public void setPfn(Fn pfn) {
        this.pfn = pfn;
    }

    public Fn(Integer fno, String fname, String fhref, Integer flag, String ftarget, Integer pno, String yl1, String yl2) {
        this.fno = fno;
        this.fname = fname;
        this.fhref = fhref;
        this.flag = flag;
        this.ftarget = ftarget;
        this.pno = pno;
        this.yl1 = yl1;
        this.yl2 = yl2;
    }

    public Fn() {
    }
}
