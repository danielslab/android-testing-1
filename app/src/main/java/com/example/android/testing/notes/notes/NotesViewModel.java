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

package com.example.android.testing.notes.notes;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.example.android.testing.notes.R;
import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.data.NotesRepository;
import com.example.android.testing.notes.util.Navigator;
import com.example.android.testing.notes.util.SnackbarMessageManager;

import java.util.List;

import it.cosenonjaviste.mv2m.ActivityResult;
import it.cosenonjaviste.mv2m.ViewModel;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Listens to user actions from the UI ({@link NotesFragment}), retrieves the data and updates the
 * UI as required.
 */
public class NotesViewModel extends ViewModel<Void, NotesModel> {

    public static final int REQUEST_ADD_NOTE = 1;

    private final NotesRepository mNotesRepository;
    private final Navigator mNavigator;
    private final SnackbarMessageManager mMessageManager;

    private ObservableBoolean loading = new ObservableBoolean();

    public NotesViewModel(
            @NonNull NotesRepository notesRepository,
            @NonNull Navigator navigator,
            @NonNull SnackbarMessageManager messageManager) {
        this.mMessageManager = checkNotNull(messageManager, "messageManager cannot be null");
        this.mNavigator = checkNotNull(navigator, "navigator cannot be null");
        mNotesRepository = checkNotNull(notesRepository, "notesRepository cannot be null");
    }

    @NonNull @Override protected NotesModel createModel() {
        return new NotesModel();
    }

    @Override public void resume() {
        super.resume();
        loadNotes(false);
    }

    public void refresh() {
        loadNotes(true);
    }

    public void loadNotes(boolean forceUpdate) {
        loading.set(true);
        if (forceUpdate) {
            mNotesRepository.refreshData();
        }

        mNotesRepository.getNotes(new NotesRepository.LoadNotesCallback() {
            @Override
            public void onNotesLoaded(List<Note> notes) {
                loading.set(false);
                model.getNotes().clear();
                model.getNotes().addAll(notes);
            }
        });
    }

    public void addNewNote() {
        mNavigator.showAddNote(view);
    }

    public void openNoteDetails(@NonNull Note requestedNote) {
        checkNotNull(requestedNote, "requestedNote cannot be null!");
        mNavigator.showNoteDetailUi(view, requestedNote.getId());
    }

    @Override public void onResult(int requestCode, ActivityResult activityResult) {
        if (REQUEST_ADD_NOTE == requestCode && activityResult.isResultOk()) {
            mMessageManager.showMessage(view, R.string.successfully_saved_note_message);
        }
    }

    public ObservableBoolean getLoading() {
        return loading;
    }
}
