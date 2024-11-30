package com.example.chymv2.sources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EjerciciosDBtemporal {

    private String tablaEjercicios = "#16B0D6;Press de pecho plano ;Pecho;Mancuernas\n" +
            "#16B0D6;Press de pecho inclinado;Pecho;Mancuernas\n" +
            "#16B0D6;Press de pecho con declinado;Pecho;Mancuernas\n" +
            "#16B0D6;Press de pecho plano;Pecho;Barra\n" +
            "#16B0D6;Press de pecho inclinado;Pecho;Barra\n" +
            "#16B0D6;Press de pecho declinado;Pecho;Barra\n" +
            "#16B0D6;Press de pecho;Pecho;Maquina\n" +
            "#16B0D6;Aperturas de pecho;Pecho;Polea\n" +
            "#16B0D6;Aperturas de pecho de un brazo;Pecho;Polea\n" +
            "#16B0D6;Aperturas de pecho;Pecho;Maquina\n" +
            "#16B0D6;Aperturas de pecho;Pecho;Mancuernas\n" +
            "#16B0D6;Fondos;Pecho;Corporal\n" +
            "#16B0D6;Flexiones inclinadas;Pecho;Corporal\n" +
            "#16B0D6;Flexiones declinadas;Pecho;Corporal\n" +
            "#16B0D6;Pullover de pecho;Pecho;Mancuernas\n" +
            "#F0DB2E;Remo con mancuernas a una mano;Espalda;Mancuernas\n" +
            "#F0DB2E;Jalon hacia abajo tras nuca;Espalda;Polea\n" +
            "#F0DB2E;Remo renegado;Espalda;Mancuernas\n" +
            "#F0DB2E;Peso muerto;Espalda;Barra\n" +
            "#F0DB2E;Remo Pendlay;Espalda;Barra\n" +
            "#F0DB2E;Remo tripode;Espalda;Mancuernas\n" +
            "#F0DB2E;Jalon con brazos rectos;Espalda;Polea\n" +
            "#F0DB2E;Pullover;Espalda;Mancuernas\n" +
            "#F0DB2E;Pullover humano;Espalda;Mancuernas\n" +
            "#F0DB2E;Remo sentado;Espalda;Polea\n" +
            "#F0DB2E;Remo Meadows;Espalda;Barra\n" +
            "#F0DB2E;Remo alto a una mano;Espalda;Polea\n" +
            "#F0DB2E;Jalones para dorsales;Espalda;Polea\n" +
            "#F0DB2E;Jalones balanceando;Espalda;Polea\n" +
            "#F0DB2E;Remo muerto;Espalda;Mancuernas\n" +
            "#F0DB2E;Remos con barra;Espalda;Barra\n" +
            "#F0DB2E;Remo apoyado en el pecho;Espalda;Maquina\n" +
            "#F0DB2E; Dominadas;Espalda;Corporal\n" +
            "#A11414;Squats ;Piernas;Barra\n" +
            "#A11414;Empuje de cadera;Piernas;Barra\n" +
            "#A11414;Zancada en Reversa;Piernas;Barra\n" +
            "#A11414;Zancada en Reversa;Piernas;Mancuernas\n" +
            "#A11414;Peso Muerto Rumano a una pierna;Piernas;Mancuernas\n" +
            "#A11414;Elevacion de pantorrillas de pie;Piernas;Mancuernas\n" +
            "#A11414;Elevacion de pantorrillas sentado;Piernas;Mancuernas\n" +
            "#A11414;Curl de femoral sentado;Piernas;Maquina\n" +
            "#A11414;Curl de femoral tumbado;Piernas;Maquina\n" +
            "#A11414;Extension de cuadriceps;Piernas;Maquina\n" +
            "#A11414;Abduccion de cadera;Piernas;Maquina\n" +
            "#A11414;Sentadillas con salto;Piernas;Corporal\n" +
            "#A11414;Press de piernas;Piernas;Maquina\n"+
            "#A11414;Hip thrust;Piernas;Barra\n" +
            "#144EA1;Jalones con Cuerda;Triceps;Polea\n" +
            "#144EA1;Jalon hacia abajo de triceps;Triceps;Polea\n" +
            "#144EA1;Flexiones de diamante;Triceps;Corporal\n" +
            "#144EA1; Flexiones cobra;Triceps;Corporal\n" +
            "#144EA1;Jalon de triceps con agarre invertido;Triceps;Polea\n" +
            "#144EA1;Extensiones por encima de la cabeza;Triceps;Polea\n" +
            "#144EA1; Press JM;Triceps;Barra\n" +
            "#144EA1;Patada hacia Atras;Triceps;Mancuernas\n" +
            "#144EA1;Bombeo de Poder Inclinado;Triceps;Mancuernas\n" +
            "#144EA1;Fondos Erguido;Triceps;Corporal\n" +
            "#144EA1;Jalones hacia Abajo Balanceando;Triceps;Polea\n" +
            "#144EA1;Press de banca con agarre cerrado;Triceps;Barra\n" +
            "#144EA1;Extensiones de triceps acostado;Triceps;Mancuernas\n" +
            "#144EA1;Bombeo de Poder de pie;Triceps;Mancuernas\n" +
            "#144EA1;Extensiones cruzadas de cara recostado;Triceps;Mancuernas\n" +
            "#144EA1;Fondos sobre banca;Triceps;Corporal\n" +
            "#1BD62C;Curls concentrados;Biceps;Mancuernas\n" +
            "#1BD62C;Curls en reversa;Biceps;Mancuernas\n" +
            "#1BD62C;Flexion de biceps;Biceps;Corporal\n" +
            "#1BD62C;Curls invertidos;Biceps;Barra\n" +
            "#1BD62C;Curl Zottman;Biceps;Mancuernas\n" +
            "#1BD62C;Curls de predicador;Biceps;Barra\n" +
            "#1BD62C;Curls con cable;Biceps;Polea\n" +
            "#1BD62C;Spider curls;Biceps;Mancuernas\n" +
            "#1BD62C;Curls de arrastre;Biceps;Barra\n" +
            "#1BD62C;Curls flexionando;Biceps;Polea\n" +
            "#1BD62C;Curls de mesero;Biceps;Mancuernas\n" +
            "#1BD62C;Curls inclinado;Biceps;Mancuernas\n" +
            "#1BD62C;Dominada a la barbilla;Biceps;Corporal\n" +
            "#1BD62C;Curl con barra;Biceps;Barra\n" +
            "#1BD62C;Curls de pie alternando;Biceps;Mancuernas\n" +
            "#FC9100;Remo Erguido;Hombro;Barra\n" +
            "#FC9100;Press cubano;Hombro;Barra\n" +
            "#FC9100;Press por detras de la cabeza;Hombro;Barra\n" +
            "#FC9100;Elevaciones laterales;Hombro;Mancuernas\n" +
            "#FC9100;Press hacia afuera;Hombro;Mancuernas\n" +
            "#FC9100;Elevaciones laterales con trampa;Hombro;Mancuernas\n" +
            "#FC9100;Flys en reversa;Hombro;Mancuernas\n" +
            "#FC9100;Press Arnold;Hombro;Mancuernas\n" +
            "#FC9100;Remo de abduccion;Hombro;Mancuernas\n" +
            "#FC9100;Jalones a la cara;Hombro;Polea\n" +
            "#FC9100;Press de pala;Hombro;Mancuernas\n" +
            "#FC9100;Elevaciones laterales;Hombro;Mancuernas\n" +
            "#FC9100;Abraza caderas;Hombro;Mancuernas\n" +
            "#FC9100; Elevacion frontal;Hombro;Mancuernas\n" +
            "#FC9100;Elevaciones laterales;Hombro;Polea\n" +
            "#FC9100;Remo para deltoides posteriores;Hombro;Mancuernas\n" +
            "#FCED00;Flexiones de carpo;Antebrazo;Mancuernas\n" +
            "#FCED00;Extensiones de carpo;Antebrazo;Mancuernas\n" +
            "#FCED00;Curl de antebrazos con barra EZ;Antebrazo;Barra\n" +
            "#FCED00;Curl de antebrazos;Antebrazo;Mancuernas\n" +
            "#FCED00;Extensiones de antebrazos;Antebrazo;Barra\n" +
            "#FCED00;Flexiones de carpo;Antebrazo;Polea\n" +
            "#FCED00;Extensiones de carpo;Antebrazo;Polea\n" +
            "#FCED00;Pinzas con agarre;Antebrazo;Maquina\n" +
            "#FCED00;Peso muerto con agarre inverso;Antebrazo;Barra\n" +
            "#FCED00;Elevacion de dedos;Antebrazo;Barra\n" +
            "#FCED00;Farmer's carry;Antebrazo;Mancuernas\n" +
            "#FCED00;Colgado de barra;Antebrazo;Corporal\n" +
            "#FC00FC;Giro Ruso;Abdominales;Mancuernas\n" +
            "#FC00FC;Crunch de bicicleta;Abdominales;Corporal\n" +
            "#FC00FC;Flexion lateral con mancuerna;Abdominales;Mancuernas\n" +
            "#FC00FC;Plancha;Abdominales;Corporal\n" +
            "#FC00FC;Elevacion de piernas recostado;Abdominales;Corporal\n" +
            "#FC00FC;Elevacion de piernas suspendido;Abdominales;Corporal\n" +
            "#FC00FC;Rueda abdominal;Abdominales;Corporal\n" +
            "#FC00FC;Elevacion de rodillas suspendido;Abdominales;Corporal\n" +
            "#FC00FC;Sacacorcho suspendido;Abdominales;Corporal\n" +
            "#FC00FC;Acarreo lento de un solo lado;Abdominales;Mancuernas\n" +
            "#FC00FC;Giro de puente lateral;Abdominales;Corporal\n" +
            "#FC00FC;Crunch de levitacion;Abdominales;Corporal\n" +
            "#FC00FC;Deslizante;Abdominales;Corporal\n" +
            "#FC00FC;Pliegue de gimnasta;Abdominales;Corporal\n" +
            "#FC00FC;Pliegue deslizante;Abdominales;Corporal\n" +
            "#9402DE;Bicicleta;Cardio; \n" +
            "#9402DE;Correr;Cardio; \n" +
            "#9402DE;Saltar a la comba;Cardio; \n" +
            "#9402DE;Natacion;Cardio; \n" +
            "#9402DE;Remo indoor cardio;Cardio;Maquina\n" +
            "#9402DE;Caminar;Cardio; \n" +
            "#9402DE;Marcha;Cardio; \n" +
            "#9402DE;Jumping jacks;Cardio; \n" +
            "#9402DE;Pegar al saco;Cardio; \n" +
            "#9402DE;Zancada de maquina eliptica;Cardio;Maquina\n" +
            "#9402DE;Jumping box;Cardio; \n" +
            "#9402DE;Maquina de escaleras;Cardio;Maquina\n" +
            "#9402DE;HIIT;Cardio; \n" +
            "#9402DE;Bailar;Cardio; ";

    private String tablaRutinas = "#FF0000;Rutina Pecho;Pecho;1,6,7,12,15,119;0\n" +
            "#00FF00;Rutina Full Body;FullBody;1,20,40,60,80,100,120;0\n" +
            "#000000;Rutina P&W;Piernas;1,1,1,1,1,1;0\n" +
            "#11FFF1;Rutina Piernas;Piernas;50,38,75,111,29,115;0\n" +
            "#FE0FE0;Rutina Espalda;Espalda;33,15,7,95,100,43;0\n" +
            "#F0F095;Rutina Push & Pull;FullBody;1,3,46,77,112,115;0\n";


    public ArrayList<String> dataTable(){
        String unity = "";
        ArrayList<String> datos = new ArrayList<>();
        for(int i = 0; i<tablaEjercicios.length();i++){
            if((tablaEjercicios.charAt(i) == ';' || tablaEjercicios.charAt(i) == '\n')&& !unity.equals("")){
                datos.add(unity);
                unity = "";
            }
            else{
                unity += tablaEjercicios.charAt(i);
            }
        }
        return datos;
    }

    public ArrayList<String> routineTable(){
        String unity = "";
        ArrayList<String> datos = new ArrayList<>();
        for(int i = 0; i<tablaRutinas.length();i++){
            if((tablaRutinas.charAt(i) == ';' || tablaRutinas.charAt(i) == '\n') && !unity.equals("")){
                datos.add(unity);
                unity = "";
            }
            else{
                unity += tablaRutinas.charAt(i);
            }
        }
        return datos;
    }



}

