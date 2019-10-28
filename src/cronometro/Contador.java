/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cronometro;

import javax.swing.JTextField;

/**
 *
 * @author Diego da Silva Parra - 10716550 e Mateus Fernandes Doimo - 10691971
 */
public class Contador implements Runnable{
    
    private final JTextField mil;
    private final JTextField seg;
    private final JTextField min;

    private long inicio;
    private long total;
    private long milissegundo;
    private long segundo;
    private long minuto;
    private int hora;
    private long aux;
    private long aux2;
    
    private boolean cont;
    private boolean zero;
    private boolean pause;

    public Contador(JTextField mil,JTextField seg,JTextField min) {
        this.inicio = 0;
        this.total = 0;
        this.aux = 0;
        this.aux2 = 0;
        this.mil = mil;
        this.seg = seg;
        this.min = min;
        
        this.cont = true;
        this.zero = true;
        this.pause = false;
        
    }
    
    @Override
    public void run() {
        inicio = System.currentTimeMillis();
        try {

            while(cont) {
                //zera o cronometro
                if(zero){
                    zerar();
                    zero = false;
                }
                //verificar se o cronometro foi pausado
                if(!pause){
                    total = System.currentTimeMillis() - inicio + aux2;
                    aux = total;

                    //Milissegundos do total
                    milissegundo = total % 1000;
                    //Convertemos o total para segundos
                    total = total / 1000;

                    //Segundos do total
                    segundo = total % 60;
                    //Convertemos o total para minutos
                    total /= 60;

                    //Minutos em 1 hora
                    minuto = total % 60;

                    //Total para horas
                    total /= 60;
                    hora = (int)total;
                    
                    //caso passe 1h zera o cronometro
                    if(hora == 1){
                        this.zero = true;
                    }                    
                }
                else{
                    aux2 = aux; //salva ultimo estado caso pause o cronomtro
                    inicio = System.currentTimeMillis(); 
                }
                
                //seta as caixas de texto com o tempo
                mil.setText(completaComZero(milissegundo));
                seg.setText(completaComZero(segundo));
                min.setText(completaComZero(minuto));
                Thread.sleep(1);
           }
        } 
        catch (InterruptedException ex) {
           //ex.printStackTrace();
        }
    }

    //completa com zero a esquerda
    private String completaComZero(Long i) {
        String retorno = null;
        if( i < 10 ) {
            retorno = "0"+i;
        } else {
            retorno = i.toString();
        }
        return retorno;
    } 
    
    //zera cronometro
    private void zerar(){
        milissegundo = 0;
        segundo = 0;
        minuto = 0;
        hora = 0;
        total = 0;
        aux2 = 0;
        aux = 0;
    }

    public boolean isCont() {
        return cont;
    }

    public void setCont(boolean cont) {
        this.cont = cont;
    }

    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
    
}
