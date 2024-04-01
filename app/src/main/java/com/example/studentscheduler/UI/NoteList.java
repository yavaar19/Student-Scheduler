package com.example.studentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Assessment;
import com.example.studentscheduler.Entity.Course;
import com.example.studentscheduler.Entity.Instructor;
import com.example.studentscheduler.Entity.Note;
import com.example.studentscheduler.Entity.Term;
import com.example.studentscheduler.R;

import java.util.List;

public class NoteList extends AppCompatActivity implements RecyclerViewInterface{

    Repository repo = new Repository(getApplication());
    List<Note> notes = repo.getAllNotes();
    NoteAdapter adapter;

    private int courseID;
    private String courseTitle;
    private String startDate;
    private String endDate;
    private String status;
    private int termID;
    private int instructorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        notes.clear();

        courseID = getIntent().getIntExtra("courseId", 0);
        instructorID = getIntent().getIntExtra("courseInstructorId", 0);
        courseTitle = getIntent().getStringExtra("courseName");
        startDate = getIntent().getStringExtra("courseStart");
        endDate = getIntent().getStringExtra("courseEnd");
        termID = getIntent().getIntExtra("courseTermId", 0);
        status = getIntent().getStringExtra("courseStatus");

        for(Note n : repo.getAllNotes()){

            if(n.getNoteCourseID() == courseID){

                notes.add(n);

            }

        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new NoteAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setNotes(notes);

    }

    public boolean onCreateOptionsMenu(Menu menu){

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case android.R.id.home:
                Intent intent = new Intent(this, CourseEdit.class);
                intent.putExtra("courseId",courseID);
                intent.putExtra("courseInstructorId", instructorID);
                intent.putExtra("courseName", courseTitle);
                intent.putExtra("courseStart", startDate);
                intent.putExtra("courseEnd", endDate);
                intent.putExtra("courseTermId", termID);
                intent.putExtra("courseStatus", status);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    public void addTerm(View view) {


    }

    public void editTerm(Term term) {

    }

    @Override
    public void onDeleteClickTerm(int position, Term term) {

    }

    @Override
    public void onEditClickTerm(int position, Term term) {

    }

    @Override
    public void onDeleteClickCourse(int position, Course course) {

    }

    @Override
    public void onCourseShowAction(Boolean isSelected) {

    }

    @Override
    public void onEditClickCourse(int position, Course current) {

    }

    @Override
    public void onEditClickAssessment(int position, Assessment current) {

    }

    @Override
    public void onDeleteClickAssessment(int position, Assessment assessment) {

    }

    @Override
    public void onDeleteClickInstructor(int position, Instructor instructor) {

    }

    @Override
    public void onEditClickInstructor(int position, Instructor instructor) {

    }

    @Override
    public void onDeleteClickNote(int position, Note note) {

        adapter.removeNote(position, note);
        repo.deleteNote(note);

    }

    @Override
    public void onEditClickNote(int position, Note note) {

        Intent intent = new Intent(this,NoteEdit.class);
        intent.putExtra("noteId", note.getNoteID());
        intent.putExtra("noteTitle", note.getTitle());
        intent.putExtra("noteContent", note.getContent());
        intent.putExtra("noteCourseId", note.getNoteCourseID());

        intent.putExtra("courseId",courseID);
        intent.putExtra("courseInstructorId", instructorID);
        intent.putExtra("courseName", courseTitle);
        intent.putExtra("courseStart", startDate);
        intent.putExtra("courseEnd", endDate);
        intent.putExtra("courseTermId", termID);
        intent.putExtra("courseStatus", status);

        startActivity(intent);

    }

}