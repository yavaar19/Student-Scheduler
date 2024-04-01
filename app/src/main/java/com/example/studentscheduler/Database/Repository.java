package com.example.studentscheduler.Database;

import android.app.Application;

import com.example.studentscheduler.DAO.AssessmentDAO;
import com.example.studentscheduler.DAO.CourseDAO;
import com.example.studentscheduler.DAO.InstructorDAO;
import com.example.studentscheduler.DAO.NoteDAO;
import com.example.studentscheduler.DAO.TermDAO;
import com.example.studentscheduler.Entity.Assessment;
import com.example.studentscheduler.Entity.Course;
import com.example.studentscheduler.Entity.Instructor;
import com.example.studentscheduler.Entity.Term;
import com.example.studentscheduler.Entity.Note;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private final TermDAO termDAO;
    private final CourseDAO courseDAO;
    private final AssessmentDAO assessmentDAO;
    private final InstructorDAO instructorDAO;
    private final NoteDAO noteDAO;

    private List<Term> allTerms;
    private List<Course> allCourses;
    private List<Assessment> allAssessments;
    private List<Instructor> allInstructors;
    private List<Note> allNotes;

    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){

        StudentSchedulerDatabaseBuilder db = StudentSchedulerDatabaseBuilder.getDatabase(application);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        instructorDAO = db.instructorDAO();
        noteDAO = db.noteDAO();

    }

    public void insertTerm(Term term){

        databaseExecutor.execute(()->{

            termDAO.insert(term);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void deleteTerm(Term term){

        databaseExecutor.execute(()->{

            termDAO.delete(term);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void updateTerm(Term term){

        databaseExecutor.execute(()->{

            termDAO.update(term);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void insertCourse(Course course){

        databaseExecutor.execute(()->{

            courseDAO.insert(course);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void deleteCourse(Course course){

        databaseExecutor.execute(()->{

            courseDAO.delete(course);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void updateCourse(Course course){

        databaseExecutor.execute(()->{

            courseDAO.update(course);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void insertAssessment(Assessment assessment){

        databaseExecutor.execute(()->{

            assessmentDAO.insert(assessment);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void deleteAssessment(Assessment assessment){

        databaseExecutor.execute(()->{

            assessmentDAO.delete(assessment);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void updateAssessment(Assessment assessment){

        databaseExecutor.execute(()->{

            assessmentDAO.update(assessment);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void insertInstructor(Instructor instructor){

        databaseExecutor.execute(()->{

            instructorDAO.insert(instructor);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void deleteInstructor(Instructor instructor){

        databaseExecutor.execute(()->{

            instructorDAO.delete(instructor);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void updateInstructor(Instructor instructor){

        databaseExecutor.execute(()->{

            instructorDAO.update(instructor);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public List<Term>getAllTerms(){

        databaseExecutor.execute(()->{

            allTerms = termDAO.getAllTerms();

        });

        try{

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

        return allTerms;

    }

    public List<Course>getAllCourses(){

        databaseExecutor.execute(()->{

            allCourses = courseDAO.getAllCourses();

        });

        try{

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

        return allCourses;

    }

    public List<Assessment>getAllAssessments(){

        databaseExecutor.execute(()->{

            allAssessments = assessmentDAO.getAllAssessments();

        });

        try{

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

        return allAssessments;

    }

    public List<Instructor>getAllInstructors(){

        databaseExecutor.execute(()->{

            allInstructors = instructorDAO.getAllInstructors();

        });

        try{

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

        return allInstructors;

    }

    public void insertNote(Note note){

        databaseExecutor.execute(()->{

            noteDAO.insert(note);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void deleteNote(Note note){

        databaseExecutor.execute(()->{

            noteDAO.delete(note);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public void updateNote(Note note){

        databaseExecutor.execute(()->{

            noteDAO.update(note);

        });

        try {

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    public List<Note>getAllNotes(){

        databaseExecutor.execute(()->{

            allNotes = noteDAO.getAllNotes();

        });

        try{

            Thread.sleep(1000);

        } catch(InterruptedException e){

            e.printStackTrace();

        }

        return allNotes;

    }

}
