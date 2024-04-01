package com.example.studentscheduler.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscheduler.Entity.Course;
import com.example.studentscheduler.Entity.Term;
import com.example.studentscheduler.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView courseName;
        TextView courseStartDate;
        TextView courseEndDate;
        TextView courseStatus;
        ImageView courseToDelete;
        ImageView courseToEdit;

        private CourseViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface){

            super(itemView);
            courseName = itemView.findViewById(R.id.course_name);
            courseStartDate = itemView.findViewById(R.id.course_start);
            courseEndDate = itemView.findViewById(R.id.course_end);
            courseStatus = itemView.findViewById(R.id.course_status);
            courseToDelete = itemView.findViewById(R.id.courselistdelete);
            courseToEdit = itemView.findViewById(R.id.courselistedit);

            courseToDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            final Course current = mCourses.get(position);
                            recyclerViewInterface.onDeleteClickCourse(position, current);

                        }

                    }

                }
            });

            courseToEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null) {

                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {

                            final Course current = mCourses.get(position);
                            recyclerViewInterface.onEditClickCourse(position, current);

                        }
                    }
                }

            });

        }

    }

    public CourseAdapter(Context context, RecyclerViewInterface recyclerViewInterface){

        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {

        if(context.toString().contains("TermEdit") || context.toString().contains("AssessmentEdit") || context.toString().contains("InstructorEdit")){

            holder.courseToEdit.setVisibility(View.INVISIBLE);

        }

        if(mCourses != null){

            Course current = mCourses.get(position);
            holder.courseName.setText("" + current.getCourseTitle());
            holder.courseStartDate.setText("" + current.getStartDate());
            holder.courseEndDate.setText("" + current.getEndDate());
            holder.courseStatus.setText(""+ current.getStatus());

        }

    }

    @Override
    public int getItemCount() {

        if(mCourses != null) {

            return mCourses.size();

        }else

            return 0;

    }

    public void setCourses(List<Course> courses){

        mCourses = courses;
        notifyDataSetChanged();

    }

    public void removeCourse(int position, Course course){

        mCourses.remove(course);
        notifyItemRemoved(position);

    }

}
