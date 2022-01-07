package com.rohfl.yournotesmvp.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohfl.yournotesmvp.R;
import com.rohfl.yournotesmvp.interactor.Interactor;

public class NoteViewHolder extends RecyclerView.ViewHolder implements Interactor.Holder {

    private LinearLayout noteBg;
    private TextView noteTitle, noteDescription;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        noteBg = itemView.findViewById(R.id.note_bg);
        noteTitle = itemView.findViewById(R.id.note_title_tv);
        noteDescription = itemView.findViewById(R.id.note_description_tv);

    }

    @Override
    public void setTitle(String title) {
        noteTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        noteDescription.setText(description);
    }

    @Override
    public void setNoteColor(String color) {
        noteBg.setBackgroundColor(Color.parseColor(color));
    }
}
