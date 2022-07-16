package com.manuni.mvvmwithroomdatabaseu4.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.manuni.mvvmwithroomdatabaseu4.DatabaseHelper;
import com.manuni.mvvmwithroomdatabaseu4.Utils;
import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    // we are using singleton here
    public static  AppRepository ourInstance;
    public DatabaseHelper databaseHelper;
    //video 14
    //we used here Executor because we want to run our room database into the background thread

    public LiveData<List<NoteEntity>> noteEntitiesForRepository;
    private final Executor mExecutors = Executors.newSingleThreadExecutor();
    public static AppRepository getInstance(Context context){
        return ourInstance = new AppRepository(context);
    }
    private AppRepository(Context context){
        databaseHelper = DatabaseHelper.getInstance(context);
        noteEntitiesForRepository = getAllNotes();
    }
    public void addSampleData(){
        mExecutors.execute(new Runnable() {
            @Override
            public void run() {
                databaseHelper.notesDao().insertAll(Utils.getSampleData());
            }
        });

    }
    //this is the first getting point from room database(this is the source for getting data)
    private LiveData<List<NoteEntity>> getAllNotes(){
        return databaseHelper.notesDao().getAllNotes();
    }

    public void deleteSampleAllData() {
        mExecutors.execute(new Runnable() {
            @Override
            public void run() {
                databaseHelper.notesDao().deleteAllNotes();
            }
        });
    }

    public NoteEntity loadNote(int id) {
        //below line is already in the background thread. So there is no need to write below line under executor that means in the background thread. I mean here the loadNote method is called under the background thread
        return databaseHelper.notesDao().getNoteById(id);
    }

    public void insertNote(NoteEntity entity) {
        mExecutors.execute(new Runnable() {
            @Override
            public void run() {
                databaseHelper.notesDao().insetNotes(entity);
            }
        });
    }

    public void deleteNote(NoteEntity value) {
        mExecutors.execute(new Runnable() {
            @Override
            public void run() {
                databaseHelper.notesDao().deleteNotes(value);
            }
        });
    }
}
