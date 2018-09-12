package gennx.model;

import java.io.*;
import java.util.*;

public class EcgLEadModel implements Serializable{

    List<String> EcgLead1;
    List<String> EcgLead2;

    List<String> EcgLeadV1;
    List<String> EcgLeadV2;
    List<String> EcgLeadV3;

    List<String> EcgLeadV4;
    List<String> EcgLeadV5;
    List<String> EcgLeadV6;


    private static final long serialVersionUID = 142L;




    public List<String> getEcgLead1() {
        return EcgLead1;
    }

    public void setEcgLead1(List<String> ecgLead1) {
        EcgLead1 = ecgLead1;
    }

    public List<String> getEcgLead2() {
        return EcgLead2;
    }

    public void setEcgLead2(List<String> ecgLead2) {
        EcgLead2 = ecgLead2;
    }

    public List<String> getEcgLeadV1() {
        return EcgLeadV1;
    }

    public void setEcgLeadV1(List<String> ecgLeadV1) {
        EcgLeadV1 = ecgLeadV1;
    }

    public List<String> getEcgLeadV2() {
        return EcgLeadV2;
    }

    public void setEcgLeadV2(List<String> ecgLeadV2) {
        EcgLeadV2 = ecgLeadV2;
    }

    public List<String> getEcgLeadV3() {
        return EcgLeadV3;
    }

    public void setEcgLeadV3(List<String> ecgLeadV3) {
        EcgLeadV3 = ecgLeadV3;
    }

    public List<String> getEcgLeadV4() {
        return EcgLeadV4;
    }

    public void setEcgLeadV4(List<String> ecgLeadV4) {
        EcgLeadV4 = ecgLeadV4;
    }

    public List<String> getEcgLeadV5() {
        return EcgLeadV5;
    }

    public void setEcgLeadV5(List<String> ecgLeadV5) {
        EcgLeadV5 = ecgLeadV5;
    }

    public List<String> getEcgLeadV6() {
        return EcgLeadV6;
    }

    public void setEcgLeadV6(List<String> ecgLeadV6) {
        EcgLeadV6 = ecgLeadV6;
    }
}
