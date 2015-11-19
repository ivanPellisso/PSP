/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carrerachapas;

import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Ninyo implements Runnable{
    /**
     * Run: 
     */
    private Condition myTurn;
    private Condition nextTurn;
    private int nombre;
    private int tirada;
    private boolean ganado;
    private Lugar lug;
    private Thread child;
    private Pista pista;
    
    public Ninyo(int n, Pista p){
        pista=p;
        nombre=n;
        tirada=0;
        child=new Thread(this);
        child.start();
        child.setName("Niño "+n);
    }
    
    //ESTA CLASE ES UNA MIERDA
    //public Afeitar afeitarBarba(){
    //if(ivan.tengoBarbita()){
    // return ivan.afeitar();
    //else {
    // return ivan.peinarBarba();
    //}
    
    public Condition getMyTurn() {
        return myTurn;
    }

    public void setMyTurn(Condition myTurn) {
        this.myTurn = myTurn;
    }

    public Condition getNextTurn() {
        return nextTurn;
    }

    public void setNextTurn(Condition nextTurn) {
        this.nextTurn = nextTurn;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public boolean isGanado() {
        return ganado;
    }

    public void setGanado(boolean ganado) {
        this.ganado = ganado;
    }
    
    public void setLugarActual(Lugar lugar) {
        this.lug=lugar;
    }
    
    public void tiraChapa(){
        int longitud=(int)(Math.random()*10)+1;
        do{
            tirada+=longitud;
            if(longitud==pista.getLargo()){
                ganado=true;
            }
        }while(!ganado);
    }
    
    private void anularPartida(){
        pista.cerrarPista();
        child.interrupt();
    }
    
    @Override
    public void run() {
        pista.entrarPista(this);
        System.out.println(this.toString());
        while(!ganado){
            try {
                pista.getLock().lock();
                
                myTurn.await();
                tiraChapa();
                nextTurn.signalAll();
            } catch (InterruptedException ex) {
                child.interrupt();
            }finally{
                pista.getLock().unlock();
            }
        }
    }

    @Override
    public String toString() {
        return "Niño " + nombre + ": tirada=" + tirada + ", lug=" + lug + '}';
    }
    
}
