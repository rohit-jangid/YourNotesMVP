package com.rohfl.yournotesmvp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohfl.yournotesmvp.holder.NoteViewHolder;
import com.rohfl.yournotesmvp.models.Note;
import com.rohfl.yournotesmvp.presenters.NotePresenter;
import com.rohfl.yournotesmvp.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    NotePresenter mNotePresenter;

    public NoteAdapter(NotePresenter mNotePresenter) {
        this.mNotePresenter = mNotePresenter;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            mNotePresenter.getNoteDetail(position);
        });
        mNotePresenter.bindDetails(holder, position);
    }

    @Override
    public int getItemCount() {
        return mNotePresenter.getListCount();
    }

}
