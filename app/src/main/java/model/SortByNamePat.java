package model;

import java.util.*;

public class SortByNamePat implements Comparator<PatientModel> {
    @Override
    public int compare(PatientModel patientModel, PatientModel t1) {
        return patientModel.getPatName().compareTo(t1.getPatName());
    }
}
