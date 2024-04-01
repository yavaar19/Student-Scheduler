package com.example.studentscheduler.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentscheduler.Entity.Note;
import com.example.studentscheduler.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.TermViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private List<Note> mNotes;
    private final Context context;
    private final LayoutInflater mInflater;

    class TermViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitle;
        ImageView noteDelete;
        ImageView noteEdit;

        private TermViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface){

            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_name);
            noteDelete = itemView.findViewById(R.id.notelistdelete);
            noteEdit = itemView.findViewById(R.id.notelistedit);


            noteDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            final Note current = mNotes.get(position);
                            recyclerViewInterface.onDeleteClickNote(position, current);

                        }

                    }

                }

            });


            noteEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(recyclerViewInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            final Note current = mNotes.get(position);
                            recyclerViewInterface.onEditClickNote(position, current);

                        }

                    }

                }
            });

        }

    }

    public NoteAdapter(Context context, RecyclerViewInterface recyclerViewInterface){

        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public NoteAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.note_list_item, parent, false);
        return new TermViewHolder(itemView, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.TermViewHolder holder, int position) {

        if(mNotes != null){

            Note current = mNotes.get(position);
            holder.noteTitle.setText("" + current.getTitle());

        }

    }

    @Override
    public int getItemCount() {

        if(mNotes != null) {

            return mNotes.size();

        }else

            return 0;

    }

    public void setNotes(List<Note> notes){

        mNotes = notes;
        notifyDataSetChanged();

    }

    public void removeNote(int position, Note note){

        mNotes.remove(note);
        notifyItemRemoved(position);

    }

}
