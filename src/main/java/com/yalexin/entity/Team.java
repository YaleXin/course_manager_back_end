/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.entity;

public class Team {
    private int id;
    private boolean isFulled;
    private boolean approved;
    private int cap_id;
    private int mem1_id;
    private int mem2_id;
    private int su_id;
    private int score;
    private String captain;
    private String member1;
    private String member2;
    private String subName;

    public boolean isApproved() {
        return approved;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getSu_id() {
        return su_id;
    }

    public void setSu_id(int su_id) {
        this.su_id = su_id;
    }

    public boolean isFulled() {
        return isFulled;
    }

    public void setFulled(boolean fulled) {
        isFulled = fulled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Team{" +
                "cap_id=" + cap_id +
                ", mem1_id=" + mem1_id +
                ", mem2_id=" + mem2_id +
                ", captain='" + captain + '\'' +
                ", member1='" + member1 + '\'' +
                ", member2='" + member2 + '\'' +
                '}';
    }

    public int getCap_id() {
        return cap_id;
    }

    public void setCap_id(int cap_id) {
        this.cap_id = cap_id;
    }

    public int getMem1_id() {
        return mem1_id;
    }

    public void setMem1_id(int mem1_id) {
        this.mem1_id = mem1_id;
    }

    public int getMem2_id() {
        return mem2_id;
    }

    public void setMem2_id(int mem2_id) {
        this.mem2_id = mem2_id;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getMember1() {
        return member1;
    }

    public void setMember1(String member1) {
        this.member1 = member1;
    }

    public String getMember2() {
        return member2;
    }

    public void setMember2(String member2) {
        this.member2 = member2;
    }

    public Team() {
    }
}
