/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinopark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IvPR
 */
public class SalasFolladero {
    private List<Dinosaurio> dinos;
    private CyclicBarrier barrier;
    private Habitat hab;
    private int num;

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }
    
    public SalasFolladero(Habitat habi, int n){
        this.hab=habi;
        num=n;
        dinos=Collections.synchronizedList(new ArrayList());
        barrier=new CyclicBarrier(2, new Runnable() {
            /**
             * Dino esperando mira si hay sitio en la sala, si hay sitio mira si dentro es de su sexo
             * Si es de su sexo, sigue su vida normal
             */
            @Override
            public void run() {
                try {
                     /**
                      *     TimeUnit milliseconds 5000
                      *     for recorre dinos. Dentro cambia lugar actual a habitat y aumenta alegria
                      *     DespuÃ©s del bucle, dinos.clear();
                      *     barrier.reset();
                      *     En catch, barrier.reset();
                      */
                    int probHuevo=(int)(Math.random()*100)+30;
                    TimeUnit.MILLISECONDS.sleep(10000);
                    synchronized(dinos){
                        for(Dinosaurio dino: dinos){
                            dino.setLugarActual(Lugares.HABITAT);
                            dino.aumentaAlegria(5);
                        }
                        if(probHuevo>60){
                            hab.addDino(new Dinosaurio(dinos.get(0).getNombre(),dinos.get(1).getNombre(), hab));
                        }
                        dinos.clear();
                        barrier.reset();
                    }
                } catch (InterruptedException ex) {
                    barrier.reset();
                    Logger.getLogger(VicenteCalderon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public boolean entrar(Dinosaurio d){
        boolean entrado=false;
        
        synchronized(dinos){
            if (dinos.size()== 1) {
                if(!(dinos.get(0).getSexo().equalsIgnoreCase(d.getSexo()))){
                    dinos.add(d);
                    d.setLugarActual(Lugares.SALA_FOLLADERO);
                    entrado=true;
                }else{
                    d.setLugarActual(Lugares.HABITAT);
                }
            }else{
                if(dinos.size()==0){
                    dinos.add(d);
                    d.setLugarActual(Lugares.SALA_FOLLADERO);
                    entrado=true;
                }else{
                    d.setLugarActual(Lugares.HABITAT);
                }
            }
        }
        if(entrado){
            try {
                barrier.await();//aquÃ­ se esperan
            } catch (InterruptedException | BrokenBarrierException ex) {
                barrier.reset();
            }
        }
        return entrado;
    }

    @Override
    public String toString() {
        return "Sala " + num +", dinos:\n"+dinosEnSala();
    }

    public String dinosEnSala(){
        StringBuilder cadena=new StringBuilder();
        for(Dinosaurio dino: dinos){
            cadena.append(dino.toString()).append("\n");
        }
        return cadena.toString();
    }
    
    public void resetSala(){
        barrier.reset();
        dinos.clear();
    }
}
