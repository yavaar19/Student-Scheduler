package com.example.studentscheduler.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteAdd extends AppCompatActivity implements RecyclerViewInterface {

    private EditText title;
    private EditText content;
    private int newID;

    private int courseID;
    private String name;
    private String start;
    private String end;
    private String status;
    private int courseTermId;
    private int courseInstructorId;

    ImageView share;

    Repository repository = new Repository(getApplication());

    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_note_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        share = findViewById(R.id.floatingActionButtonShareNote);
        share.setVisibility(View.INVISIBLE);

        title = findViewById(R.id.noteTitle);
        content = findViewById(R.id.noteContent);

        courseID = getIntent().getIntExtra("courseId", 0);
        name = getIntent().getStringExtra("courseName");
        start = getIntent().getStringExtra("courseStart");
        end = getIntent().getStringExtra("courseEnd");
        courseTermId = getIntent().getIntExtra("courseTermId", 0);
        status = getIntent().getStringExtra("courseStatus");
        courseInstructorId = getIntent().getIntExtra("courseInstructorId", 0);


    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.term_edit, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case android.R.id.home:
                Intent intent = new Intent(this,CourseEdit.class);
                intent.putExtra("courseId",courseID);
                intent.putExtra("courseName", name);
                intent.putExtra("courseStart", start);
                intent.putExtra("courseEnd", end);
                intent.putExtra("courseTermId", courseTermId);
                intent.putExtra("courseStatus", status);
                intent.putExtra("courseInstructorId", courseInstructorId);
                startActivity(intent);
                return true;

            case R.id.termsave:

                Note note;

                if(repository.getAllNotes().size() != 0){

                    newID = repository.getAllNotes().get(repository.getAllNotes().size() - 1).getNoteID() + 1;

                }else{

                    newID = 1;

                }

                note = new Note(newID, title.getText().toString(), content.getText().toString(), courseID);
                repository.insertNote(note);

                Toast.makeText(NoteAdd.this, "Note Saved!", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(this,CourseEdit.class);
                intent1.putExtra("courseId",courseID);
                intent1.putExtra("courseName", name);
                intent1.putExtra("courseStart", start);
                intent1.putExtra("courseEnd", end);
                intent1.putExtra("courseTermId", courseTermId);
                intent1.putExtra("courseStatus", status);
                intent1.putExtra("courseInstructorId", courseInstructorId);
                startActivity(intent1);
                return true;

        }

        return true;

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

    }

    @Override
    public void onEditClickNote(int position, Note note) {

    }

    public void TermEditAddCourse(View view) {


    }

}
