/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.examenpsp_ivanpr;

import java.util.Scanner;

/**
 *
 * @author dam2
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner lector=new Scanner(System.in);
        Carrera race=new Carrera();
        int op=0;
        do{
            System.out.println("Introduce opción\n\t1. Ver Niños\n\t2. Finalizar partida");
            op=lector.nextInt();
            switch(op){
                case 1:
                    System.out.println(race.muestraNinyos());
                    break;
                case 2:
                    race.cancelarJuego();
                    break;
            }
        }while(op!=2);
        
    }
    
}
