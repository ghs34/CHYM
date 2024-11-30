package com.example.chymv2.sources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private ContentValues contentValues = new ContentValues();
    //EXERCICE DATA
    public static final String EXERCICES_ID = "COLUMN_ID";
    public static final String EXERCICES_COLOR = "COLUMN_COLOR";
    public static final String EXERCICES_NAME = "COLUMN_EXERCICE";
    public static final String EXERCICES_GROUP = "COLUMN_GROUP";
    public static final String EXERCICES_TYPE = "COLUMN_TYPE";
    public static final String EXERCICES_DESCRIPTION = "COLUMN_DESCRIPTION";
    public static final String EXERCICES_SERIES = "COLUMN_SERIES";
    public static final String EXERCICES_REPS = "COLUMN_REPS";
    public static final String EXERCICES_KG = "COLUMN_KG";

    //ROUTINE DATA
    public static final String ROUTINE_ID = "COLUMN_ID";
    public static final String ROUTINE_COLOR = "COLUMN_COLOR";
    public static final String ROUTINE_NAME = "COLUMN_NAME";
    public static final String ROUTINE_TYPE = "COLUMN_TYPE";
    public static final String ROUTINE_EXERCICES = "COLUMN_EXERCICES";

    public static final String ROUTINE_LIST_ID = "COLUMN_ID_LIST";


    public DatabaseHelper(Context context) {
        super(context, "Database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("create table Exercices(" + EXERCICES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXERCICES_COLOR + " TEXT, " + EXERCICES_NAME + " TEXT, " + EXERCICES_GROUP + " TEXT, " + EXERCICES_TYPE + " TEXT, " + EXERCICES_DESCRIPTION + " TEXT, " + EXERCICES_SERIES + " TEXT, "+ EXERCICES_REPS + " TEXT, "+ EXERCICES_KG + " TEXT )");
        DB.execSQL("create table Routines(" + ROUTINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROUTINE_COLOR + " TEXT, " + ROUTINE_NAME + " TEXT, "+ ROUTINE_TYPE + " TEXT, " + ROUTINE_EXERCICES + " TEXT, " + ROUTINE_LIST_ID + " TEXT )");
        initDatabaseExercices(contentValues, DB);
        contentValues.clear();
        initDatabaseRoutine(contentValues, DB);
        contentValues.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists Exercices");
        DB.execSQL("drop table if exists Routines");

    }

    public Boolean insertExerciceData(String color, String exercice, String group, String type,String description,String series,String reps,String kg){
        SQLiteDatabase DB = this.getWritableDatabase();
        dataInsertionExercices(color,exercice,group,type,description,series,reps,kg,contentValues);
        long result = DB.insert("Exercices", null,contentValues);
        contentValues.clear();
        return (result == -1);
    }


    public Boolean insertRoutineData(String color, String routine,String routineType,String listExercices, String idList){
        SQLiteDatabase DB = this.getWritableDatabase();
        dataInsertionRoutine(color,routine,routineType,listExercices,idList,contentValues);
        long result = DB.insert("Routines", null,contentValues);
        contentValues.clear();
        return (result == -1);
    }
    public void eliminateRoutine(String id){
        SQLiteDatabase DB = this.getWritableDatabase();
        String table ="Routines";
        String whereClause = "COLUMN_ID=?";
        String[] whereArgs = {id};
        DB.delete(table,whereClause,whereArgs);
    }
    public Cursor getDataExercices(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Exercices",null);
        return cursor;
    }
    public Cursor getDataRoutine(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Routines",null);
        return cursor;
    }

    private void dataInsertionExercices(String color, String exercice, String group, String type,String description,String series,String reps,String kg,ContentValues contentValues){

        contentValues.put(EXERCICES_COLOR,color);
        contentValues.put(EXERCICES_NAME,exercice);
        contentValues.put(EXERCICES_GROUP,group);
        contentValues.put(EXERCICES_TYPE,type);
        contentValues.put(EXERCICES_DESCRIPTION,description);
        contentValues.put(EXERCICES_SERIES,series);
        contentValues.put(EXERCICES_REPS,reps);
        contentValues.put(EXERCICES_KG,kg);

    }

    private void dataInsertionRoutine(String color, String routine,String routineType,String listExercices, String idList, ContentValues contentValues){

        contentValues.put(ROUTINE_COLOR,color);
        contentValues.put(ROUTINE_NAME,routine);
        contentValues.put(ROUTINE_TYPE,routineType);
        contentValues.put(ROUTINE_EXERCICES,listExercices);
        contentValues.put(ROUTINE_LIST_ID,idList);
    }

    private void initDatabaseExercices(ContentValues contentValues,SQLiteDatabase DB){
        EjerciciosDBtemporal initialData = new EjerciciosDBtemporal();
        ArrayList<String> lista = initialData.dataTable();
        for(int i = 0; i<lista.size()-3;i+=4){
            dataInsertionExercices(lista.get(i),lista.get(i+1),lista.get(i+2),lista.get(i+3),"Descripcion",null,null,null,contentValues);
            DB.insert("Exercices",null,contentValues);

        }
    }

    private void initDatabaseRoutine(ContentValues contentValues,SQLiteDatabase DB){
        EjerciciosDBtemporal initialData = new EjerciciosDBtemporal();
        ArrayList<String> lista = initialData.routineTable();
        for(int i = 0; i<lista.size()-4;i+=5){
            dataInsertionRoutine(lista.get(i),lista.get(i+1),lista.get(i+2),lista.get(i+3),lista.get(i+4),contentValues);
            DB.insert("Routines",null,contentValues);

        }
    }
}

