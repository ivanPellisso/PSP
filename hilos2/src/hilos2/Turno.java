/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos2;

/**
 *
 * @author dam2
 */
public class Turno {
    private int turno;

    public Turno(int turno) {
        this.turno = turno;
    }

    public Turno() {
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
        //this.turno %=numHilos;
    }

    @Override
    public String toString() {
        return turno+"";
    }
    
    
}
