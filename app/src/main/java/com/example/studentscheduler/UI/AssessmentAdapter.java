package com.example.studentscheduler.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscheduler.Entity.Assessment;
import com.example.studentscheduler.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    class AssessmentViewHolder extends RecyclerView.ViewHolder{

        TextView assessmentName;
        TextView assessmentType;
        TextView startDate;
        TextView endDate;


        ImageView assessmentEdit;
        ImageView assessmentDelete;

        private AssessmentViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface){

            super(itemView);
            assessmentName = itemView.findViewById(R.id.assessment_name);
            assessmentType = itemView.findViewById(R.id.assessment_type);
            startDate = itemView.findViewById(R.id.assessment_date);
            endDate = itemView.findViewById(R.id.assessment_end_date);
            assessmentEdit = itemView.findViewById(R.id.assessmentlistedit);
            assessmentDelete = itemView.findViewById(R.id.assessmentlistdelete);

            assessmentEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            final Assessment current = mAssessments.get(position);
                            recyclerViewInterface.onEditClickAssessment(position, current);

                        }

                    }

                }
            });


            assessmentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            final Assessment current = mAssessments.get(position);
                            recyclerViewInterface.onDeleteClickAssessment(position, current);

                        }

                    }

                }
            });

        }

    }



    public AssessmentAdapter(Context context, RecyclerViewInterface recyclerViewInterface){

        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {

        if(context.toString().contains("CourseEdit")){

            holder.assessmentEdit.setVisibility(View.INVISIBLE);

        }

        if(mAssessments != null){

            Assessment current = mAssessments.get(position);
            holder.assessmentName.setText("" + current.getAssessmentTitle());
            holder.assessmentType.setText("" + current.getAssessmentType());
            holder.startDate.setText("" + current.getStartDate());
            holder.endDate.setText("" + current.getEndDate());

        }

    }

    @Override
    public int getItemCount() {

        if(mAssessments != null) {

            return mAssessments.size();

        }else

            return 0;

    }

    public void setAssessments(List<Assessment> assessments){

        mAssessments = assessments;
        notifyDataSetChanged();

    }

    public void removeAssessment(int position, Assessment assessment){

        mAssessments.remove(assessment);
        notifyItemRemoved(position);

    }

}
