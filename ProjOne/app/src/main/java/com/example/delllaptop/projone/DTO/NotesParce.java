package com.example.delllaptop.projone.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Dell Laptop on 3/17/2018.
 */

public class NotesParce implements Parcelable {
    ArrayList<String> notes;

    public NotesParce() {
    }

    protected NotesParce(Parcel in) {
        notes = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(notes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotesParce> CREATOR = new Creator<NotesParce>() {
        @Override
        public NotesParce createFromParcel(Parcel in) {
            return new NotesParce(in);
        }

        @Override
        public NotesParce[] newArray(int size) {
            return new NotesParce[size];
        }
    };

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }
}
