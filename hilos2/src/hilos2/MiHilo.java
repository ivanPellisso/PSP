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
public class MiHilo implements Runnable{
    private int id;
    private StringBuffer sb;
    private String tx;
    private StringBuffer out;
    private Turno turno;

    public MiHilo(int id, StringBuffer sb,StringBuffer out,String tx, Turno turno) {
        this.id = id;
        this.sb=sb;
        this.tx=tx;
        this.out=out;
        this.turno=turno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        //System.out.println("Hola mundo"+id+sb.toString());
        boolean hecho=false;
        synchronized(sb){
            try{
                while(!hecho){
                    if(turno.getTurno()==id){//Si el turno es igual al id, imprime e incrementa el turno
                        sb.append(tx);
                        System.out.println("Hola mundo"+turno+sb.toString());
                        turno.setTurno(id+1);
                        hecho=true;
                        sb.notifyAll();//Avisar a los demás procesos de que miren a ver si pueden entrar ya
                    }else{
                        sb.wait();
                    }
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        //out.append("Hola mundo").append(id).append(sb.toString()).append("\n");
        
    }


    
}
