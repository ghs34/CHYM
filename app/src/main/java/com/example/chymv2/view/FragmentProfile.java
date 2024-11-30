package com.example.chymv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chymv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView ProfileImageView;
    private TextView pesoNumPerfilTxt, alturaNumPerfilTxt, imcNumPerfilTxt, usernameProfileTextView,nameProfileTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference BASE_DE_DATOS;
    private LinearLayout history;
    private LinearLayout premium;
    private LinearLayout material;
    public FragmentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
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

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        BASE_DE_DATOS = FirebaseDatabase.getInstance().getReference("users");
        BASE_DE_DATOS.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String username = ""+snapshot.child("username").getValue();
                    String altura = ""+snapshot.child("altura").getValue();
                    String email = ""+snapshot.child("correo").getValue();
                    String pes = ""+snapshot.child("peso").getValue();
                    String imc = ""+snapshot.child("imc").getValue();
                    String imagen = ""+snapshot.child("imagen").getValue();

                    pesoNumPerfilTxt.setText(pes);
                    alturaNumPerfilTxt.setText(altura);
                    imcNumPerfilTxt.setText(calcularIMC(Float.parseFloat(pes),Float.parseFloat(altura)));
                    usernameProfileTextView.setText(email);
                    nameProfileTextView.setText(username);

                    try {
                        Picasso.get().load(imagen).placeholder(R.drawable.img_perfil).into(ProfileImageView);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.img_perfil).into(ProfileImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        pesoNumPerfilTxt = (TextView) view.findViewById(R.id.pesoNumPerfilTxt);
        alturaNumPerfilTxt = (TextView) view.findViewById(R.id.alturaNumPerfilTxt);
        imcNumPerfilTxt = (TextView) view.findViewById(R.id.imcNumPerfilTxt);
        usernameProfileTextView = (TextView) view.findViewById(R.id.mailProfileTxt);
        nameProfileTextView = (TextView) view.findViewById(R.id.nameProfileTextView);
        ProfileImageView = (ImageView) view.findViewById(R.id.profileImageView);
        history = (LinearLayout) view.findViewById(R.id.historyLinearLayout);
        premium = (LinearLayout) view.findViewById(R.id.premiumLinearLayout);
        material = (LinearLayout) view.findViewById(R.id.materialLinearLayout);
        //ClickListener del history
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityHistorial.class);
                startActivity(intent);
            }
        });
        /*
        premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivitySignIn.class);
                startActivity(intent);
            }
        });
        */
        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityMaterial.class);
                startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    public String calcularIMC(float peso, float altura){
        double imc = (Float) peso / Math.pow(altura/100, 2);
        return  quitarDecimales(imc+"",2);
    }
    private String quitarDecimales(String num, int n_decimales) {
        int indice = num.indexOf(".");
        return indice + n_decimales < num.length() ? num.substring(0, indice + n_decimales+1)
                :num.substring(0, num.length());
    }

}