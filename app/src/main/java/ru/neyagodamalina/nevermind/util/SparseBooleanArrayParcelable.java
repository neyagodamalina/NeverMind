package ru.neyagodamalina.nevermind.util;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

public class SparseBooleanArrayParcelable extends SparseBooleanArray implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSparseBooleanArray(this);
    }

    public static Parcelable.Creator<SparseBooleanArrayParcelable> CREATOR = new Parcelable.Creator<SparseBooleanArrayParcelable>(){

        @Override
        public SparseBooleanArrayParcelable createFromParcel(Parcel source) {
            return (SparseBooleanArrayParcelable) source.readSparseBooleanArray();
        }

        @Override
        public SparseBooleanArrayParcelable[] newArray(int size) {
            return new SparseBooleanArrayParcelable[size];
        }
    };
}
