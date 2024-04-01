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

public class CourseList extends AppCompatActivity implements RecyclerViewInterface{

    Repository repo = new Repository(getApplication());
    CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerviewcourselist);
        List<Course> courses = repo.getAllCourses();
        adapter = new CourseAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);

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

    public void addCourse(View view) {

        Intent intent = new Intent(this,CourseAdd.class);
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

        boolean check = false;

        for(Assessment a : repo.getAllAssessments()){

            if(a.getCourseId() == course.getCourseID()){

                check = true;
                break;

            }

        }

        if(check == true){

            Toast.makeText(CourseList.this, "Course has assessments! Cannot Delete!", Toast.LENGTH_SHORT).show();

        }else{

            adapter.removeCourse(position, course);
            repo.deleteCourse(course);

        }

    }

    @Override
    public void onCourseShowAction(Boolean isSelected) {

    }

    @Override
    public void onEditClickCourse(int position, Course current) {

        Intent intent = new Intent(this,CourseEdit.class);
        intent.putExtra("courseId", current.getCourseID());
        intent.putExtra("courseName", current.getCourseTitle());
        intent.putExtra("courseStart", current.getStartDate());
        intent.putExtra("courseEnd", current.getEndDate());
        intent.putExtra("courseStatus", current.getStatus());
        intent.putExtra("courseTermId", current.getTermId());
        intent.putExtra("courseInstructorId", current.getInstructorId());

        startActivity(intent);

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

}