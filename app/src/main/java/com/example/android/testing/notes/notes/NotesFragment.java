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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.testing.notes.Injection;
import com.example.android.testing.notes.R;
import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.databinding.FragmentNotesBinding;
import com.example.android.testing.notes.databinding.ItemNoteBinding;
import com.example.android.testing.notes.util.Navigator;
import com.example.android.testing.notes.util.SnackbarMessageManager;

import it.cosenonjaviste.mv2m.ViewModelFragment;
import it.cosenonjaviste.mv2m.recycler.BindableAdapter;
import it.cosenonjaviste.mv2m.recycler.BindableViewHolder;

/**
 * Display a grid of {@link Note}s
 */
public class NotesFragment extends ViewModelFragment<NotesViewModel> {

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override public NotesViewModel createViewModel() {
        return new NotesViewModel(Injection.provideNotesRepository(),
                new Navigator(getActivity().getApplication()), new SnackbarMessageManager());
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNotesBinding binding = FragmentNotesBinding.inflate(inflater, container, false);
        binding.notesList.setAdapter(new BindableAdapter<>(viewModel.getModel().getNotes(), new BindableAdapter.ViewHolderFactory<Note>() {
            @Override public BindableViewHolder<Note> create(ViewGroup viewGroup) {
                return new NoteViewHolder(ItemNoteBinding.inflate(inflater, viewGroup, false), viewModel);
            }
        }));

        int numColumns = getContext().getResources().getInteger(R.integer.num_notes_columns);

        binding.notesList.setHasFixedSize(true);
        binding.notesList.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        binding.setViewModel(viewModel);

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_notes);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addNewNote();
            }
        });

        // Pull-to-refresh
        binding.refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        return binding.getRoot();
    }
}
