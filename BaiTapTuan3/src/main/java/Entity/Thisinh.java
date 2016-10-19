package Entity;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by phucv on 10/3/2016.
 */
public class Thisinh{
    private String rollNo;
    private String name;
    private String address;
    private boolean gender;
    private double[] mark;
    private double totalMark;
    private String srcFile;
    private boolean stateChange;

    public Thisinh() {
        mark = new double[3];
        stateChange = false;
    }

    public Thisinh(String rollNo, String name, String address, boolean gender, double[] mark, double totalMark, String srcFile, boolean stateChange) {
        this.rollNo = rollNo;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.mark = mark;
        this.totalMark = totalMark;
        this.srcFile = srcFile;
        this.stateChange = stateChange;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double[] getMark() {
        return mark;
    }

    public void setMark(double[] mark) {
        this.mark = mark;
        this.totalMark = mark[0] + mark[1] + mark[2];
    }

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double totalMark) {
        this.totalMark = totalMark;
    }

    public String reverName(){
        String[] arrayName = getName().split(" ");
        String reverName = "";
        for (int i = arrayName.length-1 ; i>=0; i--){
            reverName += arrayName[i];
        }
        return reverName;
    }

    public String getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(String srcFile) {
        this.srcFile = srcFile;
    }

    public boolean isStateChange() {
        return stateChange;
    }

    public void setStateChange(boolean stateChange) {
        this.stateChange = stateChange;
    }
}

