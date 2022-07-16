package com.manuni.mvvmwithroomdatabaseu4.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;
import com.manuni.mvvmwithroomdatabaseu4.repository.AppRepository;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    public MutableLiveData<NoteEntity> list = new MutableLiveData<>();
    private AppRepository appRepository;

    public EditorViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void loadNote(int id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity noteEntity = appRepository.loadNote(id);
               /* below line of code is used while we used the main thread although here the MutableLiveData is run in the Main Thread, it will throw an exception.
                And viewModel is trying to get data within background thread. So to overcome this problem we have to use the postValue method instead of setValue.*/
                //list.setValue(noteEntity);
                list.postValue(noteEntity);
            }
        });
    }

    public void saveAndExit(String noteTxt) {
       NoteEntity entity = list.getValue();
       if (entity == null){
            if (TextUtils.isEmpty(noteTxt.trim())){
                return;
            }else {
                entity = new NoteEntity(new Date(),noteTxt.trim());
            }
       }else {
           entity.setText(noteTxt.trim());
           entity.setDate(new Date());
       }
        appRepository.insertNote(entity);
    }

    public void deleteNote() {
        appRepository.deleteNote(list.getValue());
    }
}
