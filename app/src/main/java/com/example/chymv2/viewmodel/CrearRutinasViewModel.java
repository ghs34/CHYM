package com.example.chymv2.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chymv2.model.ListExercice;
import com.example.chymv2.sources.InitializeData;
import com.example.chymv2.view.ActivityCrearRutinas;

import java.util.ArrayList;
import java.util.List;

public class CrearRutinasViewModel {
    private MutableLiveData<List<ListExercice>> mExercices;
    private List<ListExercice> elements;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    private InitializeData data;
    private Context context;
    public CrearRutinasViewModel(Context context){
        data = InitializeData.getInstance(context);
        elements = new ArrayList<>();
        mExercices = new MutableLiveData<>();
        mExercices.setValue(elements);
        this.context = context;
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

    public String listaToTexto(List<ListExercice> items){

        String accum="";
        for(ListExercice i:items){
            if(!data.getAllListExercice().contains(i)) {
                int id = InitializeData.getInstance(context).meterEjercicioDB(i);
                accum+= id + ",";
            }
            else{
                accum+=i.getId()+",";
            }

        }
        //Toast.makeText(context,accum,Toast.LENGTH_LONG).show();
        return accum;
    }

}
