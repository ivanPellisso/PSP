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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Arbol {
    private List<Dinosaurio> dinos;
    private Lock bloquea;
    private Condition levanta;
    private Habitat hab;

    public Arbol(Habitat habi){
        hab=habi;
        dinos=new ArrayList();
        bloquea=new ReentrantLock();
        levanta=bloquea.newCondition();
    }
    
    public Lock getBlock() {
        return bloquea;
    }

    public void setBlock(Lock block) {
        this.bloquea = block;
    }

    public Condition getLevanta() {
        return levanta;
    }

    public void setLevanta(Condition levanta) {
        this.levanta = levanta;
    }

    public Habitat getHab() {
        return hab;
    }

    public void setHab(Habitat hab) {
        this.hab = hab;
    }
    
    public boolean entrar(){
        boolean entrado=false;
        try {
            if(bloquea.tryLock(1000, TimeUnit.MILLISECONDS)){
                if(dinos.size()>0){
                    TimeUnit.MILLISECONDS.sleep(2000);
                    levanta.await();
                    
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entrado;
    }
    
    /**
     * Árboles equivalente a salas en el picadero. Hacer con Lock y Condition.
     * En función entrar():
     *  if( ___.trylock()){
     *      if(!hayAlguien){
     *          se mete en el árbol n segundos(Condition.await)
     *          if (pasado el tiempo){
     *              if(luchando){
     *                  if (herbívoro){
     *                      baja hambre y sube alegría
     *                  }else{
     *                      me han levantado con notify
     *                  }
     *              }
     *          }
     *      }else{
     *          if(hayAlguien){
     *              (segun el tipo)
     *                  LUCHAR ---> TimeUnit.wait
     *                  clear dinos
     *                  signalAll 
     *          }
     *      }
     *      UNLOCK
     */
    
    /**
     * Luchar:  - Carnívoro come a herbívoro o Dar golpe de tanto y le quite vida de tanto
     *          - Herbívoro más defensa y carnívoro más ataque
     */
}
