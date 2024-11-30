package com.example.chymv2.model;

import java.io.Serializable;

public class ListExercice implements Serializable {
    private String color;
    private String ejercicio;
    private String grupoMuscular;
    private String tipoEjercicio;
    private int id;
    private String descripcion;
    private String series;
    private String repeticiones;
    private String kg;
    String selected;
    public ListExercice(String color, String ejercicio, String grupoMuscular, String tipoEjercicio,String descripcion) {
        this.color = color;
        this.ejercicio = ejercicio;
        this.grupoMuscular = grupoMuscular;
        this.tipoEjercicio = tipoEjercicio;
        this.id = id;
        this.descripcion = descripcion;
        this.selected = "#FFFFFF";
    }


    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }



    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }
}
