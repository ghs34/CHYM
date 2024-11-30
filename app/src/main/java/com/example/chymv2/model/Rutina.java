package com.example.chymv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Rutina implements Serializable {
    String nombre;
    ArrayList<ListExercice> ejercicios;
    Set<String> materiales;
    Set<String> musculos;
    String descripcion;
    boolean subida;
    String color;
    String idList;
    String routineType;
    int id;

    public Rutina(String color,String nombre,String routineType,ArrayList<ListExercice> ejercicios, String idList){
        this.nombre = nombre;
        this.color = color;
        this.ejercicios = ejercicios;
        this.idList = idList;
        this.routineType = routineType;
    }
    public Rutina(String nombre, ArrayList<ListExercice> ejercicios, String descripcion, boolean subida) {
        this.nombre = nombre;
        this.ejercicios = ejercicios;
        this.materiales = new HashSet<String>();
        this.musculos = new HashSet<String>();
        this.descripcion = descripcion;
        this.subida = subida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoutineType() {
        return routineType;
    }

    public void setRoutineType(String routineType) {
        this.routineType = routineType;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<ListExercice> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<ListExercice> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public Set<String> getMateriales() {
        return materiales;
    }

    public void setMateriales(Set<String> materiales) {
        this.materiales = materiales;
    }

    public Set<String> getMusculos() {
        return musculos;
    }

    public void setMusculos(Set<String> musculos) {
        this.musculos = musculos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isSubida() {
        return subida;
    }

    public void setSubida(boolean subida) {
        this.subida = subida;
    }

    public void añadir_ejercicio(ListExercice ejercicio){
        this.ejercicios.add(ejercicio);
    }

    public void eliminar_ejercicio(ListExercice ejercicio){
        this.ejercicios.remove(ejercicio);
    }
    /*
    public void material_necesario(){
        this.materiales.clear();
        for(Ejercicio ejercicio : this.ejercicios){
            for (String material: ejercicio.getMateriales()) {
                this.materiales.add(material);
            }
        }
    }

    public void zona_muscular(){
        this.musculos.clear();
        for(Ejercicio ejercicio : this.ejercicios){
            for (String musculo: ejercicio.getMusculos()) {
                this.materiales.add(musculo);
            }
        }
    }
    */
}
