package com.example.studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscheduler.Entity.Course;
import com.example.studentscheduler.Entity.Instructor;
import com.example.studentscheduler.Entity.Term;
import com.example.studentscheduler.R;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private List<Instructor> mInstructors;
    private final Context context;
    private final LayoutInflater mInflater;

    class InstructorViewHolder extends RecyclerView.ViewHolder{

        TextView instructorName;
        TextView instructorPhoneNumber;
        TextView instructorEmail;

        ImageView instructorToDelete;
        ImageView instructorToEdit;

        private InstructorViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface){

            super(itemView);
            instructorName = itemView.findViewById(R.id.instructor_name);
            instructorPhoneNumber = itemView.findViewById(R.id.instructor_phone);
            instructorEmail = itemView.findViewById(R.id.instructor_email);
            instructorToEdit = itemView.findViewById(R.id.instructorlistedit);
            instructorToDelete = itemView.findViewById(R.id.instructorlistdelete);

            instructorToDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(recyclerViewInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            final Instructor current = mInstructors.get(position);
                            recyclerViewInterface.onDeleteClickInstructor(position, current);

                        }

                    }


                }
            });

            instructorToEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null) {

                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {

                            final Instructor current = mInstructors.get(position);
                            recyclerViewInterface.onEditClickInstructor(position, current);

                        }
                    }


                }
            });



        }

    }



    public InstructorAdapter(Context context, RecyclerViewInterface recyclerViewInterface){

        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.instructor_list_item, parent, false);
        return new InstructorViewHolder(itemView, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull InstructorViewHolder holder, int position) {

        if(mInstructors != null){

            Instructor current = mInstructors.get(position);
            holder.instructorName.setText("" + current.getInstructorName());
            holder.instructorPhoneNumber.setText("" + current.getPhoneNumber());
            holder.instructorEmail.setText("" + current.getEmailAddress());

        }

    }

    @Override
    public int getItemCount() {

        if(mInstructors != null) {

            return mInstructors.size();

        }else

            return 0;

    }

    public void setInstructors(List<Instructor> instructors){

        mInstructors = instructors;
        notifyDataSetChanged();

    }

    public void removeInstructor(int position, Instructor instructor){

        mInstructors.remove(instructor);
        notifyItemRemoved(position);

    }

}
