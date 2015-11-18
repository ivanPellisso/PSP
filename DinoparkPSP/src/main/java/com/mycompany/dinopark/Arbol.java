/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinopark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author dam2
 */
public class Arbol {

    private List<Dinosaurio> dinos;
    private Lock bloquea;
    private Condition entraArbol;
    private Habitat hab;
    private int num;
    /**
     * 
     *  
     */
    
    public Arbol(Habitat habi, int n) {
        num=n;
        hab = habi;
        dinos = new ArrayList();
        bloquea = new ReentrantLock(true);//si pasas true al ReentrantLock, hace que el que más tiempo lleve esperando, coja el lock
        entraArbol = bloquea.newCondition();
    }

    public Lock getBlock() {
        return bloquea;
    }

    public void setBlock(Lock block) {
        this.bloquea = block;
    }

    public Condition getLevanta() {
        return entraArbol;
    }

    public void setLevanta(Condition levanta) {
        this.entraArbol = levanta;
    }

    public Habitat getHab() {
        return hab;
    }

    public void setHab(Habitat hab) {
        this.hab = hab;
    }

    public boolean entrar(Dinosaurio saurio) {
        boolean entrado = false, luchando = false;
        try {
            if (bloquea.tryLock(1000, TimeUnit.MILLISECONDS)) {
                if (dinos.size() == 0) {
                    saurio.setLugarActual(Lugares.BOSQUE);
                    dinos.add(saurio);
                    entrado = true;
                    entraArbol.await(1500, TimeUnit.MILLISECONDS);
                    if (!luchando) {
                        for (Dinosaurio dino : dinos) {
                            if (dino.getTipo().equalsIgnoreCase("herbívoro")) {
                                dino.restaHambre(10);
                                dino.aumentaAlegria();
                            } else {
                                entraArbol.signalAll();
                            }
                        }
                    }
                } else {
                    if (dinos.size() ==1) {
                        entrado = true;
                        dinos.add(saurio);
                        luchando = true;
                        TimeUnit.SECONDS.sleep(3);
                        if (!dinos.get(0).getTipo().equalsIgnoreCase(saurio.getTipo())) {
                            dinos.get(0).lucha(saurio);
                            luchando=true;
                            saurio.setLugarActual(Lugares.HABITAT);
                            entraArbol.signalAll();
                            dinos.clear();
                        }
                    }
                }
                bloquea.unlock();
            }
        } catch (InterruptedException ex) {
            
        }
        return entrado;
    }
    
    public String dinosEnArbol(){
        StringBuilder cadena=new StringBuilder();
        for(Dinosaurio dino: dinos){
            cadena.append(dino.toString()).append("\n");
        }
        return cadena.toString();
    }
    
    @Override
    public String toString(){
       return "Arbol "+num+", dinos\n"+dinosEnArbol();
    }

}
