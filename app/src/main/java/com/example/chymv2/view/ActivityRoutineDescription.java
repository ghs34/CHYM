package com.example.chymv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chymv2.R;
import com.example.chymv2.adapters.RoutineDescriptionListAdapter;
import com.example.chymv2.model.ListExercice;
import com.example.chymv2.model.Rutina;
import com.example.chymv2.viewmodel.RoutineDescriptionViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ActivityRoutineDescription extends AppCompatActivity {
    CollapsingToolbarLayout routineCollapsingToolbarLayout;
    RecyclerView rvRoutineExercices;
    TextView tvRoutineName;
    RoutineDescriptionViewModel routineDescriptionViewModel;
    RoutineDescriptionListAdapter routineDescriptionListAdapter;
    Rutina element;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_description);




        element = (Rutina) getIntent().getSerializableExtra("Rutina");
        routineDescriptionViewModel = new RoutineDescriptionViewModel(element);

        routineCollapsingToolbarLayout = findViewById(R.id.collapsingToolBarRoutine);
        rvRoutineExercices = findViewById(R.id.rvRoutineExercices);
        tvRoutineName = findViewById(R.id.tvRoutineName);


        initlistaEjercicios();
        routineCollapsingToolbarLayout.setTitle(element.getNombre());
        tvRoutineName.setText(element.getNombre());


    }
    private void initlistaEjercicios() {
        routineDescriptionListAdapter = new RoutineDescriptionListAdapter(routineDescriptionViewModel.getExercices().getValue(), this, new RoutineDescriptionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListExercice item) {
                moveToDescription(item);
            }
        });
        routineDescriptionListAdapter.notifyDataSetChanged();
        rvRoutineExercices.setLayoutManager(new LinearLayoutManager(this));
        rvRoutineExercices.setHasFixedSize(true);
        rvRoutineExercices.setAdapter(routineDescriptionListAdapter);
    }
    public void moveToDescription(ListExercice item){
        Intent intent = new Intent(this, ActivityExerciceDescription.class);
        intent.putExtra("ListExercice", item);
        startActivity(intent);
    }

}
