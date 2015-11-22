/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.examenpsp_ivanpr;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

/**
 *
 * @author dam2
 */
public class Arbitro implements Runnable{
    private Condition play;
    private ArrayList<Integer> numeros;
    private ArrayList<Condition> dorsales;
    private int random;
    private Thread ref;
    private Carrera race;
    
    public Arbitro(Carrera c, Condition juega, ArrayList<Condition> numPlayers, ArrayList<Integer> numerosJ){
        play=juega;
        dorsales=numPlayers;
        race=c;
        numeros=new ArrayList();
        ref=new Thread(this);
        ref.start();
        ref.setName("Arbi");
    }

    public Thread getThread() {
        return ref;
    }

    public void setThread(Thread a) {
        this.ref = a;
    }

    public ArrayList<Integer> getNumeros() {
        return numeros;
    }

    public void setNumeros(ArrayList<Integer> numeros) {
        this.numeros = numeros;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }              

    public Condition getPlay() {
        return play;
    }

    public void setPlay(Condition play) {
        this.play = play;
    }
    
    public void expulsarArbitro(){
        ref.interrupt();
    }
    
    @Override
    public void run() {
        while(numeros.size()<5){
            race.getLock().lock();
            try {
                play.await();
                int num=0;
                do{
                    num=(int)(Math.random()*5);
                }while(numeros.contains(num));
                System.out.println("ARBI DICE NÚMERO "+num);
                numeros.add(num);
                dorsales.get(num).signalAll();
                System.out.println("SALEN JUGADORES CON NÚMERO "+num);
            } catch (InterruptedException ex) {
                dorsales.clear();
                ref.interrupt();
            }finally{
                race.getLock().unlock();
            }
        }
        if(race.getPartiesT1().size()>race.getPartiesT2().size()){
            System.out.println("Gana Equipo 1");
        }else{
            System.out.println("Gana Equipo 2");
        }
    }
}
