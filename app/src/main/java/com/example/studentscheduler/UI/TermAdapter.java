package com.example.studentscheduler.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscheduler.Entity.Term;
import com.example.studentscheduler.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    class TermViewHolder extends RecyclerView.ViewHolder{

        TextView termName;
        TextView termStartDate;
        TextView termEndDate;
        ImageView termDelete;
        ImageView termEdit;

        private TermViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface){

            super(itemView);
            termName = itemView.findViewById(R.id.term_name);
            termStartDate = itemView.findViewById(R.id.term_start);
            termEndDate = itemView.findViewById(R.id.term_end);
            termDelete = itemView.findViewById(R.id.termlistdelete);
            termEdit = itemView.findViewById(R.id.termlistedit);


            termDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            final Term current = mTerms.get(position);
                            recyclerViewInterface.onDeleteClickTerm(position, current);

                        }

                    }

                }

            });


            termEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            final Term current = mTerms.get(position);
                            recyclerViewInterface.onEditClickTerm(position, current);

                        }

                    }

                }
            });

        }

    }

    public TermAdapter(Context context, RecyclerViewInterface recyclerViewInterface){

        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {

        if(mTerms != null){

            Term current = mTerms.get(position);
            holder.termName.setText("" + current.getTermName());
            holder.termStartDate.setText("" + current.getStartDate());
            holder.termEndDate.setText("" + current.getEndDate());

        }

    }

    @Override
    public int getItemCount() {

        if(mTerms != null) {

            return mTerms.size();

        }else

            return 0;

    }

    public void setTerms(List<Term> terms){

        mTerms = terms;
        notifyDataSetChanged();

    }

    public void removeTerm(int position, Term term){

        mTerms.remove(term);
        notifyItemRemoved(position);

    }

}
