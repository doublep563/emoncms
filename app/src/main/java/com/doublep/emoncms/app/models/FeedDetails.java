package com.doublep.emoncms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable for Feed Details
 */
public class FeedDetails implements Parcelable {

    // Basic Club Details

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public FeedDetails createFromParcel(Parcel in) {
            return new FeedDetails(in);
        }

        @Override
        public FeedDetails[] newArray(int size) {
            return new FeedDetails[size];
        }
    };
    private String strID = "";
    private String strUserID = "";
    private String strName = "";
    private String strDataType = "";
    private String strTag = "";
    private String strPublic = "";
    private String strSize = "";
    private String strEngine = "";
    private String strTime = "";
    private String strValue = "";

    public FeedDetails() {

    }

    private FeedDetails(Parcel in) {
        readFromParcel(in);
    }

    public String getStrID() {
        return strID;
    }

    public void setStrID(String strID) {
        this.strID = strID;
    }

    public String getStrUserID() {
        return strUserID;
    }

    public void setStrUserID(String strUserID) {
        this.strUserID = strUserID;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrDataType() {
        return strDataType;
    }

    public void setStrDataType(String strDataType) {
        this.strDataType = strDataType;
    }

    public String getStrTag() {
        return strTag;
    }

    public void setStrTag(String strTag) {
        this.strTag = strTag;
    }

    public String getStrPublic() {
        return strPublic;
    }

    public void setStrPublic(String strPublic) {
        this.strPublic = strPublic;
    }

    public String getStrSize() {
        return strSize;
    }

    public void setStrSize(String strSize) {
        this.strSize = strSize;
    }

    public String getStrEngine() {
        return strEngine;
    }

    public void setStrEngine(String strEngine) {
        this.strEngine = strEngine;
    }

    public String getStrTime() {
        return strTime + " Secs Ago";
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strID);
        dest.writeString(strUserID);
        dest.writeString(strName);
        dest.writeString(strDataType);
        dest.writeString(strTag);
        dest.writeString(strPublic);
        dest.writeString(strSize);
        dest.writeString(strEngine);
        dest.writeString(strTime);
        dest.writeString(strValue);

    }

    private void readFromParcel(Parcel in) {

        // We just need to read back each
        // field in the order that it was
        // written to the parcel
        strID = in.readString();
        strUserID = in.readString();
        strName = in.readString();
        strDataType = in.readString();
        strTag = in.readString();
        strPublic = in.readString();
        strSize = in.readString();
        strEngine = in.readString();
        strTime = in.readString();
        strValue = in.readString();

    }
}
