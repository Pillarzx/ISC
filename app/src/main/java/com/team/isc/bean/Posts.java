package com.team.isc.bean;

import java.io.Serializable;

/**
 * Created by ZX on 2019/4/23.
 */

public class Posts implements Serializable{
    private int pno;
    private String uname;
    private int iid;
    private String ptext;
    private String pdatetime;
    private int prp;
    private int ptop;
    private String pcommentnum;
    public int getPno() {
        return pno;
    }
    public void setPno(int pno) {
        this.pno = pno;
    }
    public String getUanme() {
        return uname;
    }
    public void setUanme(String uname) {
        this.uname = uname;
    }
    public int getIid() {
        return iid;
    }
    public void setIid(int iid) {
        this.iid = iid;
    }
    public String getPtext() {
        return ptext;
    }
    public void setPtext(String ptext) {
        this.ptext = ptext;
    }
    public String getPdatetime() {
        return pdatetime;
    }
    public void setPdatetime(String pdatetime) {
        this.pdatetime = pdatetime;
    }
    public int getPrp() {
        return prp;
    }
    public void setPrp(int prp) {
        this.prp = prp;
    }
    public int getPtop() {
        return ptop;
    }
    public void setPtop(int ptop) {
        this.ptop = ptop;
    }
    public String getPcommentnum() {
        return pcommentnum;
    }

    public void setPcommentnum(String pcommentnum) {
        this.pcommentnum = pcommentnum;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "pno=" + pno +
                ", uname='" + uname + '\'' +
                ", iid=" + iid +
                ", ptext='" + ptext + '\'' +
                ", pdatetime='" + pdatetime + '\'' +
                ", prp=" + prp +
                ", ptop=" + ptop +
                ", pcommentnum='" + pcommentnum + '\'' +
                '}';
    }
}
