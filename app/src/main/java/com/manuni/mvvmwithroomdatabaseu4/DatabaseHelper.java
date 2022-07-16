package com.manuni.mvvmwithroomdatabaseu4;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;

@Database(entities = {NoteEntity.class},exportSchema = false,version = 1)
@TypeConverters(DateConverter.class)
public abstract class DatabaseHelper extends RoomDatabase {
    public static volatile DatabaseHelper instance;

    public abstract NotesDao notesDao();

    //here volatile means every thread will access this instance variable from main memory every time not from it's cache and synchronize means only one thread can be access at a time
    private static final Object LOCK = new Object();
    public static DatabaseHelper getInstance(Context context){
        if (instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),DatabaseHelper.class,DatabaseUtils.DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
