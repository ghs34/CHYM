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
import com.example.chymv2.adapters.MisRutinasListAdapter;
import com.example.chymv2.adapters.RoutineListAdapter;
import com.example.chymv2.model.ListExercice;
import com.example.chymv2.model.Rutina;
import com.example.chymv2.sources.DatabaseHelper;
import com.example.chymv2.sources.InitializeData;
import com.example.chymv2.viewmodel.RoutineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ActivityMisRutinas extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private MisRutinasListAdapter misRutinasListAdapter;
    private RoutineViewModel routineViewModel;
    private Button returnMain_rutinasRecomendadas_btn;
    private FloatingActionButton fabMisRutinas;
    private Spinner spinner;
    private androidx.appcompat.widget.SearchView svSearch;
    MisRutinasListAdapter.OnItemClickListener listener,subir,eliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_rutinas);

        NavigationBarView navigation = findViewById(R.id.bottom_navigation_misRutinas);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        returnMain_rutinasRecomendadas_btn = findViewById(R.id.returnMain_misRutinas_btn);
        fabMisRutinas = findViewById(R.id.fabMisRutinas);

        routineViewModel = new RoutineViewModel(this,2);
        initlistaRutinas();


        initListenerRoutines();
        setOnClickListeners();
        svSearch.clearFocus();

    }
    /*@Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }*/

    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.exercices_fragment:
                    startActivity(new Intent(ActivityMisRutinas.this, ActivityMain.class).putExtra("parametro",2));
                    finish();
                    return true;
                case R.id.profile_fragment:
                    startActivity(new Intent(ActivityMisRutinas.this, ActivityMain.class).putExtra("parametro",3));
                    finish();
                    return true;
                case R.id.routine_fragment:
                    startActivity(new Intent(ActivityMisRutinas.this, ActivityMain.class).putExtra("parametro",1));
                    finish();
                    return true;
            }
            return false;
        }
    };



    private void initlistaRutinas() {
        cardListeners();
        misRutinasListAdapter = new MisRutinasListAdapter(routineViewModel.getRoutines().getValue(),this, listener,eliminar,subir);
        misRutinasListAdapter.notifyDataSetChanged();
        svSearch = findViewById(R.id.routineSearch);

        //Spinner
        spinner = findViewById(R.id.spinnerMisRutinas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ActivityMisRutinas.this,R.array.combo_rutina, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        //RecyclerView
        recyclerView = findViewById(R.id.routineListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(misRutinasListAdapter);
    }
    private void initListenerRoutines(){
        svSearch.setOnQueryTextListener(this);
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        misRutinasListAdapter.filter(s);
        misRutinasListAdapter.notifyDataSetChanged();
        return false;
    }
    public void moveToRoutine(Rutina item){
        Intent intent = new Intent(this, ActivityRoutineDescription.class);
        intent.putExtra("Rutina", item);
        startActivity(intent);
    }
    public void setOnClickListeners(){
        returnMain_rutinasRecomendadas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //assert getSupportActionBar() != null;
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabMisRutinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMisRutinas.this, ActivityCrearRutinas.class);
                startActivity(intent);
                finish();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Seleccionado: " + adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
                misRutinasListAdapter.filterByType(adapterView.getItemAtPosition(i).toString());
                svSearch.setQuery("",true);
                misRutinasListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void cardListeners(){
        listener = new MisRutinasListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Rutina item) {
                moveToRoutine(item);
            }
        };
        eliminar = new MisRutinasListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Rutina item) {
                misRutinasListAdapter.eliminateItem(item);
                misRutinasListAdapter.notifyDataSetChanged();
                InitializeData.getInstance(ActivityMisRutinas.this).eliminateRoutine(item);
                String id = ""+item.getId();
                InitializeData.getDbInstance(ActivityMisRutinas.this).eliminateRoutine(id);
            }
        };
        subir = new MisRutinasListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Rutina item) {
                Toast.makeText(ActivityMisRutinas.this,"SUBIR", Toast.LENGTH_LONG).show();
            }
        };
    }
}