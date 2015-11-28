package com.example.android.testing.notes.util;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.android.testing.notes.addnote.AddNoteActivity;
import com.example.android.testing.notes.notedetail.NoteDetailActivity;
import com.example.android.testing.notes.notes.NotesViewModel;

import it.cosenonjaviste.mv2m.Mv2mView;

public class Navigator {

    private Application application;

    public Navigator(Application application) {
        this.application = application;
    }

    public boolean isCameraInstalled() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return takePictureIntent.resolveActivity(application.getPackageManager()) != null;
    }

    public void openCamera(String saveTo, Mv2mView view, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(saveTo));
        view.startActivityForResult(takePictureIntent, requestCode);
    }

    public void finish(Mv2mView view, int resultCode) {
        view.getActivity().setResult(resultCode);
        view.getActivity().finish();
    }

    public void showAddNote(Mv2mView view) {
        Intent intent = new Intent(view.getActivity(), AddNoteActivity.class);
        view.startActivityForResult(intent, NotesViewModel.REQUEST_ADD_NOTE);
    }

    public void showNoteDetailUi(Mv2mView view, String noteId) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
        Intent intent = new Intent(view.getActivity(), NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, noteId);
        view.getActivity().startActivity(intent);
    }
}
