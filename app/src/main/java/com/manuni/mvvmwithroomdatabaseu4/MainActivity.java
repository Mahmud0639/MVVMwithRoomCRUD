package com.manuni.mvvmwithroomdatabaseu4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.manuni.mvvmwithroomdatabaseu4.adapters.NotesAdapter;
import com.manuni.mvvmwithroomdatabaseu4.databinding.ActivityMainBinding;
import com.manuni.mvvmwithroomdatabaseu4.models.NoteEntity;
import com.manuni.mvvmwithroomdatabaseu4.viewmodels.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private final List<NoteEntity> noteEntities = new ArrayList<>();
    private ListViewModel listViewModel;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyEditorActivity.class);
                startActivity(intent);
            }
        });

        //noteEntities = listViewModel.sampleDataForViewModel;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(MainActivity.this,linearLayoutManager.getOrientation());
        binding.noteRecyclerView.addItemDecoration(itemDecoration);
        binding.noteRecyclerView.setLayoutManager(linearLayoutManager);
        binding.noteRecyclerView.setHasFixedSize(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteNote(notesAdapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
                //above code explanation is: we have to at first remove list item from the adapter array list because if it will successfully removed only then live data cannot be able to show in the recycler view
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.noteRecyclerView);

    }

    private void deleteNote(NoteEntity noteAtPosition) {
        listViewModel.deleteNote(noteAtPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addSampleData:
                addSampleData();
                break;
            case R.id.deleteAllData:
                deleteSampleData();
        }
        return super.onOptionsItemSelected(item);
    }
    private void deleteSampleData(){
        listViewModel.deleteSampleData();
    }
    private void addSampleData(){
        listViewModel.addSampleData();
    }

    private void initViewModel(){
        //listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel = new ViewModelProvider(MainActivity.this,getDefaultViewModelProviderFactory()).get(ListViewModel.class);
        Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntityList) {
                noteEntities.clear();
                noteEntities.addAll(noteEntityList);

                if (notesAdapter == null){
                    notesAdapter = new NotesAdapter(MainActivity.this,noteEntities);
                    binding.noteRecyclerView.setAdapter(notesAdapter);
                }else {
                    notesAdapter.notifyDataSetChanged();
                }
            }
        };
        listViewModel.sampleDataForViewModel.observe(MainActivity.this,notesObserver);
    }
}