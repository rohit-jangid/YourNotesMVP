package com.rohfl.yournotesmvp.interactor;

import com.rohfl.yournotesmvp.holder.NoteViewHolder;
import com.rohfl.yournotesmvp.models.Note;

import java.util.List;

public interface Interactor {

    interface View {
        void showAddDialog();
        void showUpdateDialog(Note note);
        void updateNoteList();
    }

    interface Presenter {
        void getAllNotes();
        void addNote(String title, String description, String color);
        void updateNote(long noteId,String title, String description, String color);
        void searchNote(String searchText);
        void getNoteDetail(int id);
        void onDestroy();
        void bindDetails(NoteViewHolder noteViewHolder, int position);
        int getListCount();
        void deleteNote(long id);
    }

    interface Holder {
        void setTitle(String title);
        void setDescription(String description);
        void setNoteColor(String color);
    }

}
