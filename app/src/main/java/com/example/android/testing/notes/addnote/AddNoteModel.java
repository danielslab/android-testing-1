package com.example.android.testing.notes.addnote;

import android.os.Parcel;
import android.os.Parcelable;

import it.cosenonjaviste.twowaydatabinding.ObservableString;

public class AddNoteModel implements Parcelable {

    private ObservableString title;

    private ObservableString description;

    private ObservableString imageUrl;

    public AddNoteModel() {
        imageUrl = new ObservableString();
        title = new ObservableString();
        description = new ObservableString();
    }

    protected AddNoteModel(Parcel in) {
        title = in.readParcelable(ObservableString.class.getClassLoader());
        description = in.readParcelable(ObservableString.class.getClassLoader());
        imageUrl = in.readParcelable(ObservableString.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(title, flags);
        dest.writeParcelable(description, flags);
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

    public ObservableString getImageUrl() {
        return imageUrl;
    }

    public ObservableString getTitle() {
        return title;
    }

    public ObservableString getDescription() {
        return description;
    }
}
