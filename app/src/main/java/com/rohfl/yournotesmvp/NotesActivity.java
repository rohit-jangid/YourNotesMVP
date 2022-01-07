package com.rohfl.yournotesmvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rohfl.yournotesmvp.adapter.NoteAdapter;
import com.rohfl.yournotesmvp.interactor.Interactor;
import com.rohfl.yournotesmvp.models.Note;
import com.rohfl.yournotesmvp.presenters.NotePresenter;

import java.util.List;

public class NotesActivity extends AppCompatActivity implements Interactor.View {

    EditText noteSearchEditText;
    RecyclerView noteRecyclerView;
    FloatingActionButton floatingActionButton;

    NoteAdapter noteAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    NotePresenter notePresenter;

    boolean isCancel;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateStatusBarColorMain("#FFFFFF");

        setContentView(R.layout.activity_notes_list);

        noteSearchEditText = findViewById(R.id.search_note_et);
        noteRecyclerView = findViewById(R.id.notes_rv);
        floatingActionButton = findViewById(R.id.fab);

        notePresenter = new NotePresenter(this, this);

        noteAdapter = new NoteAdapter(notePresenter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        noteRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        noteRecyclerView.setAdapter(noteAdapter);

        floatingActionButton.setOnClickListener(v -> {
            showAddDialog();
        });

        noteSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                notePresenter.searchNote(editable.toString());
            }
        });

        notePresenter.getAllNotes();

    }


    @Override
    public void showAddDialog() {
        Dialog noteDialog = new Dialog(this);
        noteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        noteDialog.getWindow().getAttributes().windowAnimations = R.style.SlideUpDownAnim;
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_note, null, false);

        noteDialog.setContentView(view);
        noteDialog.getWindow().setGravity(Gravity.CENTER);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noteDialog.setCancelable(true);
        noteDialog.setCanceledOnTouchOutside(false);
        noteDialog.show();
        Window window = noteDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout note_bg = noteDialog.findViewById(R.id.note_bg);

        // init all the views
        ImageView back = noteDialog.findViewById(R.id.back_iv);
        TextView delete = noteDialog.findViewById(R.id.delete_tv);
        EditText noteTitle = noteDialog.findViewById(R.id.title_et);
        EditText noteDescription = noteDialog.findViewById(R.id.description_et);
        TextView cancelTv = noteDialog.findViewById(R.id.cancel_tv);
        TextView saveTv = noteDialog.findViewById(R.id.save_tv);

        isCancel = false;

        back.setOnClickListener(v -> {
            noteDialog.dismiss();
        });

        cancelTv.setOnClickListener(v -> {
            isCancel = true;
            noteDialog.dismiss();
        });

        // default color when a notes is added is white

        color = "#FFFFFF";
        note_bg.setBackgroundColor(Color.parseColor(color));
        saveTv.setText("Add");
        delete.setVisibility(View.GONE);

        saveTv.setOnClickListener(v -> {
            noteDialog.dismiss();
        });

        // views for the color of the note
        View white = noteDialog.findViewById(R.id.white_color);

        white.setOnClickListener(v -> {
            color = "#FFFFFF";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View amber = noteDialog.findViewById(R.id.amber_color);
        amber.setOnClickListener(v -> {
            color = "#FFC107";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View teal = noteDialog.findViewById(R.id.teal_color);
        teal.setOnClickListener(v -> {
            color = "#009688";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View green = noteDialog.findViewById(R.id.green_color);
        green.setOnClickListener(v -> {
            color = "#8BC34A";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View cream = noteDialog.findViewById(R.id.cream_color);
        cream.setOnClickListener(v -> {
            color = "#DCEDC8";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        noteDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!isCancel) {
                    String title = noteTitle.getText().toString().trim();
                    String description = noteDescription.getText().toString().trim();
                    if (title.length() > 0 || description.length() > 0) {
                        notePresenter.addNote(title, description, color);
                    }
                }
                noteSearchEditText.setText(null);
                noteSearchEditText.clearFocus();
                notePresenter.getAllNotes();
            }
        });

    }

    @Override
    public void showUpdateDialog(Note note) {
        Dialog noteDialog = new Dialog(this);
        noteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        noteDialog.getWindow().getAttributes().windowAnimations = R.style.SlideUpDownAnim;
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_note, null, false);

        noteDialog.setContentView(view);
        noteDialog.getWindow().setGravity(Gravity.CENTER);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noteDialog.setCancelable(true);
        noteDialog.setCanceledOnTouchOutside(false);
        noteDialog.show();
        Window window = noteDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout note_bg = noteDialog.findViewById(R.id.note_bg);

        // init all the views
        ImageView back = noteDialog.findViewById(R.id.back_iv);
        TextView delete = noteDialog.findViewById(R.id.delete_tv);
        EditText noteTitle = noteDialog.findViewById(R.id.title_et);
        EditText noteDescription = noteDialog.findViewById(R.id.description_et);
        TextView cancelTv = noteDialog.findViewById(R.id.cancel_tv);
        TextView saveTv = noteDialog.findViewById(R.id.save_tv);

        isCancel = false;

        back.setOnClickListener(v -> {
            noteDialog.dismiss();
        });

        cancelTv.setOnClickListener(v -> {
            isCancel = true;
            noteDialog.dismiss();
        });

        // default color when a notes is added is white
        long noteId = note.getNoteId();
        noteTitle.setText(note.getNoteTitle());
        noteDescription.setText(note.getNoteDescription());
        color = note.getNoteCardColor();
        note_bg.setBackgroundColor(Color.parseColor(color));

        saveTv.setOnClickListener(v -> {
            noteDialog.dismiss();
        });

        delete.setOnClickListener(v -> {
            isCancel = true;
            notePresenter.deleteNote(noteId);
            noteDialog.dismiss();
        });

        // views for the color of the note
        View white = noteDialog.findViewById(R.id.white_color);

        white.setOnClickListener(v -> {
            color = "#FFFFFF";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View amber = noteDialog.findViewById(R.id.amber_color);
        amber.setOnClickListener(v -> {
            color = "#FFC107";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View teal = noteDialog.findViewById(R.id.teal_color);
        teal.setOnClickListener(v -> {
            color = "#009688";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View green = noteDialog.findViewById(R.id.green_color);
        green.setOnClickListener(v -> {
            color = "#8BC34A";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        View cream = noteDialog.findViewById(R.id.cream_color);
        cream.setOnClickListener(v -> {
            color = "#DCEDC8";
            note_bg.setBackgroundColor(Color.parseColor(color));
        });

        noteDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!isCancel) {
                    String title = noteTitle.getText().toString().trim();
                    String description = noteDescription.getText().toString().trim();
                    notePresenter.updateNote(noteId, title, description, color);
                }
                noteSearchEditText.setText(null);
                noteSearchEditText.clearFocus();
                notePresenter.getAllNotes();
            }
        });

    }

    @Override
    public void updateNoteList() {
        noteAdapter.notifyDataSetChanged();
    }

    @SuppressLint("ObsoleteSdkInt")
    public void updateStatusBarColorMain(String color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor(color));
                window.setNavigationBarColor(Color.parseColor(color));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int systemUiVisibility = this.getWindow().getDecorView().getSystemUiVisibility();
                int flagLightStatusBar = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                systemUiVisibility |= flagLightStatusBar;
                this.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notePresenter.onDestroy();
    }
}