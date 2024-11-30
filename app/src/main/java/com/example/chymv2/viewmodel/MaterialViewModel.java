package com.example.chymv2.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chymv2.model.ListExercice;
import com.example.chymv2.model.Rutina;

import java.util.List;

public class MaterialViewModel extends ViewModel {
    private MutableLiveData<List<ListExercice>> mMaterial;
    private List<ListExercice> elements;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();

}
