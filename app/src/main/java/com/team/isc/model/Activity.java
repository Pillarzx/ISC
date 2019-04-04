package com.team.isc.model;

import java.sql.Time;
import java.sql.Date;

/**
 * Created by ZX on 2019/3/24.
 */

public class Activity {
    private int ano;
    private int uno;
    private int astate;
    private String atitle;
    private String ahost;
    private int amaxnumber;
    private Date adate;
    private Time atime;
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

    public Date getAdate() {
        return adate;
    }

    public void setAdate(Date adate) {
        this.adate = adate;
    }

    public Time getAtime() {
        return atime;
    }

    public void setAtime(Time atime) {
        this.atime = atime;
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
}
