package com.team.isc.model;

/**
 * Created by ZX on 2019/3/22.
 */

public class User {
    private int uno;
    private int iid;
    private String usign;
    private String uname;
//    private String upwd;
    private int urole;
    private String urealname;
    private String nickname;

    private String usex;

    private String udept;
    private String uclass;
    private String unum;
    private String uphone;
    private String uqq;
    private int urp;
    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getUsign() {
        return usign;
    }

    public void setUsign(String usign) {
        this.usign = usign;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

//    public String getUpwd() {
//        return upwd;
//    }
//
//    public void setUpwd(String upwd) {
//        this.upwd = upwd;
//    }

    public int getUrole() {
        return urole;
    }

    public void setUrole(int urole) {
        this.urole = urole;
    }

    public String getUrealname() {
        return urealname;
    }

    public void setUrealname(String urealname) {
        this.urealname = urealname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUdept() {
        return udept;
    }

    public void setUdept(String udept) {
        this.udept = udept;
    }

    public String getUclass() {
        return uclass;
    }

    public void setUclass(String uclass) {
        this.uclass = uclass;
    }

    public String getUnum() {
        return unum;
    }

    public void setUnum(String unum) {
        this.unum = unum;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUqq() {
        return uqq;
    }

    public void setUqq(String uqq) {
        this.uqq = uqq;
    }

    public int getUrp() {
        return urp;
    }

    public void setUrp(int urp) {
        this.urp = urp;
    }

    @Override
    public String toString() {
        return "User{" +
                "uno=" + uno +
                ", iid=" + iid +
                ", usign='" + usign + '\'' +
                ", uname='" + uname + '\'' +
                ", urole=" + urole +
                ", urealname='" + urealname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", usex='" + usex + '\'' +
                ", udept='" + udept + '\'' +
                ", uclass='" + uclass + '\'' +
                ", unum='" + unum + '\'' +
                ", uphone='" + uphone + '\'' +
                ", uqq='" + uqq + '\'' +
                ", urp=" + urp +
                '}';
    }
}
