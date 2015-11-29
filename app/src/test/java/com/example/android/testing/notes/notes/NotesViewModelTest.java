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

import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.data.NotesRepository;
import com.example.android.testing.notes.data.NotesRepository.LoadNotesCallback;
import com.example.android.testing.notes.util.Navigator;
import com.example.android.testing.notes.util.SnackbarMessageManager;
import com.google.common.collect.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import it.cosenonjaviste.mv2m.ActivityHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link NotesViewModel}
 */
@RunWith(MockitoJUnitRunner.class)
public class NotesViewModelTest {

    private static List<Note> NOTES = Lists.newArrayList(new Note("Title1", "Description1"),
            new Note("Title2", "Description2"));

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private Navigator mNavigator;

    @Mock
    private SnackbarMessageManager mMessageManager;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<LoadNotesCallback> mLoadNotesCallbackCaptor;

    @InjectMocks
    private NotesViewModel viewModel;

    @Test
    public void loadNotesFromRepositoryAndLoadIntoView() {
        // Given an initialized NotesPresenter with initialized notes
        // When loading of Notes is requested
        NotesModel model = viewModel.initAndResume();

        // Callback is captured and invoked with stubbed notes
        verify(mNotesRepository).getNotes(mLoadNotesCallbackCaptor.capture());
        mLoadNotesCallbackCaptor.getValue().onNotesLoaded(NOTES);

        // Then progress indicator is hidden and notes are shown in UI
        assertThat(viewModel.getLoading().get()).isEqualTo(false);
        assertThat(model.getNotes()).isEqualTo(NOTES);
    }

    @Test
    public void clickOnFab_ShowsAddsNoteUi() {
        // When adding a new note
        viewModel.addNewNote();

        // Then add note UI is shown
        verify(mNavigator).showAddNote(any(ActivityHolder.class));
    }

    @Test
    public void clickOnNote_ShowsDetailUi() {
        // Given a stubbed note
        Note requestedNote = new Note("Details Requested", "For this note");

        // When open note details is requested
        viewModel.openNoteDetails(requestedNote);

        // Then note detail UI is shown
        verify(mNavigator).showNoteDetailUi(any(ActivityHolder.class), any(String.class));
    }
}
