package com.example.chymv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chymv2.R;
import com.example.chymv2.adapters.ExerciceListAdapter;
import com.example.chymv2.model.ListExercice;
import com.example.chymv2.sources.InitializeData;
import com.example.chymv2.viewmodel.ExercicesViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentExercice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentExercice extends Fragment implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ExerciceListAdapter exerciceListAdapter;
    private ExercicesViewModel exercicesViewModel;
    private androidx.appcompat.widget.SearchView svSearch;
    private Spinner spinner;


    public FragmentExercice() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment exerciceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentExercice newInstance(String param1, String param2) {
        FragmentExercice fragment = new FragmentExercice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_exercice, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        exercicesViewModel = new ExercicesViewModel(getContext());
        initlistaEjercicios(view);
        initListenerExercices();

    }

    private void initlistaEjercicios(View view) {
        exerciceListAdapter = new ExerciceListAdapter(exercicesViewModel.getExercices().getValue(), getContext(), new ExerciceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListExercice item) {
                moveToDescription(item);
            }
        });
        exerciceListAdapter.notifyDataSetChanged();
        svSearch = view.findViewById(R.id.svSearch);
        svSearch.clearFocus();
        //Spinner init logic
        spinner = view.findViewById(R.id.spinnerEx);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.combo_musculos, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        //Recyclerview init logic
        recyclerView = view.findViewById(R.id.listRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(exerciceListAdapter);
        onClickListeners();
    }

    private void initListenerExercices(){
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        exerciceListAdapter.filter(s);
        exerciceListAdapter.notifyDataSetChanged();
        return false;
    }
    public void moveToDescription(ListExercice item){
        Intent intent = new Intent(getContext(), ActivityExerciceDescription.class);
        intent.putExtra("ListExercice", item);
        startActivity(intent);
    }

    public void onClickListeners(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"Seleccionado: " + adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
                exerciceListAdapter.filterByType(adapterView.getItemAtPosition(i).toString());
                svSearch.setQuery("",true);
                exerciceListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}