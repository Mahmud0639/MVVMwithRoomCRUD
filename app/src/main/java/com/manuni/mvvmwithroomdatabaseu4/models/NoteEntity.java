package com.manuni.mvvmwithroomdatabaseu4.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.manuni.mvvmwithroomdatabaseu4.DatabaseUtils;

import java.util.Date;

@Entity(tableName = DatabaseUtils.TABLE_NAME)
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date date;

    private String text;

    @Ignore
    public NoteEntity() {
    }

    @Ignore
    public NoteEntity(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public NoteEntity(int id, Date date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
