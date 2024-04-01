package com.example.studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Assessment;
import com.example.studentscheduler.Entity.Course;
import com.example.studentscheduler.Entity.Instructor;
import com.example.studentscheduler.Entity.Note;
import com.example.studentscheduler.Entity.Term;
import com.example.studentscheduler.R;

public class MainActivity extends AppCompatActivity {

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickTerm(View view) {

        repository = new Repository(getApplication());
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

    }

    public void onClickCourses(View view) {

        repository = new Repository(getApplication());
        Intent intent = new Intent(MainActivity.this, CourseList.class);
        startActivity(intent);

    }

    public void onClickAssessments(View view) {

        repository = new Repository(getApplication());
        Intent intent = new Intent(MainActivity.this, AssessmentList.class);
        startActivity(intent);

    }

    public void onClickInstructors(View view) {

        repository = new Repository(getApplication());
        Intent intent = new Intent(MainActivity.this, InstructorList.class);
        startActivity(intent);

    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){

        repository = new Repository(getApplication());

        switch (item.getItemId()){

            case R.id.addsampledata:

                if(repository.getAllTerms().size() == 0){

                    Term term1 = new Term(1, "Term 1", "12/25/2022", "01/25/2023");
                    Term term2 = new Term(2, "Term 2", "12/25/2022", "01/25/2023");

                    repository.insertTerm(term1);
                    repository.insertTerm(term2);

                }

                if(repository.getAllCourses().size() == 0){

                    Course course1 = new Course(1, "Java Project", "12/25/2022", "01/25/2023", "In Progress", 0,0);
                    Course course2 = new Course(2, "C Plus Project", "12/25/2022", "01/25/2023", "In Progress",0,0);

                    repository.insertCourse(course1);
                    repository.insertCourse(course2);

                }

                if(repository.getAllNotes().size() == 0){

                    Note Note1 = new Note(1, "Note 1", "Note 1 Content",1);
                    Note Note2 = new Note(2, "Note 2", "Note 2 Content", 2);

                    repository.insertNote(Note1);
                    repository.insertNote(Note2);

                }

                if(repository.getAllAssessments().size() == 0){

                    Assessment assessment1 = new Assessment(1, "Exam 1", "Performance", "09/02/2022", "09/09/2022",0);
                    Assessment assessment2 = new Assessment(2, "Exam 2", "Objective", "12/03/2022", "12/09/2022",0);

                    repository.insertAssessment(assessment1);
                    repository.insertAssessment(assessment2);

                }

                if(repository.getAllInstructors().size() == 0){

                    Instructor instructor1 = new Instructor(1, "Kayla Ross", "416-875-1129", "kaylaross@gmail.com");
                    Instructor instructor2 = new Instructor(2, "Kevin Durant", "416-354-1298", "kevindurant@gmail.com");

                    repository.insertInstructor(instructor1);
                    repository.insertInstructor(instructor2);

                }

                Toast.makeText(MainActivity.this, "Sample data added!", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.removesampledata:

                for(Term term : repository.getAllTerms()){

                    if(repository.getAllTerms().size() != 0){

                        repository.deleteTerm(term);

                    }

                }

                for(Course course : repository.getAllCourses()){

                    if(repository.getAllCourses().size() != 0){

                        repository.deleteCourse(course);

                    }

                }

                for(Assessment assessment : repository.getAllAssessments()){

                    if(repository.getAllAssessments().size() != 0){

                        repository.deleteAssessment(assessment);

                    }

                }

                for(Instructor instructor : repository.getAllInstructors()){

                    if(repository.getAllInstructors().size() != 0){

                        repository.deleteInstructor(instructor);

                    }

                }

                for(Note note : repository.getAllNotes()){

                    if(repository.getAllNotes().size() != 0){

                        repository.deleteNote(note);

                    }

                }

                Toast.makeText(MainActivity.this, "All existing date removed!", Toast.LENGTH_SHORT).show();

                return true;

        }

        return super.onOptionsItemSelected(item);

    }


}