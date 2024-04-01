package com.example.studentscheduler.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseAdd extends AppCompatActivity implements RecyclerViewInterface{

    private String status = "Select Status";
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String[] spinnerStatus = {"Select Status", "In Progress", "Completed", "Dropped", "Plan To Take"};
    String[] assessmentName;
    boolean[] checkedItems;
    int newID = 0;

    private EditText editName;
    private EditText etStartSelectDate;
    private EditText etEndSelectDate;
    private EditText instructorNameField;
    private EditText instructorPhoneField;
    private EditText instructorEmailField;

    private ImageButton startSelectDate;
    private ImageButton endSelectDate;

    Button addNote;
    Button viewNote;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    Date startDate;
    Date endDate;

    final Calendar calendar = Calendar.getInstance();
    List<Assessment> filteredAssessments = new ArrayList<>();
    Spinner statusSpinner;


    Repository repository = new Repository(getApplication());
    int courseId;

    AssessmentAdapter assessmentAdapter;

    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_course_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.course_edit_coursename);

        etStartSelectDate = findViewById(R.id.course_edit_startdate);
        etStartSelectDate.setFocusable(false);
        etEndSelectDate = findViewById(R.id.course_edit_enddate);
        etEndSelectDate.setFocusable(false);

        instructorNameField = findViewById(R.id.course_edit_instructorname);
        instructorPhoneField = findViewById(R.id.course_edit_instructorphone);
        instructorEmailField = findViewById(R.id.course_edit_instructoremail);

        instructorNameField.setText("Not Yet Assigned!");
        instructorPhoneField.setText("Not Yet Assigned!");
        instructorEmailField.setText("Not Yet Assigned!");

        instructorNameField.setFocusable(false);
        instructorPhoneField.setFocusable(false);
        instructorEmailField.setFocusable(false);

        addNote = findViewById(R.id.course_edit_add_note);
        viewNote = findViewById(R.id.course_edit_view_note);
        addNote.setVisibility(View.INVISIBLE);
        viewNote.setVisibility(View.INVISIBLE);

        statusSpinner = findViewById(R.id.course_edit_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        statusSpinner.setSelection(adapter.getPosition(status));

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                status = statusSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }

        });

        RecyclerView recyclerView = findViewById(R.id.course_edit_recyclerview);
        assessmentAdapter = new AssessmentAdapter(this, this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final int yearStart = calendar.get(Calendar.YEAR);
        final int monthStart = calendar.get(Calendar.MONTH);
        final int dayStart = calendar.get(Calendar.DAY_OF_MONTH);

        final int yearEnd = calendar.get(Calendar.YEAR);
        final int monthEnd = calendar.get(Calendar.MONTH);
        final int dayEnd = calendar.get(Calendar.DAY_OF_MONTH);

        startSelectDate = findViewById(R.id.course_edit_start_btn);
        endSelectDate  = findViewById(R.id.course_edit_end_btn);

        startSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(CourseAdd.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog dialog = new DatePickerDialog(CourseAdd.this, new DatePickerDialog.OnDateSetListener() {
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
                Intent intent = new Intent(this, CourseList.class);
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

                    Toast.makeText(CourseAdd.this, "No special character allowed!", Toast.LENGTH_SHORT).show();

                }else if(status.equals("Select Status")){

                    Toast.makeText(CourseAdd.this, "Invalid status!", Toast.LENGTH_SHORT).show();

                }else if(startDate.compareTo(endDate) > -1){

                    Toast.makeText(CourseAdd.this, "Start date cannot be on or after end date!", Toast.LENGTH_SHORT).show();

                }else{

                    Course course;

                    if(repository.getAllCourses().size() != 0){

                        newID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;

                    }else{

                        newID = 1;

                    }

                    course = new Course(newID, editName.getText().toString(), etStartSelectDate.getText().toString(), etEndSelectDate.getText().toString(), status, 0, 0);
                    repository.insertCourse(course);

                    Intent intent1 = new Intent(this,CourseList.class);
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

        Assessment assessment1 = new Assessment(assessment.getAssessmentID(), assessment.getAssessmentTitle(), assessment.getAssessmentType(), assessment.getStartDate(), assessment.getEndDate(), 0);
        assessmentAdapter.removeAssessment(position, assessment);
        repository.updateAssessment(assessment1);
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

    public void CourseEditAddAssessment(View view) {

        if(newID == 0){

            Toast.makeText(CourseAdd.this, "Save Course First!", Toast.LENGTH_SHORT).show();


        }else{


            int i = 0;
            mUserItems.clear();
            assessmentName = new String[0];

            for (Assessment assessment : repository.getAllAssessments()) {

                if (assessment.getCourseId() == 0) {

                    i++;
                    assessmentName = new String[i];

                }

            }

            if (assessmentName.length > 0) {

                i = 0;

                for (Assessment assessment : repository.getAllAssessments()) {

                    if (assessment.getCourseId() == 0) {

                        assessmentName[i] = assessment.getAssessmentTitle();
                        i++;

                    }

                }

                checkedItems = new boolean[assessmentName.length];

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CourseAdd.this);
                mBuilder.setTitle("Select Assessments");
                mBuilder.setMultiChoiceItems(assessmentName, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
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

                            String[] assessment = new String[mUserItems.size()];


                            for (int i = 0; i < mUserItems.size(); i++) {

                                assessment[i] = assessmentName[mUserItems.get(i)];

                            }

                            for (Assessment a : repository.getAllAssessments()) {

                                for (int i = 0; i < assessment.length; i++) {

                                    if (a.getAssessmentTitle().equals(assessment[i])) {

                                        Assessment assmt = new Assessment(a.getAssessmentID(), a.getAssessmentTitle(), a.getAssessmentType(), a.getStartDate(), a.getEndDate(), newID);

                                        repository.updateAssessment(assmt);

                                    }

                                }

                            }

                            filteredAssessments.clear();

                            assessmentName = new String[0];

                            for (Assessment a : repository.getAllAssessments()) {

                                if (a.getCourseId() == courseId) filteredAssessments.add(a);

                            }

                            assessmentAdapter.setAssessments(filteredAssessments);

                        } else {

                            Toast.makeText(CourseAdd.this, "No assessment selected!", Toast.LENGTH_SHORT).show();

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

                Toast.makeText(CourseAdd.this, "No available assessment to add!", Toast.LENGTH_SHORT).show();

            }

        }

    }

}
