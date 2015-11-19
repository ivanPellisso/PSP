/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carrerachapas;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author dam2
 */
public class Pista {
    private CyclicBarrier barrier;
    private Lock lock;
    private ArrayList<Condition> turnos;
    private ArrayList<Ninyo> parties;
    private int largo;

    public Pista(){
        largo=40;
        turnos=new ArrayList();
        parties=new ArrayList();
        lock=new ReentrantLock(true);
        for(int i=0;i<3;i++){
            turnos.add(lock.newCondition());
        }
        barrier=new CyclicBarrier(3,new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<parties.size();i++){
                    parties.get(i).setMyTurn(turnos.get(i));
                    parties.get(i).setNextTurn(turnos.get((i+1)%turnos.size()));
                }
            }
        });
        for(int i=0;i<3;i++){
            new Ninyo(i,this);
        }
        
    }
    
    public void entrarPista(Ninyo n){
        boolean entrado=false;
        //ESTO SE HACE AQUÍ PARA QUE SE SIGA UN ORDEN DE LLEGADA 
        //DE LOS NIÑOS A LA PISTA
        for(int i=0;i<3;i++){
            parties.add(n);
        }
        try {
            if(parties.size()<barrier.getParties()){
                n.setLugarActual(Lugar.PISTA);
                entrado=true;
                barrier.await();
                turnos.get(0).signalAll();
            }
        } catch (InterruptedException ex) {
            parties.clear();
            turnos.clear();
            barrier.reset();
        } catch (BrokenBarrierException ex) {
        
        }
    }
    
    public void cerrarPista(){
        parties.clear();
        turnos.clear();
        barrier.reset();
    }
    //método entrarPista() -> turnos.get(0).signalAll();
    
    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
