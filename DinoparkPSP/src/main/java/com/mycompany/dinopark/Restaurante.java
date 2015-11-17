/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinopark;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam2
 */
public class Restaurante implements Runnable{
    private ArrayBlockingQueue<Object> mesas;
    private boolean para=false;
    private Thread rest;
    
    public Restaurante(){
        mesas=new ArrayBlockingQueue<>(10, true);//True para FIFO
        rest=new Thread(this);
        rest.start();
        rest.setName("Restaurante");
        
    }

    public void cerrarRestaurante(){
        rest.interrupt();
    }
    
    public ArrayBlockingQueue<Object> getMesas() {
        return mesas;
    }

    public void setMesas(ArrayBlockingQueue<Object> mesas) {
        this.mesas = mesas;
    }

    public boolean isPara() {
        return para;
    }

    public void setPara(boolean para) {
        this.para = para;
    }

    public Thread getRest() {
        return rest;
    }

    public void setRest(Thread rest) {
        this.rest = rest;
    }
    
    public void entrar(Dinosaurio dino){
        try {
            if(mesas.poll(500, TimeUnit.MILLISECONDS)!=null){
                dino.setLugarActual(Lugares.RESTAURANTE);
                dino.restaHambre();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while(!para){
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                mesas.put(new Object());
            } catch (InterruptedException ex) {
                Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
