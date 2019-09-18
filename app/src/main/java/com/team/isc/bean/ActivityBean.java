package com.team.isc.bean;


import java.io.Serializable;

/**
 * Created by ZX on 2019/3/24.
 */

public class ActivityBean implements Serializable{
    private int ano;
    private int uno;
    private int astate;
    private String atitle;
    private String atype;
    private String ahost;
    private int amaxnumber;
    private String adatetime;
    private String aplace;
    private int acost;
    private String atext;
    private int arp;

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public int getAstate() {
        return astate;
    }

    public void setAstate(int astate) {
        this.astate = astate;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }

    public String getAtitle() {
        return atitle;
    }

    public void setAtitle(String atitle) {
        this.atitle = atitle;
    }

    public String getAhost() {
        return ahost;
    }

    public void setAhost(String ahost) {
        this.ahost = ahost;
    }

    public int getAmaxnumber() {
        return amaxnumber;
    }

    public void setAmaxnumber(int amaxnumber) {
        this.amaxnumber = amaxnumber;
    }

    public String getAdatetime() {
        return adatetime;
    }

    public void setAdatetime(String adatetime) {
        this.adatetime = adatetime;
    }

    public String getAplace() {
        return aplace;
    }

    public void setAplace(String aplace) {
        this.aplace = aplace;
    }

    public int getAcost() {
        return acost;
    }

    public void setAcost(int acost) {
        this.acost = acost;
    }

    public String getAtext() {
        return atext;
    }

    public void setAtext(String atext) {
        this.atext = atext;
    }

    public int getArp() {
        return arp;
    }

    public void setArp(int arp) {
        this.arp = arp;
    }

    @Override
    public String toString() {
        return "ActivityBean{" +
                "ano=" + ano +
                ", uno=" + uno +
                ", astate=" + astate +
                ", atitle='" + atitle + '\'' +
                ", atype='" + atype + '\'' +
                ", ahost='" + ahost + '\'' +
                ", amaxnumber=" + amaxnumber +
                ", adatetime='" + adatetime + '\''+
                ", aplace='" + aplace + '\'' +
                ", acost=" + acost +
                ", atext='" + atext + '\'' +
                ", arp=" + arp +
                '}';
    }
}
