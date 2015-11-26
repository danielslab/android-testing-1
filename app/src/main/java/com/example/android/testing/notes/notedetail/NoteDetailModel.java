package com.example.android.testing.notes.notedetail;

import android.databinding.ObservableBoolean;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.testing.notes.util.ObservableString;

public class NoteDetailModel implements Parcelable {

    private ObservableString title;

    private ObservableString description;

    private ObservableString imageUrl;

    private ObservableBoolean missingNote;

    private boolean loaded;

    public NoteDetailModel() {
        title = new ObservableString();
        description = new ObservableString();
        imageUrl = new ObservableString();
        missingNote = new ObservableBoolean();
    }

    protected NoteDetailModel(Parcel in) {
        title = in.readParcelable(ObservableString.class.getClassLoader());
        description = in.readParcelable(ObservableString.class.getClassLoader());
        imageUrl = in.readParcelable(ObservableString.class.getClassLoader());
        missingNote = in.readParcelable(ObservableBoolean.class.getClassLoader());
        loaded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(title, flags);
        dest.writeParcelable(description, flags);
        dest.writeParcelable(imageUrl, flags);
        dest.writeParcelable(missingNote, flags);
        dest.writeByte((byte) (loaded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteDetailModel> CREATOR = new Creator<NoteDetailModel>() {
        @Override
        public NoteDetailModel createFromParcel(Parcel in) {
            return new NoteDetailModel(in);
        }

        @Override
        public NoteDetailModel[] newArray(int size) {
            return new NoteDetailModel[size];
        }
    };

    public ObservableString getTitle() {
        return title;
    }

    public ObservableString getDescription() {
        return description;
    }

    public ObservableString getImageUrl() {
        return imageUrl;
    }

    public ObservableBoolean getMissingNote() {
        return missingNote;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
