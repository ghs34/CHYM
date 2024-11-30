package com.example.chymv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.chymv2.R;
import com.example.chymv2.sources.InitializeData;
import com.google.android.material.navigation.NavigationBarView;
public class ActivityMain extends AppCompatActivity {
    private FragmentExercice fragmentExercice = new FragmentExercice();
    private FragmentProfile fragmentProfile = new FragmentProfile();
    private FragmentRoutineMenu fragmentRoutineMenu = new FragmentRoutineMenu();
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bundle = getIntent().getExtras();

        NavigationBarView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        Integer fragment = bundle.getInt("parametro");

        if (fragment == 1){
            loadFragment(fragmentRoutineMenu);
            navigation.setSelectedItemId(R.id.routine_fragment);
        }
        else if (fragment == 2) {
            loadFragment(fragmentExercice);
            navigation.setSelectedItemId(R.id.exercices_fragment);
        }
        else{
            loadFragment(fragmentProfile);
            navigation.setSelectedItemId(R.id.profile_fragment);
        }
        System.out.println("1111111111");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        System.out.println("Dulce");
        super.onNewIntent(intent);
        //setIntent(intent);
        System.out.println("JAMON");

        NavigationBarView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        bundle = intent.getExtras();

        Integer fragment = bundle.getInt("parametro");

        System.out.println(fragment);

        if (fragment == 1){
            loadFragment(fragmentRoutineMenu);
            navigation.setSelectedItemId(R.id.routine_fragment);
        }
        else if (fragment == 2) {
            loadFragment(fragmentExercice);
            navigation.setSelectedItemId(R.id.exercices_fragment);
        }
        else{
            loadFragment(fragmentProfile);
            navigation.setSelectedItemId(R.id.profile_fragment);
        }
    }
    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.exercices_fragment:
                    loadFragment(fragmentExercice);
                    return true;
                case R.id.profile_fragment:
                    loadFragment(fragmentProfile);
                    return true;
                case R.id.routine_fragment:
                    loadFragment(fragmentRoutineMenu);
                    return true;
            }
            return false;
        }
    };
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout_MainActivity,fragment);
        transaction.commit();
    }

}