package com.example.chymv2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chymv2.R;
import com.example.chymv2.adapters.ExerciceListAdapter;
import com.example.chymv2.adapters.PopUpCrearRutinasAdapter;
import com.example.chymv2.model.ListExercice;
import com.example.chymv2.sources.InitializeData;
import com.example.chymv2.viewmodel.CrearRutinasViewModel;
import com.example.chymv2.viewmodel.ExercicesViewModel;
import com.example.chymv2.viewmodel.RoutineViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ActivityCrearRutinas extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Button returnMain_crearRutinas_btn, btnCrearRutina;
    private com.example.chymv2.view.CustomEditText tvNameRoutine,tvRoutineType;
    private ImageView ivColor;
    private NavigationBarView navigation;
    private RoutineViewModel routineViewModel;
    private String color, nombre,routineType,listaEjs, idList;
    private FloatingActionButton fabAddExercices;
    boolean problemsAdding;

    private PopUpCrearRutinasAdapter popUpCrearRutinasAdapter;
    private ExerciceListAdapter exerciceListAdapter;
    private ExercicesViewModel exercicesViewModelPopUp;
    private CrearRutinasViewModel crearRutinasViewModel;
    private RecyclerView rvCrearRutinas;
    private Dialog myDialog;

    //POPUP AÑADIR EJERCICIOs

    private ImageView closePopUp;
    private androidx.appcompat.widget.SearchView searchPopUp;
    private RecyclerView recyclerPopUp;
    private Spinner spinnerPopUp;
    private Button btnPopUp;
    private List<ListExercice> elements;

    //POPUP EDITAR EJERCICIOS

    private ImageView closeEditPopUp;
    private com.example.chymv2.view.CustomEditText series,repes,kg;
    private Button btnEditPopUp;
    private String strSeries, strRepes, strKg;
    private int sumadorId;

    //POP UP CAMBIAR COLOR

    private ImageView color1,color2,color3,color4;
    private ImageView color5,color6,color7,color8;
    private ImageView color9,color10,color11,color12;
    private int col;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rutinas);
        routineViewModel = new RoutineViewModel(this,2);
        navigation = findViewById(R.id.bottom_navigation_crearRutinas);

        ivColor = findViewById(R.id.selectColor);
        tvRoutineType = findViewById(R.id.tvTypeRoutine);
        tvNameRoutine = findViewById(R.id.tvNameRoutine);
        fabAddExercices = findViewById(R.id.fabCrearRutinas);
        rvCrearRutinas = findViewById(R.id.rvCrearRutinas);

        returnMain_crearRutinas_btn = findViewById(R.id.returnMain_crearRutinas_btn);
        btnCrearRutina = findViewById(R.id.btnRoutineCreate);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        myDialog = new Dialog(this);

        crearRutinasViewModel = new CrearRutinasViewModel(this);

        sumadorId = 1;

        col= getResources().getColor(R.color.black,null);
        color= String.format("#%06X", (0xFFFFFF & col));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        onClickListeners();
        listaEjerciciosSelec();

    }

    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.exercices_fragment:
                    startActivity(new Intent(ActivityCrearRutinas.this, ActivityMain.class).putExtra("parametro",2));
                    finish();
                    return true;
                case R.id.profile_fragment:
                    startActivity(new Intent(ActivityCrearRutinas.this, ActivityMain.class).putExtra("parametro",3));
                    finish();
                    return true;
                case R.id.routine_fragment:
                    startActivity(new Intent(ActivityCrearRutinas.this, ActivityMain.class).putExtra("parametro",1));
                    finish();
                    return true;
            }
            return false;
        }
    };
    public void onClickListeners(){
        returnMain_crearRutinas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCrearRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //color = tvColorRoutine.getText().toString();
                nombre = tvNameRoutine.getText().toString();
                routineType = tvRoutineType.getText().toString();
                idList = "2";
                if(exerciceListAdapter.getItemCount()==0) {
                    Toast.makeText(ActivityCrearRutinas.this, "¡No puedes crear una rutina sin ejercicios!", Toast.LENGTH_SHORT).show();
                    listaEjs = "";
                    problemsAdding = true;
                }
                else {
                    listaEjs = crearRutinasViewModel.listaToTexto(elements);
                    problemsAdding = routineViewModel.addRoutine(routineViewModel.crearRutina(color,nombre,routineType,listaEjs,idList));
                }



                if(!problemsAdding && exerciceListAdapter.getItemCount()!=0){
                    Toast.makeText(ActivityCrearRutinas.this,"Rutina añadida correctamente a Mis Rutinas", Toast.LENGTH_SHORT).show();
                    resetActivity();
                }
                else if(problemsAdding){
                    Toast.makeText(ActivityCrearRutinas.this,"Error al añadir Rutina a Mis Rutinas", Toast.LENGTH_SHORT).show();
                    resetActivity();
                    //Eliminar la rutina de la base de datos ipsofacto
                }

            }
        });
        //PopUp de añadir ejercicios
        fabAddExercices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {;
                showPopUpViewCrear();
            }
        });
        ivColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpColor();
            }
        });
    }

    public void listaEjerciciosSelec(){
        exerciceListAdapter = new ExerciceListAdapter(crearRutinasViewModel.getExercices().getValue(), this, new ExerciceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListExercice item) {
                showPopUpViewEditar(item);
            }
        });
        rvCrearRutinas.setLayoutManager(new LinearLayoutManager(this));
        rvCrearRutinas.setHasFixedSize(true);
        rvCrearRutinas.setAdapter(exerciceListAdapter);
    }
    public void resetActivity(){
        tvNameRoutine.setText("");
        tvRoutineType.setText("");
        col= getResources().getColor(R.color.black,null);
        color= String.format("#%06X", (0xFFFFFF & col));
        ivColor.setColorFilter(Color.parseColor(color),PorterDuff.Mode.SRC_IN);
        exerciceListAdapter.deleteAll();
        exerciceListAdapter.notifyDataSetChanged();
    }
    public void asignarValores(){

    }

