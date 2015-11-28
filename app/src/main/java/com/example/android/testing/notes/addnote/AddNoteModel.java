package com.example.android.testing.notes.addnote;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.testing.notes.util.ObservableString;

public class AddNoteModel implements Parcelable {

    private ObservableString imageUrl;

    public AddNoteModel() {
        imageUrl = new ObservableString();
    }

    protected AddNoteModel(Parcel in) {
        imageUrl = in.readParcelable(ObservableString.class.getClassLoader());
    }

    public ObservableString getImageUrl() {
        return imageUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(imageUrl, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddNoteModel> CREATOR = new Creator<AddNoteModel>() {
        @Override
        public AddNoteModel createFromParcel(Parcel in) {
            return new AddNoteModel(in);
        }

        @Override
        public AddNoteModel[] newArray(int size) {
            return new AddNoteModel[size];
        }
    };
}
