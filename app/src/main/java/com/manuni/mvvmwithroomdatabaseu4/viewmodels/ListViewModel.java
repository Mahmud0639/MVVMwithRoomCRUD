package com.manuni.mvvmwithroomdatabaseu4.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.manuni.mvvmwithroomdatabaseu4.Utils;
import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;
import com.manuni.mvvmwithroomdatabaseu4.repository.AppRepository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> sampleDataForViewModel;
    private AppRepository appRepository;

    public ListViewModel(@NonNull Application application) {
        super(application);

        appRepository = AppRepository.getInstance(application.getApplicationContext());
        sampleDataForViewModel = appRepository.noteEntitiesForRepository;

    }
    public void addSampleData(){
        appRepository.addSampleData();

    }

    public void deleteSampleData() {
        appRepository.deleteSampleAllData();
    }

    public void deleteNote(NoteEntity noteAtPosition) {
        appRepository.deleteNote(noteAtPosition);
        //above deleteNote method is already there so no need to again create into the appRepository class
    }
}
