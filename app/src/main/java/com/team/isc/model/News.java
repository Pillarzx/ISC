package com.team.isc.model;

import java.io.Serializable;

/**
 * Created by ZX on 2019/4/30.
 */

public class News implements Serializable{
    private int nno;
    private String uname;
    private int iid;
    private String ntitle;
    private String ndate;
    private String ntime;
    private String ntext;
    private int nlike;
    public int getNno() {
        return nno;
    }
    public void setNno(int nno) {
        this.nno = nno;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public int getIid() {
        return iid;
    }
    public void setIid(int iid) {
        this.iid = iid;
    }
    public String getNtitle() {
        return ntitle;
    }
    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }
    public String getNdate() {
        return ndate;
    }
    public void setNdate(String ndate) {
        this.ndate = ndate;
    }
    public String getNtime() {
        return ntime;
    }
    public void setNtime(String ntime) {
        this.ntime = ntime;
    }
    public String getNtext() {
        return ntext;
    }
    public void setNtext(String ntext) {
        this.ntext = ntext;
    }
    public int getNlike() {
        return nlike;
    }
    public void setNlike(int nlike) {
        this.nlike = nlike;
    }

    @Override
    public String toString() {
        return "News{" +
                "nno=" + nno +
                ", uname='" + uname + '\'' +
                ", iid=" + iid +
                ", ntitle='" + ntitle + '\'' +
                ", ndate='" + ndate + '\'' +
                ", ntime='" + ntime + '\'' +
                ", ntext='" + ntext + '\'' +
                ", nlike=" + nlike +
                '}';
    }
}
