package com.example.studentscheduler.UI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class TermEdit extends AppCompatActivity implements RecyclerViewInterface{

    private String name;
    private String start;
    private String end;
    String[] courseName;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    private ImageButton startSelectDate;
    private ImageButton endSelectDate;

    private EditText editName;
    private EditText etStartSelectDate;
    private EditText etEndSelectDate;
    List<Course> filteredCourses = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    Date startDate;
    Date endDate;

    Repository repository = new Repository(getApplication());
    int termId;

    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_term_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = getIntent().getStringExtra("termName");
        editName = findViewById(R.id.termname);
        editName.setText(name);

        start = getIntent().getStringExtra("termStart");
        etStartSelectDate = findViewById(R.id.startdate);
        etStartSelectDate.setText(start);
        etStartSelectDate.setFocusable(false);

        end = getIntent().getStringExtra("termEnd");
        etEndSelectDate = findViewById(R.id.enddate);
        etEndSelectDate.setText(end);
        etEndSelectDate.setFocusable(false);

        termId = getIntent().getIntExtra("termId", 0);

        createNotificationChannel();

        RecyclerView recyclerView = findViewById(R.id.termeditrecyclerview);

        courseAdapter = new CourseAdapter(this, this);

        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            for(Course c : repository.getAllCourses()){

                if(c.getTermId() == termId )filteredCourses.add(c);

                courseAdapter.setCourses(filteredCourses);

            }

        final int yearStart = Integer.parseInt(start.substring(start.length() - 4));
        final int monthStart = Integer.parseInt(start.substring(0, 2)) - 1;
        final int dayStart = Integer.parseInt(start.substring(3, 5));

        final int yearEnd = Integer.parseInt(end.substring(end.length() - 4));
        final int monthEnd = Integer.parseInt(end.substring(0, 2)) - 1;
        final int dayEnd = Integer.parseInt(end.substring(3, 5));

        startSelectDate = findViewById(R.id.term_edit_start_btn);
        endSelectDate  = findViewById(R.id.term_edit_end_btn);

        startSelectDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(TermEdit.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog dialog = new DatePickerDialog(TermEdit.this, new DatePickerDialog.OnDateSetListener() {
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

        getMenuInflater().inflate(R.menu.notify_menu, menu);

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

                if(b){

                    Toast.makeText(TermEdit.this, "No special character allowed!", Toast.LENGTH_SHORT).show();

                }else if(startDate.compareTo(endDate) > -1) {

                    Toast.makeText(TermEdit.this, "Start date cannot be on or after end date!", Toast.LENGTH_SHORT).show();

                }else{

                    Term term;

                    term = new Term(termId, editName.getText().toString(), etStartSelectDate.getText().toString(), etEndSelectDate.getText().toString());
                    repository.updateTerm(term);

                    Intent intentSecond = new Intent(this, TermList.class);
                    startActivity(intentSecond);
                    return true;

                }

            case R.id.notify:

                for(Term term : repository.getAllTerms()){

                    if(termId == term.getTermID()){

                        String startDate = term.getStartDate();
                        String endDate = term.getEndDate();

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

                        Intent intent1 = new Intent(TermEdit.this, ReminderBroadcast.class);
                        intent1.putExtra("content", term.getTermName() + "starts");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(TermEdit.this, 0,intent1, PendingIntent.FLAG_IMMUTABLE);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntent);

                        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                        myAlarmDate.set(yearEnd, monthEnd, dayEnd - 1, 12, 00, 0);

                        Intent intent2 = new Intent(TermEdit.this, ReminderBroadcast.class);
                        intent2.putExtra("content", term.getTermName() + "ends");
                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(TermEdit.this, 0,intent2, PendingIntent.FLAG_IMMUTABLE);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntent);

                        Toast.makeText(TermEdit.this, "Notification set for 1 day prior to start and end date!", Toast.LENGTH_SHORT).show();

                        break;

                    }

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

    public void TermEditAddCourse(View view) {

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

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TermEdit.this);
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

                            Course c = new Course(Course.getCourseID(), Course.getCourseTitle(), Course.getStartDate(), Course.getEndDate(), Course.getStatus(), termId, Course.getInstructorId());

                            repository.updateCourse(c);

                        }

                    }

                }

                filteredCourses.clear();

                courseName = new String[0];

                for (Course c : repository.getAllCourses()) {

                    if (c.getTermId() == termId) filteredCourses.add(c);

                }

                courseAdapter.setCourses(filteredCourses);

            }else{

                    Toast.makeText(TermEdit.this, "No course selected!", Toast.LENGTH_SHORT).show();

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

            Toast.makeText(TermEdit.this, "No available courses to add!", Toast.LENGTH_SHORT).show();

        }

    }

}
