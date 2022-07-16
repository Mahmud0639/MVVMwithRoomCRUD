package com.manuni.mvvmwithroomdatabaseu4;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    //it will take date and convert it into timestamp
    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null? null:date.getTime();
    }

    //it will take timestamp and convert it into date
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null? null: new Date(timestamp);
    }
}
