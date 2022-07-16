package com.manuni.mvvmwithroomdatabaseu4.adapters;

import static com.manuni.mvvmwithroomdatabaseu4.utils.Constants.NOTE_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuni.mvvmwithroomdatabaseu4.MyEditorActivity;
import com.manuni.mvvmwithroomdatabaseu4.R;
import com.manuni.mvvmwithroomdatabaseu4.databinding.NoteItemLayoutBinding;
import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{
    private Context context;
    private List<NoteEntity> list;

    public NotesAdapter(Context context,List<NoteEntity> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item_layout,parent,false);
        return new NotesViewHolder(view);
    }
    public NoteEntity getNoteAtPosition(int position){
        return list.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NoteEntity data = list.get(position);
        holder.binding.notesTxt.setText(data.getText());
        holder.binding.fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyEditorActivity.class);
                intent.putExtra(NOTE_ID_KEY,data.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{
        NoteItemLayoutBinding binding;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = NoteItemLayoutBinding.bind(itemView);
        }
    }
}
