package com.rohfl.yournotesmvp.presenters;

import android.content.Context;
import android.view.View;

import com.rohfl.yournotesmvp.db.Database;
import com.rohfl.yournotesmvp.holder.NoteViewHolder;
import com.rohfl.yournotesmvp.interactor.Interactor;
import com.rohfl.yournotesmvp.models.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotePresenter implements Interactor.Presenter {

    private Context mContext;
    private Interactor.View view;
    private Database mDatabase;
    private List<Note> mList = new ArrayList<>();
    private List<Note> mainList = new ArrayList<>();

    public NotePresenter(Context mContext, Interactor.View view) {
        this.mContext = mContext;
        this.view = view;
        this.mDatabase = new Database(mContext);
    }

    @Override
    public void getNoteDetail(int id) {
        Note mNote = mDatabase.getNote(mList.get(id).getNoteId());
        view.showUpdateDialog(mNote);
    }

    @Override
    public void getAllNotes() {
        mList.clear();
        mainList = mDatabase.getAllNotes();
        mList.addAll(mainList);
        if(mList != null)
            view.updateNoteList();
    }

    @Override
    public void addNote(String title, String description, String color) {
        Note note = new Note(title,description, color);
        mDatabase.addNote(note);
        getAllNotes();
    }

    @Override
    public void updateNote(long noteId, String title, String description, String color) {
        Note note = new Note(noteId, title,description, color);
        mDatabase.updateNote(note);
        getAllNotes();
    }

    @Override
    public void searchNote(String searchText) {
        mList.clear();
        for(Note note: mainList) {
            if(note.getNoteTitle().contains(searchText.toLowerCase(Locale.ROOT)) ||
                note.getNoteDescription().contains(searchText.toLowerCase(Locale.ROOT))) {
                mList.add(note);
            }
        }
        view.updateNoteList();
    }

    @Override
    public void bindDetails(NoteViewHolder noteViewHolder, int position) {
        noteViewHolder.setTitle(mList.get(position).getNoteTitle());
        noteViewHolder.setDescription(mList.get(position).getNoteDescription());
        noteViewHolder.setNoteColor(mList.get(position).getNoteCardColor());
    }

    @Override
    public int getListCount() {
        return this.mList.size();
    }

    @Override
    public void deleteNote(long id) {
        mDatabase.deleteNote(id);
        getAllNotes();
    }

    @Override
    public void onDestroy() {
        this.mContext = null;
        this.view = null;
    }

}
