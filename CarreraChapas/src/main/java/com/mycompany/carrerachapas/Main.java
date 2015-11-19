/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carrerachapas;

import java.util.Scanner;

/**
 *
 * @author IvPR
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner lector=new Scanner(System.in);
        Pista p=new Pista();
        Ninyo n1=new Ninyo(1,p);
        Ninyo n2=new Ninyo(2,p);
        Ninyo n3=new Ninyo(3,p);
        p.entrarPista(n1);
        p.entrarPista(n2);
        p.entrarPista(n3);
    }
    
}
