package com.example.android.testing.notes.notes;

import com.example.android.testing.notes.BR;
import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.databinding.ItemNoteBinding;

import it.cosenonjaviste.mv2m.recycler.BaseBindableViewHolder;

public class NoteViewHolder extends BaseBindableViewHolder<ItemNoteBinding, Note> {
    private final NotesViewModel viewModel;

    protected NoteViewHolder(ItemNoteBinding binding, NotesViewModel viewModel) {
        super(binding, BR.viewHolder);
        this.viewModel = viewModel;
    }

    public void openNoteDetails() {
        viewModel.openNoteDetails(item.get());
    }
}
