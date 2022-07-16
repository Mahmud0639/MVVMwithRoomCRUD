package com.manuni.mvvmwithroomdatabaseu4;

import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Utils {
    private static final String SAMPLE_TEXT_1 = "Note text here starts";
    private static final String SAMPLE_TEXT_2 = "My name is Mahmud Islam";
    private static final String SAMPLE_TEXT_3 = "I am Mahmudul Islam. I am from Bangladesh. I would like to help all the people. Please help me to know about others people who are needy";

    private static Date getDate(int diffAmount){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MILLISECOND,diffAmount);
        return calendar.getTime();
    }
    public static List<NoteEntity> getSampleData(){
        List<NoteEntity> noteEntityList = new ArrayList<>();
        //below line of codes are for only 3 rows values insertion. If we try to add value multiple time then it again and again try to insert data with this 3 id only and replacing in it.
       /* noteEntityList.add(new NoteEntity(1,getDate(0),SAMPLE_TEXT_1));
        noteEntityList.add(new NoteEntity(2,getDate(-1),SAMPLE_TEXT_2));
        noteEntityList.add(new NoteEntity(3,getDate(-3),SAMPLE_TEXT_3));*/

        //below code is for inserting data with auto increment id with corresponding data
        noteEntityList.add(new NoteEntity(getDate(0),SAMPLE_TEXT_1));
        noteEntityList.add(new NoteEntity(getDate(-1),SAMPLE_TEXT_2));
        noteEntityList.add(new NoteEntity(getDate(-2),SAMPLE_TEXT_3));
        return noteEntityList;
    }
}
