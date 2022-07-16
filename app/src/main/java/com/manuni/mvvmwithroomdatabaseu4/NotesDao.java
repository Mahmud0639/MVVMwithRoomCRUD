package com.manuni.mvvmwithroomdatabaseu4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;

import java.util.List;

@Dao
public interface NotesDao {

    //below query is for two task, one is inserting and another is updating..first time same id use for inset and next time same id use for updating
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetNotes(NoteEntity noteEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> noteEntityList);

    @Delete
    void deleteNotes(NoteEntity noteEntity);

    //below query for is getting notes data specific id wise
    @Query("SELECT * FROM "+DatabaseUtils.TABLE_NAME+" WHERE id= :uid")
    NoteEntity getNoteById(int uid);

    //below query is for getting all notes data form table order by date and also with descending order
    @Query("SELECT * FROM "+DatabaseUtils.TABLE_NAME+" ORDER BY date DESC")
    LiveData<List<NoteEntity>> getAllNotes();

    //below query is for deleting all the records from the table
    @Query("DELETE FROM "+DatabaseUtils.TABLE_NAME+" ")
    int deleteAllNotes();

    //below query is for counting all the notes
    @Query("SELECT COUNT(*) FROM "+DatabaseUtils.TABLE_NAME+" ")
    int getCounts();

}
