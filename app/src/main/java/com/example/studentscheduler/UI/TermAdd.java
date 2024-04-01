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

public class TermAdd extends AppCompatActivity implements RecyclerViewInterface {

    String[] courseName;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    int newID = 0;

    private ImageButton startSelectDate;
    private ImageButton endSelectDate;

    private EditText editName;
    private EditText etStartSelectDate;
    private EditText etEndSelectDate;
    private ImageView courseToEdit;
    List<Course> filteredCourses= new ArrayList<>();
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    Date startDate;
    Date endDate;


    Repository repository = new Repository(getApplication());

    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_term_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.termname);

        etStartSelectDate = findViewById(R.id.startdate);

        etEndSelectDate = findViewById(R.id.enddate);

        etStartSelectDate.setFocusable(false);
        etEndSelectDate.setFocusable(false);

        RecyclerView recyclerView = findViewById(R.id.termeditrecyclerview);

        courseAdapter = new CourseAdapter(this, this);

        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final int yearStart = calendar.get(Calendar.YEAR);
        final int monthStart = calendar.get(Calendar.MONTH);
        final int dayStart = calendar.get(Calendar.DAY_OF_MONTH);

        final int yearEnd = calendar.get(Calendar.YEAR);
        final int monthEnd = calendar.get(Calendar.MONTH);
        final int dayEnd = calendar.get(Calendar.DAY_OF_MONTH);

        startSelectDate = findViewById(R.id.term_edit_start_btn);
        endSelectDate  = findViewById(R.id.term_edit_end_btn);

        startSelectDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(TermAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String date;
                        month = month + 1;

                        if(String.valueOf(month).length() < 2 && String.valueOf(dayOfMonth).length() < 2){

                            date = "0" + month + "/" + "0" + dayOfMonth + "/" + year;

                        }else if(String.valueOf(month).length() < 2){

                            date = "0" + month + "/" + dayOfMonth + "/" + year;

                        }else if(String.valueOf(dayOfMonth).length() < 2){

                            date = month + "/" + "0" + dayOfMonth + "/" + year;

                        }else{

                            date = month + "/" + dayOfMonth + "/" + year;

                        }

                        etStartSelectDate.setText(date);

                    }
                },yearStart, monthStart, dayStart);

                dialog.show();

            }
        });

        endSelectDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(TermAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month +1;
                        String date;

                        if(String.valueOf(month).length() < 2 && String.valueOf(dayOfMonth).length() < 2){

                            date = "0" + month + "/" + "0" + dayOfMonth + "/" + year;

                        }else if(String.valueOf(month).length() < 2){

                            date = "0" + month + "/" + dayOfMonth + "/" + year;

                        }else if(String.valueOf(dayOfMonth).length() < 2){

                            date = month + "/" + "0" + dayOfMonth + "/" + year;

                        }else{

                            date = month + "/" + dayOfMonth + "/" + year;

                        }

                        etEndSelectDate.setText(date);

                    }
                },yearEnd, monthEnd, dayEnd );

                dialog.show();

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
                Intent intent = new Intent(this, TermList.class);
                startActivity(intent);
                return true;

            case R.id.termsave:

                try {

                    startDate = sdf.parse(etStartSelectDate.getText().toString());
                    endDate = sdf.parse(etEndSelectDate.getText().toString());


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(editName.getText().toString());
                boolean b = m.find();

                if(b == true || etStartSelectDate.getText().toString().length() == 0 || etEndSelectDate.getText().toString().length() == 0){

                    Toast.makeText(TermAdd.this, "Invalid Input!", Toast.LENGTH_SHORT).show();

                }else if(startDate.compareTo(endDate) > -1) {

                    Toast.makeText(TermAdd.this, "Start date cannot be on or after end date!", Toast.LENGTH_SHORT).show();


                }else{

                    Term term;

                    if(repository.getAllTerms().size() != 0){

                        newID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;

                    }else{

                        newID = 1;

                    }


                    term = new Term(newID, editName.getText().toString(), etStartSelectDate.getText().toString(), etEndSelectDate.getText().toString());
                    repository.insertTerm(term);

                    Toast.makeText(TermAdd.this, "Term Saved!", Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(this,TermList.class);
                    startActivity(intent1);

                    return true;

                }

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

        Course course1 = new Course(course.getCourseID(), course.getCourseTitle(), course.getStartDate(), course.getEndDate(), course.getStatus(), 0, course.getInstructorId());
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

    public void TermEditAddCourse(View view) {

            if(newID == 0){

                Toast.makeText(TermAdd.this, "Save Term First!", Toast.LENGTH_SHORT).show();

            }else{

                int i = 0;
                mUserItems.clear();
                courseName = new String[0];

                for (Course c : repository.getAllCourses()) {

                    if (c.getTermId() == 0) {

                        i++;
                        courseName = new String[i];

                    }

                }

                if(courseName.length > 0){

                    i = 0;

                    for (Course c : repository.getAllCourses()) {

                        if (c.getTermId() == 0) {

                            courseName[i] = c.getCourseTitle();
                            i++;

                        }

                    }

                    checkedItems = new boolean[courseName.length];

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(TermAdd.this);
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

                                for (Course Course : repository.getAllCourses()) {

                                    for (int i = 0; i < course.length; i++) {

                                        if (Course.getCourseTitle().equals(course[i])) {

                                            Course c = new Course(Course.getCourseID(), Course.getCourseTitle(), Course.getStartDate(), Course.getEndDate(), Course.getStatus(), newID, Course.getInstructorId());

                                            repository.updateCourse(c);

                                        }

                                    }

                                }

                                filteredCourses.clear();

                                courseName = new String[0];

                                for (Course c : repository.getAllCourses()) {

                                    if (c.getTermId() == newID) filteredCourses.add(c);

                                }

                                courseAdapter.setCourses(filteredCourses);

                            }else{

                                Toast.makeText(TermAdd.this, "No course selected!", Toast.LENGTH_SHORT).show();

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

                }else{

                    Toast.makeText(TermAdd.this, "No available courses to add!", Toast.LENGTH_SHORT).show();

                }

            }

    }

}
