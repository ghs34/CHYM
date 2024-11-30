package com.example.chymv2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chymv2.R;
import com.example.chymv2.adapters.RoutineListAdapter;
import com.example.chymv2.model.Rutina;
import com.example.chymv2.sources.InitializeData;
import com.example.chymv2.viewmodel.RoutineViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class ActivityRecomendedRoutines extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private RoutineListAdapter routineListAdapter;
    private RoutineViewModel routineViewModel;
    private androidx.appcompat.widget.SearchView routineSearch;

    private Button returnMain_misRutinas_btn;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_recomendadas);

        NavigationBarView navigation = findViewById(R.id.bottom_navigation_rutinasRecomendadas);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        returnMain_misRutinas_btn = findViewById(R.id.returnMain_rutinasRecomendadas_btn);

        returnMain_misRutinas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        routineViewModel = new RoutineViewModel(this,0);
        initlistaRutinas();
        initListenerRoutines();
        onClickListeners();
        routineSearch.clearFocus();

    }
    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.exercices_fragment:
                    startActivity(new Intent(ActivityRecomendedRoutines.this, ActivityMain.class).putExtra("parametro",2));
                    finish();
                    return true;
                case R.id.profile_fragment:
                    startActivity(new Intent(ActivityRecomendedRoutines.this, ActivityMain.class).putExtra("parametro",3));
                    finish();
                    return true;
                case R.id.routine_fragment:
                    startActivity(new Intent(ActivityRecomendedRoutines.this, ActivityMain.class).putExtra("parametro",1));
                    finish();
                    return true;
            }
            return false;
        }
    };

    private void initlistaRutinas() {

        routineListAdapter = new RoutineListAdapter(routineViewModel.getRoutines().getValue(),this, new RoutineListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Rutina item) {
                moveToRoutine(item);
            }
        });
        routineListAdapter.notifyDataSetChanged();
        routineSearch = findViewById(R.id.routineSearch);

        //Spinner
        spinner = findViewById(R.id.spinnerRutinasRecomendadas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ActivityRecomendedRoutines.this,R.array.combo_rutina, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        //RecyclerView
        recyclerView = findViewById(R.id.routineListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(routineListAdapter);
    }
    private void initListenerRoutines(){
        routineSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        routineListAdapter.filter(s);
        routineListAdapter.notifyDataSetChanged();
        return false;
    }
    public void moveToRoutine(Rutina item){
        Intent intent = new Intent(this, ActivityRoutineDescription.class);
        intent.putExtra("Rutina", item);
        startActivity(intent);
    }
    public void onClickListeners(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ActivityRecomendedRoutines.this,"Seleccionado: " + adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
                routineListAdapter.filterByType(adapterView.getItemAtPosition(i).toString());
                routineSearch.setQuery("",true);
                routineListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}