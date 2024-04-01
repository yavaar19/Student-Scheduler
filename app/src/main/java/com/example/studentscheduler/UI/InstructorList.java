package com.example.studentscheduler.UI;

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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class InstructorList extends AppCompatActivity implements RecyclerViewInterface{

    InstructorAdapter adapter;
    Repository repo = new Repository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerviewinstructorlist);
        Repository repo = new Repository(getApplication());
        List<Instructor> instructors = repo.getAllInstructors();
        adapter = new InstructorAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setInstructors(instructors);

    }

    public boolean onCreateOptionsMenu(Menu menu){

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    public void addInstructor(View view) {

        Intent intent = new Intent(this,InstructorAdd.class);
        startActivity(intent);

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

        boolean check = false;

        for(Course c : repo.getAllCourses()){

            if(c.getInstructorId() == instructor.getInstructorID()){

                check = true;
                break;

            }

        }

        if(check == true){

            Toast.makeText(InstructorList.this, "Instructor has courses! Cannot Delete!", Toast.LENGTH_SHORT).show();

        }else{

            adapter.removeInstructor(position, instructor);
            repo.deleteInstructor(instructor);

        }


    }

    @Override
    public void onEditClickInstructor(int position, Instructor instructor) {

        Intent intent = new Intent(this,InstructorEdit.class);
        intent.putExtra("instructorId", instructor.getInstructorID());
        intent.putExtra("instructorName", instructor.getInstructorName());
        intent.putExtra("phoneNumber", instructor.getPhoneNumber());
        intent.putExtra("emailAddress", instructor.getEmailAddress());

        startActivity(intent);

    }

    @Override
    public void onDeleteClickNote(int position, Note note) {

    }

    @Override
    public void onEditClickNote(int position, Note note) {

    }
}