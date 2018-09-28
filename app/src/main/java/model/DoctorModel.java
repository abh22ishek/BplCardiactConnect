package model;

import android.os.*;

public class DoctorModel implements Parcelable {

    String DocName;
    String DocPhone;


    public DoctorModel(String docName, String docPhone) {
        DocName = docName;
        DocPhone = docPhone;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public String getDocPhone() {
        return DocPhone;
    }

    public void setDocPhone(String docPhone) {
        DocPhone = docPhone;
    }

    protected DoctorModel(Parcel in) {
        DocName = in.readString();
        DocPhone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DocName);
        dest.writeString(DocPhone);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DoctorModel> CREATOR = new Parcelable.Creator<DoctorModel>() {
        @Override
        public DoctorModel createFromParcel(Parcel in) {
            return new DoctorModel(in);
        }

        @Override
        public DoctorModel[] newArray(int size) {
            return new DoctorModel[size];
        }
    };
}