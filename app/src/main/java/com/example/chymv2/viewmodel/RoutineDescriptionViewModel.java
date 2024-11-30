package com.example.chymv2.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chymv2.model.ListExercice;
import com.example.chymv2.model.Rutina;
import com.example.chymv2.sources.DatabaseHelper;
import com.example.chymv2.sources.InitializeData;

import java.util.List;

public class RoutineDescriptionViewModel extends ViewModel {
    private MutableLiveData<List<ListExercice>> mExercices;
    private List<ListExercice> elements;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    Rutina rutina;

    public RoutineDescriptionViewModel(Rutina rutina){
        this.rutina = rutina;
        elements = rutina.getEjercicios();
        mExercices = new MutableLiveData<>();
        mExercices.setValue(elements);
    }
    public LiveData<List<ListExercice>> getExercices() {
        return mExercices;
    }



}
