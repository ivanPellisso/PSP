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

/**
 *
 * @author dam2
 */
public class VicenteCalderon{

    private List<Dinosaurio> dinos;
    private CyclicBarrier barrier;

    public VicenteCalderon() {
        dinos= Collections.synchronizedList(new ArrayList()); 
        barrier=new CyclicBarrier(6, new Runnable() {

            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                    synchronized(dinos){
                        for(Dinosaurio dino: dinos){
                            dino.setLugarActual(Lugares.HABITAT);
                            dino.aumentaAlegria(3);
                        }
                        barrier.reset();
                    }
                } catch (InterruptedException ex) {
                    dinos.clear();
                    barrier.reset();
                }
            }
        });
    }
    
    public String dinosEnEstadio(){
        StringBuilder cadena=new StringBuilder();
        for(Dinosaurio dino: dinos){
            cadena.append(dino.toString()).append("\n");
        }
        return cadena.toString();
    }
    
    public void entrar(Dinosaurio d) {
        boolean entra=false;
        synchronized(dinos){
            if (dinos.size() < barrier.getParties()) {
                dinos.add(d);
                d.setLugarActual(Lugares.VICENTE_CALDERON);
                entra=true;
            }
        }
        if(entra){
            try {
                barrier.await();
            } catch (InterruptedException ex) {
                dinos.clear();
                barrier.reset();
            } catch (BrokenBarrierException ex) {
                
            }
        }
    }
    
    public void resetStadium(){
        barrier.reset();
        dinos.clear();
    }
    
    public List<Dinosaurio> getDinos() {
        return dinos;
    }

    public void setDinos(List<Dinosaurio> dinos) {
        this.dinos = dinos;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

}
