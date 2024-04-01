package com.example.studentscheduler.UI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

public class CourseEdit extends AppCompatActivity implements RecyclerViewInterface{


    private String name;
    private String start;
    private String end;
    private String status;
    private int courseTermId;
    private int courseInstructorId;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String[] spinnerStatus = {"Select Status", "In Progress", "Completed", "Dropped", "Plan To Take"};
    String[] assessmentName;
    boolean[] checkedItems;
    Button addNote;
    Button viewNote;


    private EditText editName;
    private EditText etStartSelectDate;
    private EditText etEndSelectDate;
    private EditText instructorNameField;
    private EditText instructorPhoneField;
    private EditText instructorEmailField;

    private ImageButton startSelectDate;
    private ImageButton endSelectDate;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    Date startDate;
    Date endDate;

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

        courseId = getIntent().getIntExtra("courseId", 0);

        courseInstructorId = getIntent().getIntExtra("courseInstructorId", 0);

        name = getIntent().getStringExtra("courseName");
        editName = findViewById(R.id.course_edit_coursename);
        editName.setText(name);

        start = getIntent().getStringExtra("courseStart");
        etStartSelectDate = findViewById(R.id.course_edit_startdate);
        etStartSelectDate.setText(start);
        etStartSelectDate.setFocusable(false);

        end = getIntent().getStringExtra("courseEnd");
        etEndSelectDate = findViewById(R.id.course_edit_enddate);
        etEndSelectDate.setText(end);
        etEndSelectDate.setFocusable(false);


        courseTermId = getIntent().getIntExtra("courseTermId", 0);

        status = getIntent().getStringExtra("courseStatus");

        instructorNameField = findViewById(R.id.course_edit_instructorname);
        instructorPhoneField = findViewById(R.id.course_edit_instructorphone);
        instructorEmailField = findViewById(R.id.course_edit_instructoremail);

        addNote = findViewById(R.id.course_edit_add_note);
        addNote.setBackgroundColor(Color.WHITE);

        viewNote = findViewById(R.id.course_edit_view_note);
        viewNote.setBackgroundColor(Color.WHITE);

        statusSpinner = findViewById(R.id.course_edit_spinner);

        createNotificationChannel();

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

        for(Instructor instructor: repository.getAllInstructors()){

            if(instructor.getInstructorID() == courseInstructorId){

                instructorNameField.setText(instructor.getInstructorName());
                instructorPhoneField.setText(instructor.getPhoneNumber());
                instructorEmailField.setText(instructor.getEmailAddress());
                break;

            }else{

                instructorNameField.setText("Not Yet Assigned!");
                instructorPhoneField.setText("Not Yet Assigned!");
                instructorEmailField.setText("Not Yet Assigned!");

            }

        }

        instructorNameField.setFocusable(false);
        instructorPhoneField.setFocusable(false);
        instructorEmailField.setFocusable(false);



        RecyclerView recyclerView = findViewById(R.id.course_edit_recyclerview);
        assessmentAdapter = new AssessmentAdapter(this, this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for(Assessment assessment : repository.getAllAssessments()){

            if(assessment.getCourseId() == courseId)filteredAssessments.add(assessment);

            assessmentAdapter.setAssessments(filteredAssessments);

        }

        final int yearStart = Integer.parseInt(start.substring(start.length() - 4));
        final int monthStart = Integer.parseInt(start.substring(0, 2)) - 1;
        final int dayStart = Integer.parseInt(start.substring(3, 5));

        final int yearEnd = Integer.parseInt(end.substring(end.length() - 4));
        final int monthEnd = Integer.parseInt(end.substring(0, 2)) - 1;
        final int dayEnd = Integer.parseInt(end.substring(3, 5));

        startSelectDate = findViewById(R.id.course_edit_start_btn);
        endSelectDate  = findViewById(R.id.course_edit_end_btn);

        startSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(CourseEdit.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog dialog = new DatePickerDialog(CourseEdit.this, new DatePickerDialog.OnDateSetListener() {
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

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Course c : repository.getAllCourses()){

                    if(c.getCourseID() == courseId){

                        courseId = c.getCourseID();
                        courseInstructorId = c.getInstructorId();
                        name = c.getCourseTitle();
                        start = c.getStartDate();
                        end = c.getEndDate();
                        courseTermId = c.getTermId();
                        status = c.getStatus();
                        break;
                    }

                }

                Intent intentSecond = new Intent(CourseEdit.this, NoteAdd.class);
                intentSecond.putExtra("courseId",courseId);
                intentSecond.putExtra("courseInstructorId", courseInstructorId);
                intentSecond.putExtra("courseName", name);
                intentSecond.putExtra("courseStart", start);
                intentSecond.putExtra("courseEnd", end);
                intentSecond.putExtra("courseTermId", courseTermId);
                intentSecond.putExtra("courseStatus", status);


                startActivity(intentSecond);

            }
        });

        viewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Course c : repository.getAllCourses()){

                    if(c.getCourseID() == courseId){

                        courseId = c.getCourseID();
                        courseInstructorId = c.getInstructorId();
                        name = c.getCourseTitle();
                        start = c.getStartDate();
                        end = c.getEndDate();
                        courseTermId = c.getTermId();
                        status = c.getStatus();
                        break;
                    }

                }

                Intent intentSecond = new Intent(CourseEdit.this, NoteList.class);
                intentSecond.putExtra("courseId",courseId);
                intentSecond.putExtra("courseInstructorId", courseInstructorId);
                intentSecond.putExtra("courseName", name);
                intentSecond.putExtra("courseStart", start);
                intentSecond.putExtra("courseEnd", end);
                intentSecond.putExtra("courseTermId", courseTermId);
                intentSecond.putExtra("courseStatus", status);


                startActivity(intentSecond);

            }
        });


        }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.notify_menu, menu);

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

                if(b){

                    Toast.makeText(CourseEdit.this, "No special character allowed!", Toast.LENGTH_SHORT).show();

                }else if(status.equals("Select Status")){

                    Toast.makeText(CourseEdit.this, "Invalid status!", Toast.LENGTH_SHORT).show();

                }else if(startDate.compareTo(endDate) > -1) {

                    Toast.makeText(CourseEdit.this, "Start date cannot be on or after end date!", Toast.LENGTH_SHORT).show();

                }else{

                    Course course;

                    course = new Course(courseId, editName.getText().toString(), etStartSelectDate.getText().toString(), etEndSelectDate.getText().toString(), status, courseTermId, courseInstructorId);
                    repository.updateCourse(course);

                    Intent intentSecond = new Intent(this, CourseList.class);
                    startActivity(intentSecond);
                    return true;

                }


            case R.id.notify:

                for(Course course : repository.getAllCourses()){

                    if(courseId == course.getCourseID()){

                        String startDate = course.getStartDate();
                        String endDate = course.getEndDate();

                        int yearStart = Integer.parseInt(startDate.substring(startDate.length() - 4));
                        int monthStart = Integer.parseInt(startDate.substring(0, 2)) - 1;
                        int dayStart = Integer.parseInt(startDate.substring(3, 5));

                        int yearEnd = Integer.parseInt(endDate.substring(endDate.length() - 4));
                        int monthEnd = Integer.parseInt(endDate.substring(0, 2)) - 1;
                        int dayEnd = Integer.parseInt(endDate.substring(3, 5));

                        Calendar myAlarmDate = Calendar.getInstance();

                        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                        myAlarmDate.set(yearStart, monthStart, dayStart - 1, 12, 00, 0);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                        Intent intent1 = new Intent(CourseEdit.this, ReminderBroadcast.class);
                        intent1.putExtra("content", course.getCourseTitle() + "starts");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseEdit.this, 0,intent1, PendingIntent.FLAG_IMMUTABLE);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntent);

                        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                        myAlarmDate.set(yearEnd, monthEnd, dayEnd - 1, 12, 00, 0);

                        Intent intent2 = new Intent(CourseEdit.this, ReminderBroadcast.class);
                        intent2.putExtra("content", course.getCourseTitle() + "ends");
                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(CourseEdit.this, 0,intent2, PendingIntent.FLAG_IMMUTABLE);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntent);

                        Toast.makeText(CourseEdit.this, "Notification set for 1 day prior to start and end date!", Toast.LENGTH_SHORT).show();

                        break;

                    }

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

    public void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "ReminderChannel";
            String description = "Channel for reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }

    public void CourseEditAddAssessment(View view) {

        int i = 0;
        mUserItems.clear();
        assessmentName = new String[0];

        for (Assessment assessment : repository.getAllAssessments()) {

            if (assessment.getCourseId() == 0) {

                i++;
                assessmentName = new String[i];

            }

        }

        if(assessmentName.length > 0){

            i = 0;

            for (Assessment assessment : repository.getAllAssessments()) {

                if (assessment.getCourseId() == 0) {

                    assessmentName[i] = assessment.getAssessmentTitle();
                    i++;

                }

            }

            checkedItems = new boolean[assessmentName.length];

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(CourseEdit.this);
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

                                    Assessment assmt = new Assessment(a.getAssessmentID(), a.getAssessmentTitle(), a.getAssessmentType(), a.getStartDate(), a.getEndDate(),courseId);

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

                    }else{

                        Toast.makeText(CourseEdit.this, "No assessment selected!", Toast.LENGTH_SHORT).show();

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

            Toast.makeText(CourseEdit.this, "No available assessment to add!", Toast.LENGTH_SHORT).show();

        }

    }

}
