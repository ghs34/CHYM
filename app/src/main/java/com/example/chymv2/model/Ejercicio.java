package com.example.chymv2.model;

import java.util.ArrayList;

public class Ejercicio {
    String nombre;
    float peso;
    String desc;
    int series;
    int repes;

    ArrayList<String> materiales;
    ArrayList<String> musculos;

    public Ejercicio(String nombre, String desc, ArrayList<String> materiales, ArrayList<String> musculos) {
        this.nombre = nombre;
        this.desc = desc;
        this.materiales = materiales;
        this.musculos = musculos;
    }

    public Ejercicio(String nombre, float peso, String desc, int series, int repes, ArrayList<String> materiales,
                     ArrayList<String> musculos) {

        this.nombre = nombre;
        this.peso = peso;
        this.desc = desc;
        this.series = series;
        this.repes = repes;
        this.materiales = materiales;
        this.musculos = musculos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepes() {
        return repes;
    }

    public void setRepes(int repes) {
        this.repes = repes;
    }

    public ArrayList<String> getMateriales() {
        return materiales;
    }

    public void setMateriales(ArrayList<String> materiales) {
        this.materiales = materiales;
    }

    public ArrayList<String> getMusculos() {
        return musculos;
    }

    public void setMusculos(ArrayList<String> musculos) {
        this.musculos = musculos;
    }
}


