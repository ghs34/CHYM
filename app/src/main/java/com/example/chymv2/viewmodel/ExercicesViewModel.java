package com.example.chymv2.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chymv2.model.ListExercice;
import com.example.chymv2.model.Rutina;
import com.example.chymv2.sources.DatabaseHelper;
import com.example.chymv2.sources.EjerciciosDBtemporal;
import com.example.chymv2.sources.InitializeData;
import com.example.chymv2.view.ActivityMain;

import java.util.ArrayList;
import java.util.List;

public class ExercicesViewModel extends ViewModel {
    private MutableLiveData<List<ListExercice>> mExercices;
    private List<ListExercice> elements;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    private InitializeData data;
    private Context context;
    public ExercicesViewModel(Context context){
        data = InitializeData.getInstance(context);
        //elements = data.getAllListExercice();
        elements = new ArrayList<>();
        llenarElements();
        mExercices = new MutableLiveData<>();
        mExercices.setValue(elements);
        this.context = context;
    }
    public void llenarElements(){
        for(ListExercice i: data.getAllListExercice()){
            if(i.getSeries()== null && i.getRepeticiones()==null && i.getKg()==null){
                elements.add(i);
            }
        }
    }
    public LiveData<List<ListExercice>> getExercices() {
        return mExercices;
    }

    public void addNewValue(final ListExercice listElement) {

        List<ListExercice> currentElement = mExercices.getValue();
        currentElement.add(listElement);

    }
    public void setElements(List<ListExercice> items){
        elements = items;
    }

    public boolean addExercice(ListExercice exercice){

        elements.add(exercice);
        mExercices.setValue(elements);
        boolean problemsAdding = InitializeData.getDbInstance(context).insertExerciceData(exercice.getColor(),
                exercice.getEjercicio(),exercice.getGrupoMuscular(),exercice.getTipoEjercicio(),
                exercice.getDescripcion(),exercice.getSeries(),exercice.getRepeticiones(),exercice.getKg());

        InitializeData.getInstance(context).addExerciceData(exercice);
        return problemsAdding;
    }
}

