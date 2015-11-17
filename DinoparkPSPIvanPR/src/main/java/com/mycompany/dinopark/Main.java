/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dinopark;

import java.util.InputMismatchException;
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
        int opcion=0;
        boolean esNum=false;
        do{
            
            do{
                try{
                    System.out.println("Introduce opción:\n\t1. Big Bang\n\t2. Lista dinosaurios vivos\n\t3. Lista dinosaurios muertos\n"
                    + "\t4. Listado en estadio\n\t5. Dinos en Picadero\n\t6. Dinos en Bosque\n\t7. Lanzar meteorito");
                    esNum=true;
                    opcion=lector.nextInt();
                }catch(InputMismatchException  ex){
                    System.out.println("Debes introducir un número entero.");
                    esNum=false;
                    lector.nextLine();
                }
            }while(!esNum);
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
                    for(int i=0;i<5;i++){
                        System.out.println(hab.getForest().getArboles().get(i).toString());
                    }
                    break;
                case 7: 
                    hab.lanzaMeteorito();
                    System.out.println("Sa matao Paco");
                    break;
            }
        }while(opcion!=7);
    }
    
}
