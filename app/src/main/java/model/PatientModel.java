package model;


import android.support.annotation.*;

import java.util.*;

public class PatientModel implements Comparable<PatientModel>{

    int patId;
    String patName;

    int  patAge;
    String gender;
    String patRace;

    String patRefDoc;

    String height;

    public int getPatId() {
        return patId;
    }

    String weight;

    String time;

    public void setPatId(int patId) {
        this.patId = patId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPacemaker() {
        return pacemaker;
    }

    public void setPacemaker(String pacemaker) {
        this.pacemaker = pacemaker;
    }

    String pacemaker;
    String systolic, diabolic, drug1,drug2,clinicDiagnosis,consultName;

   /* public PatientModel(int patId, String patName, int patAge, String gender, String patRace,
                        String patRefDoc, String height, String weight, boolean pacemaker,
                        String systolic, String diabolic, String drug1, String drug2,
                        String clinicDiagnosis, String consultName) {
        this.patId = patId;
        this.patName = patName;
        this.patAge = patAge;
        this.gender = gender;
        this.patRace = patRace;
        this.patRefDoc = patRefDoc;
        this.height = height;
        this.weight = weight;
        this.pacemaker = pacemaker;
        this.systolic = systolic;
        this.diabolic = diabolic;
        this.drug1 = drug1;
        this.drug2 = drug2;
        this.clinicDiagnosis = clinicDiagnosis;
        this.consultName = consultName;
    }*/

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiabolic() {
        return diabolic;
    }

    public void setDiabolic(String diabolic) {
        this.diabolic = diabolic;
    }

    public String getDrug1() {
        return drug1;
    }

    public void setDrug1(String drug1) {
        this.drug1 = drug1;
    }

    public String getDrug2() {
        return drug2;
    }

    public void setDrug2(String drug2) {
        this.drug2 = drug2;
    }

    public String getClinicDiagnosis() {
        return clinicDiagnosis;
    }

    public void setClinicDiagnosis(String clinicDiagnosis) {
        this.clinicDiagnosis = clinicDiagnosis;
    }

    public String getConsultName() {
        return consultName;
    }

    public void setConsultName(String consultName) {
        this.consultName = consultName;
    }

    public int getPatAge() {
        return patAge;
    }

    public void setPatAge(int patAge) {
        this.patAge = patAge;
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
