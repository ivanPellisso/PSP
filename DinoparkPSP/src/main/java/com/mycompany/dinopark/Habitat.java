/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinopark;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author dam2
 */
public class Habitat implements Runnable{
    private ArrayList<Dinosaurio> dinosVivos;
    private ArrayList<Dinosaurio> dinosMuertos;
    private boolean para=false;
    private ExecutorService exe;
    private VicenteCalderon estadio;
    private Folladero picadero;
    private Restaurante rest;
    private Bosque forest;
    private Thread bang;
    private boolean meteoLanzado;
    
    public Habitat(){
        dinosVivos=new ArrayList();
        dinosMuertos=new ArrayList();
        exe=Executors.newFixedThreadPool(11);
        estadio=new VicenteCalderon();
        rest=new Restaurante();
        picadero=new Folladero(this);
        forest=new Bosque(this);
        bang=new Thread(this);
        bang.start();
        bang.setName("Habitat");
        meteoLanzado=false;
    }
    
    public Habitat(ArrayList<Dinosaurio> dinosVivos,ArrayList<Dinosaurio> dinosMuertos,ExecutorService exe){
        this.dinosVivos=dinosVivos;
        this.dinosMuertos=dinosMuertos;
        this.exe=exe;
    }

    public ArrayList<Dinosaurio> getDinosVivos() {
        return dinosVivos;
    }

    public void setDinosVivos(ArrayList<Dinosaurio> dinosVivos) {
        this.dinosVivos = dinosVivos;
    }

    public String toStringDinosMuertos() {
        StringBuilder cadena=new StringBuilder();
        for(int i=0;i<dinosMuertos.size();i++){
            cadena.append(dinosMuertos.get(i).toString()).append("\n");
        }
        return cadena.toString();
    }

    public void setDinosMuertos(ArrayList<Dinosaurio> dinosMuertos) {
        this.dinosMuertos = dinosMuertos;
    }
    
    public String toStringDinosVivos() {
        StringBuilder cadena=new StringBuilder();
        for(Dinosaurio dino: dinosVivos){
            cadena.append(dino.toString()).append("\n");
        }
        return cadena.toString();
    }

    public Restaurante getRestaurante() {
        return rest;
    }

    public void setRestaurante(Restaurante rest) {
        this.rest = rest;
    }

    public VicenteCalderon getEstadio() {
        return estadio;
    }

    public void setEstadio(VicenteCalderon estadio) {
        this.estadio = estadio;
    }

    public Folladero getPicadero() {
        return picadero;
    }

    public void setPicadero(Folladero picadero) {
        this.picadero = picadero;
    }

    public boolean isPara() {
        return para;
    }

    public void setPara(boolean para) {
        this.para = para;
    }

    public ExecutorService getExe() {
        return exe;
    }

    public void setExe(ExecutorService exe) {
        this.exe = exe;
    }
    
    public void addDino(Dinosaurio saurio){
        if(!meteoLanzado){
            synchronized(dinosVivos){
                dinosVivos.add(saurio);
            }
        }
    }
    
    public void lanzaMeteorito(){
        bang.interrupt();
        rest.cerrarRestaurante();
        estadio.resetStadium();
        picadero.resetPicadero();
        forest.resetBosque();
        meteoLanzado=true;
    }
    
    public void bigBang(){
        bang=new Thread(this);
        bang.start();
        for(int i=0;i<10;i++){
            dinosVivos.add(new Dinosaurio(i+"",this));
            exe.execute(dinosVivos.get(i));
        }
        /*
            for que recorra num de dinos que se crean al iniciar y los añada al arrayList de dinos. Despúes, execute dinos.get(i)
        */
    }
    
    public void entrarEstadio(Dinosaurio d){
        estadio.entrar(d);
    }
    
    public void entrarRestaurante(Dinosaurio d){
        rest.entrar(d);
    }
    
    public void entrarFolladero(Dinosaurio d){
        picadero.entrar(d);
    }
    
    public void entrarBosque(Dinosaurio d) {
        forest.entrar(d);
    }

    @Override
    public void run() {
        while(!para){
            Dinosaurio dinoMuerto=null;
            //sincronizamos dinosVivos para que sólo habitat en ese momento pueda bajarle la vida
            try { 
                TimeUnit.MILLISECONDS.sleep(2000);
                synchronized(dinosVivos){
                    for(Dinosaurio dino: dinosVivos){
                        dino.aumentaHambre();
                        dino.restaVida();
                        dino.aumentaEdad();
                        if(dino.getVida()<=0){
                            dinoMuerto=dino;
                            dinosMuertos.add(dinoMuerto);
                        }
                    }
                    dinosVivos.removeAll(dinosMuertos);
                }
            } catch (InterruptedException ex) {
                para=true;
            }
        }
        estadio.resetStadium();
        rest.cerrarRestaurante();
        forest.resetBosque();
        for(Dinosaurio dino: dinosVivos){
            dino.muereDino();
        }
        //dinos=null;
        exe.shutdown();
    }

    

}
