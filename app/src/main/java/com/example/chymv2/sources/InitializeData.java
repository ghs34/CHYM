package com.example.chymv2.sources;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.chymv2.model.ListExercice;
import com.example.chymv2.model.Rutina;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class InitializeData {
    private List<ListExercice> exercices;
    private List<Rutina> routines;
    private DatabaseHelper databaseHelper;
    private static InitializeData instance;
    private static DatabaseHelper dbInstance;
    private Context context;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    public static synchronized InitializeData getInstance(Context context) {
        if (instance == null) {
            instance = new InitializeData(context.getApplicationContext());
        }
        return instance;
    }
    public static synchronized DatabaseHelper getDbInstance(Context context){
        if(dbInstance == null){
            dbInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    public InitializeData(Context context){
        this.context = context;
        //Base de datos
        databaseHelper = getDbInstance(context);

        //Listas de los objetos
        exercices = new ArrayList<>();
        routines = new ArrayList<>();

        //Llenar las listas con la información de la base de datos
        displayExercicesData();
        displayRoutinesData();
    }

    public void eliminateRoutine(Rutina item){
        if(routines.contains(item)){
            routines.remove(item);
        }
    }

    private void displayExercicesData() {
        Cursor cursor = databaseHelper.getDataExercices();
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No Entry Exists", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String color = cursor.getString(1);
                String exercice = cursor.getString(2);
                String group = cursor.getString(3);
                String type = cursor.getString(4);
                String description = cursor.getString(5);
                ListExercice ex = new ListExercice(color, exercice, group, type,description);
                ex.setId(id);
                ex.setSeries(cursor.getString(6));
                ex.setRepeticiones(cursor.getString(7));
                ex.setKg(cursor.getString(8));
                exercices.add(ex);
            }
        }
    }

    private void displayRoutinesData() {
        Cursor cursor = databaseHelper.getDataRoutine();
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No Entry Exists", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String color = cursor.getString(1);
                String routine = cursor.getString(2);
                String routineType = cursor.getString(3);
                String listExercices = cursor.getString(4);
                String idList = cursor.getString(5);
                ArrayList<ListExercice> exercices = conversorStringToExercice(listExercices);
                Rutina rutina = new Rutina(color, routine,routineType, exercices,idList);
                rutina.setId(Integer.parseInt(id));
                routines.add(rutina);
                /* En el caso de querer añadir mas rutinas recomendadas -> eliminar el path entero de las
                rutinas recomendadas de la firebase, descomentar este codigo y volver a ejecutarlo (MODO ADMIN).*/
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("rutinas recomendadas");
                String key = reference.push().getKey();
                reference.child(key).setValue(rutina);
                System.out.println(rutina.getNombre());

            }
        }
    }
    public int meterEjercicioDB(ListExercice ex){
        String color = ex.getColor();
        String exercice = ex.getEjercicio();
        String group = ex.getGrupoMuscular();
        String type = ex.getTipoEjercicio();
        String description = ex.getDescripcion();
        String series = ex.getSeries();
        String reps = ex.getRepeticiones();
        String kg = ex.getKg();
        databaseHelper.insertExerciceData(color,exercice,group,type,description,series,reps,kg);
        Cursor c = databaseHelper.getDataExercices();
        c.moveToFirst();
        /*int id = c.getInt(0);
        while(c.moveToNext()){
            id = c.getInt(0);
        }
        //Cursor cursor= DB.rawQuery("SELECT last_insert_rowid() as LastID FROM Exercices",null);
        ListExercice ex = new ListExercice(color, exercice, group, type,description);
        ex.setId(id);*/
        exercices.add(ex);
        c.close();
        return ex.getId();

    }
    public void addRoutineData(Rutina rutina){
        routines.add(rutina);
    }public void addExerciceData(ListExercice exercice){
        exercices.add(exercice);
    }

    public ArrayList<ListExercice>conversorStringToExercice(String lista){
        String id = "";
        ArrayList<ListExercice> listExercices = new ArrayList<>();

        for(int i = 0; i<lista.length();i++){
            if((lista.charAt(i) == ',' || i == lista.length()-1) && !id.equals("")){

                int traductor = Integer.parseInt(id);
                listExercices.add(findExerciceById(traductor));
                id = "";
            }
            else{
                id += lista.charAt(i);
            }
        }
        return listExercices;

    }

    public ListExercice findExerciceById(int id){
        for(ListExercice i:exercices){
            if(i.getId() == id){
                return i;
            }
        }
        return null;
    }

    public List<ListExercice> getAllListExercice(){
        return exercices;
    }
    public List<Rutina> getAllRoutines(){
        return routines;
    }
}
