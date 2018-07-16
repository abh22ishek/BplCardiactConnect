package model;


public class PatientModel {

    String patId;
    String patName;

    String patAge;
    String gender;
    String patRace;

    String patRefDoc;
    String refDoc;

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getPatAge() {
        return patAge;
    }

    public void setPatAge(String patAge) {
        this.patAge = patAge;
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
}
