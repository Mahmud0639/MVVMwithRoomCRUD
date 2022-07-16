package com.manuni.mvvmwithroomdatabaseu4;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.manuni.mvvmwithroomdatabaseu4.databinding.ActivityMyEditorBinding;
import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;
import com.manuni.mvvmwithroomdatabaseu4.utils.Constants;
import com.manuni.mvvmwithroomdatabaseu4.viewmodels.EditorViewModel;

import java.util.Objects;

public class MyEditorActivity extends AppCompatActivity {

    private static final String EDITING_KEY = "editing_key";
    private ActivityMyEditorBinding binding;
    private EditorViewModel mEditorViewModel;
    private boolean newNotes; // ekhane boolean newNotes neoya hoice eta dekhar jonno new note create kora hobe naki existing diye kaj kora hocce
    //jodi new note create kora na hoy only tokhon e menu inflate kora hobe ta chara inflate kora hobe na
    private boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbarMy;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        //toolBarLayout.setTitle(getTitle());

        //below line of code is used to set any drawable as back press action
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null){
            isEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }


        initViewModel();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_KEY,true);
        super.onSaveInstanceState(outState);
    }

    private void initViewModel(){
        mEditorViewModel = new ViewModelProvider(MyEditorActivity.this,getDefaultViewModelProviderFactory()).get(EditorViewModel.class);
        Observer<NoteEntity> observer = new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                if (noteEntity != null && !isEditing){
                    Objects.requireNonNull(binding.includeId).noteEditTxt.setText(noteEntity.getText());
                }

            }
        };
        mEditorViewModel.list.observe(MyEditorActivity.this,observer);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            binding.toolbarLayout.setTitle("New Note");
            newNotes = true;
        }else {
            binding.toolbarLayout.setTitle("Edit Notes");
            int id = bundle.getInt(Constants.NOTE_ID_KEY);
            mEditorViewModel.loadNote(id);
            newNotes = false;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAndExit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!newNotes){
            getMenuInflater().inflate(R.menu.menu_editor,menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //below home id is reserve and it is used here while we are going to click on the tick button
        if (item.getItemId()== android.R.id.home){
            saveAndExit();
        }else if (item.getItemId()==R.id.deleteNote){
            deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void deleteNote(){
        mEditorViewModel.deleteNote();
    }
    private void saveAndExit(){
        mEditorViewModel.saveAndExit(Objects.requireNonNull(binding.includeId.noteEditTxt).getText().toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}