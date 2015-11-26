/*
 * Copyright (C) 2015 The Android Open Source Project
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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.testing.notes.Injection;
import com.example.android.testing.notes.databinding.FragmentDetailBinding;
import com.example.android.testing.notes.util.StringTranslator;

import it.cosenonjaviste.mv2m.ArgumentManager;
import it.cosenonjaviste.mv2m.ViewModelFragment;

/**
 * Main UI for the note detail screen.
 */
public class NoteDetailFragment extends ViewModelFragment<NoteDetailViewModel> {

    public static NoteDetailFragment newInstance(String noteId) {
        Bundle arguments = ArgumentManager.writeArgument(new Bundle(), noteId);
        NoteDetailFragment fragment = new NoteDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override public NoteDetailViewModel createViewModel() {
        return new NoteDetailViewModel(Injection.provideNotesRepository(), new StringTranslator(getActivity().getApplication()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDetailBinding binding = FragmentDetailBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
