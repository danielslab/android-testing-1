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

package com.example.android.testing.notes.notedetail;

import android.support.annotation.NonNull;

import com.example.android.testing.notes.R;
import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.data.NotesRepository;
import com.example.android.testing.notes.util.StringTranslator;

import it.cosenonjaviste.mv2m.ViewModel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link NoteDetailFragment}), retrieves the data and updates
 * the UI as required.
 */
public class NoteDetailViewModel extends ViewModel<String, NoteDetailModel> {

    private final NotesRepository mNotesRepository;

    private final StringTranslator mStringTranslator;

    private boolean loading;

    public NoteDetailViewModel(@NonNull NotesRepository notesRepository,
                               @NonNull StringTranslator stringTranslator) {
        mNotesRepository = checkNotNull(notesRepository, "notesRepository cannot be null!");
        mStringTranslator = checkNotNull(stringTranslator, "stringTranslator cannot be null!");
    }

    @NonNull @Override protected NoteDetailModel createModel() {
        return new NoteDetailModel();
    }

    @Override public void resume() {
        super.resume();
        if (!loading && !model.isLoaded()) {
            loading = true;
            if (null == argument || argument.isEmpty()) {
                model.getTitle().set("");
                model.getDescription().set(mStringTranslator.getString(R.string.no_data));
                model.setLoaded(true);
                return;
            }

            model.getTitle().set("");
            model.getDescription().set(mStringTranslator.getString(R.string.loading));
            mNotesRepository.getNote(argument, new NotesRepository.GetNoteCallback() {
                @Override
                public void onNoteLoaded(Note note) {
                    if (null == note) {
                        model.getTitle().set("");
                        model.getDescription().set(mStringTranslator.getString(R.string.no_data));
                    } else {
                        model.getTitle().set(note.getTitle());
                        model.getDescription().set(note.getDescription());
                        model.getImageUrl().set(note.getImageUrl());
                    }
                    model.setLoaded(true);
                }
            });
        }
    }
}
