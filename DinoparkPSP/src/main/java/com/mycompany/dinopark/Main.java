/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinopark;

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
        Habitat hab=new Habitat();
        int opcion;
        do{
            System.out.println("Introduce opción:\n\t1. Big Bang\n\t2. Lista dinosaurios vivos\n\t3. Lista dinosaurios muertos\n\t4. Listado en estadio\n\t5. Dinos en Picadero\n\t6. Lanzar meteorito");
            opcion=lector.nextInt();
            switch(opcion){
                case 1:
                    hab.bigBang();
                    break;
                case 2:
                    System.out.println(hab.toStringDinosVivos());
                    break;
                case 3:
                    System.out.println(hab.toStringDinosMuertos());
                    break;
                case 4:
                    System.out.println(hab.getEstadio().dinosEnEstadio());
                    break;
                case 5:
                    for(int i=0;i<2;i++){
                        System.out.println(hab.getPicadero().getSalas().get(i).toString());
                    }
                    break;
                case 6: 
                    hab.lanzaMeteorito();
                    System.out.println("Sa matao Paco");
                    break;
            }
        }while(opcion!=6);
    }
    
}
