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
    private String strUpdated = "";
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
        if(strDataType.equalsIgnoreCase("1")) {
            this.strDataType = "REALTIME";
        }
        else if(strDataType.equalsIgnoreCase("2")){
            this.strDataType = "DAILY";
        }
        else if(strDataType.equalsIgnoreCase("3")){
            this.strDataType = "HISTOGRAM";
        }
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
        if(strPublic.equalsIgnoreCase("1")) {
            this.strPublic = "Yes";
        }
        else {
            this.strPublic = "No";
        }
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
        if(strEngine.equalsIgnoreCase("0")) {
            this.strEngine = "MYSQL";
        }
        else if(strEngine.equalsIgnoreCase("1")) {
            this.strEngine = "TIMESTORE";
        }
        else if(strEngine.equalsIgnoreCase("2")) {
            this.strEngine = "PHPTIMESERIES";
        }
        else if(strEngine.equalsIgnoreCase("3")) {
            this.strEngine = "GRAPHITE";
        }
        else if(strEngine.equalsIgnoreCase("4")) {
            this.strEngine = "PHPTIMESTORE";
        }
        else if(strEngine.equalsIgnoreCase("5")) {
            this.strEngine = "PHPFINA";
        }
        else if(strEngine.equalsIgnoreCase("6")) {
            this.strEngine = "PHPFIWA";
        }
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }


    public String getStrUpdated() {
        return strUpdated;
    }

    public void setStrUpdated(String strUpdated) {
        this.strUpdated = strUpdated;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    @Override
    public int describeContents() {

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
        dest.writeString(strUpdated);
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
        strUpdated = in.readString();
        strValue = in.readString();

    }
}
