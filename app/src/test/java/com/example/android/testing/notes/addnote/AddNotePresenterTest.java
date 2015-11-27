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

import com.example.android.testing.notes.R;
import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.data.NotesRepository;
import com.example.android.testing.notes.util.ImageFile;
import com.example.android.testing.notes.util.SnackbarMessageManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link AddNotePresenter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddNotePresenterTest {

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private ImageFile mImageFile;

    @Mock
    private AddNoteContract.View mAddNoteView;

    @Mock
    private SnackbarMessageManager messageManager;

    @InjectMocks
    private AddNotePresenter mAddNotesPresenter;

    @Test
    public void saveNoteToRepository_showsSuccessMessageUi() {
        // When the presenter is asked to save a note
        mAddNotesPresenter.saveNote("New Note Title", "Some Note Description");

        // Then a note is,
        verify(mNotesRepository).saveNote(any(Note.class)); // saved to the model
        verify(mAddNoteView).showNotesList(); // shown in the UI
    }

    @Test
    public void saveNote_emptyNoteShowsErrorUi() {
        // When the presenter is asked to save an empty note
        mAddNotesPresenter.saveNote("", "");

        // Then an empty not error is shown in the UI
        verify(messageManager).showMessage(any(Activity.class), eq(R.string.empty_note_message));
    }

    @Test
    public void takePicture_CreatesFileAndOpensCamera() throws IOException {
        // When the presenter is asked to take an image
        mAddNotesPresenter.takePicture();

        // Then an image file is created snd camera is opened
        verify(mImageFile).create(anyString(), anyString());
        verify(mImageFile).getPath();
        verify(mAddNoteView).openCamera(anyString());
    }

    @Test
    public void imageAvailable_SavesImageAndUpdatesUiWithThumbnail() {
        // Given an a stubbed image file
        String imageUrl = "path/to/file";
        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(imageUrl);

        // When an image is made available to the presenter
        mAddNotesPresenter.imageAvailable();

        // Then the preview image of the stubbed image is shown in the UI
        verify(mAddNoteView).showImagePreview(contains(imageUrl));
    }

    @Test
    public void imageAvailable_FileDoesNotExistShowsErrorUi() {
        // Given the image file does not exist
        when(mImageFile.exists()).thenReturn(false);

        // When an image is made available to the presenter
        mAddNotesPresenter.imageAvailable();

        // Then an error is shown in the UI and the image file is deleted
        verify(messageManager).showMessage(any(Activity.class), eq(R.string.cannot_connect_to_camera_message));
        verify(mImageFile).delete();
    }

    @Test
    public void noImageAvailable_ShowsErrorUi() {
        // When the presenter is notified that image capturing failed
        mAddNotesPresenter.imageCaptureFailed();

        // Then an error is shown in the UI and the image file is deleted
        verify(messageManager).showMessage(any(Activity.class), eq(R.string.cannot_connect_to_camera_message));
        verify(mImageFile).delete();
    }

}
