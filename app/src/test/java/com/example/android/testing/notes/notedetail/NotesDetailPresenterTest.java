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

import com.example.android.testing.notes.R;
import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.data.NotesRepository;
import com.example.android.testing.notes.util.StringTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link NoteDetailViewModel}
 */
@RunWith(MockitoJUnitRunner.class)
public class NotesDetailPresenterTest {

    public static final String INVALID_ID = "INVALID_ID";

    public static final String TITLE_TEST = "title";

    public static final String DESCRIPTION_TEST = "description";
    public static final String STRING = "string-";

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private StringTranslator stringTranslator;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackCaptor;

    @InjectMocks
    private NoteDetailViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        when(stringTranslator.getString(anyInt())).thenAnswer(new Answer<String>() {
            @Override public String answer(InvocationOnMock invocation) throws Throwable {
                return STRING + invocation.getArguments()[0];
            }
        });
    }

    @Test
    public void getNoteFromRepositoryAndLoadIntoView() {
        // Given an initialized NoteDetailPresenter with stubbed note
        Note note = new Note(TITLE_TEST, DESCRIPTION_TEST);

        // When notes presenter is asked to open a note
        NoteDetailModel model = viewModel.initAndResume(note.getId());

        // Then note is loaded from model, callback is captured and progress indicator is shown
        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackCaptor.capture());
        assertThat(model.getDescription().get()).isEqualTo(STRING + R.string.loading);

        // When note is finally loaded
        mGetNoteCallbackCaptor.getValue().onNoteLoaded(note); // Trigger callback

        // Then progress indicator is hidden and title and description are shown in UI
        assertThat(model.getTitle().get()).isEqualTo(TITLE_TEST);
        assertThat(model.getDescription().get()).isEqualTo(DESCRIPTION_TEST);
    }

    @Test
    public void getUnknownNoteFromRepositoryAndLoadIntoView() {
        // When loading of a note is requested with an invalid note ID.
        NoteDetailModel model = viewModel.initAndResume(INVALID_ID);

        // Then note with invalid id is attempted to load from model, callback is captured and
        // progress indicator is shown.
        assertThat(model.getDescription().get()).isEqualTo(STRING + R.string.loading);
        verify(mNotesRepository).getNote(eq(INVALID_ID), mGetNoteCallbackCaptor.capture());

        // When note is finally loaded
        mGetNoteCallbackCaptor.getValue().onNoteLoaded(null); // Trigger callback

        // Then progress indicator is hidden and missing note UI is shown
        assertThat(model.getDescription().get()).isEqualTo("string-" + R.string.no_data);
    }
}
