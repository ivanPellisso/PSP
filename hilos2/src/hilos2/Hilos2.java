/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos2;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author dam2
 */
public class Hilos2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        StringBuffer sb=new StringBuffer();
//        StringBuffer out=new StringBuffer();
//        Scanner lector=new Scanner(System.in);
//        /*
//        Thread hilo=new Thread(new MiHilo(1,sb,out,"1"));
//        hilo.start();
//        hilo=new Thread(new MiHilo(2,sb,out,"2"));
//        hilo.start();
//        hilo=new Thread(new MiHilo(3,sb,out,"3"));
//        hilo.start();
//        System.out.println("gsgrgfs");
//                
//        
//        Thread hilo=new Thread(new MiHilo(1,sb,out,"1",t));
//        Thread hilo1=new Thread(new MiHilo(2,sb,out,"2",t));
//        Thread hilo2=new Thread(new MiHilo(3,sb,out,"3",t));
//        
//        hilo.start();
//        hilo1.start();
//        hilo2.start();
//        */
//        Turno t=new Turno();
//        t.setTurno(1);
//        
//        int numHilos=0;
//        ExecutorService exe=null;
//        
//        do{
//            try{
//                System.out.println("Introduce número máximo de hilos:");
//                numHilos=lector.nextInt();
//                exe=Executors.newFixedThreadPool(numHilos);
//
//                for(int i=0;i<=numHilos;i++){
//                    MiHilo mh=new MiHilo(i, sb, out, i+"", t);
//                    exe.execute(mh);
//                }
//            }catch(InputMismatchException e){
//                System.out.println(e.getMessage());
//                lector.nextLine();
//            }
//        }while(numHilos<1);
//        
//        exe.shutdown();
//        try{
//            TimeUnit.SECONDS.sleep(1);
//        }catch(InterruptedException e){
//            System.out.println(e.getMessage());
//            
//        }
//        
//        System.out.println("buffer "+sb.toString());
//        System.out.println("out "+out.toString());
//        System.out.println("turno "+t.toString());
        boolean b=false;
        System.out.println(!b);
       
    }
    
}
