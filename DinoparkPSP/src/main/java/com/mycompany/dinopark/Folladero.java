/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinopark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author compaq
 */
public class Folladero {
    private List<SalasFolladero> salas;
    private Habitat hab;
    
    public Folladero(Habitat habi){
        this.hab=habi;
        salas=Collections.synchronizedList(new ArrayList());
        for(int i=0;i<2;i++){
            salas.add(new SalasFolladero(hab,i));
        }
    }
    
    public void entrar(Dinosaurio d){
        for(SalasFolladero sala: salas){
            if(sala.entrar(d)){
                break;
            }
        }
    }

    public List<SalasFolladero> getSalas() {
        return salas;
    }
    
    public void setSalas(List<SalasFolladero> salas) {
        this.salas = salas;
    }

    public Habitat getHab() {
        return hab;
    }

    public void setHab(Habitat hab) {
        this.hab = hab;
    }

    void resetPicadero() {
        for(int i=0;i<salas.size();i++){
            salas.get(i).resetSala();
        }
        salas.clear();
    }
}
