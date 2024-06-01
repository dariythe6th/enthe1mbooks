package com.enthe1m.myapplication;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE))
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String author;
    public int userId;
    public String notes;
    //public long timestamp;  // Add a timestamp field
}
