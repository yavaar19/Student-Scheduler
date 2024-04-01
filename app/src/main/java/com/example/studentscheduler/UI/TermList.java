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

public class TermList extends AppCompatActivity implements RecyclerViewInterface{

    Repository repo = new Repository(getApplication());
    List<Term> terms = repo.getAllTerms();
    TermAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new TermAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

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

    public void addTerm(View view) {

        Intent intent=new Intent(TermList.this,TermAdd.class);
        startActivity(intent);

    }

    public void editTerm(Term term) {

        Intent intent=new Intent(TermList.this,TermEdit.class);
        intent.putExtra("termId", term.getTermID());
        intent.putExtra("termName", term.getTermName());
        intent.putExtra("termStart", term.getStartDate());
        intent.putExtra("termEnd", term.getEndDate());
        startActivity(intent);

    }

    @Override
    public void onDeleteClickTerm(int position, Term term) {

        boolean check = false;

        for(Course c : repo.getAllCourses()){

            if(c.getTermId() == term.getTermID()){

                check = true;
                break;

            }

        }

        if(check == true){

            Toast.makeText(TermList.this, "Term has courses! Cannot Delete!", Toast.LENGTH_SHORT).show();

        }else{

            adapter.removeTerm(position, term);
            repo.deleteTerm(term);

        }

    }

    @Override
    public void onEditClickTerm(int position, Term term) {

        Intent intent=new Intent(this,TermEdit.class);
        intent.putExtra("termId", term.getTermID());
        intent.putExtra("termName", term.getTermName());
        intent.putExtra("termStart", term.getStartDate());
        intent.putExtra("termEnd", term.getEndDate());
        startActivity(intent);

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

}