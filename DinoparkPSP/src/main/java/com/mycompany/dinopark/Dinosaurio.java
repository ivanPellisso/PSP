/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinopark;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Dinosaurio implements Runnable{
    private int vida;
    private int edad;
    private int hambre;
    private String nombre;
    private int alegria;
    private String tipo;
    private int ata;
    private int def;
    private int personalidad;
    private char sexo;
    private boolean para;
    private Habitat hab;
    private Lugares lug;
    
    public Dinosaurio(String nombre, Habitat hab){
        this.hab=hab;
        vida=(int)(Math.random()*25)+20;
        hambre=0;
        alegria=0;
        edad=0;
        char [] sexos={'M','H'};
        sexo=sexos[(int)(Math.random()*2)];
        this.nombre=nombre;
    }
    
    public Dinosaurio(String n1, String n2, Habitat hab){
        this.hab=hab;
        vida=(int)(Math.random()*25)+20;
        hambre=0;
        alegria=0;
        char [] sexos={'M','H'};
        sexo=sexos[(int)(Math.random()*2)];
        this.nombre=n1.concat(" - ").concat(n2);
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getHambre() {
        return hambre;
    }

    public void setHambre(int hambre) {
        this.hambre = hambre;
    }

    public int getAlegria() {
        return alegria;
    }

    public void setAlegria(int alegria) {
        this.alegria = alegria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAta() {
        return ata;
    }

    public void setAta(int ata) {
        this.ata = ata;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getPersonalidad() {
        return personalidad;
    }

    public void setPersonalidad(int personalidad) {
        this.personalidad = personalidad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public boolean isPara() {
        return para;
    }

    public void setPara(boolean para) {
        this.para = para;
    }

    public Habitat getHab() {
        return hab;
    }

    public void setHab(Habitat hab) {
        this.hab = hab;
    }

    public void restaVida(){
        vida--;
    }
    
    public void restaVida(int n){
        vida-=n;
    }
    
    public void restaHambre(){
        hambre-=2;
    }
    
    public void aumentaHambre(){
        hambre++;
        if(hambre>30){
            hambre=30;
        }
        if(hambre>20*0.75){
            vida--;
        }
    }

    public void restaAlegria(){
        alegria--;
    }
    
    public void aumentaAlegria(){
        alegria++;
        if(alegria==20){
            alegria=0;
            vida++;
        }
    }
    
    public void aumentaAlegria(int n){
        alegria+=n;
        if(alegria==30){
            alegria=0;
            vida++;
        }
    }
    
    public void muereDino(){
        vida=0;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public void aumentaEdad(){
        edad++;
    }

    @Override
    public String toString() {
        return "Dinosaurio "+nombre+"{" + "vida= " + vida + ", hambre= "+ hambre+", alegría= "+alegria+", lugar= "+lug+", sexo= "+sexo+", edad= "+edad;
    }
    
    public Lugares irLugar() {
        Lugares lugar = null;
        
        if (alegria>hambre){
            lugar=Lugares.VICENTE_CALDERON;
        }else{
            if(alegria<hambre&&hambre>15){
                lugar=Lugares.RESTAURANTE;
            }else{
                if(edad>8){
                    lugar=Lugares.SALA_FOLLADERO;
                }
            }
        }
        
//        if (hambre > 15) {
//            lugar = Lugares.RESTAURANTE;
//        }
        if(lugar==null){
            lugar=Lugares.BOSQUE;
        }
        return lugar;//lugar;
    }
    
    @Override
    public void run() {
        while (vida > 0) {
            alegria++;
            try {
                TimeUnit.SECONDS.sleep(2);
                Lugares lugar = irLugar();
                switch (lugar) {
                    case VICENTE_CALDERON:
                        hab.entrarEstadio(this);
                        break;
                    case RESTAURANTE:
                        hab.entrarRestaurante(this);
                        break;
                    case FOLLADERO:
                        hab.entrarFolladero(this);
                        break;
                    case BOSQUE:
                        //hab.entrarBosque(this);
                        break;
                }
            } catch (InterruptedException ex) {
                vida=0;
            }
        }
    }

    public void setLugarActual(Lugares lugar) {
        this.lug=lugar;
    }
}
