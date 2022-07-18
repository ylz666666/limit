package com.domain;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private Integer rno ;
    private String rname ;
    private String description ;

    private String yl1 ;
    private String yl2 ;

    private List<Fn> fns ;

    public Integer getRno() {
        return rno;
    }

    public void setRno(Integer rno) {
        this.rno = rno;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Fn> getFns() {
        return fns;
    }

    public void setFns(List<Fn> fns) {
        this.fns = fns;
    }

    public Role(Integer rno, String rname, String description, String yl1, String yl2) {
        this.rno = rno;
        this.rname = rname;
        this.description = description;
        this.yl1 = yl1;
        this.yl2 = yl2;
    }

    public Role() {
    }
}
