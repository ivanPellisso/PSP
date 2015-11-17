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
 * @author dam2
 */
public class Bosque {
    private List<Arbol> arboles;
    private Habitat hab;
    
    public Bosque(Habitat habi){
        this.hab=habi;
        arboles=Collections.synchronizedList(new ArrayList());
        for(int i=0;i<5;i++){
            arboles.add(new Arbol(hab,i));
        }
    }
    
    public void entrar(Dinosaurio d){
        for(Arbol tree: arboles){
            if(tree.entrar(d)){
                break;
            }
        }
    }
    
    void resetBosque() {
        for (Arbol a : arboles) {
            a = null;
        }
    }

    public List<Arbol> getArboles() {
        return arboles;
    }

    
    public void setArboles(List<Arbol> arboles) {
        this.arboles = arboles;
    }

    public Habitat getHab() {
        return hab;
    }

    public void setHab(Habitat hab) {
        this.hab = hab;
    }
    
    
}
