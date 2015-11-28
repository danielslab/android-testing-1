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
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.testing.notes.Injection;
import com.example.android.testing.notes.R;
import com.example.android.testing.notes.databinding.FragmentAddnoteBinding;
import com.example.android.testing.notes.util.Navigator;
import com.example.android.testing.notes.util.SnackbarMessageManager;

import java.io.IOException;

import it.cosenonjaviste.mv2m.ViewModelFragment;

/**
 * Main UI for the add note screen. Users can enter a note title and description. Images can be
 * added to notes by clicking on the options menu.
 */
public class AddNoteFragment extends ViewModelFragment<AddNoteViewModel> {

    private FragmentAddnoteBinding binding;

    public static AddNoteFragment newInstance() {
        return new AddNoteFragment();
    }

    @Override public AddNoteViewModel createViewModel() {
        return new AddNoteViewModel(Injection.provideNotesRepository(),
                Injection.provideImageFile(), new SnackbarMessageManager(), new Navigator(getActivity().getApplication()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_notes);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.saveNote(binding.addNoteTitle.getText().toString(),
                        binding.addNoteDescription.getText().toString());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddnoteBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.take_picture:
                try {
                    viewModel.takePicture();
                } catch (IOException ioe) {
                    if (getView() != null) {
                        Snackbar.make(getView(), getString(R.string.take_picture_error),
                                Snackbar.LENGTH_LONG).show();
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_addnote_options_menu_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If an image is received, display it on the ImageView.
        if (AddNoteViewModel.REQUEST_CODE_IMAGE_CAPTURE == requestCode && Activity.RESULT_OK == resultCode) {
            viewModel.imageAvailable();
        } else {
            viewModel.imageCaptureFailed();
        }
    }
}
