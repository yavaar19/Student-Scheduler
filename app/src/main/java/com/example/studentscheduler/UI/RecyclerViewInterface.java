package com.example.studentscheduler.UI;

import com.example.studentscheduler.Entity.Assessment;
import com.example.studentscheduler.Entity.Course;
import com.example.studentscheduler.Entity.Instructor;
import com.example.studentscheduler.Entity.Note;
import com.example.studentscheduler.Entity.Term;

public interface RecyclerViewInterface {

    void onDeleteClickTerm(int position, Term term);

    void onEditClickTerm(int position, Term term);

    void onDeleteClickCourse(int position, Course course);

    void onCourseShowAction(Boolean isSelected);

    void onEditClickCourse(int position, Course current);

    void onEditClickAssessment(int position, Assessment current);

    void onDeleteClickAssessment(int position, Assessment assessment);

    void onDeleteClickInstructor(int position, Instructor instructor);

    void onEditClickInstructor(int position, Instructor instructor);

    void onDeleteClickNote(int position, Note note);

    void onEditClickNote(int position, Note note);

}
