package model;


import android.support.annotation.*;

import java.io.*;
import java.util.*;

public class PatientModel implements Comparable<PatientModel>{

     int patId;
    String patName;

    int  patAge;
    String gender;
    String patRace;

    public int getPatAge() {
        return patAge;
    }

    public void setPatAge(int patAge) {
        this.patAge = patAge;
    }

    String patRefDoc;
    String refDoc;


    public PatientModel(int patId, String patName, int patAge, String gender, String patRace, String patRefDoc, String refDoc) {
        this.patId = patId;
        this.patName = patName;
        this.patAge = patAge;
        this.gender = gender;
        this.patRace = patRace;
        this.patRefDoc = patRefDoc;
        this.refDoc = refDoc;
    }

    public int getPatId() {
        return patId;
    }

    public void setPatId(int patId) {
        this.patId = patId;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPatRace() {
        return patRace;
    }

    public void setPatRace(String patRace) {
        this.patRace = patRace;
    }

    public String getPatRefDoc() {
        return patRefDoc;
    }

    public void setPatRefDoc(String patRefDoc) {
        this.patRefDoc = patRefDoc;
    }

    public String getRefDoc() {
        return refDoc;
    }

    public void setRefDoc(String refDoc) {
        this.refDoc = refDoc;
    }

    @Override
    public int compareTo(@NonNull PatientModel patientModel) {
        int patAge1=patientModel.getPatAge();
        /* For Ascending order*/
        return this.patAge-patAge1;
    }





    public static Comparator<PatientModel> patNameComparator = new Comparator<PatientModel>() {

        public int compare(PatientModel s1, PatientModel s2) {

            String PatName1 = s1.getPatName();
            String PatName2 = s2.getPatName();

            //ascending order
            return PatName1.compareTo(PatName2);

            //descending order
            //return PatName2.compareTo(PatName1);
        }};



    public static Comparator<PatientModel> patIdComparator = new Comparator<PatientModel>() {

        public int compare(PatientModel s1, PatientModel s2) {


            //ascending order
            return s1.getPatId()-s2.getPatId();

            //descending order
            //return PatName2.compareTo(PatName1);
        }};





}
