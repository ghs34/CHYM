package com.example.chymv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chymv2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRoutineMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRoutineMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button misRutinas_btn, rutinasComunidad_btn, crearRutinas_btn, rutinasRecomendadas_btn;




    public FragmentRoutineMenu() {
        // Required empty public constructor


    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment routineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRoutineMenu newInstance(String param1, String param2) {
        FragmentRoutineMenu fragment = new FragmentRoutineMenu();
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
        View v = inflater.inflate(R.layout.fragment_routine, container, false);
        crearRutinas_btn = v.findViewById(R.id.crearRutinasBtn);
        misRutinas_btn = v.findViewById(R.id.misRutinasBtn);
        rutinasComunidad_btn = v.findViewById(R.id.rutinasComunidadBtn);
        rutinasRecomendadas_btn = v.findViewById(R.id.rutinasRecomendadasBtn);
        View.OnClickListener miClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.crearRutinasBtn:
                        startActivity(new Intent(getContext(), ActivityCrearRutinas.class));
                        break;
                    case R.id.misRutinasBtn:
                        startActivity(new Intent(getContext(), ActivityMisRutinas.class));
                        break;
                    case R.id.rutinasComunidadBtn:
                        startActivity(new Intent(getContext(), ActivityRutinasComunidad.class));
                        break;
                    case R.id.rutinasRecomendadasBtn:
                        startActivity(new Intent(getContext(), ActivityRecomendedRoutines.class));
                        break;
                }
            }
        };

        crearRutinas_btn.setOnClickListener(miClickListener);
        misRutinas_btn.setOnClickListener(miClickListener);
        rutinasComunidad_btn.setOnClickListener(miClickListener);
        rutinasRecomendadas_btn.setOnClickListener(miClickListener);

        return v;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }
}