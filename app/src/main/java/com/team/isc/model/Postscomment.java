package com.team.isc.model;

/**
 * Created by ZX on 2019/5/9.
 */

public class Postscomment {
    private int pcno;
    private String uname;
    private int pno;
    private String pctext;
    public int getPcno() {
        return pcno;
    }
    public void setPcno(int pcno) {
        this.pcno = pcno;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public int getPno() {
        return pno;
    }
    public void setPno(int pno) {
        this.pno = pno;
    }
    public String getPctext() {
        return pctext;
    }
    public void setPctext(String pctext) {
        this.pctext = pctext;
    }
}
