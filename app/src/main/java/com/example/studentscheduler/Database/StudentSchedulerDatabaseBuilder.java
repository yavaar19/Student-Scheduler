package com.example.studentscheduler.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.studentscheduler.DAO.AssessmentDAO;
import com.example.studentscheduler.DAO.CourseDAO;
import com.example.studentscheduler.DAO.InstructorDAO;
import com.example.studentscheduler.DAO.NoteDAO;
import com.example.studentscheduler.DAO.TermDAO;
import com.example.studentscheduler.Entity.Assessment;
import com.example.studentscheduler.Entity.Course;
import com.example.studentscheduler.Entity.Instructor;
import com.example.studentscheduler.Entity.Note;
import com.example.studentscheduler.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class, Note.class}, version=9, exportSchema = false)
public abstract class StudentSchedulerDatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract InstructorDAO instructorDAO();
    public abstract NoteDAO noteDAO();

    private static volatile StudentSchedulerDatabaseBuilder INSTANCE;

    static StudentSchedulerDatabaseBuilder getDatabase(final Context context) {

        if (INSTANCE == null) {

            synchronized (StudentSchedulerDatabaseBuilder.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentSchedulerDatabaseBuilder.class, "myStudentSchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();

                }

            }
        }

        return INSTANCE;

    }

}
