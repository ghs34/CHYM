package com.example.chymv2.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chymv2.model.Ejercicio;
import com.example.chymv2.model.ListExercice;
import com.example.chymv2.model.Rutina;
import com.example.chymv2.sources.DatabaseHelper;
import com.example.chymv2.sources.EjerciciosDBtemporal;
import com.example.chymv2.sources.InitializeData;
import com.example.chymv2.view.ActivityMain;
import com.example.chymv2.view.ActivityMisRutinas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RoutineViewModel {
    private MutableLiveData<List<Rutina>> mRoutines;
    private List<Rutina> RoutineElements;
    private List<ListExercice> ExerciceElements;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();

    private InitializeData data;
    private Context context;
    private FirebaseAuth firebaseAuth;
    public RoutineViewModel(Context context, int vLista){
        mRoutines = new MutableLiveData<>();
        this.context = context;
        data = InitializeData.getInstance(context);
        actualizarLista(vLista);
        ExerciceElements = data.getAllListExercice();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public LiveData<List<Rutina>> getRoutines() {
        return mRoutines;
    }

    public void addNewValue(final Rutina listElement) {

        List<Rutina> currentElement = mRoutines.getValue();
        currentElement.add(listElement);

    }

    public ListExercice findExerciceById(int id){
        return data.findExerciceById(id);
    }

    public List<Rutina> listaRutinasCorrespondiente(int vLista){
        List<Rutina> rutinas;
        switch (vLista){
            case 0:
                //Rutinas recomendadas
                rutinas = getRoutineByIdList("0");

                break;
            case 1:
                //Rutinas comunidad
                rutinas = getRoutineByIdList("1");
                break;
            case 2:
                //Mis Rutinas
                rutinas = getRoutineByIdList("2");
                break;
            default:
                rutinas = null;
                break;
        }


        return rutinas;
    }
    public void actualizarLista(int vLista){
        RoutineElements = listaRutinasCorrespondiente(vLista);

        mRoutines.setValue(RoutineElements);
    }
    public boolean addRoutine(Rutina rutina){

        RoutineElements.add(rutina);
        mRoutines.setValue(RoutineElements);
        String color = rutina.getColor();
        String nombre = rutina.getNombre();
        String tipo = rutina.getRoutineType();
        String ejercicios = ListaEjerciciosToStringId(rutina);
        String idList = rutina.getIdList();

        boolean problemsAdding = InitializeData.getDbInstance(context).insertRoutineData(color,nombre,tipo,ejercicios,idList);
        InitializeData.getInstance(context).addRoutineData(rutina);
        actualizarLista(Integer.parseInt(rutina.getIdList()));

        return problemsAdding;
    }
    public List<Rutina> getRoutineByIdList(String idList){
        List<Rutina> rutinas = new ArrayList<>();
        for(Rutina i: data.getAllRoutines()){
            if(i.getIdList().equals(idList)){
                rutinas.add(i);
            }
        }

        return rutinas;
    }
    public String ListaEjerciciosToStringId(Rutina rutina){
        String ids ="";
        List<ListExercice> lista= rutina.getEjercicios();
        for(int i=0;i<lista.size();i++){
            ids += lista.get(i).getId();
            ids += ",";
            /*if(i < lista.size() -1){

            }*/
        }
        return ids;
    }
    public Rutina crearRutina(String color, String name,String routineType, String listEx, String idList){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("mis rutinas").child(user.getUid());
        String key = reference.push().getKey();
        Rutina rutina = new Rutina(color,name,routineType,StringToExercices(listEx),idList);
        reference.child(key).setValue(rutina);
        return rutina;
    }
    public ArrayList<ListExercice> StringToExercices(String listEx){
        ArrayList<ListExercice> listExercices = new ArrayList<>();
        String almacenar = "";
        for(int i = 0; i<listEx.length();i++){
            if(listEx.charAt(i) == ','){
                listExercices.add(findExerciceById(Integer.parseInt(almacenar)));
                almacenar = "";
            }
            else{
                almacenar += listEx.charAt(i);
            }
        }
        return listExercices;
    }

}
