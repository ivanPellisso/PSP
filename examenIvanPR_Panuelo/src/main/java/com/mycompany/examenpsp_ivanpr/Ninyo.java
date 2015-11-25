/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.examenpsp_ivanpr;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;

/**
 *
 * @author dam2
 */
public class Ninyo implements Runnable{
    /**
     * Run: 
     */
    private int dorsal;
    private int nombre;
    private boolean ganado;
    private Thread child;
    private Condition meToca;
    private int velocidad;
    private Lugares lug;
    private Carrera race;
    private boolean primero;
    private int equipo;
    
    public Ninyo(int num,Carrera c){
        ganado=false;
        nombre=num;
        primero=false;
        velocidad=(int)(Math.random()*20)+20;
        race=c;
        child=new Thread(this);
        child.start();
        child.setName(num+"");
        
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
    
    public boolean isPrimero() {
        return primero;
    }
    public int getEquipo() {    
        return equipo;
    }
    
    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    public void setPrimero(boolean primero) {    
        this.primero = primero;
    }

    public Condition getMeToca() {
        return meToca;
    }

    public void setMeToca(Condition meToca) {
        this.meToca = meToca;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
    

    public boolean isGanado() {
        return ganado;
    }

    public void setGanado(boolean ganado) {
        this.ganado = ganado;
    }
        
    public void cancelarPartida(){
        ganado=true;
        child.interrupt();
    }
    
    public void vuelta(){
        System.out.println("VOLVIENDO JUGADOR "+ dorsal+" DEL EQUIPO "+equipo);
        int v=velocidad;
        try{
            int recorrido=0;
            while(recorrido<100){
            TimeUnit.MILLISECONDS.sleep(500);
            recorrido+=v;
            }
            synchronized(race.getJugadores()){
                race.addChild(this);
            }
            if(race.getJugadores().size()==2){
                race.getLock().lock();
                race.getReferee().signalAll();
                race.getLock().unlock();
            }
        }catch(InterruptedException e){
            
        }
        
    }
    
    public void corre(){
        System.out.println("CORRIENDO JUGADOR "+dorsal+" DEL EQUIPO "+equipo);
        int v=velocidad;
        try {
            int recorrido=0;
            while(recorrido<100){
                TimeUnit.MILLISECONDS.sleep(500);
                recorrido+=v;
                if(recorrido>=110){
                    System.out.println("HA PERDIDO EL JUGADOR "+dorsal+" DEL EQUIPO "+equipo+" PORQUE SE HA PASADO");
                    ganado=true;
                }else{
                    if(recorrido<100){
                        v-=(int)(Math.random()*7);
                        if(v<10){
                            v=10;
                        }
                    }else{
                        System.out.println("JUGADOR "+dorsal+" DEL EQUIPO "+equipo+" HA LLEGADO AL PAÑUELO");
                        race.getNinyosCarrera().await(2,TimeUnit.SECONDS);
                        vuelta();
                    }
                }
            }
        } catch (InterruptedException ex) {
            ganado=true;
        } catch (BrokenBarrierException ex) {
            
        } catch (TimeoutException ex) {
            
        }
    }
    
    @Override
    public void run() {
        race.jugar(this);
        while(!ganado){
            try{
                race.getLock().lock();
                race.getArbitroLevanta().countDown();
                if(race.getArbitroLevanta().getCount()==0){
                    TimeUnit.MILLISECONDS.sleep(1500);
                    race.getReferee().signalAll();
                }
                meToca.await();
                race.getLock().unlock();
                corre();
            } catch (InterruptedException ex) {
                ganado=true;
                child.interrupt();
            }
        }
    }

    public void desintegrar(){
        ganado=true;
        race.getLock().lock();
        meToca.signalAll();
        race.getLock().unlock();
        child.interrupt();
    }
    
    @Override
    public String toString() {
        return "Niño "+ nombre + ", lugar=" + lug +
                ", velocidad=" + velocidad + ", dorsal="+dorsal+"\n";
    }

    void setLugarActual(Lugares lugares) {
        lug=lugares;
    }
    
}
