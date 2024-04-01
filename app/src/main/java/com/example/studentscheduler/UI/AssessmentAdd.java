package com.example.studentscheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssessmentAdd extends AppCompatActivity implements RecyclerViewInterface{


    private String type = "Select Type";
    String[] spinnerStatus = {"Select Type", "Performance", "Objective"};
    final Calendar calendar = Calendar.getInstance();
    int newID;

    private EditText editName;
    private EditText editDate;
    private EditText editEndDate;

    private ImageButton selectDate;
    private ImageButton selectEndDate;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    Date startDate;
    Date endDate;

    Spinner typeSpinner;

    Repository repository = new Repository(getApplication());

    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_assessment_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.assessment_edit_assessmentname);

        editDate = findViewById(R.id.assessment_edit_assessment_date);
        editEndDate = findViewById(R.id.assessment_edit_assessment_end_date);

        editDate.setFocusable(false);
        editEndDate.setFocusable(false);


        typeSpinner = findViewById(R.id.assessment_edit_assessment_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setSelection(adapter.getPosition(type));

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type = typeSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }

        });

        RecyclerView recyclerView = findViewById(R.id.assessment_edit_recyclerview);
        courseAdapter = new CourseAdapter(this, this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final int yearStart = calendar.get(Calendar.YEAR);
        final int monthStart = calendar.get(Calendar.MONTH);
        final int dayStart = calendar.get(Calendar.DAY_OF_MONTH);

        selectDate = findViewById(R.id.assessment_edit_date_btn);
        selectEndDate = findViewById(R.id.assessment_edit_end_date_btn);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(AssessmentAdd.this, new DatePickerDialog.OnDateSetListener() {
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

                        editDate.setText(date);

                    }
                },yearStart, monthStart, dayStart);

                dialog.show();

            }
        });

        selectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(AssessmentAdd.this, new DatePickerDialog.OnDateSetListener() {
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

                        editEndDate.setText(date);

                    }
                },yearStart, monthStart, dayStart);

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
                Intent intent = new Intent(this, AssessmentList.class);
                startActivity(intent);
                return true;

            case R.id.termsave:

                try {

                    startDate = sdf.parse(editDate.getText().toString());
                    endDate = sdf.parse(editEndDate.getText().toString());


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(editName.getText().toString());
                boolean b = m.find();

                if(b == true ||  editName.getText().toString().length() == 0 || editDate.getText().toString().length() == 0){

                    Toast.makeText(AssessmentAdd.this, "Invalid Input!", Toast.LENGTH_SHORT).show();

                }else if(type.equals("Select Type")){

                    Toast.makeText(AssessmentAdd.this, "Invalid assessment type!", Toast.LENGTH_SHORT).show();

                }else if(startDate.compareTo(endDate) > -1){

                    Toast.makeText(AssessmentAdd.this, "Start date cannot be on or after end date!", Toast.LENGTH_SHORT).show();

                }else{

                    Assessment assessment;

                    if(repository.getAllAssessments().size() != 0){

                        newID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;

                    }else{

                        newID = 1;

                    }

                    assessment = new Assessment(newID, editName.getText().toString(), type, editDate.getText().toString(), editEndDate.getText().toString(), 0);
                    repository.insertAssessment(assessment);

                    Intent intentSecond = new Intent(this, AssessmentList.class);
                    startActivity(intentSecond);
                    return true;

                }

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

        for(Assessment a : repository.getAllAssessments()){

            if(a.getCourseId() == course.getCourseID()){

                Assessment assessment1 = new Assessment(a.getAssessmentID(), a.getAssessmentTitle(), a.getAssessmentType(), a.getStartDate(), a.getEndDate(), 0);
                courseAdapter.removeCourse(position, course);
                repository.updateAssessment(assessment1);

            }

        }

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

    public void AssessmentEditAddCourse(View view) {
/*
        if(newID != 0) {

            if (filteredCourses.size() == 0) {

                int i = 0;
                mUserItems.clear();
                courseName = new String[0];
                assessmentCourseID = new Integer[0];
                filteredCourses.clear();
                final int[] selected = new int[1];

                for (Course c : repository.getAllCourses()) {

                    filteredCourses.add(c);

                }

                for (Assessment a : repository.getAllAssessments()) {

                    for (Iterator iterator = filteredCourses.iterator(); iterator.hasNext(); ) {

                        Course course = (Course) iterator.next();

                        if (a.getCourseId() == course.getCourseID()) {

                            iterator.remove();

                        }

                    }

                }

                i = 0;
                courseName = new String[filteredCourses.size()];

                for (Course c : filteredCourses) {

                    courseName[i] = c.getCourseTitle();
                    i++;

                }

                i = 0;


                if (courseName.length > 0) {

                    checkedItems = new boolean[courseName.length];

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(AssessmentAdd.this);
                    mBuilder.setTitle("Select Course");
                    mBuilder.setSingleChoiceItems(courseName, 0, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            selected[0] = position;

                        }

                    });

                    mBuilder.setCancelable(false);

                    mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {


                            String course = new String();

                            course = courseName[selected[0]];

                            for (Course c : repository.getAllCourses()) {


                                if (c.getCourseTitle().equals(course)) {

                                    Assessment assmt = new Assessment(assessmentId, name, type, date, c.getCourseID());

                                    repository.updateAssessment(assmt);

                                }


                            }

                            filteredCourses.clear();

                            for (Course c : repository.getAllCourses()) {

                                if (c.getCourseTitle().equals(course)) filteredCourses.add(c);

                            }

                            courseAdapter.setCourses(filteredCourses);

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

                    Toast.makeText(AssessmentAdd.this, "No available course to add!", Toast.LENGTH_SHORT).show();

                }

            } else {

                Toast.makeText(AssessmentAdd.this, "Only 1 course per assessment! Please delete course first!", Toast.LENGTH_SHORT).show();

            }

        }else{

            Toast.makeText(AssessmentAdd.this, "Save Term First!", Toast.LENGTH_SHORT).show();

        }
*/
    }

}
