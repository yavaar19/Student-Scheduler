package com.example.studentscheduler.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructorAdd extends AppCompatActivity implements RecyclerViewInterface{

    String[] courseName;
    int newID = 0;

    ArrayList<Integer> mUserItems = new ArrayList<>();
    boolean[] checkedItems;

    private EditText editName;
    private EditText editPhoneNumber;
    private EditText editEmailAddress;

    List<Course> filteredCourses = new ArrayList<>();
    Repository repository = new Repository(getApplication());

    int instructorId;

    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_instructor_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        editName = findViewById(R.id.instructor_edit_instructorname);

        editPhoneNumber = findViewById(R.id.instructor_edit_instructorphone);

        editEmailAddress = findViewById(R.id.instructor_edit_instructoremail);

        RecyclerView recyclerView = findViewById(R.id.instructor_edit_recyclerview);
        courseAdapter = new CourseAdapter(this, this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.term_edit, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case android.R.id.home:
                Intent intent = new Intent(this, InstructorList.class);
                startActivity(intent);
                return true;

            case R.id.termsave:
                Pattern p = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(editName.getText().toString());
                boolean b = m.find();

                Pattern p1 = Pattern.compile("[^0-9- ]", Pattern.CASE_INSENSITIVE);
                Matcher m1 = p1.matcher(editPhoneNumber.getText().toString());
                boolean b1 = m1.find();

                Pattern p2 = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", Pattern.CASE_INSENSITIVE);
                Matcher m2 = p2.matcher(editEmailAddress.getText().toString());
                boolean b2 = m2.find();

                if(b){

                    Toast.makeText(InstructorAdd.this, "Invalid Input!", Toast.LENGTH_SHORT).show();

                }else if(b1){

                    Toast.makeText(InstructorAdd.this, "Invalid Phone Number!", Toast.LENGTH_SHORT).show();

                }else if(!b2) {

                    Toast.makeText(InstructorAdd.this, "Invalid Email!", Toast.LENGTH_SHORT).show();

                }else{

                    Instructor instructor;

                    if(repository.getAllInstructors().size() != 0){

                        newID = repository.getAllInstructors().get(repository.getAllInstructors().size() - 1).getInstructorID() + 1;

                    }else{

                        newID = 1;

                    }

                    instructor = new Instructor(newID, editName.getText().toString(), editPhoneNumber.getText().toString(), editEmailAddress.getText().toString());
                    repository.insertInstructor(instructor);

                    Toast.makeText(InstructorAdd.this, "Term Saved!!", Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(this,InstructorList.class);
                    startActivity(intent1);

                    return true;

                }

                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    public void enterCourse(View view) {
    }

    @Override
    public void onDeleteClickTerm(int position, Term term) {

    }

    @Override
    public void onEditClickTerm(int position, Term term) {

    }

    @Override
    public void onDeleteClickCourse(int position, Course course) {

        Course course1 = new Course(course.getCourseID(), course.getCourseTitle(), course.getStartDate(), course.getEndDate(), course.getStatus(), course.getTermId(), 0);
        courseAdapter.removeCourse(position, course);
        repository.updateCourse(course1);

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

    public void InstructorEditAddCourse(View view) {

        if(newID == 0){

            Toast.makeText(InstructorAdd.this, "Save Course First!", Toast.LENGTH_SHORT).show();


        }else {

            int i = 0;
            mUserItems.clear();
            courseName = new String[0];

            for (Course course : repository.getAllCourses()) {

                if (course.getInstructorId() == 0) {

                    i++;
                    courseName = new String[i];

                }

            }

            if (courseName.length > 0) {

                i = 0;

                for (Course course : repository.getAllCourses()) {

                    if (course.getInstructorId() == 0) {

                        courseName[i] = course.getCourseTitle();
                        i++;

                    }

                }

                checkedItems = new boolean[courseName.length];

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(InstructorAdd.this);
                mBuilder.setTitle("Select Courses");
                mBuilder.setMultiChoiceItems(courseName, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {

                        if (isChecked) {

                            if (!mUserItems.contains(position)) {

                                mUserItems.add(position);

                            } else {

                                mUserItems.remove(position);

                            }

                        }

                    }

                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        if (mUserItems.size() > 0) {

                            String[] course = new String[mUserItems.size()];

                            for (int i = 0; i < mUserItems.size(); i++) {

                                course[i] = courseName[mUserItems.get(i)];

                            }

                            for (Course c : repository.getAllCourses()) {

                                for (int i = 0; i < course.length; i++) {

                                    if (c.getCourseTitle().equals(course[i])) {

                                        Course course1 = new Course(c.getCourseID(), c.getCourseTitle(), c.getStartDate(), c.getEndDate(), c.getStatus(), c.getTermId(), newID);

                                        repository.updateCourse(course1);

                                    }

                                }

                            }

                            filteredCourses.clear();

                            courseName = new String[0];

                            for (Course c : repository.getAllCourses()) {

                                if (c.getInstructorId() == newID)
                                    filteredCourses.add(c);

                            }

                            courseAdapter.setCourses(filteredCourses);

                        } else {

                            Toast.makeText(InstructorAdd.this, "No course selected!", Toast.LENGTH_SHORT).show();

                        }

                    }

                });

                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            } else {

                Toast.makeText(InstructorAdd.this, "No available courses to add!", Toast.LENGTH_SHORT).show();

            }

        }

    }
}