/*
--------------------------------------------------------------------------
--------------------------------------------------------------------------
--------------------------------------------------------------------------

                            ACTIVITY POP UP AÑADIR EJERCICIOS

--------------------------------------------------------------------------
--------------------------------------------------------------------------
--------------------------------------------------------------------------
 */
    public void showPopUpViewCrear(){
        elements = new ArrayList<>();

        myDialog.setContentView(R.layout.pop_up_crear_rutinas);
        myDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        exercicesViewModelPopUp = new ExercicesViewModel(myDialog.getContext());
        closePopUp = (ImageView) myDialog.findViewById(R.id.ivClosePopUp);
        searchPopUp = (androidx.appcompat.widget.SearchView) myDialog.findViewById(R.id.svPopUp);
        recyclerPopUp = (RecyclerView) myDialog.findViewById(R.id.rvPopUp);
        btnPopUp = (Button) myDialog.findViewById(R.id.btnPopUp);
        spinnerPopUp =(Spinner) myDialog.findViewById(R.id.spinnerPopUp);

        popUpOnClickListeners();
        searchPopUp.requestFocus();
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initListEjercicios();
        initListenerTeclado();
        myDialog.show();
    }

    public void popUpOnClickListeners(){
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog.dismiss();
            }
        });

        spinnerPopUp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(myDialog.getContext(),"Seleccionado: " + adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
                popUpCrearRutinasAdapter.filterByType(adapterView.getItemAtPosition(i).toString());
                searchPopUp.setQuery("",true);
                popUpCrearRutinasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(ListExercice i: elements){
                    i.setSelected("#FFFFFF");
                    crearRutinasViewModel.addNewValue(i);
                    exerciceListAdapter.notifyDataSetChanged();
                }

                myDialog.dismiss();
            }
        });
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                for(ListExercice i:exercicesViewModelPopUp.getExercices().getValue()){
                    i.setSelected("#FFFFFF");
                }
                popUpCrearRutinasAdapter.filterByType("Todo");
            }
        });
    }

    public void initListEjercicios(){

        popUpCrearRutinasAdapter = new PopUpCrearRutinasAdapter(exercicesViewModelPopUp.getExercices().getValue(), myDialog.getContext(), new PopUpCrearRutinasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListExercice item) {

                if(!elements.contains(item)){
                    elements.add(item);
                }

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(myDialog.getContext(), R.array.combo_musculos, android.R.layout.simple_spinner_item);
        spinnerPopUp.setAdapter(adapter);
        recyclerPopUp.setLayoutManager(new LinearLayoutManager(myDialog.getContext()));
        recyclerPopUp.setHasFixedSize(true);
        recyclerPopUp.setAdapter(popUpCrearRutinasAdapter);
    }

    private void initListenerTeclado(){
        searchPopUp.setOnQueryTextListener(this);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        popUpCrearRutinasAdapter.filter(newText);
        popUpCrearRutinasAdapter.notifyDataSetChanged();
        return false;
    }
/*
--------------------------------------------------------------------------
--------------------------------------------------------------------------
--------------------------------------------------------------------------

                      ACTIVITY POP UP EDITAR EJERCICIOS

--------------------------------------------------------------------------
--------------------------------------------------------------------------
--------------------------------------------------------------------------
*/


    public void showPopUpViewEditar(ListExercice item){

        myDialog.setContentView(R.layout.pop_up_editar_ejercicios);
        myDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        series =  myDialog.findViewById(R.id.cetSeries);
        repes = myDialog.findViewById(R.id.cetRepes);
        kg = myDialog.findViewById(R.id.cetKg);
        btnEditPopUp =  myDialog.findViewById(R.id.btnEditPopUp);
        closeEditPopUp =  myDialog.findViewById(R.id.ivCloseEditPopUp);

        editExercicesOnClickListeners(item);
        myDialog.show();
    }
    public void editExercicesOnClickListeners(ListExercice item){
        closeEditPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        btnEditPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListExercice newExercice = new ListExercice(item.getColor(),item.getEjercicio(),item.getGrupoMuscular(),item.getTipoEjercicio(),item.getDescripcion());
                Cursor c = InitializeData.getDbInstance(ActivityCrearRutinas.this).getDataExercices();
                c.moveToLast();
                int id = c.getInt(0) +sumadorId;

                newExercice.setId(id);
                sumadorId +=1;

                strSeries = series.getText().toString();
                strRepes = repes.getText().toString();
                strKg = kg.getText().toString();

                newExercice.setSeries(strSeries);
                newExercice.setRepeticiones(strRepes);
                newExercice.setKg(strKg);

                elements.remove(item);
                elements.add(newExercice);

                //String pa = newExercice.getSeries() + ", " + newExercice.getId();
                //Toast.makeText(ActivityCrearRutinas.this,pa,Toast.LENGTH_LONG).show();
                myDialog.dismiss();
            }
        });
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
    }
    /*
--------------------------------------------------------------------------
--------------------------------------------------------------------------
--------------------------------------------------------------------------

                      ACTIVITY POP UP CAMBIAR COLOR

--------------------------------------------------------------------------
--------------------------------------------------------------------------
--------------------------------------------------------------------------
*/
    public void showPopUpColor(){
        myDialog.setContentView(R.layout.pop_up_select_color);

        color1 = myDialog.findViewById(R.id.color1);
        color2 = myDialog.findViewById(R.id.color2);
        color3 = myDialog.findViewById(R.id.color3);
        color4 = myDialog.findViewById(R.id.color4);
        color5 = myDialog.findViewById(R.id.color5);
        color6 = myDialog.findViewById(R.id.color6);
        color7 = myDialog.findViewById(R.id.color7);
        color8= myDialog.findViewById(R.id.color8);
        color9 = myDialog.findViewById(R.id.color9);
        color10 = myDialog.findViewById(R.id.color10);
        color11 = myDialog.findViewById(R.id.color11);
        color12 = myDialog.findViewById(R.id.color12);


        colorOnClickListeners();
        myDialog.show();
    }
    public void colorOnClickListeners(){

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.azulFlaco,null);
                cambiarColor();
            }
        });
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.azulMarino,null);
                cambiarColor();
            }
        });
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.magenta,null);
                cambiarColor();
            }
        });
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.purple_500,null);
                cambiarColor();

            }
        });
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.amarisho,null);
                cambiarColor();
            }
        });

        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.verdesito,null);
                cambiarColor();
            }
        });
        color7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.naranja,null);
                cambiarColor();
            }
        });
        color8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.rojoPuro,null);
                cambiarColor();

            }
        });
        color9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.white,null);
                cambiarColor();
            }
        });
        color10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.humito,null);
                cambiarColor();
            }
        });
        color11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.humo,null);
                cambiarColor();
            }
        });
        color12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                col= getResources().getColor(R.color.black,null);
                cambiarColor();
            }
        });
    }
    public void cambiarColor(){
        color= String.format("#%06X", (0xFFFFFF & col));
        ivColor.setColorFilter(Color.parseColor(color),PorterDuff.Mode.SRC_IN);
        myDialog.dismiss();
    }

}