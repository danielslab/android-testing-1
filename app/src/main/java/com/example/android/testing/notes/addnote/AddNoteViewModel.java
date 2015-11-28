/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.notes.addnote;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.android.testing.notes.R;
import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.data.NotesRepository;
import com.example.android.testing.notes.util.ImageFile;
import com.example.android.testing.notes.util.Navigator;
import com.example.android.testing.notes.util.SnackbarMessageManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.cosenonjaviste.mv2m.ActivityResult;
import it.cosenonjaviste.mv2m.ViewModel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link AddNoteFragment}), retrieves the data and updates
 * the UI as required.
 */
public class AddNoteViewModel extends ViewModel<Void, AddNoteModel> {

    public static final int REQUEST_CODE_IMAGE_CAPTURE = 0x1001;

    @NonNull
    private final NotesRepository mNotesRepository;
    @NonNull
    private final ImageFile mImageFile;
    @NonNull
    private final SnackbarMessageManager mSnackbarMessageManager;
    @NonNull
    private final Navigator mNavigator;

    public AddNoteViewModel(@NonNull NotesRepository notesRepository,
                            @NonNull ImageFile imageFile,
                            @NonNull SnackbarMessageManager snackbarMessageManager,
                            @NonNull Navigator navigator) {
        this.mNavigator = checkNotNull(navigator);
        mNotesRepository = checkNotNull(notesRepository);
        mSnackbarMessageManager = checkNotNull(snackbarMessageManager);
        mImageFile = imageFile;
    }

    @NonNull @Override protected AddNoteModel createModel() {
        return new AddNoteModel();
    }

    public void saveNote(String title, String description) {
        String imageUrl = null;
        if (mImageFile.exists()) {
            imageUrl = mImageFile.getPath();
        }
        Note newNote = new Note(title, description, imageUrl);
        if (newNote.isEmpty()) {
            mSnackbarMessageManager.showMessage(view, R.string.empty_note_message);
        } else {
            mNotesRepository.saveNote(newNote);
            mNavigator.finish(view, Activity.RESULT_OK);
        }
    }

    public void takePicture() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        mImageFile.create(imageFileName, ".jpg");

        if (mNavigator.isCameraInstalled()) {
            mNavigator.openCamera(mImageFile.getPath(), view, REQUEST_CODE_IMAGE_CAPTURE);
        } else {
            mSnackbarMessageManager.showMessage(view, R.string.cannot_connect_to_camera_message);
        }
    }

    public void imageAvailable() {
        if (mImageFile.exists()) {
            model.getImageUrl().set(mImageFile.getPath());
        } else {
            imageCaptureFailed();
        }
    }

    public void imageCaptureFailed() {
        captureFailed();
    }

    private void captureFailed() {
        mImageFile.delete();
        mSnackbarMessageManager.showMessage(view, R.string.cannot_connect_to_camera_message);
    }

    @Override public int getOptionMenuId() {
        return R.menu.fragment_addnote_options_menu_actions;
    }

    @Override public boolean onOptionsItemSelected(int itemId) {
        if (itemId == R.id.take_picture) {
            try {
                takePicture();
            } catch (IOException ioe) {
                mSnackbarMessageManager.showMessage(view, R.string.take_picture_error);
            }
            return true;
        }
        return super.onOptionsItemSelected(itemId);
    }

    @Override public void onResult(int requestCode, ActivityResult activityResult) {
        if (REQUEST_CODE_IMAGE_CAPTURE == requestCode && activityResult.isResultOk()) {
            imageAvailable();
        } else {
            imageCaptureFailed();
        }
    }
}
