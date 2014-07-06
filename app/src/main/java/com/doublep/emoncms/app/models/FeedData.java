package com.doublep.emoncms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable for Feed Data
 */
public class FeedData implements Parcelable {

    // Basic Club Details

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<FeedData> CREATOR = new Parcelable.Creator<FeedData>() {
        @Override
        public FeedData createFromParcel(Parcel in) {
            return new FeedData(in);
        }

        public FeedData[] newArray(int size) {
            return new FeedData[size];
        }
    };
    private long feedTime;
    private double feedData;

    public FeedData() {

    }

    private FeedData(Parcel in) {
        readFromParcel(in);
    }

    public long getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(long feedTime) {
        this.feedTime = feedTime;
    }

    public double getFeedData() {
        return feedData;
    }

    public void setFeedData(double feedData) {
        this.feedData = feedData;
    }


    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(feedTime);
        dest.writeDouble(feedData);


    }

    private void readFromParcel(Parcel in) {

        // We just need to read back each
        // field in the order that it was
        // written to the parcel
        feedTime = in.readLong();
        feedData = in.readDouble();


    }
}
