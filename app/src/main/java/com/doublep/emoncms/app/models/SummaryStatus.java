package com.doublep.emoncms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Paul Patchell on 02/06/2014.
 */
public class SummaryStatus implements Parcelable {

    // Basic Club Details

    @SuppressWarnings("rawtypes")
    public static final Creator CREATOR = new Creator() {
        @Override
        public SummaryStatus createFromParcel(Parcel in) {
            return new SummaryStatus(in);
        }

        @Override
        public SummaryStatus[] newArray(int size) {
            return new SummaryStatus[size];
        }
    };
    private String strRaspPiStatus = "";
    private String strFeedsGood = "";
    private String strFeedsBad = "";


    public SummaryStatus() {

    }

    private SummaryStatus(Parcel in) {
        readFromParcel(in);
    }

    public String getStrRaspPiStatus() {
        return strRaspPiStatus;
    }

    public void setStrRaspPiStatus(String strRaspPiStatus) {
        if (strRaspPiStatus.equalsIgnoreCase("true")) {
            String strRaspPiStatusUp = "RFM12 to Pi interface script is up and running";
            this.strRaspPiStatus = strRaspPiStatusUp;
        }
        else {
            String strRaspPiStatusDown = "RFM12 to Pi interface script is DOWN!!!";
            this.strRaspPiStatus = strRaspPiStatusDown;
        }
    }

    public String getStrFeedsGood() {
        return strFeedsGood;
    }

    public void setStrFeedsGood(String strFeedsGood) {
        this.strFeedsGood = strFeedsGood;
    }

    public String getStrFeedsBad() {
        return strFeedsBad;
    }

    public void setStrFeedsBad(String strFeedsBad) {
        this.strFeedsBad = strFeedsBad;
    }


    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strRaspPiStatus);
        dest.writeString(strFeedsGood);
        dest.writeString(strFeedsBad);


    }

    private void readFromParcel(Parcel in) {

        // We just need to read back each
        // field in the order that it was
        // written to the parcel
        strRaspPiStatus = in.readString();
        strFeedsGood = in.readString();
        strFeedsBad = in.readString();


    }
}
