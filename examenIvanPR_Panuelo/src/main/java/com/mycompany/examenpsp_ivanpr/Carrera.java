/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.examenpsp_ivanpr;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author dam2
 */
public class Carrera {
    private Lock lock;
    private CyclicBarrier barrier;
    private CyclicBarrier ninyosCarrera;
    private CountDownLatch arbitroLevanta;
    private Condition referee;
    private ArrayList<Condition> dorsales;
    private ArrayList<Integer> numeros;
    private ArrayList<Ninyo> partiesT1;
    private ArrayList<Ninyo> partiesT2;
    private ArrayList<Ninyo> jugadores;
    private Arbitro arbi;
    private StringBuilder ganador;
    /**
     * 
     */
    
    public Carrera(){
        ganador=new StringBuilder();
        numeros=new ArrayList();
        partiesT1=new ArrayList();
        partiesT2=new ArrayList();
        lock=new ReentrantLock();
        referee=lock.newCondition();
        jugadores=new ArrayList();
        dorsales=new ArrayList();
        for(int i=0;i<5;i++){
            dorsales.add(lock.newCondition());
        }
        ninyosCarrera=new CyclicBarrier(2);
        arbitroLevanta=new CountDownLatch(10);
        barrier=new CyclicBarrier(10,new Runnable() {

            @Override
            public void run() {
                int j=0;
                for(int i=0;i<10;i++){
                    if(i%2==0){
                        jugadores.get(i).setMeToca(dorsales.get(j));
                        jugadores.get(i).setDorsal(j);
                        partiesT1.add(jugadores.get(i));
                        partiesT1.get(j).setEquipo(1);
                        numeros.add(j);
                    }else{
                        jugadores.get(i).setMeToca(dorsales.get(j));
                        jugadores.get(i).setDorsal(j);
                        partiesT2.add(jugadores.get(i));
                        partiesT2.get(j).setEquipo(2);
                        numeros.add(j);
                        j++;
                    }
                }
            }
        });
        arbi=new Arbitro(this,referee,dorsales, numeros);
        for(int i=0;i<10;i++){
            new Ninyo(i,this);
        }
    }
    
    public void jugar(Ninyo n){
        try{
            synchronized(jugadores){
                jugadores.add(n);
            }
            barrier.await();
        } catch (InterruptedException ex) {
            arbi.getNumeros().clear();
            barrier.reset();
        } catch (BrokenBarrierException ex) {
            
        }
    }
    
    public String muestraNinyos(){
        StringBuilder cadena=new StringBuilder();
        cadena.append("Equipo 1:\n");
        for(Ninyo n1:partiesT1){
            cadena.append(n1.toString()).append("\n");
        }
        cadena.append("Equipo 2:\n");
        for(Ninyo n2:partiesT2){
            cadena.append(n2.toString()).append("\n");
        }
        return cadena.toString();
    }

    public void addChild(Ninyo n){
        jugadores.add(n);//Gestión del niño que gana 
        if(jugadores.size()==2){
            //Aquí se quitan del array a los niños que hayan perdido la carrera
            for(int i=0;i<jugadores.size();i++){
                if(!n.equals(jugadores.get(i))){
                    partiesT1.remove(n);
                    partiesT2.remove(n);
                }
            }
            
            ninyosCarrera.reset();
            jugadores.clear();
        }
    }
    
    public void cancelarJuego(){
        barrier.reset();
        ninyosCarrera.reset();
        arbitroLevanta.countDown();
        for(Ninyo child:jugadores){
            child.desintegrar();
        }
        arbi.expulsarArbitro();
    }
    
    public StringBuilder getGanador() {
        return ganador;
    }

    public void setGanador(StringBuilder ganador) {
        this.ganador = ganador;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public CyclicBarrier getNinyosCarrera() {
        return ninyosCarrera;
    }

    public void setNinyosCarrera(CyclicBarrier ninyosCarrera) {
        this.ninyosCarrera = ninyosCarrera;
    }

    public Condition getReferee() {
        return referee;
    }

    public void setReferee(Condition referee) {
        this.referee = referee;
    }

    public ArrayList<Condition> getDorsales() {
        return dorsales;
    }

    public void setDorsales(ArrayList<Condition> dorsales) {
        this.dorsales = dorsales;
    }

    public ArrayList<Ninyo> getPartiesT1() {
        return partiesT1;
    }

    public void setPartiesT1(ArrayList<Ninyo> partiesT1) {
        this.partiesT1 = partiesT1;
    }

    public ArrayList<Ninyo> getPartiesT2() {
        return partiesT2;
    }

    public void setPartiesT2(ArrayList<Ninyo> partiesT2) {
        this.partiesT2 = partiesT2;
    }

    public ArrayList<Ninyo> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Ninyo> jugadores) {
        this.jugadores = jugadores;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Arbitro getArbi() {
        return arbi;
    }

    public void setArbi(Arbitro arbi) {
        this.arbi = arbi;
    }

    public CountDownLatch getArbitroLevanta() {
        return arbitroLevanta;
    }

    public void setArbitroLevanta(CountDownLatch arbitroLevanta) {
        this.arbitroLevanta = arbitroLevanta;
    }
    
    public String whoIsTheWinner(){
        return ganador.toString();
    }
}
