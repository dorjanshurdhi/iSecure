package com.cindytech.isecure.model;

public class Model {

    private int id;
    private String number;
    private String arm;
    private String disarm;
    private String night;
    private String day;
    private String status;
    private String enable;
    private String disable;
    private String time;
    private String password;

    //Constructors
    public Model() {}

    public Model(int id, String number, String arm, String disarm, String night, String day, String status, String enable, String disable, String time, String password) {
        this.id = id;
        this.number = number;
        this.arm = arm;
        this.disarm = disarm;
        this.night = night;
        this.day = day;
        this.status = status;
        this.enable = enable;
        this.disable = disable;
        this.time = time;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArm() {
        return arm;
    }

    public void setArm(String arm) {
        this.arm = arm;
    }

    public String getDisarm() {
        return disarm;
    }

    public void setDisarm(String disarm) {
        this.disarm = disarm;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", arm='" + arm + '\'' +
                ", disarm='" + disarm + '\'' +
                ", night='" + night + '\'' +
                ", day='" + day + '\'' +
                ", status='" + status + '\'' +
                ", enable='" + enable + '\'' +
                ", disable='" + disable + '\'' +
                ", time='" + time + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}