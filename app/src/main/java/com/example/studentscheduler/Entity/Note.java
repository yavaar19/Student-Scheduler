package com.example.studentscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteID;

    private String title;
    private String content;

    private int noteCourseID;

    public Note(int noteID, String title, String content, int noteCourseID) {

        this.noteID = noteID;
        this.title = title;
        this.content = content;
        this.noteCourseID = noteCourseID;

    }

    public int getNoteCourseID() {
        return noteCourseID;
    }

    public void setNoteCourseID(int noteCourseID) {
        this.noteCourseID = noteCourseID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
