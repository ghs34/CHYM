package com.example.chymv2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;

import com.example.chymv2.R;
import com.example.chymv2.adapters.MaterialListAdapter;
import com.example.chymv2.model.CardMaterial;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityMaterial extends AppCompatActivity {

    private List<CardMaterial> elements;
    private RecyclerView materialRecyclerView;
    private MaterialListAdapter materialAdapter;

    private CardMaterial cardMaterial;
    private FirebaseAuth firebaseAuth;

    private CheckBox materialCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);


        init();
    }

    public void init() {
        elements = new ArrayList<>();
        elements.add(new CardMaterial("Mancuerna", false));
        elements.add(new CardMaterial("Barra de Pesas", false));
        elements.add(new CardMaterial("Barra de Dominadas", false));
        elements.add(new CardMaterial("Maquinas", false));
        elements.add(new CardMaterial("Kettlebell", false));
        elements.add(new CardMaterial("Peso Corporal", false));
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("materiales");
        materialAdapter = new MaterialListAdapter(elements, this, new MaterialListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardMaterial item) {
                updateMaterial(item);
            }
        });
        materialAdapter.notifyDataSetChanged();
        materialRecyclerView = findViewById(R.id.rvMaterial);
        materialRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        materialRecyclerView.setHasFixedSize(true);
        materialRecyclerView.setAdapter(materialAdapter);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer numElement = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String elemento = snapshot.getValue(String.class);
                    for (CardMaterial card : elements) {
                        if (card.getName().equals(elemento)) {
                            card.setStatus(true);
                            elements.set(numElement, card);
                            materialAdapter.notifyItemChanged(numElement);
                        }
                        numElement++;
                    }
                    numElement = 0;
                }
                for (CardMaterial card : elements) {
                    System.out.println(card.getName());
                    if (card.isStatus()) System.out.println("true");
                    else System.out.println("false");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja cualquier error de lectura desde Firebase
                Log.e("Firebase", "Error al leer desde Firebase: " + databaseError.getMessage());
            }
        });

    }

    public void updateMaterial(CardMaterial item) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        cardMaterial = item;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("materiales");

        if (item.isStatus()) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean elementoExistente = false;
                    String keyExistnte = null;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String elemento = snapshot.getValue(String.class);
                        if (elemento.equals(item.getName())) {
                            elementoExistente = true;
                            keyExistnte = snapshot.getKey();
                            break;
                        }
                    }
                    if (!elementoExistente) {
                        Log.d("Firebase", "Elemento no exitstente de la lista");
                    } else {
                        reference.child(keyExistnte).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Firebase", "Elemento eliminado exitosamente de la lista");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Firebase", "Error al eliminar elemento de la lista: " + e.getMessage());
                                    }
                                });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja cualquier error de lectura desde Firebase
                    Log.e("Firebase", "Error al leer desde Firebase: " + databaseError.getMessage());
                }
            });
            item.setStatus(false);
            System.out.println("desmarcada");
        } else {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean elementoExistente = false;
                    //System.out.println(item.getName());
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String elemento = snapshot.getValue(String.class);
                        if (elemento.equals(item.getName())) {
                            elementoExistente = true;
                            break;
                        }
                    }
                    // Añade el nuevo elemento solo si no existe en la lista
                    if (!elementoExistente) {
                        String key = reference.push().getKey();
                        reference.child(key).setValue(item.getName())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // El elemento se añadió exitosamente a la lista
                                        Log.d("Firebase", "Elemento añadido exitosamente a la lista");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Ocurrió un error al añadir el elemento a la lista
                                        Log.e("Firebase", "Error al añadir elemento a la lista: " + e.getMessage());
                                    }
                                });
                    } else {
                        // El elemento ya existe en la lista, puedes realizar alguna acción si es necesario
                        Log.d("Firebase", "El elemento ya existe en la lista");
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja cualquier error de lectura desde Firebase
                    Log.e("Firebase", "Error al leer desde Firebase: " + databaseError.getMessage());
                }
            });
            item.setStatus(true);
            System.out.println("marcada");
        }
    }
}