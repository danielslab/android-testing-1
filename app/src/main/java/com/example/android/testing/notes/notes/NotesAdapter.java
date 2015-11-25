package com.example.android.testing.notes.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.testing.notes.data.Note;
import com.example.android.testing.notes.databinding.ItemNoteBinding;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> mNotes;
    private NotesFragment.NoteItemListener mItemListener;

    public NotesAdapter(List<Note> notes, NotesFragment.NoteItemListener itemListener) {
        setList(notes);
        mItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemNoteBinding binding = ItemNoteBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding, mItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Note note = mNotes.get(position);

        viewHolder.binding.setNote(note);
        viewHolder.binding.executePendingBindings();
    }

    public void replaceData(List<Note> notes) {
        setList(notes);
        notifyDataSetChanged();
    }

    private void setList(List<Note> notes) {
        mNotes = checkNotNull(notes);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public Note getItem(int position) {
        return mNotes.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ItemNoteBinding binding;
        private NotesFragment.NoteItemListener mItemListener;

        public ViewHolder(ItemNoteBinding binding, NotesFragment.NoteItemListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            mItemListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Note note = getItem(position);
            mItemListener.onNoteClick(note);

        }
    }
}
