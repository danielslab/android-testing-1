package com.example.android.testing.notes.notes;

import android.databinding.ObservableArrayList;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.testing.notes.data.Note;

public class NotesModel implements Parcelable {

    private ObservableArrayList<Note> notes;

    public NotesModel() {
        notes = new ObservableArrayList<>();
    }

    protected NotesModel(Parcel in) {
        notes = new ObservableArrayList<>();
        notes.addAll(in.createTypedArrayList(Note.CREATOR));
    }

    public ObservableArrayList<Note> getNotes() {
        return notes;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(notes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotesModel> CREATOR = new Creator<NotesModel>() {
        @Override
        public NotesModel createFromParcel(Parcel in) {
            return new NotesModel(in);
        }

        @Override
        public NotesModel[] newArray(int size) {
            return new NotesModel[size];
        }
    };
}
