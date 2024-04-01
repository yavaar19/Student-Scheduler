package com.example.studentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Assessment;
import com.example.studentscheduler.Entity.Course;
import com.example.studentscheduler.Entity.Instructor;
import com.example.studentscheduler.Entity.Note;
import com.example.studentscheduler.Entity.Term;
import com.example.studentscheduler.R;

public class NoteEdit extends AppCompatActivity implements RecyclerViewInterface {

    private EditText title;
    private EditText content;
    private int newID;

    private int noteID;
    private String noteTitle;
    private String noteContent;
    private int noteCourseID;

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


        content = findViewById(R.id.noteContent);

        noteID = getIntent().getIntExtra("noteId", 0);

        noteCourseID = getIntent().getIntExtra("noteCourseId", 0);

        noteTitle = getIntent().getStringExtra("noteTitle");
        title = findViewById(R.id.noteTitle);
        title.setText(noteTitle);

        noteContent = getIntent().getStringExtra("noteContent");
        content = findViewById(R.id.noteContent);
        content.setText(noteContent);

        share = findViewById(R.id.floatingActionButtonShareNote);

        courseID = getIntent().getIntExtra("courseId", 0);
        name = getIntent().getStringExtra("courseName");
        start = getIntent().getStringExtra("courseStart");
        end = getIntent().getStringExtra("courseEnd");
        courseTermId = getIntent().getIntExtra("courseTermId", 0);
        status = getIntent().getStringExtra("courseStatus");
        courseInstructorId = getIntent().getIntExtra("courseInstructorId", 0);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, title.getText().toString());
                shareIntent.putExtra(Intent.EXTRA_TEXT, content.getText().toString());
                shareIntent.setType("text/plain");
                shareIntent = Intent.createChooser(shareIntent, "Share Via: ");
                startActivity(shareIntent);

            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.term_edit, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case android.R.id.home:
                Intent intent = new Intent(this,NoteList.class);
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

                newID = repository.getAllNotes().get(repository.getAllNotes().size() - 1).getNoteID() + 1;

                note = new Note(noteID, title.getText().toString(), content.getText().toString(), noteCourseID);
                repository.updateNote(note);

                Toast.makeText(NoteEdit.this, "Note Saved!", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(this,NoteList.class);
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
