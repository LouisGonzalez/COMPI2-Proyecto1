/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import interfaz.Inicio;
import seccion_3.ExpresionesRegulares;

/**
 *
 * @author luisGonzalez
 */
public class main {
    
    public static void main(String[] args){
        Inicio inicio = new Inicio();
        ExpresionesRegulares b = new ExpresionesRegulares();
        b.prueva();
        inicio.setVisible(true);
    }
    
}
