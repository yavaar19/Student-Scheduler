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

public class AssessmentList extends AppCompatActivity implements RecyclerViewInterface{

    AssessmentAdapter adapter;
    Repository repo = new Repository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerviewassessmentlist);
        Repository repo = new Repository(getApplication());
        List<Assessment> terms = repo.getAllAssessments();
        adapter = new AssessmentAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(terms);

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

    public void addAssessment(View view) {

        Intent intent = new Intent(this,AssessmentAdd.class);
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

        Intent intent = new Intent(this,AssessmentEdit.class);
        intent.putExtra("assessmentID", current.getAssessmentID());
        intent.putExtra("assessmentEndDate", current.getEndDate());
        intent.putExtra("assessmentName", current.getAssessmentTitle());
        intent.putExtra("assessmentType", current.getAssessmentType());
        intent.putExtra("assessmentDate", current.getStartDate());
        intent.putExtra("assessmentCourseId", current.getCourseId());

        startActivity(intent);

    }

    @Override
    public void onDeleteClickAssessment(int position, Assessment assessment) {

        boolean check = assessment.getCourseId() != 0;


        if(check == true){

            Toast.makeText(AssessmentList.this, "Assessment has course! Cannot Delete!", Toast.LENGTH_SHORT).show();

        }else{

            adapter.removeAssessment(position, assessment);
            repo.deleteAssessment(assessment);

        }

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