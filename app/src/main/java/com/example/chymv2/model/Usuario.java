package com.example.chymv2.model;

import java.util.ArrayList;

public class Usuario {
    String username;
    String userUID;
    String correo;
    String imagen;
    float peso;
    float altura;
    float IMC;
    ArrayList<Rutina> rutinas;
    ArrayList<String> materiales;
    boolean premium;

    public Usuario(String userUID, String username, String correo, float peso, float altura,
                   ArrayList<Rutina> rutinas, ArrayList<String> materiales, boolean premium,
                   String imagen) {
        this.username = username;
        this.correo = correo;
        this.peso = peso;
        this.IMC = calcularIMC(this.peso, this.altura);
        this.altura = altura;
        this.rutinas = rutinas;
        this.materiales = materiales;
        this.premium = premium;
        this.imagen = imagen;
        this.userUID = userUID;
        materiales = new ArrayList<String>();
        materiales.add("Peso Corporal");
    }

    public Usuario(String username, String correo) {
        this.username = username;
        this.correo = correo;
        materiales = new ArrayList<String>();
        materiales.add("Peso Corporal");
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public float getPeso() {
        return peso;
    }
    public void setPeso(float peso) {
        this.peso = peso;
    }
    public float getAltura() {
        return altura;
    }
    public void setAltura(float altura) {
        this.altura = altura;
    }
    public float getIMC() {
        return IMC;
    }
    public void setIMC(float IMC) {
        this.IMC = IMC;
    }
    public ArrayList<Rutina> getRutinas() {
        return rutinas;
    }
    public void setRutinas(ArrayList<Rutina> rutinas) {
        this.rutinas = rutinas;
    }
    public ArrayList<String> getMateriales() {
        return materiales;
    }
    public void setMateriales(ArrayList<String> materiales) {
        this.materiales = materiales;
    }
    public boolean isPremium() {
        return premium;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    public float calcularIMC(float peso, float altura){
        return (float) (peso / Math.pow(altura/100, 2));
    }
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
